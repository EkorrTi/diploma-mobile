package com.example.diploma.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.models.Worker

@SuppressLint("NotifyDataSetChanged")
class ContactsRecyclerViewAdapter : RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsRecyclerViewHolder>() {
    var onClick:((Worker) -> Unit)? = null
    var data: List<Worker> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsRecyclerViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_contacts, parent, false)
        return ContactsRecyclerViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ContactsRecyclerViewHolder, position: Int) {
        val person = data[position]
        // insert text for name - role
        holder.contactsName.apply {
            text = resources.getString(
                R.string.contacts_name,
                person.firstName,
                person.lastName,
                person.specialization //TODO handle specialization
            )
        }
        holder.contactsEmail.text = person.email
        // insert text for phone
        holder.contactsPhone.text = person.phone
        holder.itemView.setOnClickListener { onClick?.invoke(person) }
    }

    override fun getItemCount(): Int = data.size

    class ContactsRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contactsName: TextView = view.findViewById(R.id.contacts_item_name)
        val contactsEmail: TextView = view.findViewById(R.id.contacts_item_email)
        val contactsPhone: TextView = view.findViewById(R.id.contacts_item_phone)
    }
}