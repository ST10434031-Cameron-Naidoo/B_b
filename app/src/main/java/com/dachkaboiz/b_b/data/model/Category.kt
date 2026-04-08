package com.dachkaboiz.b_b.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["username"],
            childColumns = ["accountUsername"],
            onDelete = ForeignKey.CASCADE
        )
    ]
    )
data class Category(
    @PrimaryKey
    //REQUIRED
    val id: Int,
    val accountUsername: String,
    val name: String,
    val iconName: String,

    //OPTIONAL
    val description: String? = null,
    val minGoal: Double? = null,
    val maxGoal: Double? = null,
    val mainCategoryId: Int? = null 
)
