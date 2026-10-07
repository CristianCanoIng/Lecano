package com.example.lecano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PetsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var scrollTopButton: FloatingActionButton
    private lateinit var databaseHelper: MascotaDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_pets, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerPets)
        scrollTopButton = view.findViewById(R.id.fabScrollTop)
        databaseHelper = MascotaDatabaseHelper(requireContext())

        val mascotas = MascotaData.allPets()
        val persistedPets = databaseHelper.getSavedPetsById()

        mascotas.forEach { mascota ->
            persistedPets[mascota.id]?.let { persisted ->
                mascota.rating = persisted.rating
                mascota.ratedByUser = persisted.ratedByUser
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MascotaAdapter(
            mascotas = mascotas,
            onPetRated = { mascota ->
                databaseHelper.saveRatedPet(mascota)
            }
        )
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)

        scrollTopButton.hide()
        scrollTopButton.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.canScrollVertically(-1)) {
                    scrollTopButton.show()
                } else {
                    scrollTopButton.hide()
                }
            }
        })
    }

    override fun onDestroyView() {
        if (::databaseHelper.isInitialized) {
            databaseHelper.close()
        }
        super.onDestroyView()
    }
}
