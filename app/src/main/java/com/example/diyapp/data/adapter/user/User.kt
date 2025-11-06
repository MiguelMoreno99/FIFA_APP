package com.example.diyapp.data.adapter.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("CORREO_USUARIO") val CORREO_USUARIO: String,
    @SerializedName("NOMBRE_USUARIO") val NOMBRE_USUARIO: String,
    @SerializedName("APELLIDO_USUARIO") val APELLIDO_USUARIO: String,
    @SerializedName("CONTRASEÑA_USUARIO") val CONTRASEÑA_USUARIO: String,
    @SerializedName("FOTO_PERFIL_USUARIO") val FOTO_PERFIL_USUARIO: String,
) : Parcelable