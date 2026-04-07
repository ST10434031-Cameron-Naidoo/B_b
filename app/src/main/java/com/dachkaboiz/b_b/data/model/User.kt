package com.dachkaboiz.b_b.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    // REQUIRED
    val username: String,
    val password: String,

    //OPTIONAL
    val fullName: String?=null,
    val birthDate: Date? =null,
    val age: Int? =null,
    val profilePicUri: String? = null
)
