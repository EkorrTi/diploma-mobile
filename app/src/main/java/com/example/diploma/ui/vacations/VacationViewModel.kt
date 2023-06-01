package com.example.diploma.ui.vacations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.ApiServiceObject
import com.example.diploma.network.models.RequestResponse
import com.example.diploma.network.models.VacationRequest
import kotlinx.coroutines.launch
import java.time.LocalDate

class VacationViewModel : ViewModel() {
    private val _response = MutableLiveData<RequestResponse>()
    val response: LiveData<RequestResponse> = _response

    fun sendRequest(start: LocalDate, end: LocalDate, type: String){
        viewModelScope.launch {
            try {
                Log.i("API POST VacationRequest", "send data: ${VacationRequest(type, start.toString(), end.toString())}")

                _response.value = ApiServiceObject.retrofitService
                    .postVacationRequest( VacationRequest(type, start.toString(), end.toString()) )

                Log.i("API POST VacationRequest", "request successful, response: ${_response.value.toString()}")
            } catch (e: Exception) { Log.w("API POST Request", e.toString()) }
        }
    }
}