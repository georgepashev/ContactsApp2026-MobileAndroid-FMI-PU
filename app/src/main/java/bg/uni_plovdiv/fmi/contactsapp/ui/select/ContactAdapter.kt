package bg.uni_plovdiv.fmi.contactsapp.ui.select

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bg.uni_plovdiv.fmi.contactsapp.R
import bg.uni_plovdiv.fmi.contactsapp.data.entity.ContactWithCategory

class ContactAdapter : ListAdapter<ContactWithCategory, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textContactName)
        private val phoneTextView: TextView = itemView.findViewById(R.id.textContactPhone)
        private val emailTextView: TextView = itemView.findViewById(R.id.textContactEmail)
        private val categoryTextView: TextView = itemView.findViewById(R.id.textContactCategory)

        fun bind(item: ContactWithCategory) {
            nameTextView.text = item.contact.name
            phoneTextView.text = item.contact.phone
            emailTextView.text = item.contact.email
            categoryTextView.text = item.category.name
        }
    }

    class ContactDiffCallback : DiffUtil.ItemCallback<ContactWithCategory>() {
        override fun areItemsTheSame(oldItem: ContactWithCategory, newItem: ContactWithCategory): Boolean {
            return oldItem.contact.id == newItem.contact.id
        }

        override fun areContentsTheSame(oldItem: ContactWithCategory, newItem: ContactWithCategory): Boolean {
            return oldItem == newItem
        }
    }
}
