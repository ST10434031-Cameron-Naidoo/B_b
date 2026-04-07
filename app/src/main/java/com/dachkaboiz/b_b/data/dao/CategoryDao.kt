package com.dachkaboiz.b_b.data.dao

import androidx.room.*
import com.dachkaboiz.b_b.data.model.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    //@Query("SELECT * FROM categories ORDER BY name DESC")
    //fun getAllCategories(): Flow<List<Category>>

    //@Query("SELECT * FROM categories WHERE type = :type")
    //fun getCategoriesByType(type: CategoryType): Flow<List<Category>>
}