package com.example.diploma.database

import androidx.room.*
import com.example.diploma.network.models.BearerToken
import java.util.concurrent.Flow

@Dao
interface CFASDao {
    @Insert(entity = BearerToken::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateToken(bearerToken: BearerToken)

//    @Query("SELECT")
//    fun getBearerToken(): String

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(contactsResponse: ContactsResponse)
}