package com.example.komoke.roomUser

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user")
    suspend fun getAllUser(): List<User>

    @Query("SELECT * FROM user WHERE username =:user_id")
    suspend fun getUser(user_id: String): List<User>
}