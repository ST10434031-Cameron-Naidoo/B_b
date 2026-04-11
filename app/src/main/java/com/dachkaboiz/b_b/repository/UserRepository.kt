package com.dachkaboiz.b_b.repository

import com.dachkaboiz.b_b.data.dao.UserDao
import com.dachkaboiz.b_b.data.model.User

/**
 * Repository that handles all User-related data operations.
 * It provides a clean API to the ViewModel so the UI doesn't have to
 * worry about database specifics.
 */
class UserRepository(private val userDao: UserDao) {

    // 1. SIGNUP LOGIC
    // Follows the requirements to check for existing credentials before creation
    suspend fun registerUser(user: User): AuthResult {
        val existingUser = userDao.getUserByUsername(user.username)
        val existingEmail = userDao.getUserByEmail(user.email)

        return when {
            existingUser != null -> AuthResult.Error("Username already registered")
            existingEmail != null -> AuthResult.Error("Email already registered")
            else -> {
                userDao.insertUser(user)
                AuthResult.Success(user)
            }
        }
    }

    // 2. LOGIN LOGIC
    // Validates the user exists and the password matches
    suspend fun loginUser(username: String, pass: String): AuthResult {
        val user = userDao.getUserByUsername(username)
        return if (user != null && user.password == pass) {
            AuthResult.Success(user)
        } else {
            AuthResult.Error("Invalid username or password")
        }
    }

    // 3. PROFILE & GAMIFICATION LOGIC
    // Handles updates for profile changes and Reward/Level progress
    suspend fun updateProfile(user: User) {
        userDao.updateUser(user)
    }

    // 4. ACCOUNT MANAGEMENT
    // Required for the 'Delete Account' feature in your Profile mockup
    suspend fun deleteAccount(user: User) {
        userDao.deleteUser(user)
    }
}

/**
 * Result wrapper for authentication processes.
 * Allows the UI to easily react to different states (Loading, Success, Error).
 */
sealed class AuthResult {
    object Loading : AuthResult()
    data class Success(val user: User) : AuthResult()
    data class SuccessMessage(val message: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}