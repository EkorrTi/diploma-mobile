package com.example.diploma.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.diploma.databinding.FragmentTeamBinding

class TeamFragment : Fragment() {
    private var _binding: FragmentTeamBinding? = null
    private val viewModel: TeamViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        setHasOptionsMenu(false)
        return binding.root
    }
}