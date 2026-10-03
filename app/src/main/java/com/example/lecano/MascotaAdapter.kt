package com.example.lecano

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class MascotaAdapter(
    private val mascotas: MutableList<Mascota>,
    private val allowRating: Boolean = true
) : RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mascota, parent, false)
        return MascotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        holder.bind(mascotas[position])
    }

    override fun getItemCount(): Int = mascotas.size

    inner class MascotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageContainer: MaterialCardView =
            itemView.findViewById(R.id.petImageContainer)
        private val petEmoji: TextView = itemView.findViewById(R.id.textPetEmoji)
        private val petName: TextView = itemView.findViewById(R.id.textPetName)
        private val ratingCount: TextView = itemView.findViewById(R.id.textRating)
        private val rateButton: ImageButton = itemView.findViewById(R.id.buttonRate)

        fun bind(mascota: Mascota) {
            val context = itemView.context

            petEmoji.text = mascota.emoji
            petName.text = mascota.nombre
            ratingCount.text = mascota.rating.toString()
            imageContainer.setCardBackgroundColor(
                ContextCompat.getColor(context, mascota.colorRes)
            )

            if (!allowRating) {
                rateButton.visibility = View.GONE
                return
            }

            rateButton.visibility = View.VISIBLE
            rateButton.isEnabled = !mascota.ratedByUser
            rateButton.setImageResource(
                if (mascota.ratedByUser) R.drawable.ic_bone_filled_24
                else R.drawable.ic_bone_outline_24
            )

            val tintColor = if (mascota.ratedByUser) R.color.bone_yellow
            else R.color.text_secondary

            ImageViewCompat.setImageTintList(
                rateButton,
                ColorStateList.valueOf(ContextCompat.getColor(context, tintColor))
            )

            rateButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedPet = mascotas[position]
                    if (!selectedPet.ratedByUser) {
                        selectedPet.rating += 1
                        selectedPet.ratedByUser = true
                        notifyItemChanged(position)
                    }
                }
            }
        }
    }
}
