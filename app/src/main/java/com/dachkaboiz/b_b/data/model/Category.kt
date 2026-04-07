package com.dachkaboiz.b_b.data.model

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
data class Category(
    val id: Int
)
