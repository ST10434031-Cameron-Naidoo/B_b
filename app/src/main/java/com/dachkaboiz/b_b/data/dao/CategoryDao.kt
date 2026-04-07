package com.dachkaboiz.b_b.data.dao

import androidx.room.*
import com.dachkaboiz.b_b.data.model.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    //@Query("SELECT * FROM categories ORDER BY name DESC")
    //fun getAllCategories(): Flow<List<Category>>

    //@Query("SELECT * FROM categories WHERE type = :type")
    //fun getCategoriesByType(type: CategoryType): Flow<List<Category>>
}