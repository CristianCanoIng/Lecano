package com.example.lecano

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class FavoritesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        configureToolbar()
        configureRecyclerView()
    }

    private fun configureToolbar() {
        findViewById<MaterialToolbar>(R.id.toolbarFavorites)
            .setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
    }

    private fun configureRecyclerView() {
        findViewById<RecyclerView>(R.id.recyclerFavorites).apply {
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
            adapter = MascotaAdapter(
                mascotas = MascotaData.favoritePets(),
                allowRating = false
            )
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.fade_out)
    }
}
