package com.example.diploma.ui.vacations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.ApiServiceObject
import kotlinx.coroutines.launch
import java.time.LocalDate

class VacationViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

    fun sendRequest(start: LocalDate, end: LocalDate, type: String){
        viewModelScope.launch {
            try {
                _response.value = ApiServiceObject.retrofitService.postVacationRequest(start, end, type)
                Log.i("API POST Request", "request successful, response: ${_response.value.toString()}")
            } catch (e: Exception) { Log.w("API POST Request", e.toString()) }
        }
    }
}