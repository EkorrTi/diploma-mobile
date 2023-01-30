package com.example.diploma.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.ApiServiceObject
import com.example.diploma.network.SecuredLoginRequest
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class LoginViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val token: LiveData<String> = _response

    /**
     * Logs the user with given credentials to the server, and gets a Bearer token
     * @param username Email
     * @param password Password
     * @return Bearer token - as LiveData
     */
    fun login(username: String, password: String){
        viewModelScope.launch {
            val securedLoginRequest = encodedRequest(username, password)

            Log.i("API Login","Sent data: $securedLoginRequest")

            try {
                Log.i("API Login","login started")
                _response.value = ApiServiceObject.retrofitService.postLogin(securedLoginRequest)
                Log.i("API Login", "Login successful, token = ${_response.value}")
            } catch (e: SocketTimeoutException) { throw e }
            catch (e: Exception) { Log.w("API Login", e.toString()) }
        }
    }

    /**
     * Encodes user credentials before sending
     * @param username Email
     * @param password Password
     * @return [SecuredLoginRequest]
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