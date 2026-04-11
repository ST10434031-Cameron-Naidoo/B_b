package com.dachkaboiz.b_b.data.dao

import androidx.room.*
import com.dachkaboiz.b_b.data.model.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {

    // INSERT
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertGoal(goal: Goal)

    // READ - all goals for a user
    @Query("SELECT * FROM goals WHERE userId = :userId ORDER BY deadline ASC")
    fun getGoalsByUser(userId: Long): Flow<List<Goal>>

    // READ - only active (incomplete) goals
    @Query("SELECT * FROM goals WHERE userId = :userId AND isCompleted = 0")
    fun getActiveGoals(userId: Long): Flow<List<Goal>>

    // READ - single goal (for detail/edit screen)
    @Query("SELECT * FROM goals WHERE id = :goalId LIMIT 1")
    suspend fun getGoalById(goalId: Long): Goal?

    // UPDATE - full goal object
    @Update
    suspend fun updateGoal(goal: Goal)

    // UPDATE - progress only (called when expense is logged)
    @Query("UPDATE goals SET currentAmount = :amount WHERE id = :goalId")
    suspend fun updateGoalProgress(goalId: Long, amount: Double)

    // UPDATE - mark complete
    @Query("UPDATE goals SET isCompleted = 1 WHERE id = :goalId")
    suspend fun markGoalComplete(goalId: Long)

    // DELETE
    @Delete
    suspend fun deleteGoal(goal: Goal)
}