package com.dachkaboiz.b_b.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "categories",
    indices = [Index(value = ["accountUsername"])],
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
    //REQUIRED
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val accountUsername: String,
    val name: String,
    val iconName: String,

    //OPTIONAL
    val description: String? = null,
    val minGoal: Double? = null,
    val maxGoal: Double? = null,
    val mainCategoryId: Int? = null
)
