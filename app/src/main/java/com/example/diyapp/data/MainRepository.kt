package com.example.diyapp.data

import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.creations.FeedCreationsProvider
import com.example.diyapp.data.adapter.explore.FeedAlbum
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.data.adapter.favorites.FeedFavoritesProvider
import com.example.diyapp.data.adapter.user.User
import com.example.diyapp.data.database.daos.CreationsDao
import com.example.diyapp.data.database.daos.FavoriteDao
import com.example.diyapp.data.database.daos.UserDao
import com.example.diyapp.data.database.entities.CreationEntity
import com.example.diyapp.data.database.entities.UserEntity
import com.example.diyapp.data.model.CreationModel
import com.example.diyapp.data.model.UserModel
import com.example.diyapp.data.model.toDomain
import com.example.diyapp.domain.RetrofitManager
import com.example.diyapp.domain.ServerResponse
import java.util.UUID
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: RetrofitManager,
    private val creationsDao: CreationsDao,
    private val favoritesDao: FavoriteDao,
    private val userDao: UserDao
) {
    //USER
    suspend fun editUser(
        CORREO_USUARIO: String,
        NOMBRE_USUARIO: String,
        APELLIDO_USUARIO: String,
        CONTRASEÑA_USUARIO: String,
        FOTO_PERFIL_USUARIO: String
    ): List<User> {
        val response = api.editUser(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)
        return response
    }
    suspend fun registerUser(
        CORREO_USUARIO: String,
        NOMBRE_USUARIO: String,
        APELLIDO_USUARIO: String,
        CONTRASEÑA_USUARIO: String,
        FOTO_PERFIL_USUARIO: String
    ): ServerResponse {
        val response = api.registerUser(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)
        return response
    }
    suspend fun getUserFromApi(CORREO_USUARIO: String): List<UserModel> {
        val response: List<User> = api.getUser(CORREO_USUARIO)
        return response.map { it.toDomain() }
    }
    suspend fun getUserFromDataBase(CORREO_USUARIO: String): List<UserModel> {
        val response: List<UserEntity> = userDao.getUserInfo(CORREO_USUARIO)
        return response.map { it.toDomain() }
    }
    suspend fun insertUsers(CORREO_USUARIO: List<UserEntity>) {
        userDao.insertUser(CORREO_USUARIO)
    }
    suspend fun clearUsers() {
        userDao.deleteAllUsers()
    }

    //ALBUM
    suspend fun getAllPlayersByEmailFromApi(CORREO_USUARIO: String): List<CreationModel> {
        val response: List<FeedAlbum> = api.getAllPlayersByEmail(CORREO_USUARIO)
        return response.map { it.toDomain() }
    }

    suspend fun getAllPlayersByEmailFromDataBase(): List<CreationModel> {
        val response: List<CreationEntity> = creationsDao.getAllPlayers()
        return response.map { it.toDomain() }
    }

    //ALBUM
    suspend fun getAllPlayersFromApi(): List<CreationModel> {
        val response: List<FeedAlbum> = api.getAllPlayers()
        return response.map { it.toDomain() }
    }

    suspend fun getAllPlayersFromDataBase(): List<CreationModel> {
        val response: List<CreationEntity> = creationsDao.getAllPlayers()
        return response.map { it.toDomain() }
    }

    suspend fun getUserFavorites(CORREO_USUARIO: String): List<FeedFavorites> {
        val response = api.getUserFavorites(CORREO_USUARIO)
        FeedFavoritesProvider.feedFavoritesList = response
        return response
    }

    suspend fun userRedeemCard(
        UUID_JUGADOR: UUID,
        CORREO_USUARIO: String
    ): ServerResponse {
        val response = api.userRedeemCard(
            UUID_JUGADOR,
            CORREO_USUARIO
        )
        return response
    }

//    suspend fun getFeedCreations(CORREO_USUARIO: String): List<FeedCreations> {
//        val response = api.getFeedCreations(CORREO_USUARIO)
//        FeedCreationsProvider.feedCreationsList = response
//        return response
//    }

    suspend fun insertCreations(creation: List<CreationEntity>) {
        creationsDao.insertPublication(creation)
    }

    suspend fun clearPublications() {
        creationsDao.deleteAllPublications()
    }

//    suspend fun editPublication(
//        UUID_JUGADOR: UUID,
//        IMG_PAIS_JUGADOR: String,
//        NOMBRE_PAIS_JUGADOR: String,
//        NOMBRE_ABREVIADO_PAIS_JUGADOR: String,
//        IMG_SELECCION_JUGADOR: String,
//        IMG_JUGADOR_JUGADOR: String,
//        NOMBRE_SELECCION_JUGADOR: String,
//        POSICION_JUGADOR: String,
//        POSICION_ABREVIADO_JUGADOR: String,
//        NUMERO_JUGADOR: Int,
//        NOMBRE_COMPLETO_JUGADOR: String,
//        NOMBRE_CORTO_JUGADOR: String,
//        NACIMIENTO_CORTO_JUGADOR: String,
//        NACIMIENTO_JUGADOR: String,
//        ALTURA_JUGADOR: String,
//        ACTUAL_CLUB_JUGADOR: String,
//        PRIMER_CLUB_JUGADOR: String,
//        LOGROS_JUGADOR: String
//    ): ServerResponse {
//        val response = api.editPublication(
//            UUID_JUGADOR,
//            IMG_PAIS_JUGADOR,
//            NOMBRE_PAIS_JUGADOR,
//            NOMBRE_ABREVIADO_PAIS_JUGADOR,
//            IMG_SELECCION_JUGADOR,
//            IMG_JUGADOR_JUGADOR,
//            NOMBRE_SELECCION_JUGADOR,
//            POSICION_JUGADOR,
//            POSICION_ABREVIADO_JUGADOR,
//            NUMERO_JUGADOR,
//            NOMBRE_COMPLETO_JUGADOR,
//            NOMBRE_CORTO_JUGADOR,
//            NACIMIENTO_CORTO_JUGADOR,
//            NACIMIENTO_JUGADOR,
//            ALTURA_JUGADOR,
//            ACTUAL_CLUB_JUGADOR,
//            PRIMER_CLUB_JUGADOR,
//            LOGROS_JUGADOR
//        )
//        return response
//    }

//    suspend fun deletePublication(UUID_JUGADOR: UUID, CORREO_USUARIO: String): ServerResponse {
//        val response = api.deletePublication(UUID_JUGADOR, CORREO_USUARIO)
//        return response
//    }

    suspend fun removeFavorite(UUID_JUGADOR: UUID, CORREO_USUARIO: String): ServerResponse {
        val response = api.removeFavorite(UUID_JUGADOR, CORREO_USUARIO)
        return response
    }

    suspend fun addFavoritePublication(UUID_JUGADOR: UUID, CORREO_USUARIO: String): ServerResponse {
        val response = api.addFavoritePublication(UUID_JUGADOR, CORREO_USUARIO)
        return response
    }
}