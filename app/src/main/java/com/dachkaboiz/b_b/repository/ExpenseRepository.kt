package com.dachkaboiz.b_b.repository

import com.dachkaboiz.b_b.data.dao.ExpenseDao
import com.dachkaboiz.b_b.data.model.Expense
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    // 1. CREATE
    suspend fun addExpense(expense: Expense): ExpenseResult {
        return try {
            expenseDao.insertExpense(expense)
            ExpenseResult.Success
        } catch (e: Exception) {
            ExpenseResult.Error("Failed to add expense: ${e.message}")
        }
    }

    // 2. READ - live list for a user (use in ViewModel with collectAsState)
    fun getExpenses(userId: Long): Flow<List<Expense>> {
        return expenseDao.getExpensesByUser(userId)
    }

    // 3. READ - filtered by category
    fun getExpensesByCategory(userId: Long, categoryId: Int): Flow<List<Expense>> {
        return expenseDao.getExpensesByCategory(userId, categoryId)
    }

    // 4. READ - total spending (for dashboard/gamification)
    suspend fun getTotalSpending(userId: Long): Double {
        return expenseDao.getTotalSpending(userId) ?: 0.0
    }

    // 5. UPDATE
    suspend fun updateExpense(expense: Expense): ExpenseResult {
        return try {
            expenseDao.updateExpense(expense)
            ExpenseResult.Success
        } catch (e: Exception) {
            ExpenseResult.Error("Failed to update expense: ${e.message}")
        }
    }

    // 6. DELETE
    suspend fun deleteExpense(expense: Expense): ExpenseResult {
        return try {
            expenseDao.deleteExpense(expense)
            ExpenseResult.Success
        } catch (e: Exception) {
            ExpenseResult.Error("Failed to delete expense: ${e.message}")
        }
    }
}

// Result wrapper — mirrors AuthResult in UserRepository
sealed class ExpenseResult {
    object Success : ExpenseResult()
    object Loading : ExpenseResult()
    data class Error(val message: String) : ExpenseResult()
}