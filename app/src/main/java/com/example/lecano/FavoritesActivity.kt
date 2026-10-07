package com.example.lecano

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class FavoritesActivity : ComponentActivity() {

    private lateinit var databaseHelper: MascotaDatabaseHelper
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        databaseHelper = MascotaDatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerFavorites)

        configureToolbar()
        configureRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        loadLastFivePets()
    }

    private fun configureToolbar() {
        findViewById<MaterialToolbar>(R.id.toolbarFavorites)
            .setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
    }

    private fun configureRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
        loadLastFivePets()
    }

    private fun loadLastFivePets() {
        recyclerView.adapter = MascotaAdapter(
            mascotas = databaseHelper.getLastFivePets(),
            allowRating = false
        )
    }

    override fun onDestroy() {
        databaseHelper.close()
        super.onDestroy()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.fade_out)
    }
}
