package com.example.diploma.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diploma.R
import com.example.diploma.adapters.ContactsRecyclerViewAdapter
import com.example.diploma.databinding.FragmentContactsBinding
import com.example.diploma.models.Worker

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val viewModel: ContactsViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.get()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ContactsRecyclerViewAdapter()
        val recyclerView = binding.contactsRecyclerView

        viewModel.response.observe(viewLifecycleOwner) {
            // put data into the adapter and set it after getting the response
            adapter.data = it
            adapter.onClick = { worker ->
                findNavController()
                    .navigate(
                        R.id.action_navigation_contacts_to_profileFragment,
                        bundleOf( Pair("worker", worker) )
                    )
            }
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                this.adapter = adapter
            }

            // hide the loading icon and show the list
            binding.contactsProgressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            binding.contactsSwipeRefreshLayout.isRefreshing = false
        }

        binding.contactsSwipeRefreshLayout.setOnRefreshListener { viewModel.get() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}