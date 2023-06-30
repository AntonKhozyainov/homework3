package ru.khozyainov.homework3

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.khozyainov.homework3.databinding.ActivityMainBinding
import ru.khozyainov.homework3.databinding.DialogContactBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var contactAdapter: ContactAdapter

    private val contactService: ContactService
        get() = (applicationContext as App).contactService

    private val contactListener: (contacts: List<Contact>) -> Unit = {
        contactAdapter.items = it
    }

    private val itemTouchHelper by lazy {
        val itemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, 0) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    val fromPosition = viewHolder.bindingAdapterPosition
                    val toPosition = target.bindingAdapterPosition
                    contactService.moveContact(fromPosition, toPosition)
                    contactAdapter.notifyItemMoved(fromPosition, toPosition)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
            }
        ItemTouchHelper(itemTouchCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initList()
        setListeners()
    }

    override fun onDestroy() {
        contactService.removeListener(contactListener)
        super.onDestroy()
    }

    private fun setListeners() {
        contactService.addListener(contactListener)

        binding.addContactFloatingActionButton.setOnClickListener {
            showContactDialog()
        }

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.deleteButton) {
                contactService.deleteContacts()
                return@setOnMenuItemClickListener true
            }
            return@setOnMenuItemClickListener false
        }
    }

    private fun initList() {

        contactAdapter = ContactAdapter(
            checkClickedListener = { contactService.setCheckContact(it) },
            editClickedListener = ::showContactDialog
        )

        itemTouchHelper.attachToRecyclerView(binding.contactRecyclerView)

        binding.contactRecyclerView.run {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(ItemOffsetDecoration(this@MainActivity, 4))
            (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        }
    }

    private fun showContactDialog(contact: Contact? = null) {
        val dialogBinding = DialogContactBinding.inflate(layoutInflater)

        val id = if (contact != null) {
            dialogBinding.contactPhoneNumberEditText.setText(contact.phoneNumber)
            dialogBinding.contactFirstNameEditText.setText(contact.firstName)
            dialogBinding.contactLastNameEditText.setText(contact.lastName)
            contact.id
        } else Random.nextLong()


        val dialog = AlertDialog.Builder(this).setTitle(getString(R.string.item_id, id))
            .setView(dialogBinding.root).setPositiveButton(R.string.done, null)
            .setNegativeButton(R.string.cancel) { _, _ -> }.create()

        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val updatePhoneNumber = dialogBinding.contactPhoneNumberEditText.text.toString()
                if (updatePhoneNumber.isBlank()) {
                    dialogBinding.contactPhoneNumberEditText.error = getString(R.string.empty_value)
                    return@setOnClickListener
                }
                val updateFirstName = dialogBinding.contactFirstNameEditText.text.toString()
                if (updateFirstName.isBlank()) {
                    dialogBinding.contactFirstNameEditText.error = getString(R.string.empty_value)
                    return@setOnClickListener
                }
                val updateLastName = dialogBinding.contactLastNameEditText.text.toString()
                if (updateLastName.isBlank()) {
                    dialogBinding.contactLastNameEditText.error = getString(R.string.empty_value)
                    return@setOnClickListener
                }

                if (contact != null) {
                    val updateContact = contact.copy(
                        phoneNumber = updatePhoneNumber,
                        firstName = updateFirstName,
                        lastName = updateLastName
                    )
                    contactService.updateContact(updateContact)
                } else {
                    val newContact = Contact(
                        id = id,
                        phoneNumber = updatePhoneNumber,
                        firstName = updateFirstName,
                        lastName = updateLastName
                    )
                    contactService.addNewContact(newContact) { position ->
                        binding.contactRecyclerView.smoothScrollToPosition(position)
                    }
                }
                dialog.dismiss()
            }
        }

        dialog.show()
    }


}