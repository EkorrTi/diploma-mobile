package com.example.diploma.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.ApiService
import com.example.diploma.network.ApiServiceObject
import com.example.diploma.network.models.RequestResponse
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val TAG = "Dashboard ViewModel"

class DashboardViewModel : ViewModel() {
    private var _responseProduction = MutableLiveData<Int>()
    val responseProduction : LiveData<Int> = _responseProduction

    private var _responseRequests = MutableLiveData<List<RequestResponse>>()
    val responseRequests : LiveData<List<RequestResponse>> = _responseRequests

    fun getProductionStatus(){
        viewModelScope.launch {
            try {
                val resp: Double = ApiServiceObject.retrofitService.getProductionStatus()
                Log.i(TAG, resp.toString())
                _responseProduction.value = resp.roundToInt()
            } catch (e: Exception) { Log.w(TAG, e.toString()) }
        }
    }

    fun getRequestsList(){
        viewModelScope.launch {
            try {
                _responseRequests.value = ApiServiceObject.retrofitService.getRequestStatus()
                Log.i(TAG, responseRequests.value.toString())
            } catch (e: Exception) { Log.w(TAG, e.toString()) }
        }
    }
}