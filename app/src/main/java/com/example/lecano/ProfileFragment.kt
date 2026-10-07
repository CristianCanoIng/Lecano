package com.example.lecano

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularimageview.CircularImageView

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val profileImage = view.findViewById<CircularImageView>(R.id.imagePetProfile)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerPetPhotos)

        profileImage.setImageBitmap(createPetAvatar("🐶"))

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = PetPhotoAdapter(dummyPhotos())
        recyclerView.setHasFixedSize(true)
    }

    private fun dummyPhotos(): List<PetPhoto> = listOf(
        PetPhoto("🐶", R.color.pet_blue, 5),
        PetPhoto("🐕", R.color.pet_pink, 0),
        PetPhoto("🐶", R.color.pet_orange, 3),
        PetPhoto("🦮", R.color.pet_green, 10),
        PetPhoto("🐕", R.color.pet_purple, 2),
        PetPhoto("🐶", R.color.pet_teal, 3),
        PetPhoto("🐩", R.color.pet_peach, 4),
        PetPhoto("🐶", R.color.pet_gray, 6),
        PetPhoto("🐕", R.color.pet_blue, 1)
    )

    private fun createPetAvatar(emoji: String): Bitmap {
        val size = 320
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(ContextCompat.getColor(requireContext(), R.color.pet_blue))

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 190f
            textAlign = Paint.Align.CENTER
        }

        val metrics = paint.fontMetrics
        val baseline = size / 2f - (metrics.ascent + metrics.descent) / 2f
        canvas.drawText(emoji, size / 2f, baseline, paint)

        return bitmap
    }
}
