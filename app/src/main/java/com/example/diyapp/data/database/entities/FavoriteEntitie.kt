package com.example.diyapp.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "favoritesTable",
    primaryKeys = ["CORREO_USUARIO", "idPublication"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["CORREO_USUARIO"],
            childColumns = ["CORREO_USUARIO"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CreationEntity::class,
            parentColumns = ["idPublication"],
            childColumns = ["idPublication"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FavoriteEntity(
    val CORREO_USUARIO: String,
    val idPublication: Int
)
