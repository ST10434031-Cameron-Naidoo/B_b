package com.dachkaboiz.b_b.data.dao

import androidx.room.*
import com.dachkaboiz.b_b.data.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    // --- CREATE ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    // --- READ ---
    // Fetches top-level categories (e.g., "Food", "Transport")
    @Query("SELECT * FROM categories WHERE accountUsername = :username")
    fun getMainCategories(username: String): Flow<List<Category>>

    // Fetches subcategories for a specific main category (e.g., "Sushi" under "Food")
    @Query("SELECT * FROM categories WHERE mainCategoryId = :parentId")
    suspend fun getSubcategories(parentId: Long): List<Category>

    // Used by the ExpenseRepository to check goals before saving an expense
    @Query("SELECT * FROM categories WHERE id = :categoryId LIMIT 1")
    suspend fun getCategoryById(categoryId: Long): Category?

    // --- UPDATE ---
    @Update
    suspend fun updateCategory(category: Category)

    // --- DELETE ---
    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("DELETE FROM categories WHERE id = :userId")
    suspend fun deleteAllCategoriesForUser(userId: Long)
}