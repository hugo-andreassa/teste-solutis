package com.example.android.testesolutis.db

import android.content.Context
import androidx.room.*
import com.example.android.testesolutis.db.dao.LoginDao
import com.example.android.testesolutis.db.dao.UserDao
import com.example.android.testesolutis.model.Login
import com.example.android.testesolutis.model.User

@Database(entities = [Login::class, User::class], version = 1, exportSchema = false)
abstract class SolutisDatabase() : RoomDatabase() {
    abstract val loginDao: LoginDao
    abstract val userDao: UserDao
}

private lateinit var INSTANCE: SolutisDatabase
fun getDatabase(context: Context): SolutisDatabase {
    synchronized(SolutisDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                SolutisDatabase::class.java,
                "solutis"
            ).build()
        }
        return INSTANCE
    }
}
