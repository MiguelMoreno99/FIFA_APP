package com.example.diyapp.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.UUID

@Entity(
    tableName = "favoritesTable",
    primaryKeys = ["CORREO_USUARIO", "UUID_JUGADOR"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["CORREO_USUARIO"],
            childColumns = ["CORREO_USUARIO"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CreationEntity::class,
            parentColumns = ["UUID_JUGADOR"],
            childColumns = ["UUID_JUGADOR"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FavoriteEntity(
    val CORREO_USUARIO: String,
    val UUID_JUGADOR: UUID
)
