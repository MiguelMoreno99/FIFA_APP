package com.example.diyapp.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.databinding.ActivityFavoriteDetailBinding
import com.example.diyapp.ui.viewmodel.FavoriteDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import coil.load

@AndroidEntryPoint
class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteDetailBinding
    private val viewModel: FavoriteDetailViewModel by viewModels()
    private lateinit var args: FavoriteDetailActivityArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        args = FavoriteDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedFavoriteItem

        setupObservers()
        viewModel.loadPublicationDetails(item)

        binding.buttonRemoveFromFavorites.setOnClickListener {
            viewModel.removeFavorite(item.UUID_JUGADOR, getEmailFromSession())
        }
    }

    private fun setupObservers() {
        viewModel.countryImage.observe(this) {
            binding.countryImage.load(it)
        }
        viewModel.selectionImage.observe(this) {
            binding.selectionImage.load(it)
        }
        viewModel.playerImage.observe(this) {
            binding.playerImage.load(it)
        }
        viewModel.textViewFullNamePlayer.observe(this) { binding.textViewFullNamePlayer.text = it }
        viewModel.textViewBirthPlayer.observe(this) { binding.textViewBirthPlayer.text = it }
        viewModel.textViewPositionPlayer.observe(this) { binding.textViewPositionPlayer.text = it }
        viewModel.textViewHeightPlayer.observe(this) { binding.textViewHeightPlayer.text = it }
        viewModel.textViewCurrentTeamPlayer.observe(this) { binding.textViewCurrentTeamPlayer.text = it }
        viewModel.textViewFirstTeamPlayer.observe(this) { binding.textViewFirstTeamPlayer.text = it }
        viewModel.textViewSelectionAchievementsPlayer.observe(this) { binding.textViewSelectionAchievementsPlayer.text = it }

//        viewModel.photoProcess.observe(this) {
//            binding.recyclerViewInstructionPhotos.layoutManager = LinearLayoutManager(
//                this, LinearLayoutManager.HORIZONTAL, false
//            )
//            binding.recyclerViewInstructionPhotos.adapter = InstructionsAdapter(it)
//        }
        viewModel.isFavoriteRemoved.observe(this) { isRemoved ->
            if (isRemoved) {
                SessionManager.showToast(this, R.string.removeFavorite)
                finish()
            } else {
                SessionManager.showToast(this, R.string.error2)
            }
        }
    }

    private fun getEmailFromSession(): String {
        val sharedPref = SessionManager.getUserInfo(this)
        return sharedPref["CORREO_USUARIO"] ?: ""
    }
}