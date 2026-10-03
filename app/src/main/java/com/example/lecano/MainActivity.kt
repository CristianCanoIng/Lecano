package com.example.lecano

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var scrollTopButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureToolbar()
        configureScrollTopButton()
        configureRecyclerView()
    }

    private fun configureToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_main)

        val favoritesItem = toolbar.menu.findItem(R.id.action_favorites)
        val actionView = layoutInflater.inflate(
            R.layout.view_action_favorites,
            toolbar,
            false
        )

        favoritesItem.actionView = actionView
        actionView.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out)
        }
    }

    private fun configureRecyclerView() {
        recyclerView = findViewById(R.id.recyclerPets)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MascotaAdapter(MascotaData.allPets())
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)

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

    private fun configureScrollTopButton() {
        scrollTopButton = findViewById(R.id.fabScrollTop)
        scrollTopButton.visibility = View.INVISIBLE
        scrollTopButton.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }
    }
}
