package com.example.android.testesolutis.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.testesolutis.model.Login

@Dao
interface LoginDao {
    @Query("select * from login limit 1")
    suspend fun getLogin(): Login?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(login: Login)

    @Query("delete from login")
    suspend fun deleteAll()
}