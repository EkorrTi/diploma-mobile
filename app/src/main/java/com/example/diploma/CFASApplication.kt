package com.example.diploma

import android.app.Application
import com.example.diploma.database.CFASDatabase


class CFASApplication: Application() {
    val database: CFASDatabase by lazy { CFASDatabase.getDatabase(this) }
}


