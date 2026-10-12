package bg.uni_plovdiv.fmi.contactsapp.validation

interface Validatable {
    fun validate(): List<ValidationRule>

    companion object {
        val EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        val PHONE_REGEX = "^\\+?[0-9 \\-\\.]{7,15}$".toRegex()
    }
}
