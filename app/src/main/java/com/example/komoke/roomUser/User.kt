package com.example.komoke.roomUser

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val numphone: String
//    val date: Date
)