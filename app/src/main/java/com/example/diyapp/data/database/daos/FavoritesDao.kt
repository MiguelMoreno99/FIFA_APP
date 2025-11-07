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
            Pub.UUID_JUGADOR, 
            Pub.IMG_PAIS_JUGADOR, 
            Pub.NOMBRE_PAIS_JUGADOR, 
            Pub.NOMBRE_ABREVIADO_PAIS_JUGADOR, 
            Pub.IMG_SELECCION_JUGADOR,
            Pub.IMG_JUGADOR_JUGADOR, 
            Pub.NOMBRE_SELECCION_JUGADOR, 
            Pub.POSICION_JUGADOR, 
            Pub.POSICION_ABREVIADO_JUGADOR, 
            Pub.NUMERO_JUGADOR,
            Pub.NOMBRE_COMPLETO_JUGADOR,
            Pub.NOMBRE_CORTO_JUGADOR,
            Pub.NACIMIENTO_CORTO_JUGADOR,
            Pub.NACIMIENTO_JUGADOR,
            Pub.ALTURA_JUGADOR,
            Pub.ACTUAL_CLUB_JUGADOR,
            Pub.PRIMER_CLUB_JUGADOR,
            Pub.LOGROS_JUGADOR
        FROM 
            CreationTable AS Pub
        JOIN 
            favoritesTable ON favoritesTable.UUID_JUGADOR = Pub.UUID_JUGADOR
        WHERE 
            favoritesTable.CORREO_USUARIO = :CORREO_USUARIO
    """)
    suspend fun getFavoritesByUser(CORREO_USUARIO: String): List<FavoriteEntity>
}