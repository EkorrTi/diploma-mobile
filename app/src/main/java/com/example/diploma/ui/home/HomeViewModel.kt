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

    fun fcmToken( sp: SharedPreferences ){
        FirebaseMessaging.getInstance().token.addOnCompleteListener{ task ->
            if (task.isSuccessful){
                val cachedToken = sp.getString("fcm_token", "")
                val token = task.result
                Log.d("HomeViewModel FCM", "FCM Token: $token      Cached token: $cachedToken")

                if (cachedToken != token) {
                    sp.edit {
                        putString("fcm_token", token)
                        commit()
                    }
                    sendFcmToken(token)
                }
            } else {
                Log.e(TAG, task.exception.toString())
            }
        }
    }

    private fun sendFcmToken(token: String){
        Log.i(TAG, "Sending the FCM token to server")
        viewModelScope.launch {
            try {
                ApiServiceObject.retrofitService.postFirebaseToken(token)
            } catch (e: Exception) {
                Log.w(TAG, e.toString())
            }
        }
    }
}