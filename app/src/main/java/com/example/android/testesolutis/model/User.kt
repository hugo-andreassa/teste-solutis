package com.example.android.testesolutis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val nome: String,

    @PrimaryKey
    val cpf: String,

    val saldo: Double,

    val token: String
)