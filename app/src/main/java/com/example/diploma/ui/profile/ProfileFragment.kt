package com.example.diploma.ui.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.example.diploma.R
import com.example.diploma.databinding.FragmentProfileBinding
import com.example.diploma.models.Worker

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var worker: Worker

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(false)
        super.onCreate(savedInstanceState)
        arguments?.let { worker = it.getSerializable("worker") as Worker }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.profile)
        binding.profileName.text = "${worker.firstName} ${worker.lastName}"
        binding.profileRole.text = worker.specialization
        if (worker.email.isNotBlank()) binding.profileEmail.text = worker.email
        else binding.profileEmail.text = "None"
        binding.profilePhone.text = worker.phone
        binding.profileDepartment.text = getString(R.string.department, worker.department)
        binding.profileTeam.text = getString(R.string.team, worker.team)

        // Copy the email/phone to clipboard when clicked
        binding.profileEmail.setOnClickListener {
            requireContext().copyToClipboard( (it as TextView).text )
            Toast.makeText(requireContext(), R.string.email_copied, Toast.LENGTH_SHORT).show()
        }
        binding.profilePhone.setOnClickListener {
            requireContext().copyToClipboard( (it as TextView).text )
            Toast.makeText(requireContext(), R.string.phone_copied, Toast.LENGTH_SHORT).show()
        }
    }

    private fun Context.copyToClipboard(text: CharSequence){
        val clipboard = getSystemService(this, ClipboardManager::class.java)
        clipboard?.setPrimaryClip(ClipData.newPlainText("",text))
    }
}