package com.example.diyapp.data.adapter.explore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.model.CreationModel
import coil.load

class FeedExploreAdapter(
    private var feedExplorerList: List<CreationModel>,
    private val onClick: (CreationModel) -> Unit
) :
    RecyclerView.Adapter<FeedExploreAdapter.FeedExploreViewHolder>() {

    private var filteredList = feedExplorerList.toMutableList()

    inner class FeedExploreViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val urlFlagImage: ImageView = view.findViewById(R.id.ivFlag)
        private val urlSelectionImage: ImageView = view.findViewById(R.id.ivTeamShield)
        private val urlPlayerImage: ImageView = view.findViewById(R.id.ivPlayerImage)
        private val playerPosition: TextView = view.findViewById(R.id.tvPlayerPosition)
        private val playerNumber: TextView = view.findViewById(R.id.tvJerseyNumber)
        private val playerName: TextView = view.findViewById(R.id.tvPlayerName)
        private val playerBirthDate: TextView = view.findViewById(R.id.tvPlayerBirthDate)
        fun render(feedExploreModel: CreationModel) {
            urlFlagImage.load(feedExploreModel.IMG_PAIS_JUGADOR)
            urlSelectionImage.load(feedExploreModel.IMG_SELECCION_JUGADOR)
            urlPlayerImage.load(feedExploreModel.IMG_JUGADOR_JUGADOR)
            playerPosition.text = feedExploreModel.POSICION_ABREVIADO_JUGADOR
            playerNumber.text = feedExploreModel.NUMERO_JUGADOR.toString()
            playerName.text = feedExploreModel.NOMBRE_CORTO_JUGADOR
            playerBirthDate.text = feedExploreModel.NACIMIENTO_CORTO_JUGADOR
            itemView.setOnClickListener { onClick(feedExploreModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedExploreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FeedExploreViewHolder(
            layoutInflater.inflate(
                R.layout.item_feedexplore,
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<CreationModel>) {
        filteredList.clear()
        filteredList.addAll(newData)
        feedExplorerList = filteredList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteData() {
        filteredList.clear()
        feedExplorerList = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: FeedExploreViewHolder, position: Int) {
        val item = filteredList[position]
        holder.render(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            feedExplorerList.toMutableList()
        } else {
            feedExplorerList.filter {
                it.NOMBRE_COMPLETO_JUGADOR.contains(query, ignoreCase = true) || it.NOMBRE_PAIS_JUGADOR.contains(
                    query,
                    ignoreCase = true
                )
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}