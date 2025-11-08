package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyapp.data.database.daos.CreationsDao
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageAccountsViewModel @Inject constructor(
    private val useCases: UseCases,
    private val db: CreationsDao
) : ViewModel() {

    suspend fun updateUser(
        CORREO_USUARIO: String,
        NOMBRE_USUARIO: String,
        APELLIDO_USUARIO: String,
        CONTRASEÑA_USUARIO: String,
        FOTO_PERFIL_USUARIO: String
    ) {
        useCases.editUser(CORREO_USUARIO, NOMBRE_USUARIO, APELLIDO_USUARIO, CONTRASEÑA_USUARIO, FOTO_PERFIL_USUARIO)
    }

    fun clearAllDatabaseTables() {
        viewModelScope.launch(Dispatchers.IO) {
            db.deleteAllPublications()
        }
    }
}