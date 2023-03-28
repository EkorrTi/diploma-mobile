package com.example.diploma.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diploma.R
import com.example.diploma.databinding.FragmentHomeBinding
import com.example.diploma.network.SecuredLoginRequest
import com.example.diploma.network.gson
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.fcmToken(
            requireActivity()
                .getSharedPreferences(
                    getString(R.string.app_name),
                    Context.MODE_PRIVATE
                )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO schedule list recycler or normal list?
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}