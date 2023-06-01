package com.example.diploma.ui.team

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.diploma.R
import com.example.diploma.databinding.FragmentTeamBinding
import com.example.diploma.network.models.TeamRequest

class TeamFragment : Fragment() {
    private var _binding: FragmentTeamBinding? = null
    private val viewModel: TeamViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(false)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.teamChangeSendButton.setOnClickListener{ onSendClicked() }
        viewModel.response.observe(viewLifecycleOwner){
            if (it.id != null) { showAlertDialog(R.string.success, R.string.request_ok) }
            else { showAlertDialog(R.string.error_unknown) }
        }
    }

    private fun onSendClicked(){
        val uname = binding.teamChangeUsernameEditText.text.toString()
        val teamId = binding.teamChangeTeamIdEditText.text.toString()
        if ( uname.isBlank() || teamId.isBlank() ){
            showAlertDialog(R.string.error_not_filled)
            return
        }

        viewModel.sendRequest( TeamRequest(uname, teamId) )
    }
    private fun showAlertDialog(@StringRes message: Int) = showAlertDialog(R.string.error, message)
    private fun showAlertDialog(@StringRes title: Int, @StringRes message: Int){
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            .show()
    }
}