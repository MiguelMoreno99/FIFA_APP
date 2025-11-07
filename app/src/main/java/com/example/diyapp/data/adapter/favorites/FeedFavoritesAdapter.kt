package com.example.diyapp.data.adapter.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.ImageUtils

class FeedFavoritesAdapter(
    private var feedFavoritesList: List<FeedFavorites>,
    private val onClick: (FeedFavorites) -> Unit
) :
    RecyclerView.Adapter<FeedFavoritesAdapter.FeedFavoritesViewHolder>() {

    private var filteredList = feedFavoritesList.toMutableList()

    inner class FeedFavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val urlFlagImage: ImageView = view.findViewById(R.id.ivFlag)
        private val urlSelectionImage: ImageView = view.findViewById(R.id.ivTeamShield)
        private val urlPlayerImage: ImageView = view.findViewById(R.id.ivPlayerImage)
        private val playerPosition: TextView = view.findViewById(R.id.tvPlayerPosition)
        private val playerNumber: TextView = view.findViewById(R.id.tvJerseyNumber)
        private val playerName: TextView = view.findViewById(R.id.tvPlayerName)
        private val playerBirthDate: TextView = view.findViewById(R.id.tvPlayerBirthDate)
        fun render(feedFavoritesModel: FeedFavorites) {
            urlFlagImage.load(feedFavoritesModel.IMG_PAIS_JUGADOR)
            urlSelectionImage.load(feedFavoritesModel.IMG_SELECCION_JUGADOR)
            urlPlayerImage.load(feedFavoritesModel.IMG_JUGADOR_JUGADOR)
            playerPosition.text = feedFavoritesModel.POSICION_ABREVIADO_JUGADOR
            playerNumber.text = feedFavoritesModel.NUMERO_JUGADOR.toString()
            playerName.text = feedFavoritesModel.NOMBRE_CORTO_JUGADOR
            playerBirthDate.text = feedFavoritesModel.NACIMIENTO_CORTO_JUGADOR
            itemView.setOnClickListener { onClick(feedFavoritesModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedFavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FeedFavoritesViewHolder(
            layoutInflater.inflate(
                R.layout.item_feedfavorites,
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<FeedFavorites>) {
        filteredList.clear()
        filteredList.addAll(newData)
        feedFavoritesList = filteredList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteData() {
        filteredList.clear()
        feedFavoritesList = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: FeedFavoritesViewHolder, position: Int) {
        val item = filteredList[position]
        holder.render(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            feedFavoritesList.toMutableList()
        } else {
            feedFavoritesList.filter {
                it.NOMBRE_COMPLETO_JUGADOR.contains(query, ignoreCase = true) || it.NOMBRE_PAIS_JUGADOR.contains(
                    query,
                    ignoreCase = true
                )
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}