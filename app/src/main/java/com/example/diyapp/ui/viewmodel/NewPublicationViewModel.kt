package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diyapp.R
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NewPublicationViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    var CORREO_USUARIO: String = ""
    val isCardAdded = MutableLiveData<Boolean>()
    val cardMessage = MutableLiveData<String>()

    fun setUserEmail(userEmail: String) {
        CORREO_USUARIO = userEmail
    }

    suspend fun userRedeemCard(
        UUID_JUGADOR: UUID
    ) {
            val response = useCases.userRedeemCard(
                UUID_JUGADOR,
                CORREO_USUARIO
            )
        if (response.message == "Estampa reclamada exitosamente"){
            isCardAdded.postValue(true)
        }else if (response.message == "Ya tienes esta estampa!"){
            isCardAdded.postValue(true)
        }else if (response.message == "Esta estampa ya está reclamada por alguien más."){
            isCardAdded.postValue(true)
        }else{
            isCardAdded.postValue(false)
        }
        cardMessage.postValue(response.message)
    }
}