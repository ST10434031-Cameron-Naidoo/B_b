package com.dachkaboiz.b_b.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "goals",
    indices = [Index(value = ["userId"])]
)
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Long,
    val title: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val deadline: Date? = null,
    val isCompleted: Boolean = false,
    val createdAt: Date = Date()
)