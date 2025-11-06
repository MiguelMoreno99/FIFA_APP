package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diyapp.data.model.UserModel
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    val loginSuccess = MutableLiveData<Boolean>()

    suspend fun validateUser(CORREO_USUARIO: String, CONTRASEÑA_USUARIO: String) {
        val response = useCases.getUser(CORREO_USUARIO)
        if (response.isNotEmpty() && response[0].CONTRASEÑA_USUARIO == CONTRASEÑA_USUARIO) {
            loginSuccess.postValue(true)
        } else {
            loginSuccess.postValue(false)
        }
    }

    suspend fun getUserData(CORREO_USUARIO: String): UserModel {
        return useCases.getUser(CORREO_USUARIO)[0]
    }
}