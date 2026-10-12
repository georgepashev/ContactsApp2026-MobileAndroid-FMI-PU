package bg.uni_plovdiv.fmi.contactsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import bg.uni_plovdiv.fmi.contactsapp.data.ContactsDatabase
import bg.uni_plovdiv.fmi.contactsapp.data.entity.Category
import bg.uni_plovdiv.fmi.contactsapp.data.entity.Contact
import bg.uni_plovdiv.fmi.contactsapp.data.entity.ContactWithCategory
import bg.uni_plovdiv.fmi.contactsapp.data.repository.ContactFilter
import bg.uni_plovdiv.fmi.contactsapp.data.repository.ContactRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ContactRepository
    val allCategories: LiveData<List<Category>>
    val allContacts: LiveData<List<ContactWithCategory>>

    private val _filter = MutableStateFlow(ContactFilter())

    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredContacts: LiveData<List<ContactWithCategory>> = _filter
        .flatMapLatest { filter -> repository.filterContacts(filter) }
        .asLiveData()

    init {
        val contactDao = ContactsDatabase.getDatabase(application).contactDao()
        val categoryDao = ContactsDatabase.getDatabase(application).categoryDao()
        repository = ContactRepository(contactDao, categoryDao)
        allCategories = repository.allCategories.asLiveData()
        allContacts = repository.allContacts.asLiveData()
    }

    fun setFilter(nameQuery: String?, categoryId: Long?) {
        _filter.value = ContactFilter(nameQuery, categoryId)
    }

    fun insertContact(contact: Contact) = viewModelScope.launch {
        repository.insertContact(contact)
    }
}
