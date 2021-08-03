package com.example.android.testesolutis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Login(
    @PrimaryKey
    val username: String,
    val password: String
)