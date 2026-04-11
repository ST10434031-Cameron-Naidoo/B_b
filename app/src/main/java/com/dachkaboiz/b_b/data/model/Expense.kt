package com.dachkaboiz.b_b.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Long,
    val categoryId: Long,
    val amount: Double,
    val date: Date,
    val description: String,
    val imagePath: String? = null, // For "Take Photo" feature
    val isAutomated: Boolean = false
)