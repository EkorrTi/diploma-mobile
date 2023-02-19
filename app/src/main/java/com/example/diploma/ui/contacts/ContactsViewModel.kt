package com.example.diploma.ui.contacts

import android.util.Log
import androidx.lifecycle.*
import com.example.diploma.models.Worker
import com.example.diploma.network.ApiServiceObject
import com.example.diploma.network.models.ContactsResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class ContactsViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
//    private val _response = MutableLiveData< ContactsResponse >()
    private val _response = MutableLiveData< List<Worker> >()

    // The external immutable LiveData for the request status
//    val response: LiveData< ContactsResponse > = _response
    val response: LiveData< List<Worker> > = _response

    fun get(){
        viewModelScope.launch {
            try {
                _response.value = ApiServiceObject.retrofitService.getContacts()
                Log.i("API GET Contacts", "request successful, data:${_response.value.toString()}")
            } catch (e: Exception) { Log.w("API GET", e.toString()) }
        }
    }
}