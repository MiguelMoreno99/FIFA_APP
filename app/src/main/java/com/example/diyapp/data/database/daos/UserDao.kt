package com.example.diyapp.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.diyapp.data.database.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: List<UserEntity>)

    @Update
    suspend fun updateUser(users: List<UserEntity>)

    @Query(value = "SELECT * FROM userTable WHERE CORREO_USUARIO = :CORREO_USUARIO")
    suspend fun getUserInfo(CORREO_USUARIO: String): List<UserEntity>

    @Query(value = "DELETE FROM userTable")
    suspend fun deleteAllUsers()
}