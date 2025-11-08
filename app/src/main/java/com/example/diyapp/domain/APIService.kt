package com.example.diyapp.domain

import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.explore.FeedAlbum
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.data.adapter.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

    //END POINTS USER
    @POST("user/listUser/")
    suspend fun listUser(@Body userEmail: UserEmail): Response<List<User>> //
    @POST("user/insert/")
    suspend fun insertUser(@Body user: User): Response<ServerResponse> //
    @POST("user/modify/")
    suspend fun modifyUser(@Body user: User): Response<List<User>> //
    @POST("user/verify/")
    suspend fun verifyUser(@Body userCredentials: UserCredentials): Response<ServerResponse>
    @POST("user/list/")
    suspend fun listUsers(): Response<List<User>>

    //END POINTS ALBUM
    @GET("album/list/")
    suspend fun getAllPlayers(): Response<List<FeedAlbum>>
    @GET("album/listUser/")
    suspend fun getAllPlayersbyUser(@Body userEmail: UserEmail): Response<List<FeedAlbum>>
    @POST("album/reclamar/")
    suspend fun userRedeemCard(@Body userRedeem: UserRedeem): Response<ServerResponse>

    //END POINTS FAVORITES
    @POST("favorito/list/")
    suspend fun getUserFavorites(@Body userEmail: UserEmail): Response<List<FeedFavorites>>
    @POST("favorito/insertFavorito/")
    suspend fun addToFavorites(@Body userRedeem: UserRedeem): Response<ServerResponse>
    @POST("favorito/eliminar/")
    suspend fun deleteFromFavorites(@Body userRedeem: UserRedeem): Response<ServerResponse>

    //////
//    @POST("album/reclamar/")
//    suspend fun getFeedCreations(@Body userRedeem: UserRedeem): Response<ServerResponse>

//    @POST("album/eliminar/")
//    suspend fun editCreation(@Body userRedeem: UserRedeem): Response<ServerResponse>

//    @POST("favorito/list/")
//    suspend fun deleteCreation(@Body userEmail: UserEmail): Response<List<FeedFavorites>>
}