package com.dachkaboiz.b_b.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users", indices = [Index(value = ["username"], unique = true)])
data class User(
    // REQUIRED
    @PrimaryKey(autoGenerate = true) val userID: Long = 0,
    val username: String,
    val email: String,
    val password: String,

    //OPTIONAL
    val fullName: String? = null,
    val birthDate: java.util.Date? = null,
    val age: Int? =null,
    val profilePicUri: String? = null,

    // GAMIFICATION FIELDS
    val currentLevel: Int = 1,
    val budgetPoints: Int = 0,
    val dailyStreak: Int = 0
)