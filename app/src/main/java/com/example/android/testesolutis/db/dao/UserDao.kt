package com.example.android.testesolutis.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.testesolutis.model.User

@Dao
interface UserDao {
    @Query("select * from user limit 1")
    suspend fun getLoggedUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("delete from user")
    suspend fun deleteAll()
}