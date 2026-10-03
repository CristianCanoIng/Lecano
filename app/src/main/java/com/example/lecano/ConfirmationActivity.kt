package com.example.lecano

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.android.material.button.MaterialButton

class ConfirmationActivity : ComponentActivity() {

    private var fullName: String = ""
    private var birthDate: String = ""
    private var phone: String = ""
    private var email: String = ""
    private var description: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        readContactData()
        showContactData()
        animateEntrance()

        findViewById<MaterialButton>(R.id.buttonEditData).setOnClickListener {
            returnToEdit()
        }
    }

    private fun animateEntrance() {
        val views = listOf(
            findViewById<View>(R.id.textConfirmationEyebrow),
            findViewById<View>(R.id.textConfirmationTitle),
            findViewById<View>(R.id.textConfirmationSubtitle),
            findViewById<View>(R.id.confirmationCard),
            findViewById<View>(R.id.buttonEditData),
            findViewById<View>(R.id.textConfirmationStep)
        )

        views.forEachIndexed { index, view ->
            view.alpha = 0f
            view.translationY = 24f
            view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(index * 65L)
                .setDuration(340L)
                .start()
        }
    }

    private fun readContactData() {
        fullName = intent.getStringExtra(MainActivity.EXTRA_FULL_NAME).orEmpty()
        birthDate = intent.getStringExtra(MainActivity.EXTRA_BIRTH_DATE).orEmpty()
        phone = intent.getStringExtra(MainActivity.EXTRA_PHONE).orEmpty()
        email = intent.getStringExtra(MainActivity.EXTRA_EMAIL).orEmpty()
        description = intent.getStringExtra(MainActivity.EXTRA_DESCRIPTION).orEmpty()
    }

    private fun showContactData() {
        findViewById<TextView>(R.id.textFullNameValue).text = fullName
        findViewById<TextView>(R.id.textBirthDateValue).text = birthDate
        findViewById<TextView>(R.id.textPhoneValue).text = phone
        findViewById<TextView>(R.id.textEmailValue).text = email
        findViewById<TextView>(R.id.textDescriptionValue).text = description
    }

    private fun returnToEdit() {
        val editIntent = Intent(this, MainActivity::class.java).apply {
            putExtra(MainActivity.EXTRA_FULL_NAME, fullName)
            putExtra(MainActivity.EXTRA_BIRTH_DATE, birthDate)
            putExtra(MainActivity.EXTRA_PHONE, phone)
            putExtra(MainActivity.EXTRA_EMAIL, email)
            putExtra(MainActivity.EXTRA_DESCRIPTION, description)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        startActivity(editIntent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.fade_out)
        finish()
    }
}
