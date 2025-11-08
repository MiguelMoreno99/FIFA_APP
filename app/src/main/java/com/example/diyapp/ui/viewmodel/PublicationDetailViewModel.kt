package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyapp.R
import com.example.diyapp.data.adapter.explore.FeedAlbum
import com.example.diyapp.data.model.CreationModel
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PublicationDetailViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    val publication = MutableLiveData<CreationModel>()
    val isAddedToFavorites = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<Int?>()

    fun loadPublicationInfo(item: CreationModel) {
        publication.value = item
    }

    fun addFavoritePublication(UUID_JUGADOR: UUID, CORREO_USUARIO: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = useCases.addFavoritePublication(UUID_JUGADOR, CORREO_USUARIO)
            if (response.message.isNotEmpty()) {
                if(response.message == "Ya tienes esta estampa en favoritos!"){
                    errorMessage.postValue(R.string.alreadyFavorite)
                }else{
                    isAddedToFavorites.postValue(true)
                }
            } else {
                errorMessage.postValue(R.string.error2)
            }
        }
    }
}
