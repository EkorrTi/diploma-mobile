package com.example.diploma.ui.dashboard

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.diploma.R
import com.example.diploma.databinding.FragmentDashboardBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DashboardFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    //TODO separate 2 request types

    private var _binding: FragmentDashboardBinding? = null
    private val viewModel: DashboardViewModel by viewModels()
    private var isStartSelected = true

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        binding.dashboardButtonSendRequest.setOnClickListener {
            viewModel.sendRequest()
        }
        //TODO better way?

        viewModel.response.observe(viewLifecycleOwner) {
            binding.dashboardTextview.text = it
        }

        binding.dashboardStartDateTextview.setOnClickListener {
            isStartSelected = true
            showDatePickerDialog()
        }

        binding.dashboardEndDateTextview.setOnClickListener {
            isStartSelected = false
            showDatePickerDialog()
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(view: android.widget.DatePicker?, y: Int, m: Int, d: Int) {
        val cal = Calendar.getInstance()
        cal.set(y, m, d)
        val dateString = SimpleDateFormat("yyyy-MM-dd").format(cal.time)
        val date = LocalDate.parse(dateString)

        Log.i("Dashboard Date","Picked = $y-$m-$d \\ $date")

        if (isStartSelected) {
            if ( !date.isAfter(LocalDate.now()) && !date.isBefore(viewModel.end) ) {
                Toast.makeText(context, "Incorrect date", Toast.LENGTH_SHORT).show()
                return
                //TODO("Show error")
            }

            binding.dashboardStartDateTextview.text = getString(R.string.start_date, dateString)
            viewModel.start = date
        } else {
            if ( !date.isAfter(viewModel.start) ) {
                Toast.makeText(context, "Incorrect date", Toast.LENGTH_SHORT).show()
                return
                //TODO("Show error")
            }

            binding.dashboardEndDateTextview.text = getString(R.string.end_date, dateString)
            viewModel.end = date
        }
    }

    private fun showDatePickerDialog(){
        val newFragment = DatePicker(this)
        newFragment.show(parentFragmentManager, "datePicker")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}