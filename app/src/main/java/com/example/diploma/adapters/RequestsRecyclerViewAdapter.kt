package com.example.diploma.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.models.Worker
import com.example.diploma.network.models.RequestResponse

// TODO: Change appropriately to Requests
// TODO: Make request class

@SuppressLint("NotifyDataSetChanged")
class RequestsRecyclerViewAdapter : RecyclerView.Adapter<RequestsRecyclerViewAdapter.RequestsRecyclerViewHolder>() {
    var data: List<RequestResponse> = emptyList()
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
        if (data.isEmpty()){ // if no requests are in the list
            holder.requestDetails.apply { text = resources.getString(R.string.no_requests) }
            return
        }
        val item = data[position]
        // insert text for type and time
        holder.requestDetails.text = when (item.requestType){
            "VACANCY" -> "Vacation"
            "TEAM" -> "Team change"
            else -> "Unknown type"
        }
        // insert text for status
        holder.requestStatus.text = item.status


        // Set the image depending on the status of the request
        when (item.status) {
            "PENDING" -> { holder.requestImage.setImageResource(R.drawable.ic_baseline_hourglass_empty_24) }
            "APPROVED" -> { holder.requestImage.setImageResource(R.drawable.ic_baseline_check_24) }
            else -> { holder.requestImage.setImageResource(R.drawable.ic_baseline_close_24) }
        }
    }

    override fun getItemCount(): Int = data.size

    class RequestsRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val requestDetails: TextView = view.findViewById(R.id.request_item_details)
        val requestStatus: TextView = view.findViewById(R.id.request_item_status)
        val requestImage: ImageView = view.findViewById(R.id.request_item_image)
    }
}