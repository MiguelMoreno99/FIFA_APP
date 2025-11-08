package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.load
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FavoriteDetailViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    val countryImage = MutableLiveData<String>()
    val selectionImage = MutableLiveData<String>()
    val playerImage = MutableLiveData<String>()
    val textViewFullNamePlayer = MutableLiveData<String>()
    val textViewBirthPlayer = MutableLiveData<String>()
    val textViewPositionPlayer = MutableLiveData<String>()
    val textViewHeightPlayer = MutableLiveData<String>()
    val textViewCurrentTeamPlayer = MutableLiveData<String>()
    val textViewFirstTeamPlayer = MutableLiveData<String>()
    val textViewSelectionAchievementsPlayer = MutableLiveData<String>()


    val isFavoriteRemoved = MutableLiveData<Boolean>()

    fun loadPublicationDetails(item: FeedFavorites) {
        countryImage.value = item.IMG_PAIS_JUGADOR
        selectionImage.value =item.IMG_SELECCION_JUGADOR
        playerImage.value = item.IMG_JUGADOR_JUGADOR
        textViewFullNamePlayer.value  = item.NOMBRE_COMPLETO_JUGADOR
        textViewBirthPlayer.value  = item.NACIMIENTO_JUGADOR
        textViewPositionPlayer.value  = item.POSICION_JUGADOR
        textViewHeightPlayer.value = item.ALTURA_JUGADOR
        textViewCurrentTeamPlayer.value  = item.ACTUAL_CLUB_JUGADOR
        textViewFirstTeamPlayer.value =  item.PRIMER_CLUB_JUGADOR
        textViewSelectionAchievementsPlayer.value = item.LOGROS_JUGADOR
    }

    fun removeFavorite(UUID_JUGADOR: UUID, CORREO_USUARIO: String) {
        viewModelScope.launch {
            val response = useCases.removeFavorite(UUID_JUGADOR, CORREO_USUARIO)
            isFavoriteRemoved.value = response.message.isNotEmpty()
        }
    }
}