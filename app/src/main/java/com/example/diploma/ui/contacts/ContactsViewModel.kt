package com.example.diploma.ui.contacts

import androidx.lifecycle.ViewModel

class ContactsViewModel : ViewModel() {
    fun getData(): HashMap<String, List<String>>{
        return hashMapOf(
            "Senior" to listOf("Errasyl", "Sayan"),
            "Junior" to listOf("Dauir")
        )
    }
}