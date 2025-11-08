package com.example.diyapp.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.diyapp.data.database.entities.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("""
        SELECT * FROM favoritesTable WHERE favoritesTable.CORREO_USUARIO = :CORREO_USUARIO
    """)
    suspend fun getFavoritesByUser(CORREO_USUARIO: String): List<FavoriteEntity>
}