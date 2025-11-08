package com.example.diyapp.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.databinding.ActivityPublicationDetailBinding
import com.example.diyapp.ui.viewmodel.PublicationDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import coil.load

@AndroidEntryPoint
class PublicationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublicationDetailBinding
    private lateinit var args: PublicationDetailActivityArgs
    private val viewModel: PublicationDetailViewModel by viewModels()
    private var CORREO_USUARIO: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        args = PublicationDetailActivityArgs.fromBundle(intent.extras!!)
        CORREO_USUARIO = SessionManager.getUserInfo(this)["CORREO_USUARIO"]!!
        viewModel.loadPublicationInfo(args.exploreToDetail)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.publication.observe(this) { item ->
            binding.apply {
                countryImage.load(item.IMG_PAIS_JUGADOR)
                selectionImage.load(item.IMG_SELECCION_JUGADOR)
                playerImage.load(item.IMG_JUGADOR_JUGADOR)
                textViewFullNamePlayer.text = item.NOMBRE_COMPLETO_JUGADOR
                textViewBirthPlayer.text = item.NACIMIENTO_JUGADOR
                textViewPositionPlayer.text = item.POSICION_JUGADOR
                textViewHeightPlayer.text = item.ALTURA_JUGADOR
                textViewCurrentTeamPlayer.text = item.ACTUAL_CLUB_JUGADOR
                textViewFirstTeamPlayer.text = item.PRIMER_CLUB_JUGADOR
                textViewSelectionAchievementsPlayer.text = item.LOGROS_JUGADOR
//                recyclerViewInstructionPhotos.layoutManager = LinearLayoutManager(
//                    this@PublicationDetailActivity, LinearLayoutManager.HORIZONTAL, false
//                )
//                recyclerViewInstructionPhotos.adapter = InstructionsAdapter(item.photoProcess)
            }
        }

        viewModel.isAddedToFavorites.observe(this) { isAdded ->
            if (isAdded) {
                SessionManager.showToast(this, R.string.addedToFavorites)
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                SessionManager.showToast(this, it)
            }
        }
    }

    private fun setupListeners() {
        binding.buttonAddToFavorites.setOnClickListener {
            val item = viewModel.publication.value
            if (SessionManager.isUserLoggedIn(this) && item != null) {
                viewModel.addFavoritePublication(item.UUID_JUGADOR, CORREO_USUARIO)
            } else {
                SessionManager.showToast(this, R.string.loginRequired)
            }
        }
    }
}
