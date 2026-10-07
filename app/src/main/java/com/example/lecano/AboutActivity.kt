package com.example.lecano

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.android.material.appbar.MaterialToolbar

class AboutActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        findViewById<MaterialToolbar>(R.id.toolbarAbout)
            .setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
    }
}
