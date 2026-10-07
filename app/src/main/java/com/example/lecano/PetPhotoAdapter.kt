package com.example.lecano

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class PetPhotoAdapter(
    private val photos: List<PetPhoto>
) : RecyclerView.Adapter<PetPhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photoCard: MaterialCardView =
            itemView.findViewById(R.id.photoCard)
        private val emoji: TextView =
            itemView.findViewById(R.id.textPhotoEmoji)
        private val rating: TextView =
            itemView.findViewById(R.id.textPhotoRating)

        fun bind(photo: PetPhoto) {
            emoji.text = photo.emoji
            rating.text = photo.rating.toString()
            photoCard.setCardBackgroundColor(
                ContextCompat.getColor(itemView.context, photo.colorRes)
            )
        }
    }
}
