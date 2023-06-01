package com.example.diploma.ui.login

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.ApiServiceObject
import com.example.diploma.network.UnsecureLoginRequest
import com.example.diploma.network.models.BearerToken
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val TAG = "LoginViewModel"
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginState = _loginState.asStateFlow()
    private var fcmToken = ""

    sealed class LoginState {
        object Empty : LoginState()
        object Loading : LoginState()
        data class Success(val result: BearerToken) : LoginState()
        data class Error(val error: Throwable) : LoginState()
    }

    fun fcmToken( sp: SharedPreferences){
        FirebaseMessaging.getInstance().token.addOnCompleteListener{ task ->
            if (task.isSuccessful){
                val cachedToken = sp.getString("fcm_token", "")
                val token = task.result
                Log.d(TAG, "FCM Token: $token      Cached token: $cachedToken")

                if (cachedToken != token) {
                    sp.edit {
                        putString("fcm_token", token)
                        commit()
                    }
                }
                fcmToken = token
            } else {
                Log.e(TAG, task.exception.toString())
            }
        }
    }

    /**
     * Logs the user with given credentials to the server, and gets a Bearer token
     * @param username Email
     * @param password Password
     */
    fun login(username: String, password: String){
        viewModelScope.launch {
            val unsecureLoginRequest = UnsecureLoginRequest(username, password, fcmToken)
            Log.i("API Login", "Sent data: $unsecureLoginRequest")

            try {
                Log.i("API Login", "login started")
                _loginState.value = LoginState.Loading
                _loginState.value = LoginState.Success(
                    ApiServiceObject.retrofitService.unsecureLogin(unsecureLoginRequest)
                )

                ApiServiceObject.token = (loginState.value as LoginState.Success).result.id
                ApiServiceObject.role = (loginState.value as LoginState.Success).result.specialization

                Log.i("API Login", "ApiServiceObject.token = ${ApiServiceObject.token}")
            } catch (e: Exception) {
                Log.e("API Login", e.toString())
                _loginState.value = LoginState.Error(e)
            }
        }
    }

    /**
     * Encodes user credentials before sending
     * @param username Email
     * @param password Password
     * @return [SecuredLoginRequest] with given username & password
     * */
    /*private fun encodedRequest(username: String, password: String): SecuredLoginRequest{
        fun encode(value: String): List<Byte> {
            if (value.isEmpty()) { throw Error("Cannot encode empty string") }

            val byteArray: MutableList<Byte> = value.toByteArray().toMutableList()

            // First Round
            if ( byteArray.size % 2 == 0 ) byteArray.add(-1)

            // Second Round
            val newByteArray: MutableList<Byte> = mutableListOf()
            val size = byteArray.size-1
            val middle = byteArray.size / 2
            for (i in middle..size) newByteArray.add( byteArray[i] )
            for (i in 0..middle) newByteArray.add( byteArray[i] )
            byteArray.clear()
            byteArray.addAll(newByteArray)

            //  Third Round
            newByteArray.clear()

            for (i in 0..size) {
                val byte = byteArray[i]

                if (byte % 2 == 0)
                    newByteArray.add( (byte/2+i).toByte() )
                else {
                    newByteArray.add( -2 )
                    newByteArray.add( ((byte+i) * 2).toByte() )
                }
            }

            return byteArray
        }

        return SecuredLoginRequest(
            encode(username),
            encode(password)
        )
    }*/
}