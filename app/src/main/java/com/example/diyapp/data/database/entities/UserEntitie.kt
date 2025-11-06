package com.example.diyapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diyapp.data.model.UserModel

@Entity(tableName = "userTable")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "CORREO_USUARIO") val CORREO_USUARIO: String,
    @ColumnInfo(name = "NOMBRE_USUARIO") val NOMBRE_USUARIO: String,
    @ColumnInfo(name = "APELLIDO_USUARIO") val APELLIDO_USUARIO: String,
    @ColumnInfo(name = "CONTRASEÑA_USUARIO") val CONTRASEÑA_USUARIO: String,
    @ColumnInfo(name = "FOTO_PERFIL_USUARIO") val FOTO_PERFIL_USUARIO: String
)

fun UserModel.toDatabase() = UserEntity(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)