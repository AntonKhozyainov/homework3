package ru.khozyainov.homework3

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.khozyainov.homework3.databinding.ItemContactBinding

class ContactAdapter(
    checkClickedListener: (Contact) -> Unit,
    editClickedListener: (Contact) -> Unit
) : AsyncListDifferDelegationAdapter<Contact>(ContactDiffUtilCallback()) {

    init {
        val adapterDelegate = getContactAdapterDelegate(
            checkClickedListener = checkClickedListener,
            editClickedListener = editClickedListener
        )

        delegatesManager.addDelegate(adapterDelegate)
    }

    private fun getContactAdapterDelegate(
        checkClickedListener: (Contact) -> Unit,
        editClickedListener: (Contact) -> Unit
    ) = adapterDelegateViewBinding<Contact, Contact, ItemContactBinding>(
            viewBinding = {
                    layoutInflater,
                    root -> ItemContactBinding.inflate(layoutInflater, root, false)
            }
        ) {

            with(binding) {
                contactCheckImageButton.setOnClickListener {
                    checkClickedListener(item)
                }

                editContactButton.setOnClickListener {
                    editClickedListener(item)
                }
            }

            bind {
                with(binding) {
                    contactIdTextView.text = getString(R.string.item_id, item.id)
                    contactPhoneNumberTextView.text = item.phoneNumber
                    contactFirstNameTextView.text = item.firstName
                    contactLastNameTextView.text = item.lastName

                    contactCheckImageButton.isChecked = item.isChecked
                }
            }
        }

    class ContactDiffUtilCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem == newItem

    }
}