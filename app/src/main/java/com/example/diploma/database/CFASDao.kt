package com.example.diploma.database

import androidx.room.*
import com.example.diploma.network.models.BearerToken
import com.example.diploma.network.models.ContactsResponse
import java.util.concurrent.Flow

@Dao
interface CFASDao {
//    @Insert(entity = BearerToken::class, onConflict = OnConflictStrategy.REPLACE)
//    fun updateToken(bearerToken: BearerToken)

//    @Query()
//    fun dd : Flow<>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(contactsResponse: ContactsResponse)
}