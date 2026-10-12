package bg.uni_plovdiv.fmi.contactsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import bg.uni_plovdiv.fmi.contactsapp.data.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM contacts_cat")
    fun getAllCategories(): Flow<List<Category>>

    @Insert
    suspend fun insertAll(categories: List<Category>)

    @Insert
    suspend fun insert(category: Category)
}
