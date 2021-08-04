package com.example.android.testesolutis.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Login(
    @PrimaryKey
    val username: String,
    @Ignore
    val password: String
) {
    // Necessário para não gerar conflitos com o ROOM por conta do @Ignore
    constructor(username: String) : this(username, "")
}