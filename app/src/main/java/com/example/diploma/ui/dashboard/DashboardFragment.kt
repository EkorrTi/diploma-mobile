package com.example.diploma.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diploma.R
import com.example.diploma.adapters.RequestsRecyclerViewAdapter
import com.example.diploma.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(){
    private var _binding: FragmentDashboardBinding? = null
    private val viewModel: DashboardViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getProductionStatus()

        // TODO request statuses as List
        val adapter = RequestsRecyclerViewAdapter()
        binding.dashboardRequestStatusRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        viewModel.responseProduction.observe(viewLifecycleOwner){
            binding.dashboardProgressText.text = getString(R.string.percentage_text, it)
            binding.dashboardLinearProgress.progress = it
            binding.dashboardProgressBar.isGone = true
        }

        // TODO Change layout if user is manager
        if (true) {
            binding.dashboardManagerOnlyGroup.isVisible = true

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.root)
            constraintSet.connect(
                binding.dashboardRequestStatusRecyclerView.id,  ConstraintSet.TOP,
                binding.dashboardLinearProgress.id,             ConstraintSet.BOTTOM
            )
            constraintSet.applyTo( binding.root )
        }

        binding.dashboardToLeaveRequestButton.setOnClickListener{
            val action = DashboardFragmentDirections.actionNavigationDashboardToVacationFragment()
            findNavController().navigate(action)
        }

        binding.dashboardToTeamRequestButton.setOnClickListener {
            val action = DashboardFragmentDirections.actionNavigationDashboardToTeamFragment()
            findNavController().navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}