package bg.uni_plovdiv.fmi.contactsapp.ui.insert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import bg.uni_plovdiv.fmi.contactsapp.R
import bg.uni_plovdiv.fmi.contactsapp.data.entity.Category
import bg.uni_plovdiv.fmi.contactsapp.data.entity.Contact
import bg.uni_plovdiv.fmi.contactsapp.validation.Validatable
import bg.uni_plovdiv.fmi.contactsapp.viewmodel.ContactViewModel

class InsertContactFragment : Fragment() {

    private val viewModel: ContactViewModel by viewModels()
    private var categoriesList: List<Category> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_insert_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputName = view.findViewById<EditText>(R.id.inputName)
        val inputPhone = view.findViewById<EditText>(R.id.inputPhone)
        val inputEmail = view.findViewById<EditText>(R.id.inputEmail)
        val spinnerCategory = view.findViewById<Spinner>(R.id.spinnerCategory)
        val btnSave = view.findViewById<Button>(R.id.btnSaveContact)

        viewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            categoriesList = categories
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories.map { it.name }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter
        }

        btnSave.setOnClickListener {
            val name = inputName.text.toString().trim()
            val phone = inputPhone.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val selectedPosition = spinnerCategory.selectedItemPosition

            if (name.isEmpty()) {
                inputName.error = "Name is required"
                return@setOnClickListener
            }
            if (!Validatable.PHONE_REGEX.matches(phone)) {
                inputPhone.error = "Invalid phone format"
                return@setOnClickListener
            }
            if (!Validatable.EMAIL_REGEX.matches(email)) {
                inputEmail.error = "Invalid email format"
                return@setOnClickListener
            }
            if (categoriesList.isEmpty() || selectedPosition < 0) {
                Toast.makeText(requireContext(), "Select a category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val categoryId = categoriesList[selectedPosition].id
            val contact = Contact(name = name, phone = phone, email = email, categoryId = categoryId)
            viewModel.insertContact(contact)
            Toast.makeText(requireContext(), "Contact saved", Toast.LENGTH_SHORT).show()

            inputName.text.clear()
            inputPhone.text.clear()
            inputEmail.text.clear()
            if (categoriesList.isNotEmpty()) {
                spinnerCategory.setSelection(0)

            }
        }
    }
}
