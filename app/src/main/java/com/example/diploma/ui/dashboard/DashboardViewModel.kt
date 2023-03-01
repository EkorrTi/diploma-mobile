package com.example.diploma.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.ApiServiceObject
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date

class DashboardViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

    var start: LocalDate = LocalDate.now()
    var end: LocalDate = LocalDate.now()

    fun sendRequest(){
        if (start.compareTo(LocalDate.now()) < 1 || end.compareTo(start) < 1 )
            throw Exception("Select the correct dates please") //TODO handle the Exception

        viewModelScope.launch {
            try {
                _response.value = ApiServiceObject.retrofitService.postRequest(start, end)
                Log.i("API POST Request", "request successful, response: ${_response.value.toString()}")
            } catch (e: Exception) { Log.w("API POST Request", e.toString()) }
        }
    }
}