package com.example.diploma.ui.vacations

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.diploma.R
import com.example.diploma.databinding.FragmentDashboardBinding
import com.example.diploma.databinding.FragmentVacationBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class VacationFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private var _binding: FragmentVacationBinding? = null
    private val viewModel: VacationViewModel by viewModels()
    private var isStartSelected = true

    private var start: LocalDate = LocalDate.now()
    private var end: LocalDate = LocalDate.now()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_vacation)

        binding.vacationButtonSendRequest.setOnClickListener {
            if (start.compareTo(LocalDate.now()) < 1 ||  // Should be after today
                end.compareTo(start) < 1 // Should be after start
            ) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.error)
                    .setMessage(R.string.error_invalid_date)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                    .show()
                return@setOnClickListener
            }

            val type = when (binding.vacationTypeOptions.checkedRadioButtonId) {
                R.id.option_sickleave -> "sick"
                else -> "vacation"
            }

            viewModel.sendRequest(start, end, type)
        }

        //TODO better way?
        binding.vacationStartDateTextview.setOnClickListener {
            isStartSelected = true
            showDatePickerDialog()
        }

        binding.vacationEndDateTextview.setOnClickListener {
            isStartSelected = false
            showDatePickerDialog()
        }
    }

    class DatePicker( private val listener: DatePickerDialog.OnDateSetListener ): DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()

            return DatePickerDialog(
                requireContext(),
                listener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
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
            if ( !date.isAfter(LocalDate.now()) && !date.isBefore(end) ) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.error)
                    .setMessage(R.string.error_invalid_date)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                    .show()
                return
            }

            binding.vacationStartDateTextview.text = getString(R.string.start_date, dateString)
            start = date
        } else {
            if ( !date.isAfter(start) ) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.error)
                    .setMessage(R.string.error_invalid_date)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                    .show()
                return
            }

            binding.vacationEndDateTextview.text = getString(R.string.end_date, dateString)
            end = date
        }
    }

    private fun showDatePickerDialog() = DatePicker(this).show(parentFragmentManager, "datePicker")
}