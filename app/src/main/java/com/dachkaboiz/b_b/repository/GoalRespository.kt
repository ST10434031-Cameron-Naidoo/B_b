package com.dachkaboiz.b_b.repository

import com.dachkaboiz.b_b.data.dao.GoalDao
import com.dachkaboiz.b_b.data.model.Goal
import kotlinx.coroutines.flow.Flow

class GoalRepository(private val goalDao: GoalDao) {

    // 1. CREATE
    suspend fun addGoal(goal: Goal): GoalResult {
        return try {
            goalDao.insertGoal(goal)
            GoalResult.Success
        } catch (e: Exception) {
            GoalResult.Error("Failed to create goal: ${e.message}")
        }
    }

    // 2. READ - all goals (for goals list screen)
    fun getGoals(userId: Long): Flow<List<Goal>> {
        return goalDao.getGoalsByUser(userId)
    }

    // 3. READ - active goals only (for dashboard)
    fun getActiveGoals(userId: Long): Flow<List<Goal>> {
        return goalDao.getActiveGoals(userId)
    }

    // 4. READ - single goal (for edit screen)
    suspend fun getGoalById(goalId: Long): Goal? {
        return goalDao.getGoalById(goalId)
    }

    // 5. UPDATE - edit goal details
    suspend fun updateGoal(goal: Goal): GoalResult {
        return try {
            goalDao.updateGoal(goal)
            GoalResult.Success
        } catch (e: Exception) {
            GoalResult.Error("Failed to update goal: ${e.message}")
        }
    }

    // 6. UPDATE - add progress toward goal
    suspend fun addProgress(goalId: Long, amount: Double): GoalResult {
        return try {
            val goal = goalDao.getGoalById(goalId)
                ?: return GoalResult.Error("Goal not found")

            val newAmount = goal.currentAmount + amount
            goalDao.updateGoalProgress(goalId, newAmount)

            // auto-complete if target reached
            if (newAmount >= goal.targetAmount) {
                goalDao.markGoalComplete(goalId)
                GoalResult.Completed
            } else {
                GoalResult.Success
            }
        } catch (e: Exception) {
            GoalResult.Error("Failed to update progress: ${e.message}")
        }
    }

    // 7. DELETE
    suspend fun deleteGoal(goal: Goal): GoalResult {
        return try {
            goalDao.deleteGoal(goal)
            GoalResult.Success
        } catch (e: Exception) {
            GoalResult.Error("Failed to delete goal: ${e.message}")
        }
    }
}

sealed class GoalResult {
    object Success : GoalResult()
    object Loading : GoalResult()
    object Completed : GoalResult()         // ← unique to goals, triggers reward/level up
    data class Error(val message: String) : GoalResult()
}