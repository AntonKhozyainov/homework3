package ru.khozyainov.homework3

import java.util.Collections
import kotlin.random.Random

class ContactService {

    private var contactList = mutableListOf<Contact>()

    private val listeners = mutableSetOf<(contacts: List<Contact>) -> Unit>()

    init {
        contactList = getRandomContactList()
    }

    fun addNewContact(
        contact: Contact, positionCallback: (position: Int) -> Unit
    ) {
        contactList = (contactList + listOf(contact)).toMutableList()
        positionCallback(contactList.indexOf(contact))
        notifyChanges()
    }

    fun deleteContacts() {
        contactList = contactList.filter { !it.isChecked }.toMutableList()
        notifyChanges()
    }

    fun setCheckContact(contact: Contact) {
        val updateContact = contact.copy(isChecked = !contact.isChecked)
        updateContact(updateContact)
    }

    fun updateContact(contact: Contact) {
        val indexToReplace = contactList.indexOfFirst { it.id == contact.id }
        if (indexToReplace != -1) {
            contactList = ArrayList(contactList)
            contactList[indexToReplace] = contact
            notifyChanges()
        }
    }

    fun moveContact(fromPosition: Int, toPosition: Int) {
        Collections.swap(contactList, fromPosition, toPosition)
        contactList = ArrayList(contactList)
        notifyChanges()
    }

    fun addListener(listener: (contacts: List<Contact>) -> Unit) {
        listeners.add(listener)
        listener.invoke(contactList)
    }

    fun removeListener(listener: (contacts: List<Contact>) -> Unit) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(contactList) }
    }

    private fun getRandomContactList(): MutableList<Contact> =
        (0..150).map { getNewRandomContact() }.toMutableList()

    private fun getNewRandomContact(): Contact = Contact(
        id = Random.nextLong(),
        firstName = firstNameList.random(),
        lastName = lastNameList.random(),
        phoneNumber = getRandomPhoneNumber()
    )

    private fun getRandomPhoneNumber(): String =
        "+7(${Random.nextInt(900, 999)})${Random.nextInt(100, 999)}-" +
                "${Random.nextInt(10, 99)}-${Random.nextInt(10, 99)}"

    companion object {
        private val firstNameList = listOf(
            "Василий",
            "Петр",
            "Олег",
            "Игорь",
            "Артем",
            "Владимир",
            "Алексей",
            "Иван",
            "Александр",
            "Евгений"
        )

        private val lastNameList = listOf(
            "Иванов",
            "Петров",
            "Сидоров",
            "Курочкин",
            "Кульков",
            "Владимиров",
            "Литвинов",
            "Беленко",
            "Смирнов",
            "Соловьев"
        )
    }
}