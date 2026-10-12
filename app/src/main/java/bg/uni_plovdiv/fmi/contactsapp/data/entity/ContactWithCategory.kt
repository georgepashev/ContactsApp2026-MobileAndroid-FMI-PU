package bg.uni_plovdiv.fmi.contactsapp.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ContactWithCategory(
    @Embedded val contact: Contact,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: Category
)
