package com.example.diyapp.data.adapter.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.ImageUtils

class FeedFavoritesAdapter(
    private var feedFavoritesList: List<FeedFavorites>,
    private val onClick: (FeedFavorites) -> Unit
) :
    RecyclerView.Adapter<FeedFavoritesAdapter.FeedFavoritesViewHolder>() {

    private var filteredList = feedFavoritesList.toMutableList()

    inner class FeedFavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val userName: TextView = view.findViewById(R.id.tvUserName)
        private val category: TextView = view.findViewById(R.id.tvCategory)
        private val title: TextView = view.findViewById(R.id.tvTitle)
        private val likesCountNumber: TextView = view.findViewById(R.id.tvLikesCountNumber)
        private val creationDate: TextView = view.findViewById(R.id.tvCreationDate)
        private val photoMain: ImageView = view.findViewById(R.id.ivMainImage)
        fun render(feedFavoritesModel: FeedFavorites) {
            userName.text = feedFavoritesModel.email
            category.text = feedFavoritesModel.theme
            title.text = feedFavoritesModel.title
            likesCountNumber.text = feedFavoritesModel.numLikes.toString()
            creationDate.text = feedFavoritesModel.dateCreation

            photoMain.setImageBitmap(ImageUtils.base64ToBitmap(feedFavoritesModel.photoMain))

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
                it.theme.contains(query, ignoreCase = true) || it.title.contains(
                    query,
                    ignoreCase = true
                )
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}