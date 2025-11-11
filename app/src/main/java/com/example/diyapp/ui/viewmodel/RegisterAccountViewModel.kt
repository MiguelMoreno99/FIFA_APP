package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class RegisterAccountViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    val errorMessage = MutableLiveData<Int?>()
    val isUserRegistered = MutableLiveData<Boolean>()

    suspend fun registerAccount(
        CORREO_USUARIO: String,
        NOMBRE_USUARIO: String,
        APELLIDO_USUARIO: String,
        CONTRASEÑA_USUARIO: String,
        CONFIRMAR_CONTRASEÑA_USUARIO: String,
        FOTO_PERFIL_USUARIO: String?
    ) {
        if (validateFields(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, CONFIRMAR_CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)) {
            checkIfUserExists(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO!!)
        }
    }

    private fun validateFields(
        CORREO_USUARIO: String,
        NOMBRE_USUARIO: String,
        APELLIDO_USUARIO: String,
        CONTRASEÑA_USUARIO: String,
        CONFIRMAR_CONTRASEÑA_USUARIO: String,
        FOTO_PERFIL_USUARIO: String?
    ): Boolean {
        return when {
            CORREO_USUARIO.isEmpty() || NOMBRE_USUARIO.isEmpty() || APELLIDO_USUARIO.isEmpty() || CONTRASEÑA_USUARIO.isEmpty() || CONFIRMAR_CONTRASEÑA_USUARIO.isEmpty() || FOTO_PERFIL_USUARIO == null || FOTO_PERFIL_USUARIO == ""-> {
                errorMessage.postValue(R.string.fillFields)
                false
            }

            CONTRASEÑA_USUARIO != CONFIRMAR_CONTRASEÑA_USUARIO -> {
                errorMessage.postValue(R.string.differentPasswords)
                false
            }

            !SessionManager.isValidEmail(CORREO_USUARIO) ->{
                errorMessage.postValue(R.string.checkEmail)
                false
            }

            !SessionManager.isValidPassword(CONTRASEÑA_USUARIO) ->{
                errorMessage.postValue(R.string.checkPassword)
                false
            }

            else -> true
        }
    }

    private suspend fun checkIfUserExists(
        CORREO_USUARIO: String,
        NOMBRE_USUARIO: String,
        APELLIDO_USUARIO: String,
        CONTRASEÑA_USUARIO: String,
        FOTO_PERFIL_USUARIO: String
    ) {
        val response = useCases.getUser(CORREO_USUARIO)
        if (response.isEmpty()) {
            registerUser(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)
        } else {
            errorMessage.postValue(R.string.userAlreadyExists)
        }
    }

    private suspend fun registerUser(
        CORREO_USUARIO: String,
        NOMBRE_USUARIO: String,
        APELLIDO_USUARIO: String,
        CONTRASEÑA_USUARIO: String,
        FOTO_PERFIL_USUARIO: String
    ) {
        val response = useCases.registerUser(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)
        if (response.message.isNotEmpty()) {
            isUserRegistered.postValue(true)
        } else {
            errorMessage.postValue(R.string.error2)
        }
    }
}