package com.example.diploma.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.ApiService
import com.example.diploma.network.ApiServiceObject
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private var _response = MutableLiveData<Int>()
    val response : LiveData<Int> = _response

    fun getProductionStatus(){
        viewModelScope.launch {
            try {
                _response.value = ApiServiceObject.retrofitService.getProductionStatus()
            } catch (e: Exception) { Log.w("API GET", e.toString()) }
        }
    }
}