package com.example.diyapp.data.adapter.creations

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

class FeedCreationsAdapter(
    private var feedCreationsList: List<FeedCreations>,
    private val onClick: (FeedCreations) -> Unit
) :
    RecyclerView.Adapter<FeedCreationsAdapter.FeedCreationsViewHolder>() {

    private var filteredList = feedCreationsList.toMutableList()

    inner class FeedCreationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val urlFlagImage: ImageView = view.findViewById(R.id.ivFlag)
        private val urlSelectionImage: ImageView = view.findViewById(R.id.ivTeamShield)
        private val urlPlayerImage: ImageView = view.findViewById(R.id.ivPlayerImage)
        private val playerPosition: TextView = view.findViewById(R.id.tvPlayerPosition)
        private val playerNumber: TextView = view.findViewById(R.id.tvJerseyNumber)
        private val playerName: TextView = view.findViewById(R.id.tvPlayerName)
        private val playerBirthDate: TextView = view.findViewById(R.id.tvPlayerBirthDate)
        fun render(feedCreationsModel: FeedCreations) {
            urlFlagImage.load(feedCreationsModel.IMG_PAIS_JUGADOR)
            urlSelectionImage.load(feedCreationsModel.IMG_SELECCION_JUGADOR)
            urlPlayerImage.load(feedCreationsModel.IMG_JUGADOR_JUGADOR)
            playerPosition.text = feedCreationsModel.POSICION_ABREVIADO_JUGADOR
            playerNumber.text = feedCreationsModel.NUMERO_JUGADOR.toString()
            playerName.text = feedCreationsModel.NOMBRE_CORTO_JUGADOR
            playerBirthDate.text = feedCreationsModel.NACIMIENTO_CORTO_JUGADOR
            itemView.setOnClickListener { onClick(feedCreationsModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedCreationsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FeedCreationsViewHolder(
            layoutInflater.inflate(
                R.layout.item_feedcreations,
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<FeedCreations>) {
        filteredList.clear()
        filteredList.addAll(newData)
        feedCreationsList = filteredList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteData() {
        filteredList.clear()
        feedCreationsList = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: FeedCreationsViewHolder, position: Int) {
        val item = filteredList[position]
        holder.render(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            feedCreationsList.toMutableList()
        } else {
            feedCreationsList.filter {
                it.NOMBRE_COMPLETO_JUGADOR.contains(query, ignoreCase = true) || it.NOMBRE_PAIS_JUGADOR.contains(
                    query,
                    ignoreCase = true
                )
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}