package com.example.lecano

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var fullNameLayout: TextInputLayout
    private lateinit var birthDateLayout: TextInputLayout
    private lateinit var phoneLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var descriptionLayout: TextInputLayout

    private lateinit var fullNameInput: TextInputEditText
    private lateinit var birthDateInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var descriptionInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        configureInputs()
        populateFromIntent(intent)

        findViewById<MaterialButton>(R.id.buttonNext).setOnClickListener {
            if (validateForm()) {
                openConfirmation()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        populateFromIntent(intent)
    }

    private fun bindViews() {
        fullNameLayout = findViewById(R.id.layoutFullName)
        birthDateLayout = findViewById(R.id.layoutBirthDate)
        phoneLayout = findViewById(R.id.layoutPhone)
        emailLayout = findViewById(R.id.layoutEmail)
        descriptionLayout = findViewById(R.id.layoutDescription)

        fullNameInput = findViewById(R.id.inputFullName)
        birthDateInput = findViewById(R.id.inputBirthDate)
        phoneInput = findViewById(R.id.inputPhone)
        emailInput = findViewById(R.id.inputEmail)
        descriptionInput = findViewById(R.id.inputDescription)
    }

    private fun configureInputs() {
        birthDateInput.setOnClickListener { showDatePicker() }

        fullNameInput.doAfterTextChanged { fullNameLayout.error = null }
        birthDateInput.doAfterTextChanged { birthDateLayout.error = null }
        phoneInput.doAfterTextChanged { phoneLayout.error = null }
        emailInput.doAfterTextChanged { emailLayout.error = null }
        descriptionInput.doAfterTextChanged { descriptionLayout.error = null }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val currentValue = birthDateInput.text?.toString().orEmpty()
        val parts = currentValue.split("/")

        if (parts.size == 3) {
            val day = parts[0].toIntOrNull()
            val month = parts[1].toIntOrNull()
            val year = parts[2].toIntOrNull()

            if (day != null && month != null && year != null) {
                calendar.set(year, month - 1, day)
            }
        }

        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format(
                    Locale.getDefault(),
                    "%02d/%02d/%04d",
                    dayOfMonth,
                    month + 1,
                    year
                )
                birthDateInput.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
        }.show()
    }

    private fun validateForm(): Boolean {
        clearErrors()

        var isValid = true

        if (fullNameInput.text.isNullOrBlank()) {
            fullNameLayout.error = getString(R.string.required_field)
            isValid = false
        }

        if (birthDateInput.text.isNullOrBlank()) {
            birthDateLayout.error = getString(R.string.required_field)
            isValid = false
        }

        if (phoneInput.text.isNullOrBlank()) {
            phoneLayout.error = getString(R.string.required_field)
            isValid = false
        }

        val email = emailInput.text?.toString()?.trim().orEmpty()
        if (email.isBlank()) {
            emailLayout.error = getString(R.string.required_field)
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = getString(R.string.invalid_email)
            isValid = false
        }

        if (descriptionInput.text.isNullOrBlank()) {
            descriptionLayout.error = getString(R.string.required_field)
            isValid = false
        }

        return isValid
    }

    private fun clearErrors() {
        fullNameLayout.error = null
        birthDateLayout.error = null
        phoneLayout.error = null
        emailLayout.error = null
        descriptionLayout.error = null
    }

    private fun openConfirmation() {
        val confirmationIntent = Intent(this, ConfirmationActivity::class.java).apply {
            putExtra(EXTRA_FULL_NAME, fullNameInput.text?.toString()?.trim().orEmpty())
            putExtra(EXTRA_BIRTH_DATE, birthDateInput.text?.toString().orEmpty())
            putExtra(EXTRA_PHONE, phoneInput.text?.toString()?.trim().orEmpty())
            putExtra(EXTRA_EMAIL, emailInput.text?.toString()?.trim().orEmpty())
            putExtra(EXTRA_DESCRIPTION, descriptionInput.text?.toString()?.trim().orEmpty())
        }

        startActivity(confirmationIntent)
    }

    private fun populateFromIntent(data: Intent) {
        if (!data.hasExtra(EXTRA_FULL_NAME)) return

        fullNameInput.setText(data.getStringExtra(EXTRA_FULL_NAME).orEmpty())
        birthDateInput.setText(data.getStringExtra(EXTRA_BIRTH_DATE).orEmpty())
        phoneInput.setText(data.getStringExtra(EXTRA_PHONE).orEmpty())
        emailInput.setText(data.getStringExtra(EXTRA_EMAIL).orEmpty())
        descriptionInput.setText(data.getStringExtra(EXTRA_DESCRIPTION).orEmpty())
    }

    companion object {
        const val EXTRA_FULL_NAME = "extra_full_name"
        const val EXTRA_BIRTH_DATE = "extra_birth_date"
        const val EXTRA_PHONE = "extra_phone"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_DESCRIPTION = "extra_description"
    }
}
