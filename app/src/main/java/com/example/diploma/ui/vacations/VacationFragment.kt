package com.example.diploma.ui.vacations

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacationBinding.inflate(inflater, container, false)
        setHasOptionsMenu(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_vacation)

        binding.vacationButtonSendRequest.setOnClickListener {
            if (!::start.isInitialized){
                showAlertDialog(R.string.pick_starting_date)
            } else if (!::end.isInitialized){
                showAlertDialog(R.string.pick_ending_date)
            }

            //val zoneId = ZoneId.systemDefault() // TODO this doesn't work
            //val startDate = start.toInstant().atZone(zoneId).toLocalDate()
            //val endDate = end.toInstant().atZone(zoneId).toLocalDate()

//            Log.i("Vacation", "Sent a request for $startDate to $endDate")
            Log.i("Vacation", "Sent a request")
            //viewModel.sendRequest(startDate, endDate, "")
        }

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


/*    class DatePicker( private val listener: DatePickerDialog.OnDateSetListener, private val isStartSelected: Boolean ): DialogFragment() {
//        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//            val c = Calendar.getInstance()
//            c.add(Calendar.DAY_OF_MONTH, 1)
//            val datePickerDialog = DatePickerDialog(
//                requireContext(),
//                listener,
//                c.get(Calendar.YEAR),
//                c.get(Calendar.MONTH),
//                c.get(Calendar.DAY_OF_MONTH)
//            )
//
//            if (isStartSelected)
//                datePickerDialog.datePicker.minDate = c.timeInMillis
//            else
//                datePickerDialog.datePicker.minDate =
//            // TODO set maxDate according to days left
//            return datePickerDialog
//        }
//    } */
}