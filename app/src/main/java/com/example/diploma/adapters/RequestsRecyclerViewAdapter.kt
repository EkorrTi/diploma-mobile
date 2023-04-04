package com.example.diploma.adapters

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.models.Worker

// TODO: Change appropriately to Requests
// TODO: Make request class

@SuppressLint("NotifyDataSetChanged")
class RequestsRecyclerViewAdapter : RecyclerView.Adapter<RequestsRecyclerViewAdapter.RequestsRecyclerViewHolder>() {
    var data: List<Worker> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestsRecyclerViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_requests, parent, false)
        return RequestsRecyclerViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: RequestsRecyclerViewHolder, position: Int) {
        // insert text for name - role
        holder.requestDetails.text = "Vacation - 02/01/2002 -> 09/01/2002"
        //"${person.firstName} ${person.lastName} - ${person.roleId}"
        // insert text for phone
        holder.requestStatus.text = "Approved - 25/12/2001"
    }

    override fun getItemCount(): Int = 5

    class RequestsRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val requestDetails: TextView = view.findViewById(R.id.request_item_details)
        val requestStatus: TextView = view.findViewById(R.id.request_item_status)
    }
}