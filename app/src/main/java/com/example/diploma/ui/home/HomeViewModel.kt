package com.example.diploma.ui.home

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.ApiService
import com.example.diploma.network.ApiServiceObject
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import java.lang.Exception
private const val TAG = "Home ViewModel"
class HomeViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _response = MutableLiveData<String>()
    // The external immutable LiveData for the request status
    val text: LiveData<String> = _response

    fun get(){
        viewModelScope.launch {
            try {
//                _response.value = ApiServiceObject.retrofitService.postLogin()
                Log.i(TAG, "got GET request")
            } catch (e: Exception) { Log.w(TAG, e.toString()) }
        }
    }


}