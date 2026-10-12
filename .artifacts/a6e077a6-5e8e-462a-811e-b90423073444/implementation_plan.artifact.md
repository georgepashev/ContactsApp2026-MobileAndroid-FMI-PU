# Implementation Plan - Database Version 2 with Category ID 0 ("Uncategorized")

Update the Room database to version 2, adding Category ID 0 with the name `"Uncategorized"` via a Room migration (`MIGRATION_1_2`) and fresh database creation callback.

## User Review Required

> [!IMPORTANT]
> - Database version will be bumped from `1` to `2`.
> - A migration `MIGRATION_1_2` will insert `(id = 0, name = 'Uncategorized')` into `contacts_cat`.
> - Fresh installations will also include Category ID 0 `"Uncategorized"`.

## Proposed Changes

### Data Layer
#### [MODIFY] [ContactsDatabase.kt](file:///C:/Users/fmi/AndroidStudioProjects/ContactsApp/app/src/main/java/bg/uni_plovdiv/fmi/contactsapp/data/ContactsDatabase.kt)
- Update `@Database` version to `2`.
- Define `MIGRATION_1_2` inserting `(0, 'Uncategorized')`.
- Add `.addMigrations(MIGRATION_1_2)` to Room database builder.
- Include Category ID 0 `"Uncategorized"` in initial category population for fresh installs.

## Verification Plan

### Automated Tests
- Run Gradle build (`app:assembleDebug`) to verify migration and Room database schema compilation.
