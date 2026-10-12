package bg.uni_plovdiv.fmi.contactsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import bg.uni_plovdiv.fmi.contactsapp.data.entity.Contact
import bg.uni_plovdiv.fmi.contactsapp.data.entity.ContactWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<ContactWithCategory>>

    @Query("""
        SELECT * FROM contacts 
        WHERE (:nameQuery IS NULL OR name LIKE '%' || :nameQuery || '%')
        AND (:categoryId IS NULL OR categoryId = :categoryId)
    """)
    fun filter(nameQuery: String?, categoryId: Long?): Flow<List<ContactWithCategory>>

    @Insert
    suspend fun insert(contact: Contact)
}
