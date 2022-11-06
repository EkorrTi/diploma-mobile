package com.example.diploma.ui.contacts

import android.util.Log
import androidx.lifecycle.*
import com.example.diploma.models.Worker
import com.example.diploma.network.ApiServiceObject
import com.example.diploma.network.models.ContactsResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class ContactsViewModel : ViewModel() {
    fun getData(): HashMap<String, List<String>>{
        return hashMapOf(
            "Senior" to listOf("Errasyl", "Sayan"),
            "Junior" to listOf("Dauir")
        )
    }

    // The internal MutableLiveData that stores the status of the most recent request
    private val _response = MutableLiveData< ContactsResponse >()

    // The external immutable LiveData for the request status
    val response: LiveData< ContactsResponse > = _response

    fun get(){
        viewModelScope.launch {
            try {
                _response.value = ApiServiceObject.retrofitService.getContacts("Bearer \${token}")
                Log.i("API GET", "got GET request")
            } catch (e: Exception) { Log.w("API GET", e.toString()) }
        }
    }
}