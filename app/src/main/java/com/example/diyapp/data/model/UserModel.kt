package com.example.diyapp.data.model

import com.example.diyapp.data.adapter.user.User
import com.example.diyapp.data.database.entities.UserEntity

data class UserModel(
    val CORREO_USUARIO: String,
    val NOMBRE_USUARIO: String,
    val APELLIDO_USUARIO: String,
    val CONTRASEÑA_USUARIO: String,
    val FOTO_PERFIL_USUARIO: String,
)

fun User.toDomain() = UserModel(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)
fun UserEntity.toDomain() = UserModel(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)
