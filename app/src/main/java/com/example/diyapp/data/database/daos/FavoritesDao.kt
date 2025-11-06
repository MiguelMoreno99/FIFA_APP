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
        SELECT 
            Pub.idPublication, 
            Pub.CORREO_USUARIO, 
            Pub.theme, 
            Pub.photoMain, 
            Pub.description,
            Pub.numLikes, 
            Pub.state, 
            Pub.dateCreation, 
            Pub.instructions, 
            Pub.photoProcess
        FROM 
            CreationTable AS Pub
        JOIN 
            userTable ON userTable.CORREO_USUARIO = Pub.CORREO_USUARIO
        JOIN 
            favoritesTable ON favoritesTable.idPublication = Pub.idPublication
        WHERE 
            favoritesTable.CORREO_USUARIO = :CORREO_USUARIO
    """)
    suspend fun getFavoritesByUser(CORREO_USUARIO: String): List<FavoriteEntity>
}