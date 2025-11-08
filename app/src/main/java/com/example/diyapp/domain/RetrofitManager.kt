package com.example.diyapp.domain

import android.util.Log
import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.explore.FeedAlbum
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.data.adapter.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class RetrofitManager @Inject constructor(private val apiService: APIService) {
    //USER
    suspend fun getUser(CORREO_USUARIO: String): List<User> {
        return withContext(Dispatchers.IO) {
            try {
                val user = UserEmail(CORREO_USUARIO)
                val call = apiService.listUser(user)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
    suspend fun registerUser(
        CORREO_USUARIO: String,
        NOMBRE_USUARIO: String,
        APELLIDO_USUARIO: String,
        CONTRASEÑA_USUARIO: String,
        FOTO_PERFIL_USUARIO: String
    ): ServerResponse {

        return withContext(Dispatchers.IO) {
            try {
                val user = User(
                    CORREO_USUARIO,
                    NOMBRE_USUARIO,
                    APELLIDO_USUARIO,
                    CONTRASEÑA_USUARIO,
                    FOTO_PERFIL_USUARIO
                )
                val call = apiService.insertUser(user)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: ServerResponse("")
            } catch (e: Exception) {
                ServerResponse("")
            }
        }
    }
    suspend fun editUser(
        CORREO_USUARIO: String,
        NOMBRE_USUARIO: String,
        APELLIDO_USUARIO: String,
        CONTRASEÑA_USUARIO: String,
        FOTO_PERFIL_USUARIO: String
    ): List<User> {
        return withContext(Dispatchers.IO) {
            try {
                val user = User(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)
                val call = apiService.modifyUser(user)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    //ALBUM
    suspend fun getAllPlayers(): List<FeedAlbum> {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService.getAllPlayers()
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
    suspend fun userRedeemCard(
        UUID_JUGADOR: UUID,
        CORREO_USUARIO: String
    ): ServerResponse {
        return withContext(Dispatchers.IO) {
            try {
                val creation = UserRedeem(
                    UUID_JUGADOR,
                    CORREO_USUARIO
                )
                val call = apiService.userRedeemCard(creation)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: ServerResponse("")
            } catch (e: Exception) {
                ServerResponse("")
            }
        }
    }

    suspend fun getUserFavorites(CORREO_USUARIO: String): List<FeedFavorites> {
        return withContext(Dispatchers.IO) {
            try {
                val user = UserEmail(CORREO_USUARIO)
                val call = apiService.getUserFavorites(user)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

//    suspend fun getFeedCreations(UUID_JUGADOR: UUID, CORREO_USUARIO: String): ServerResponse {
//        return withContext(Dispatchers.IO) {
//            try {
//                val user = UserRedeem(UUID_JUGADOR, CORREO_USUARIO)
//                val call = apiService.getFeedCreations(user)
//                val responseBody = call.body()
//                Log.d("API Response", "Server Response: $responseBody")
//                responseBody ?: ServerResponse("")
//            } catch (e: Exception) {
//                ServerResponse("")
//            }
//        }
//    }


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
//        LOGROS_JUGADOR: String,
//    ): ServerResponse {
//        return withContext(Dispatchers.IO) {
//            try {
//                val creation = UserEditPublication(
//                    UUID_JUGADOR,
//                    IMG_PAIS_JUGADOR,
//                    NOMBRE_PAIS_JUGADOR,
//                    NOMBRE_ABREVIADO_PAIS_JUGADOR,
//                    IMG_SELECCION_JUGADOR,
//                    IMG_JUGADOR_JUGADOR,
//                    NOMBRE_SELECCION_JUGADOR,
//                    POSICION_JUGADOR,
//                    POSICION_ABREVIADO_JUGADOR,
//                    NUMERO_JUGADOR,
//                    NOMBRE_COMPLETO_JUGADOR,
//                    NOMBRE_CORTO_JUGADOR,
//                    NACIMIENTO_CORTO_JUGADOR,
//                    NACIMIENTO_JUGADOR,
//                    ALTURA_JUGADOR,
//                    ACTUAL_CLUB_JUGADOR,
//                    PRIMER_CLUB_JUGADOR,
//                    LOGROS_JUGADOR
//                )
//                val call = apiService.editCreation(creation)
//                val responseBody = call.body()
//                Log.d("API Response", "Server Response: $responseBody")
//                responseBody ?: ServerResponse("")
//            } catch (e: Exception) {
//                ServerResponse("")
//            }
//        }
//    }

//    suspend fun deletePublication(UUID_JUGADOR: UUID, CORREO_USUARIO: String): ServerResponse {
//        return withContext(Dispatchers.IO) {
//            try {
//                val idResponse = IdResponse(UUID_JUGADOR, CORREO_USUARIO)
//                val call = apiService.deleteCreation(idResponse)
//                val responseBody = call.body()
//                Log.d("API Response", "Server Response: $responseBody")
//                responseBody ?: ServerResponse("")
//            } catch (e: Exception) {
//                ServerResponse("")
//            }
//        }
//    }

    suspend fun removeFavorite(UUID_JUGADOR: UUID, CORREO_USUARIO: String): ServerResponse {
        return withContext(Dispatchers.IO) {
            try {
                val idResponse = UserRedeem(UUID_JUGADOR, CORREO_USUARIO)
                val call = apiService.deleteFromFavorites(idResponse)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: ServerResponse("")
            } catch (e: Exception) {
                ServerResponse("")
            }
        }
    }

    suspend fun addFavoritePublication(UUID_JUGADOR: UUID, CORREO_USUARIO: String): ServerResponse {
        return withContext(Dispatchers.IO) {
            try {
                val idResponse = UserRedeem(UUID_JUGADOR, CORREO_USUARIO)
                val call = apiService.addToFavorites(idResponse)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: ServerResponse("")
            } catch (e: Exception) {
                ServerResponse("")
            }
        }
    }
}