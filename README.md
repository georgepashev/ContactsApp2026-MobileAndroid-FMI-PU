# ContactsApp

An Android application developed in Kotlin using **Jetpack Room**, **KSP**, **ViewModel**, **LiveData**, and a responsive dual-fragment layout for managing and filtering contacts.

---

## 🚀 Features & Architecture

### 1. Data Layer (Room Database v2)
- **Entities**:
  - `Category` (`contacts_cat` table, supporting Category ID `0` `"Uncategorized"`).
  - `Contact` (foreign key relationship referencing `contacts_cat` with cascading deletes).
  - `ContactWithCategory` (Room relation combining Contact and Category data).
- **DAOs**:
  - `CategoryDao` for category operations and retrieval.
  - `ContactDao` featuring direct database-level filtering (`filter(nameQuery, categoryId)`).
- **Database & Migration**:
  - `ContactsDatabase` pre-populates initial categories on creation and includes `MIGRATION_1_2` for schema updates.
- **Repository**:
  - `ContactRepository` abstracting data sources and exposing reactive data streams (`Flow` / `LiveData`).
  - `ContactFilter` data class for search queries and category filtering.

### 2. Validation Layer
- `Validatable` interface featuring robust companion object regex patterns:
  - `EMAIL_REGEX`
  - `PHONE_REGEX`
- `ValidationRule` data class for validation status and error messages.

### 3. ViewModel Layer
- `ContactViewModel` handling UI state, observing repository data, and executing reactive filtering using Kotlin Coroutines (`flatMapLatest`) and LiveData.

### 4. UI Layer & Layouts
- **Fragments & Adapter**:
  - `InsertContactFragment`: Form for adding new contacts with inline validation and automatic form resetting upon successful save.
  - `SelectContactsFragment`: Search bar, category spinner filter, and RecyclerView with `ContactAdapter` (`ListAdapter` & `DiffUtil`).
  - `ContactAdapter`: RecyclerView adapter binding contact details and categories.
- **Responsive Layouts (Simultaneous Dual-Fragment View)**:
  - `activity_main.xml` (Portrait): Stacks `InsertContactFragment` and `SelectContactsFragment` vertically so both are visible simultaneously.
  - `activity_main.xml` (Landscape): Arranges both fragments side-by-side in a dual-pane layout.

---

## 🛠 Tech Stack & Dependencies
- **Language**: Kotlin
- **Persistence**: Android Room (v2.8.5) with KSP
- **Lifecycle & Architecture**: ViewModel, LiveData, Fragment KTX, Coroutines
- **UI**: Material Components, ConstraintLayout, RecyclerView
- **Min SDK**: 24
- **Compile/Target SDK**: 37
