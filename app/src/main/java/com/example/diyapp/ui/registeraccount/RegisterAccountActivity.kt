package com.example.diyapp.ui.registeraccount

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.lifecycle.lifecycleScope
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.databinding.ActivityRegisterAccountBinding
import com.example.diyapp.ui.viewmodel.RegisterAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class RegisterAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterAccountBinding
    private var imageSet = false
    private val viewModel: RegisterAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupImagePicker()
        setupListeners()
        observeViewModel()
    }

    private fun setupImagePicker() {
        val photoFile = File(externalCacheDir, "temp_photo.jpg")
        val photoUri = FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.provider",
            photoFile
        )

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val uri = result.data?.data ?: photoUri
                    if (uri != null) {
                        binding.profileImageView.setImageURI(uri)
                        imageSet = true
                    } else {
                        SessionManager.showToast(this, R.string.error2)
                    }
                }
            }

        binding.changePhotoButton.setOnClickListener {
            // Create intents for camera and gallery
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            }

            val galleryIntent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }

            // Combine the intents in a chooser
            val chooserIntent = Intent.createChooser(galleryIntent, "Select Photo").apply {
                putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
            }

            // Launch the chooser
            pickMedia.launch(chooserIntent)
        }
    }

    private fun setupListeners() {
        binding.registerButton.setOnClickListener {
            val email = binding.mailEditText.text.toString().trim()
            val name = binding.nameEditText.text.toString().trim()
            val lastname = binding.lastNameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            val imageBlob = if (imageSet) {
                ImageUtils.bitmapToBase64(binding.profileImageView.drawToBitmap())
            } else {
                null
            }
            lifecycleScope.launch {
                viewModel.registerAccount(
                    email,
                    name,
                    lastname,
                    password,
                    confirmPassword,
                    imageBlob
                )
            }
        }
    }

    private fun observeViewModel() {
        viewModel.errorMessage.observe(this) { messageResId ->
            messageResId?.let {
                SessionManager.showToast(this, it)
            }
        }

        viewModel.isUserRegistered.observe(this) { isRegistered ->
            if (isRegistered) {
                SessionManager.showToast(this, R.string.userCreated)
                finish()
            }
        }
    }
}