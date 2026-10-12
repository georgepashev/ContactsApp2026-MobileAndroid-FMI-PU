package bg.uni_plovdiv.fmi.contactsapp.ui.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bg.uni_plovdiv.fmi.contactsapp.R
import bg.uni_plovdiv.fmi.contactsapp.data.entity.Category
import bg.uni_plovdiv.fmi.contactsapp.viewmodel.ContactViewModel

class SelectContactsFragment : Fragment() {

    private val viewModel: ContactViewModel by viewModels()
    private lateinit var contactAdapter: ContactAdapter
    private var categoriesList: List<Category> = emptyList()
    private var selectedCategoryId: Long? = null
    private var nameQuery: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewContacts)
        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)
        val categorySpinner = view.findViewById<Spinner>(R.id.filterCategorySpinner)

        contactAdapter = ContactAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = contactAdapter

        viewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            categoriesList = categories
            val categoryNames = mutableListOf("All Categories")
            categoryNames.addAll(categories.map { it.name })
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categoryNames
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
        }

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCategoryId = if (position == 0) null else categoriesList[position - 1].id
                updateFilter()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        searchEditText.addTextChangedListener { text ->
            nameQuery = text?.toString()?.takeIf { it.isNotBlank() }
            updateFilter()
        }

        viewModel.filteredContacts.observe(viewLifecycleOwner) { contacts ->
            contactAdapter.submitList(contacts)
        }
    }

    private fun updateFilter() {
        viewModel.setFilter(nameQuery, selectedCategoryId)
    }
}
