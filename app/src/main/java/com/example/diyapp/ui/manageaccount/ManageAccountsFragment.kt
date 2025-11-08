package com.example.diyapp.ui.manageaccount

import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.databinding.FragmentManageAccountsBinding
import com.example.diyapp.ui.viewmodel.ManageAccountsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import coil.load
import java.io.IOException

@AndroidEntryPoint
class ManageAccountsFragment : Fragment() {

    private var _binding: FragmentManageAccountsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ManageAccountsViewModel by viewModels()
    private var newProfileImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = SessionManager.getUserInfo(requireContext())
        val NOMBRE_USUARIO = sharedPref["NOMBRE_USUARIO"]
        val APELLIDO_USUARIO = sharedPref["APELLIDO_USUARIO"]
        val FOTO_PERFIL_USUARIO_URL = sharedPref["FOTO_PERFIL_USUARIO"]

        with(binding) {
            nameEditText.setText(NOMBRE_USUARIO)
            lastNameEditText.setText(APELLIDO_USUARIO)

            when {
                FOTO_PERFIL_USUARIO_URL?.startsWith("http") == true -> {
                    profileImageView.load(FOTO_PERFIL_USUARIO_URL)
                }
                else -> {
                    try {
                        val bitmap = ImageUtils.base64ToBitmap(FOTO_PERFIL_USUARIO_URL!!)
                        profileImageView.setImageBitmap(bitmap)
                    } catch (_: Exception) {
                        SessionManager.showToast(requireContext(),R.string.error2)
                    }
                }
            }
        }

        binding.logoutButton.setOnClickListener {
            SessionManager.setUserLoggedIn(requireContext(), false)
            SessionManager.showToast(requireContext(), R.string.logoutSuccessful)
            viewModel.clearAllDatabaseTables()
            findNavController().navigate(R.id.exploreFragment)
        }

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let {
                    binding.profileImageView.setImageURI(it)
                    newProfileImageUri = it
                }
            }
        binding.changePhotoButton.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.applyChangesButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        val user = SessionManager.getUserInfo(requireContext())
        val NOMBRE_USUARIO = binding.nameEditText.text.toString().trim()
        val APELLIDO_USUARIO = binding.lastNameEditText.text.toString().trim()
        val NUEVA_CONTRASEÑA_USUARIO = binding.newPasswordEditText.text.toString().trim()
        val CONTRASEÑA_USUARIO = binding.passwordEditText.text.toString().trim()
        val CONFIRMAR_CONTRASEÑA_USUARIO = binding.confirmPasswordEditText.text.toString().trim()
        var FOTO_PERFIL_PARA_API: String
        var FOTO_PERFIL_PARA_API2: String

        if (newProfileImageUri != null) {
            val imageBytes = getBytesFromUri(newProfileImageUri!!)

            if (imageBytes != null) {
                val base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
                FOTO_PERFIL_PARA_API = "data:image/png;base64,$base64String"
                FOTO_PERFIL_PARA_API2 = base64String
            } else {
                FOTO_PERFIL_PARA_API = user["FOTO_PERFIL_USUARIO"] ?: ""
                FOTO_PERFIL_PARA_API2 = user["FOTO_PERFIL_USUARIO"] ?: ""
                }
        } else {
            FOTO_PERFIL_PARA_API = user["FOTO_PERFIL_USUARIO"] ?: ""
            FOTO_PERFIL_PARA_API2 = user["FOTO_PERFIL_USUARIO"] ?: ""
        }

        when {
            NOMBRE_USUARIO.isBlank() || APELLIDO_USUARIO.isBlank() || CONTRASEÑA_USUARIO.isBlank() || CONFIRMAR_CONTRASEÑA_USUARIO.isBlank() -> {
                SessionManager.showToast(requireContext(), R.string.fillFields)
            }

            CONTRASEÑA_USUARIO != CONFIRMAR_CONTRASEÑA_USUARIO -> {
                SessionManager.showToast(requireContext(), R.string.differentPasswords)
            }

            CONTRASEÑA_USUARIO != user["CONTRASEÑA_USUARIO"] -> {
                SessionManager.showToast(requireContext(), R.string.verifyPassword)
            }

            else -> {
                lifecycleScope.launch {
                    if (NUEVA_CONTRASEÑA_USUARIO.isBlank()) {
                        viewModel.updateUser(
                            user["CORREO_USUARIO"]!!, NOMBRE_USUARIO, APELLIDO_USUARIO, user["CONTRASEÑA_USUARIO"]!!, FOTO_PERFIL_PARA_API
                        )
                        SessionManager.setUserLoggedIn(
                            requireContext(),
                            true,
                            user["CORREO_USUARIO"]!!,
                            NOMBRE_USUARIO,
                            APELLIDO_USUARIO,
                            user["CONTRASEÑA_USUARIO"]!!,
                            FOTO_PERFIL_PARA_API2
                        )
                    } else if (SessionManager.isValidPassword(NUEVA_CONTRASEÑA_USUARIO)) {
                        viewModel.updateUser(
                            user["CORREO_USUARIO"]!!, NOMBRE_USUARIO, APELLIDO_USUARIO, NUEVA_CONTRASEÑA_USUARIO, FOTO_PERFIL_PARA_API
                        )
                        SessionManager.setUserLoggedIn(
                            requireContext(),
                            true,
                            user["CORREO_USUARIO"]!!,
                            NOMBRE_USUARIO,
                            APELLIDO_USUARIO,
                            NUEVA_CONTRASEÑA_USUARIO,
                            FOTO_PERFIL_PARA_API2
                        )
                    } else {
                        SessionManager.showToast(requireContext(), R.string.checkPassword)
                        return@launch
                    }
                    SessionManager.showToast(requireContext(), R.string.userUpdated)
                    findNavController().navigate(R.id.exploreFragment)
                }
            }
        }
    }

    private fun getBytesFromUri(uri: Uri): ByteArray? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            inputStream?.readBytes()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}