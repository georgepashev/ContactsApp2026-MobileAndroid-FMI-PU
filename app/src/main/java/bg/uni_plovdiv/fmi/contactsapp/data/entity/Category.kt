package bg.uni_plovdiv.fmi.contactsapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_cat")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)
