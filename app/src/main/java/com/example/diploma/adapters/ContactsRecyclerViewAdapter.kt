package com.example.diploma.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.models.Worker

class ContactsRecyclerViewAdapter : RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsRecyclerViewHolder>() {
    var data: List<Worker> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsRecyclerViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_contacts_item, parent, false)
        return ContactsRecyclerViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ContactsRecyclerViewHolder, position: Int) {
        val person = data[position]
        // insert text for name - role
        holder.contactsName.text = "${person.firstName} ${person.lastName} - ${person.roleId}"
        // insert text for phone
        holder.contactsPhone.text = person.phone
    }

    override fun getItemCount(): Int = data.size

    class ContactsRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contactsName: TextView = view.findViewById(R.id.contacts_item_name)
        val contactsPhone: TextView = view.findViewById(R.id.contacts_item_phone)
    }
}