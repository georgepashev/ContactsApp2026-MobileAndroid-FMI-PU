# Walkthrough - Database Version 2 Migration

Updated the Room database schema to version 2, incorporating Category ID 0 `"Uncategorized"`.

## Changes

### Data Layer
- **[ContactsDatabase.kt](file:///C:/Users/fmi/AndroidStudioProjects/ContactsApp/app/src/main/java/bg/uni_plovdiv/fmi/contactsapp/data/ContactsDatabase.kt)**:
  - Bumped database version to `2`.
  - Added `MIGRATION_1_2` migration to insert `(id = 0, name = 'Uncategorized')` into `contacts_cat`.
  - Added `.addMigrations(MIGRATION_1_2)` to database builder.
  - Included `Category(id = 0, name = "Uncategorized")` in initial categories creation callback for fresh installs.

## Verification Results

### Automated Tests
- Executed `app:assembleDebug` via Gradle: **Build finished successfully.**
