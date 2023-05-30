package com.example.diploma.ui.vacations

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.diploma.R
import com.example.diploma.databinding.FragmentVacationBinding
import java.time.ZoneId
import java.util.*

class VacationFragment : Fragment() {
    private var _binding: FragmentVacationBinding? = null
    private val viewModel: VacationViewModel by viewModels()
    private var isStartSelected = true

    private lateinit var start : Calendar
    private lateinit var end : Calendar
    private var type = ""

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_vacation)

        binding.vacationButtonSendRequest.setOnClickListener { onSendClicked() }

        binding.vacationStartDateEditText.setOnClickListener {
            isStartSelected = true
            showDatePickerDialog()
        }

        binding.vacationEndDateEditText.setOnClickListener {
            isStartSelected = false
            showDatePickerDialog()
        }

        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.leave_type_dropdown,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.vacationSpinner.adapter = spinnerAdapter
        binding.vacationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                type = when (position) {
                    1 -> "vacation"
                    2 -> "paternity"
                    3 -> "maternity"
                    else -> "sick_leave"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) { }
        }
    }

    private fun onSendClicked(){
        if (!::start.isInitialized){
            showAlertDialog(R.string.pick_starting_date)
        } else if (!::end.isInitialized){
            showAlertDialog(R.string.pick_ending_date)
        }

        val startDate = start.toInstant().atZone(start.timeZone.toZoneId()).toLocalDate()
        val endDate = end.toInstant().atZone(end.timeZone.toZoneId()).toLocalDate()

        Log.i("Vacation", "Sent a request for $startDate to $endDate, type $type")
        //viewModel.sendRequest(startDate, endDate, "")
    }

    private fun showAlertDialog(@StringRes message: Int){
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.error)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showDatePickerDialog() {
        // Stop if clicked on end date, w/o selecting start
        if (!isStartSelected && !::start.isInitialized){
            showAlertDialog(R.string.pick_starting_date)
            return
        }

        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, 1)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, y, m, d ->
                val cal = Calendar.getInstance()
                cal.set(y,m,d)

                val selectedDate = "$d/${m+1}/$y"

                Log.i("Dashboard Date","Picked = $selectedDate")

                if (isStartSelected) {
                    binding.vacationStartDateEditText.setText(selectedDate)
                    start = cal.clone() as Calendar
                } else {
                    binding.vacationEndDateEditText.setText(selectedDate)
                    end = cal.clone() as Calendar
                }
            },
            c[Calendar.YEAR], c[Calendar.MONTH], c[Calendar.DAY_OF_MONTH]
        )

        if (isStartSelected)
            datePickerDialog.datePicker.minDate = c.timeInMillis
        else {
            val s: Calendar = start.clone() as Calendar
            s.add(Calendar.DAY_OF_MONTH, 1)
            datePickerDialog.datePicker.minDate = s.timeInMillis
        }

        datePickerDialog.show()
    }
}