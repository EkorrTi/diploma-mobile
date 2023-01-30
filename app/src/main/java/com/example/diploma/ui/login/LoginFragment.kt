package com.example.diploma.ui.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.diploma.CFASApplication
import com.example.diploma.database.DatabaseViewModel
import com.example.diploma.database.DatabaseViewModelFactory
import com.example.diploma.databinding.FragmentLoginBinding
import com.example.diploma.network.models.BearerToken
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()
    private val databaseViewModel: DatabaseViewModel by activityViewModels {
        DatabaseViewModelFactory(
            (activity?.application as CFASApplication).database.cfasDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.loginButton.setOnClickListener {
            // HIDE KEYBOARD
            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view.windowToken, 0)

            binding.loginProgressBar.visibility = View.VISIBLE
            try {
                loginViewModel.login(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            } catch (e: SocketTimeoutException) {
                binding.loginProgressBar.visibility = View.GONE
                binding.loginTimeoutErrorTextview.visibility = View.VISIBLE
            }
        }

        loginViewModel.token.observe(viewLifecycleOwner) {
            databaseViewModel.updateToken(it)
            val action = LoginFragmentDirections.actionNavigationLoginToNavigationHome()
            findNavController().navigate(action)
        }

        binding.skipButton.setOnClickListener {
            val action = LoginFragmentDirections.actionNavigationLoginToNavigationHome()
            findNavController().navigate(action)
        }
    }
}