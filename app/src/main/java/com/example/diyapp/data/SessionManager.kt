package com.example.diyapp.data

import android.content.Context
import android.widget.Toast
import com.example.diyapp.R

object SessionManager {
    private const val PREF_NAME = "AppPreferences"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    private const val KEY_EMAIL = "CORREO_USUARIO"
    private const val KEY_NAME = "NOMBRE_USUARIO"
    private const val KEY_LASTNAME = "APELLIDO_USUARIO"
    private const val KEY_PASSWORD = "CONTRASEÑA_USUARIO"
    private const val KEY_PHOTO = "FOTO_PERFIL_USUARIO"

    fun isUserLoggedIn(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setUserLoggedIn(
        context: Context,
        loggedIn: Boolean,
        CORREO_USUARIO: String = "",
        NOMBRE_USUARIO: String = "",
        APELLIDO_USUARIO: String = "",
        CONTRASEÑA_USUARIO: String = "",
        FOTO_PERFIL_USUARIO: String = ""
    ) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(KEY_IS_LOGGED_IN, loggedIn)
            putString(KEY_EMAIL, CORREO_USUARIO)
            putString(KEY_NAME, NOMBRE_USUARIO)
            putString(KEY_LASTNAME, APELLIDO_USUARIO)
            putString(KEY_PASSWORD, CONTRASEÑA_USUARIO)
            putString(KEY_PHOTO, FOTO_PERFIL_USUARIO)
            apply()
        }
    }

    fun getUserInfo(context: Context): Map<String, String> {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return mapOf(
            "CORREO_USUARIO" to (sharedPref.getString(KEY_EMAIL, "") ?: ""),
            "NOMBRE_USUARIO" to (sharedPref.getString(KEY_NAME, "") ?: ""),
            "APELLIDO_USUARIO" to (sharedPref.getString(KEY_LASTNAME, "") ?: ""),
            "CONTRASEÑA_USUARIO" to (sharedPref.getString(KEY_PASSWORD, "") ?: ""),
            "FOTO_PERFIL_USUARIO" to (sharedPref.getString(KEY_PHOTO, "") ?: "")
        )
    }

    fun showToast(context: Context, message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun validateInputs(context: Context, CORREO_USUARIO: String, CONTRASEÑA_USUARIO: String): Boolean {
        val isEmailValid = isValidEmail(CORREO_USUARIO)
        val isPasswordValid = isValidPassword(CONTRASEÑA_USUARIO)

        if (!isEmailValid) {
            showToast(context, R.string.checkEmail)
            return false
        }
        if (!isPasswordValid) {
            showToast(context, R.string.checkPassword)
            return false
        }
        return true
    }

    private fun isValidEmail(CORREO_USUARIO: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return CORREO_USUARIO.matches(emailPattern.toRegex())
    }

    fun isValidPassword(CONTRASEÑA_USUARIO: String): Boolean {
        val passwordPattern =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
        return CONTRASEÑA_USUARIO.matches(passwordPattern.toRegex())
    }
}
