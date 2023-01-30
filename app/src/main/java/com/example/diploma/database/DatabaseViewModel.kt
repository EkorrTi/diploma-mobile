package com.example.diploma.database

import androidx.lifecycle.*
import com.example.diploma.network.models.BearerToken
import kotlinx.coroutines.launch

class DatabaseViewModel(private val cfasDao: CFASDao): ViewModel() {
    fun updateToken(token: String) {
        viewModelScope.launch {
            cfasDao.updateToken(BearerToken(token = token))
        }
    }
}


// Instance example in LoginFragment
class DatabaseViewModelFactory(private val cfasDao: CFASDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatabaseViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DatabaseViewModel(cfasDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}