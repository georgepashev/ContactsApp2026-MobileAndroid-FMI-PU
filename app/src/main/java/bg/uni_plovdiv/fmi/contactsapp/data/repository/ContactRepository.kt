package bg.uni_plovdiv.fmi.contactsapp.data.repository

import bg.uni_plovdiv.fmi.contactsapp.data.dao.CategoryDao
import bg.uni_plovdiv.fmi.contactsapp.data.dao.ContactDao
import bg.uni_plovdiv.fmi.contactsapp.data.entity.Category
import bg.uni_plovdiv.fmi.contactsapp.data.entity.Contact
import bg.uni_plovdiv.fmi.contactsapp.data.entity.ContactWithCategory
import kotlinx.coroutines.flow.Flow

class ContactRepository(
    private val contactDao: ContactDao,
    private val categoryDao: CategoryDao
) {
    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()
    val allContacts: Flow<List<ContactWithCategory>> = contactDao.getAllContacts()

    fun filterContacts(filter: ContactFilter): Flow<List<ContactWithCategory>> {
        return contactDao.filter(filter.nameQuery, filter.categoryId)
    }

    suspend fun insertContact(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun insertCategory(category: Category) {
        categoryDao.insert(category)
    }
}
