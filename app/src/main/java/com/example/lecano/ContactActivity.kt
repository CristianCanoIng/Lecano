package com.example.lecano

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ContactActivity : ComponentActivity() {

    private lateinit var nameLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var messageLayout: TextInputLayout

    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var messageInput: TextInputEditText
    private lateinit var sendButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        findViewById<MaterialToolbar>(R.id.toolbarContact)
            .setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

        nameLayout = findViewById(R.id.layoutContactName)
        emailLayout = findViewById(R.id.layoutContactEmail)
        messageLayout = findViewById(R.id.layoutContactMessage)

        nameInput = findViewById(R.id.inputContactName)
        emailInput = findViewById(R.id.inputContactEmail)
        messageInput = findViewById(R.id.inputContactMessage)
        sendButton = findViewById(R.id.buttonSendComment)

        sendButton.setOnClickListener {
            if (validateForm()) {
                sendComment()
            }
        }
    }

    private fun validateForm(): Boolean {
        nameLayout.error = null
        emailLayout.error = null
        messageLayout.error = null

        val name = nameInput.text?.toString()?.trim().orEmpty()
        val email = emailInput.text?.toString()?.trim().orEmpty()
        val message = messageInput.text?.toString()?.trim().orEmpty()

        var valid = true

        if (name.isBlank()) {
            nameLayout.error = getString(R.string.required_field)
            valid = false
        }

        if (email.isBlank()) {
            emailLayout.error = getString(R.string.required_field)
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = getString(R.string.invalid_email)
            valid = false
        }

        if (message.isBlank()) {
            messageLayout.error = getString(R.string.required_field)
            valid = false
        }

        return valid
    }

    private fun sendComment() {
        val name = nameInput.text?.toString()?.trim().orEmpty()
        val email = emailInput.text?.toString()?.trim().orEmpty()
        val message = messageInput.text?.toString()?.trim().orEmpty()

        sendButton.isEnabled = false
        sendButton.text = getString(R.string.sending_comment)

        Thread {
            val result = MailSender.sendContactMessage(name, email, message)

            runOnUiThread {
                sendButton.isEnabled = true
                sendButton.text = getString(R.string.send_comment)

                result.onSuccess {
                    nameInput.text?.clear()
                    emailInput.text?.clear()
                    messageInput.text?.clear()

                    Snackbar.make(
                        findViewById(R.id.contactRoot),
                        R.string.comment_sent,
                        Snackbar.LENGTH_LONG
                    ).show()
                }.onFailure { error ->
                    Snackbar.make(
                        findViewById(R.id.contactRoot),
                        error.message ?: getString(R.string.comment_send_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }.start()
    }
}
