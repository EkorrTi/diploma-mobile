package com.example.diploma.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DatabaseViewModel(val cfasDao: CFASDao): ViewModel() {

}

class DatabaseViewModelFactory(private val cfasDao: CFASDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatabaseViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DatabaseViewModel(cfasDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}