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
//        if (data.isEmpty()){
//            holder.requestDetails.apply { text = resources.getString(R.string.no_requests) }
//            return
//        }
        // insert text for type and time
        holder.requestDetails.text = "Vacation - 02/01/2002 -> 09/01/2002"
        // insert text for status
        holder.requestStatus.text = "Approved - 25/12/2001"
        // TODO Set the image depending on the status of the request
        if (true)
            holder.requestImage.setImageResource(R.drawable.ic_baseline_check_24)
        else
            holder.requestImage.setImageResource(R.drawable.ic_baseline_close_24)
    }

    override fun getItemCount(): Int {
        if (data.isEmpty()) return 2
        return data.size
    }

    class RequestsRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val requestDetails: TextView = view.findViewById(R.id.request_item_details)
        val requestStatus: TextView = view.findViewById(R.id.request_item_status)
        val requestImage: ImageView = view.findViewById(R.id.request_item_image)
    }
}