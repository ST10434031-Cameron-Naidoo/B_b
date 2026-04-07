package com.dachkaboiz.b_b.data.dao

import androidx.room.*
import com.dachkaboiz.b_b.data.model.User

@Dao
interface UserDao {

    //CREATE
    //throws error username exists
  @Insert(onConflict = OnConflictStrategy.ABORT)
  suspend fun insertUser(user: User)

    //READ
  @Query("SELECT * FROM users WHERE username= :username LIMIT 1")
  suspend fun getUserByUsername(username: String): User?

  @Query("SELECT EXISTS(SELECT 1 FROM users WHERE username= :username)")
  suspend fun isUsernameTaken(username: String): Boolean

  //UPDATE
  @Update
  suspend fun  updateUser(user: User)

  //DELETE
  @Delete
  suspend fun  deleteUser(user: User)


}