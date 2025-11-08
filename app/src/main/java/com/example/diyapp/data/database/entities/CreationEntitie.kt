package com.example.diyapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.diyapp.data.database.Converters
import com.example.diyapp.data.model.CreationModel
import java.util.UUID

@Entity(
    tableName = "CreationTable"
)
@TypeConverters(Converters::class)
data class CreationEntity(
    @PrimaryKey
    @ColumnInfo(name = "UUID_JUGADOR") val UUID_JUGADOR: UUID,
    @ColumnInfo(name = "IMG_PAIS_JUGADOR") val IMG_PAIS_JUGADOR: String = "",
    @ColumnInfo(name = "NOMBRE_PAIS_JUGADOR") val NOMBRE_PAIS_JUGADOR: String = "",
    @ColumnInfo(name = "NOMBRE_ABREVIADO_PAIS_JUGADOR") val NOMBRE_ABREVIADO_PAIS_JUGADOR: String = "",
    @ColumnInfo(name = "IMG_SELECCION_JUGADOR") val IMG_SELECCION_JUGADOR: String = "",
    @ColumnInfo(name = "IMG_JUGADOR_JUGADOR") val IMG_JUGADOR_JUGADOR: String = "",
    @ColumnInfo(name = "NOMBRE_SELECCION_JUGADOR") val NOMBRE_SELECCION_JUGADOR: String = "",
    @ColumnInfo(name = "POSICION_JUGADOR") val POSICION_JUGADOR: String = "",
    @ColumnInfo(name = "POSICION_ABREVIADO_JUGADOR") val POSICION_ABREVIADO_JUGADOR: String = "",
    @ColumnInfo(name = "NUMERO_JUGADOR") val NUMERO_JUGADOR: Int = 0,
    @ColumnInfo(name = "NOMBRE_COMPLETO_JUGADOR") val NOMBRE_COMPLETO_JUGADOR: String = "",
    @ColumnInfo(name = "NOMBRE_CORTO_JUGADOR") val NOMBRE_CORTO_JUGADOR: String = "",
    @ColumnInfo(name = "NACIMIENTO_CORTO_JUGADOR") val NACIMIENTO_CORTO_JUGADOR: String = "",
    @ColumnInfo(name = "NACIMIENTO_JUGADOR") val NACIMIENTO_JUGADOR: String = "",
    @ColumnInfo(name = "ALTURA_JUGADOR") val ALTURA_JUGADOR: String = "",
    @ColumnInfo(name = "ACTUAL_CLUB_JUGADOR") val ACTUAL_CLUB_JUGADOR: String = "",
    @ColumnInfo(name = "PRIMER_CLUB_JUGADOR") val PRIMER_CLUB_JUGADOR: String = "",
    @ColumnInfo(name = "LOGROS_JUGADOR") val LOGROS_JUGADOR: String = ""
)

fun CreationModel.toDatabase() = CreationEntity(
    UUID_JUGADOR,
    IMG_PAIS_JUGADOR,
    NOMBRE_PAIS_JUGADOR,
    NOMBRE_ABREVIADO_PAIS_JUGADOR,
    IMG_SELECCION_JUGADOR,
    IMG_JUGADOR_JUGADOR,
    NOMBRE_SELECCION_JUGADOR,
    POSICION_JUGADOR,
    POSICION_ABREVIADO_JUGADOR,
    NUMERO_JUGADOR,
    NOMBRE_COMPLETO_JUGADOR,
    NOMBRE_CORTO_JUGADOR,
    NACIMIENTO_CORTO_JUGADOR,
    NACIMIENTO_JUGADOR,
    ALTURA_JUGADOR,
    ACTUAL_CLUB_JUGADOR,
    PRIMER_CLUB_JUGADOR,
    LOGROS_JUGADOR
)