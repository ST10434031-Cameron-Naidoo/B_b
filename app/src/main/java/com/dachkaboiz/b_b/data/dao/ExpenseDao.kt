package com.dachkaboiz.b_b.data.dao

import androidx.room.*
import com.dachkaboiz.b_b.data.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    // READ - all expenses for a user
    @Query("SELECT * FROM expenses WHERE userId = :userId ORDER BY date DESC")
    fun getExpensesByUser(userId: Long): Flow<List<Expense>>

    // READ - expenses filtered by category
    @Query("SELECT * FROM expenses WHERE userId = :userId AND categoryId = :categoryId")
    fun getExpensesByCategory(userId: Long, categoryId: Int): Flow<List<Expense>>

    // READ - total spending for a user
    @Query("SELECT SUM(amount) FROM expenses WHERE userId = :userId")
    suspend fun getTotalSpending(userId: Long): Double?

    // UPDATE
    @Update
    suspend fun updateExpense(expense: Expense)

    // DELETE
    @Delete
    suspend fun deleteExpense(expense: Expense)
}