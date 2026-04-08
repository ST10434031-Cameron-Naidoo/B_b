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
    val id: Int,
    val accountUsername: String,
    val name: String,
    val iconName: String,
    val description: String,
    val minGoal: Double? = null, //optional
    val maxGoal: Double? = null, //optional
    val mainCategoryId: Int? = null //depends on if has a subcategory
)
