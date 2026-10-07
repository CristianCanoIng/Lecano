package com.example.lecano

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureToolbar()
        configureViewPager()
    }

    private fun configureToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_main)

        val favoritesItem = toolbar.menu.findItem(R.id.action_favorites)
        val favoriteActionView = layoutInflater.inflate(
            R.layout.view_action_favorites,
            toolbar,
            false
        )

        favoritesItem.actionView = favoriteActionView
        favoriteActionView.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out)
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_contact -> {
                    startActivity(Intent(this, ContactActivity::class.java))
                    true
                }

                R.id.action_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    private fun configureViewPager() {
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        viewPager.adapter = MainPagerAdapter(this)
        viewPager.offscreenPageLimit = 1

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val icon = if (position == 0) {
                R.drawable.ic_home_24
            } else {
                R.drawable.ic_profile_24
            }

            tab.icon = ContextCompat.getDrawable(this, icon)
            tab.contentDescription = getString(
                if (position == 0) R.string.tab_home else R.string.tab_profile
            )
        }.attach()
    }
}
