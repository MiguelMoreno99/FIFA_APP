package com.example.diyapp.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.diyapp.data.database.entities.CreationEntity
import java.util.UUID

@Dao
interface CreationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPublication(publications: List<CreationEntity>)

    @Query("SELECT * FROM CreationTable")
    suspend fun getAllCompletedPublications(): List<CreationEntity>

    @Query("SELECT * FROM CreationTable ORDER BY NOMBRE_PAIS_JUGADOR ASC")
    suspend fun getAllCompletedPublicationsAsc(): List<CreationEntity>

    @Query("SELECT * FROM CreationTable ORDER BY NOMBRE_PAIS_JUGADOR DESC")
    suspend fun getAllCompletedPublicationsDesc(): List<CreationEntity>

    @Query("SELECT * FROM CreationTable WHERE UUID_JUGADOR = :UUID_JUGADOR")
    suspend fun getPublicationById(UUID_JUGADOR: UUID): CreationEntity

    @Update
    suspend fun updatePublication(publication: CreationEntity)

    @Query(value = "DELETE FROM CreationTable")
    suspend fun deleteAllPublications()
}