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
    val isPublicationCreated = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<Int?>()

    fun setUserEmail(userEmail: String) {
        CORREO_USUARIO = userEmail
    }

    suspend fun createPublication(
        UUID_JUGADOR: UUID,
        CORREO_USUARIO: String
    ) {
//        if (validateFields(title, description, instructions, mainPhoto, photoProcess)) {
            val response = useCases.userRedeemCard(
                UUID_JUGADOR,
                CORREO_USUARIO
            )
            isPublicationCreated.postValue(response.message.isNotEmpty())
//        }
    }

//    private fun validateFields(
//        title: String,
//        description: String,
//        instructions: String,
//        mainPhoto: String,
//        photoProcess: List<String>
//    ): Boolean {
//        return when {
//            title.isEmpty() || description.isEmpty() || instructions.isEmpty() || mainPhoto.isEmpty() -> {
//                errorMessage.postValue(R.string.fillFields)
//                false
//            }
//
//            photoProcess.isEmpty() -> {
//                errorMessage.postValue(R.string.fillFields)
//                false
//            }
//
//            else -> true
//        }
//    }
}