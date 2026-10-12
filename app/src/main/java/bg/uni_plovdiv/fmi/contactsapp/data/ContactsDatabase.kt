package bg.uni_plovdiv.fmi.contactsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import bg.uni_plovdiv.fmi.contactsapp.data.dao.CategoryDao
import bg.uni_plovdiv.fmi.contactsapp.data.dao.ContactDao
import bg.uni_plovdiv.fmi.contactsapp.data.entity.Category
import bg.uni_plovdiv.fmi.contactsapp.data.entity.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Category::class, Contact::class], version = 2, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("INSERT OR IGNORE INTO contacts_cat (id, name) VALUES (0, 'Uncategorized')")
            }
        }

        fun getDatabase(context: Context): ContactsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java,
                    "contacts_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateInitialCategories(database.categoryDao())
                    }
                }
            }

            private suspend fun populateInitialCategories(dao: CategoryDao) {
                val categories = listOf(
                    Category(id = 0, name = "Uncategorized"),
                    Category(name = "Family"),
                    Category(name = "Friends"),
                    Category(name = "Work"),
                    Category(name = "School"),
                    Category(name = "College"),
                    Category(name = "Acquaintances"),
                    Category(name = "Neighbors"),
                    Category(name = "VIP"),
                    Category(name = "Emergency"),
                    Category(name = "Doctor"),
                    Category(name = "Dentist"),
                    Category(name = "Lawyer"),
                    Category(name = "Mechanic"),
                    Category(name = "Plumber"),
                    Category(name = "Electrician"),
                    Category(name = "Gym"),
                    Category(name = "Colleagues"),
                    Category(name = "Clients"),
                    Category(name = "Suppliers"),
                    Category(name = "Other")
                )
                dao.insertAll(categories)
            }
        }
    }
}
