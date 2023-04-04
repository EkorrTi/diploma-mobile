package com.example.diploma.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.ApiService
import com.example.diploma.network.ApiServiceObject
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val TAG = "Dashboard ViewModel"

class DashboardViewModel : ViewModel() {
    private var _responseProduction = MutableLiveData<Int>()
    val responseProduction : LiveData<Int> = _responseProduction

    private var _responseRequests = MutableLiveData<List<Int>>()
    val responseRequests : LiveData<List<Int>> = _responseRequests

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
                ApiServiceObject.retrofitService.getRequestStatus()
            } catch (e: Exception) { Log.w(TAG, e.toString()) }
        }
    }
}