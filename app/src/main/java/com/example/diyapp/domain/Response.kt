package com.example.diyapp.domain

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ServerResponse(
    @SerializedName("message") val message: String
)

data class IdResponse(
    @SerializedName("UUID_JUGADOR") val UUID_JUGADOR: UUID,
    @SerializedName("CORREO_USUARIO") val CORREO_USUARIO: String
)

data class UserEmail(
    @SerializedName("CORREO_USUARIO") val CORREO_USUARIO: String
)

data class UserCredentials(
    @SerializedName("CORREO_USUARIO") val CORREO_USUARIO: String,
    @SerializedName("CONTRASEÑA_USUARIO") val CONTRASEÑA_USUARIO: String
)

data class UserRedeem(
    @SerializedName("UUID_JUGADOR") val UUID_JUGADOR: UUID,
    @SerializedName("CORREO_USUARIO") val CORREO_USUARIO: String
)

data class UserNewPublication(
    @SerializedName("UUID_JUGADOR") val UUID_JUGADOR: UUID,
    @SerializedName("IMG_PAIS_JUGADOR") val IMG_PAIS_JUGADOR: String,
    @SerializedName("NOMBRE_PAIS_JUGADOR") val NOMBRE_PAIS_JUGADOR: String,
    @SerializedName("NOMBRE_ABREVIADO_PAIS_JUGADOR") val NOMBRE_ABREVIADO_PAIS_JUGADOR: String,
    @SerializedName("IMG_SELECCION_JUGADOR") val IMG_SELECCION_JUGADOR: String,
    @SerializedName("IMG_JUGADOR_JUGADOR") val IMG_JUGADOR_JUGADOR: String,
    @SerializedName("NOMBRE_SELECCION_JUGADOR") val NOMBRE_SELECCION_JUGADOR: String,
    @SerializedName("POSICION_JUGADOR") val POSICION_JUGADOR: String,
    @SerializedName("POSICION_ABREVIADO_JUGADOR") val POSICION_ABREVIADO_JUGADOR: String,
    @SerializedName("NUMERO_JUGADOR") val NUMERO_JUGADOR: Int,
    @SerializedName("NOMBRE_COMPLETO_JUGADOR") val NOMBRE_COMPLETO_JUGADOR: String,
    @SerializedName("NOMBRE_CORTO_JUGADOR") val NOMBRE_CORTO_JUGADOR: String,
    @SerializedName("NACIMIENTO_CORTO_JUGADOR") val NACIMIENTO_CORTO_JUGADOR: String,
    @SerializedName("NACIMIENTO_JUGADOR") val NACIMIENTO_JUGADOR: String,
    @SerializedName("ALTURA_JUGADOR") val ALTURA_JUGADOR: String,
    @SerializedName("ACTUAL_CLUB_JUGADOR") val ACTUAL_CLUB_JUGADOR: String,
    @SerializedName("PRIMER_CLUB_JUGADOR") val PRIMER_CLUB_JUGADOR: String,
    @SerializedName("LOGROS_JUGADOR") val LOGROS_JUGADOR: String,
)

data class UserEditPublication(
    @SerializedName("UUID_JUGADOR") val UUID_JUGADOR: UUID,
    @SerializedName("IMG_PAIS_JUGADOR") val IMG_PAIS_JUGADOR: String,
    @SerializedName("NOMBRE_PAIS_JUGADOR") val NOMBRE_PAIS_JUGADOR: String,
    @SerializedName("NOMBRE_ABREVIADO_PAIS_JUGADOR") val NOMBRE_ABREVIADO_PAIS_JUGADOR: String,
    @SerializedName("IMG_SELECCION_JUGADOR") val IMG_SELECCION_JUGADOR: String,
    @SerializedName("IMG_JUGADOR_JUGADOR") val IMG_JUGADOR_JUGADOR: String,
    @SerializedName("NOMBRE_SELECCION_JUGADOR") val NOMBRE_SELECCION_JUGADOR: String,
    @SerializedName("POSICION_JUGADOR") val POSICION_JUGADOR: String,
    @SerializedName("POSICION_ABREVIADO_JUGADOR") val POSICION_ABREVIADO_JUGADOR: String,
    @SerializedName("NUMERO_JUGADOR") val NUMERO_JUGADOR: Int,
    @SerializedName("NOMBRE_COMPLETO_JUGADOR") val NOMBRE_COMPLETO_JUGADOR: String,
    @SerializedName("NOMBRE_CORTO_JUGADOR") val NOMBRE_CORTO_JUGADOR: String,
    @SerializedName("NACIMIENTO_CORTO_JUGADOR") val NACIMIENTO_CORTO_JUGADOR: String,
    @SerializedName("NACIMIENTO_JUGADOR") val NACIMIENTO_JUGADOR: String,
    @SerializedName("ALTURA_JUGADOR") val ALTURA_JUGADOR: String,
    @SerializedName("ACTUAL_CLUB_JUGADOR") val ACTUAL_CLUB_JUGADOR: String,
    @SerializedName("PRIMER_CLUB_JUGADOR") val PRIMER_CLUB_JUGADOR: String,
    @SerializedName("LOGROS_JUGADOR") val LOGROS_JUGADOR: String,
)