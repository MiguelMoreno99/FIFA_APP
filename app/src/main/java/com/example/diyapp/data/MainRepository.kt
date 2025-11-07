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
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: RetrofitManager,
    private val creationsDao: CreationsDao,
    private val favoritesDao: FavoriteDao,
    private val userDao: UserDao
) {

    suspend fun getFeedExploreFromApi(): List<CreationModel> {
        val response: List<FeedAlbum> = api.getFeedExplore()
        return response.map { it.toDomain() }
    }

    suspend fun getUserFromApi(CORREO_USUARIO: String): List<UserModel> {
        val response: List<User> = api.getUser(CORREO_USUARIO)
        return response.map { it.toDomain() }
    }

    suspend fun getFeedExploreFromDataBase(): List<CreationModel> {
        val response: List<CreationEntity> = creationsDao.getAllCompletedPublications()
        return response.map { it.toDomain() }
    }

    suspend fun getUserFromDataBase(CORREO_USUARIO: String): List<UserModel> {
        val response: List<UserEntity> = userDao.getUserInfo(CORREO_USUARIO)
        return response.map { it.toDomain() }
    }

    suspend fun insertUsers(CORREO_USUARIO: List<UserEntity>) {
        userDao.insertUser(CORREO_USUARIO)
    }

    suspend fun insertCreations(creation: List<CreationEntity>) {
        creationsDao.insertPublication(creation)
    }

    suspend fun clearUsers() {
        userDao.deleteAllUsers()
    }

    suspend fun clearPublications() {
        creationsDao.deleteAllPublications()
    }

    suspend fun getFeedFavorite(CORREO_USUARIO: String): List<FeedFavorites> {
        val response = api.getFeedFavorite(CORREO_USUARIO)
        FeedFavoritesProvider.feedFavoritesList = response
        return response
    }

    suspend fun getFeedCreations(CORREO_USUARIO: String): List<FeedCreations> {
        val response = api.getFeedCreations(CORREO_USUARIO)
        FeedCreationsProvider.feedCreationsList = response
        return response
    }

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

    suspend fun createPublication(
        CORREO_USUARIO: String,
        title: String,
        theme: String,
        photoMain: String,
        description: String,
        instructions: String,
        photoProcess: List<String>
    ): ServerResponse {
        val response = api.createPublication(
            CORREO_USUARIO,
            title,
            theme,
            photoMain,
            description,
            instructions,
            photoProcess
        )
        return response
    }

    suspend fun editPublication(
        idPublication: Int,
        CORREO_USUARIO: String,
        title: String,
        theme: String,
        photoMain: String,
        description: String,
        instructions: String,
        photoProcess: List<String>
    ): ServerResponse {
        val response = api.editPublication(
            idPublication,
            CORREO_USUARIO,
            title,
            theme,
            photoMain,
            description,
            instructions,
            photoProcess
        )
        return response
    }

    suspend fun deletePublication(idPublication: Int, CORREO_USUARIO: String): ServerResponse {
        val response = api.deletePublication(idPublication, CORREO_USUARIO)
        return response
    }

    suspend fun removeFavorite(idPublication: Int, CORREO_USUARIO: String): ServerResponse {
        val response = api.removeFavorite(idPublication, CORREO_USUARIO)
        return response
    }

    suspend fun addFavoritePublication(idPublication: Int, CORREO_USUARIO: String): ServerResponse {
        val response = api.addFavoritePublication(idPublication, CORREO_USUARIO)
        return response
    }
}