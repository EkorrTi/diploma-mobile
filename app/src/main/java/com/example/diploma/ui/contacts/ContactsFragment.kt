package com.example.diploma.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.diploma.adapters.ContactsExpandableListAdapter
import com.example.diploma.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val viewModel: ContactsViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = ContactsExpandableListAdapter(requireContext())
        val expandableList: ExpandableListView = binding.contactsList

//        viewModel.response.observe(viewLifecycleOwner) {
//            adapter.listDetail = it.dataMap
//            expandableList.setAdapter( adapter )
//            binding.contactsProgressBar.visibility = View.GONE
//            expandableList.visibility = View.VISIBLE
//        }

        adapter.listDetail = viewModel.getData()
        expandableList.setAdapter( adapter )
//        expandableList.setOnGroupExpandListener {
//            Toast.makeText(context,
//                    listTitle[it] + "List Expanded",
//                    Toast.LENGTH_SHORT).show()
//        }
//        expandableList.setOnChildClickListener { expandableListView, view, groupPosition, childPosition, id ->
//            Toast.makeText(context,
//                listTitle[groupPosition] + " -> " + listDetail[ listTitle[groupPosition] ]!![childPosition],
//                Toast.LENGTH_SHORT).show()
//            true
//        }

        binding.contactsProgressBar.visibility = View.GONE
        expandableList.visibility = View.VISIBLE
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}