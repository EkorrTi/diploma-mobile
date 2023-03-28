package com.example.diploma.ui.login

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.diploma.CFASApplication
import com.example.diploma.R
import com.example.diploma.database.DatabaseViewModel
import com.example.diploma.database.DatabaseViewModelFactory
import com.example.diploma.databinding.FragmentLoginBinding
import com.example.diploma.ui.login.LoginViewModel.LoginState
import kotlinx.coroutines.launch

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
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_login)

        binding.loginButton.setOnClickListener {
            // HIDE KEYBOARD
            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view.windowToken, 0)

            // Check for empty credentials
            if (binding.emailEditText.text.toString().isBlank() || binding.passwordEditText.text.toString().isBlank()){
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.error)
                    .setMessage(R.string.error_credentials)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok) {dialog, _ -> dialog.dismiss()}
                    .show()
                return@setOnClickListener
            }

            loginViewModel.login(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        // Dynamically update UI and respond to changes to request status
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                loginViewModel.loginState.collect { state ->
                    binding.loginProgressBar.isGone = state !is LoginState.Loading

                    when (state) {
                        is LoginState.Error -> AlertDialog.Builder(requireContext())
                            .setTitle(R.string.error)
                            .setMessage(
                                when (state.error.message){
                                    "timeout" -> getString(R.string.error_timeout)
                                    else -> state.error.message
                                }
                            )
                            .setCancelable(false)
                            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                            .show()

                        is LoginState.Success -> {
                            databaseViewModel.updateToken(state.result)
                            navigateHome()
                        }

                        else -> Unit
                    }
                }
            }
        }

        binding.skipButton.setOnClickListener { navigateHome() }
    }

    private fun navigateHome() {
        val action = LoginFragmentDirections.actionNavigationLoginToNavigationHome()
        findNavController().navigate(action)
    }
}