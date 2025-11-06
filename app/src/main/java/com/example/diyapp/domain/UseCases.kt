package com.example.diyapp.domain

import com.example.diyapp.data.MainRepository
import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.data.adapter.user.User
import com.example.diyapp.data.database.entities.toDatabase
import com.example.diyapp.data.model.CreationModel
import com.example.diyapp.data.model.UserModel
import javax.inject.Inject

class UseCases @Inject constructor(
    private val repository: MainRepository
) {

    suspend fun getFeedExplore(): List<CreationModel> {
        val feed = repository.getFeedExploreFromApi()
        return if (feed.isNotEmpty()) {
            repository.clearPublications()
            repository.insertCreations(feed.map { it.toDatabase() })
            feed
        } else {
            repository.getFeedExploreFromDataBase()
        }
    }

    suspend fun getUser(CORREO_USUARIO: String): List<UserModel> {
        val users = repository.getUserFromApi(CORREO_USUARIO)
        return if (users.isNotEmpty()) {
            repository.clearUsers()
            repository.insertUsers(users.map { it.toDatabase() })
            users
        } else {
            repository.getUserFromDataBase(CORREO_USUARIO)
        }
    }

    suspend fun getFeedFavorite(email: String): List<FeedFavorites> {
        return repository.getFeedFavorite(email)
    }

    suspend fun getFeedCreations(email: String): List<FeedCreations> {
        return repository.getFeedCreations(email)
    }

    suspend fun editUser(
        CORREO_USUARIO: String,
        NOMBRE_USUARIO: String,
        APELLIDO_USUARIO: String,
        CONTRASEÑA_USUARIO: String,
        FOTO_PERFIL_USUARIO: String
    ): List<User> {
        return repository.editUser(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)
    }

    suspend fun registerUser(
        CORREO_USUARIO: String,
        NOMBRE_USUARIO: String,
        APELLIDO_USUARIO: String,
        CONTRASEÑA_USUARIO: String,
        FOTO_PERFIL_USUARIO: String
    ): ServerResponse {
        return repository.registerUser(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)
    }

    suspend fun createPublication(
        email: String,
        title: String,
        theme: String,
        photoMain: String,
        description: String,
        instructions: String,
        photoProcess: List<String>
    ): ServerResponse {
        return repository.createPublication(
            email,
            title,
            theme,
            photoMain,
            description,
            instructions,
            photoProcess
        )
    }

    suspend fun editPublication(
        idPublication: Int,
        email: String,
        title: String,
        theme: String,
        photoMain: String,
        description: String,
        instructions: String,
        photoProcess: List<String>
    ): ServerResponse {
        return repository.editPublication(
            idPublication,
            email,
            title,
            theme,
            photoMain,
            description,
            instructions,
            photoProcess
        )
    }

    suspend fun deletePublication(idPublication: Int, email: String): ServerResponse {
        return repository.deletePublication(idPublication, email)
    }

    suspend fun removeFavorite(idPublication: Int, email: String): ServerResponse {
        return repository.removeFavorite(idPublication, email)
    }

    suspend fun addFavoritePublication(idPublication: Int, email: String): ServerResponse {
        return repository.addFavoritePublication(idPublication, email)
    }
}