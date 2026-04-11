package com.dachkaboiz.b_b.repository

import com.dachkaboiz.b_b.data.dao.CategoryDao
import com.dachkaboiz.b_b.data.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {

    // Fetches top-level categories for the main screen
    fun getMainCategories(username: String): Flow<List<Category>> =
        categoryDao.getMainCategories(username)

    // Fetches subcategories for a specific breakdown
    suspend fun getSubcategories(parentId: Long): List<Category> =
        categoryDao.getSubcategories(parentId)

    suspend fun addCategory(category: Category) = categoryDao.insertCategory(category)

    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)

    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)
}