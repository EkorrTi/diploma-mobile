package com.example.diploma.ui.team

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.ApiServiceObject
import com.example.diploma.network.models.RequestResponse
import com.example.diploma.network.models.TeamRequest
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {
    private val _response = MutableLiveData<RequestResponse>()
    val response: LiveData<RequestResponse> = _response

    fun sendRequest( teamRequest: TeamRequest ){
        viewModelScope.launch {
            try {
                Log.i("API POST VacationRequest", "send data: $teamRequest")

                _response.value = ApiServiceObject.retrofitService
                    .postTeamRequest( teamRequest )

                Log.i("API POST VacationRequest", "request successful, response: ${_response.value.toString()}")
            } catch (e: Exception) { Log.w("API POST Request", e.toString()) }
        }
    }
}