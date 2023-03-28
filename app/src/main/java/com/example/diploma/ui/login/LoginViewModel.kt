package com.example.diploma.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.ApiServiceObject
import com.example.diploma.network.SecuredLoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginState = _loginState.asStateFlow()

    sealed class LoginState {
        object Empty : LoginState()
        object Loading : LoginState()
        data class Success(val result: String) : LoginState()
        data class Error(val error: Throwable) : LoginState()
    }

    /**
     * Logs the user with given credentials to the server, and gets a Bearer token
     * @param username Email
     * @param password Password
     */
    fun login(username: String, password: String){
        viewModelScope.launch {
            val securedLoginRequest = encodedRequest(username, password)
            Log.i("API Login", "Sent data: $securedLoginRequest")

            try {
                Log.i("API Login", "login started")
                _loginState.value = LoginState.Loading
                _loginState.value = LoginState.Success(
                    ApiServiceObject.retrofitService.postLogin(securedLoginRequest)
                )

                ApiServiceObject.token = (loginState.value as LoginState.Success).result
                Log.i("API Login", "ApiServiceObject.token = ${ApiServiceObject.token}")
            } catch (e: Exception) {
                Log.w("API Login", e.toString())
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
    private fun encodedRequest(username: String, password: String): SecuredLoginRequest{
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
    }
}