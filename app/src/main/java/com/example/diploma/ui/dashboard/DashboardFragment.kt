package com.example.diploma.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        // TODO request statuses as List


        binding.dashboardToVacationButton.setOnClickListener {
            val action = DashboardFragmentDirections.actionNavigationDashboardToVacationFragment()
            findNavController().navigate(action)
        }

        binding.dashboardToTeamButton.setOnClickListener {
            // TODO create team request fragment, add click listener
            //val action = DashboardFragmentDirections.actionNavigationDashboardToVacationFragment()
            //findNavController().navigate(action)
            binding.dashboardLinearProgress.progress = binding.dashboardLinearProgress.progress.inc()

            binding.dashboardProgressText.text = "${binding.dashboardLinearProgress.progress}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}