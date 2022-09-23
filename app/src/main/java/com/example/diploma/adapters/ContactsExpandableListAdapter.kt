package com.example.diploma.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.diploma.R

class ContactsExpandableListAdapter(
    val context: Context,
    val listTitle: List<String>,
    val listDetail: HashMap<String, List<String>>
): BaseExpandableListAdapter() {

    override fun getGroupCount(): Int { return listTitle.size }

    override fun getChildrenCount(p0: Int): Int { return listDetail[ listTitle[p0] ]!!.size }

    override fun getGroup(p0: Int): Any { return listTitle[p0] }

    override fun getChild(p0: Int, p1: Int): Any { return listDetail[ listTitle[p0] ]!![p1] }

    override fun getGroupId(p0: Int): Long { return p0.toLong() }

    override fun getChildId(p0: Int, p1: Int): Long { return p1.toLong() }

    override fun hasStableIds(): Boolean { return false }

    override fun getGroupView(listPos: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val title = getGroup(listPos) as String
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (convertView == null) {
            val view = inflater.inflate(R.layout.list_contacts_group, null)
            val titleTextView = view.findViewById<TextView>(R.id.contacts_title)
            titleTextView.setTypeface(null, Typeface.BOLD)
            titleTextView.text = title
            return view
        }

        val titleTextView = convertView.findViewById<TextView>(R.id.contacts_title)
        titleTextView.setTypeface(null, Typeface.BOLD)
        titleTextView.text = title
        return convertView
    }

    override fun getChildView(listPos: Int, expandedListPos: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val expandedListText = getChild(listPos, expandedListPos) as String
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (convertView == null) {
            val view = inflater.inflate(R.layout.list_contacts_item, null)
            val expandedItem = view.findViewById<TextView>(R.id.contacts_item)
            expandedItem?.text = expandedListText
            return view
        }

        val expandedItem = convertView.findViewById<TextView>(R.id.contacts_item)
        expandedItem.text = expandedListText
        return convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean { return true }
}