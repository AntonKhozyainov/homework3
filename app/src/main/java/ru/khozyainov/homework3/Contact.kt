package ru.khozyainov.homework3

data class Contact(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val isChecked: Boolean = false
)
