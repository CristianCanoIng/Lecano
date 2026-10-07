package com.example.lecano

import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object MailSender {

    fun sendContactMessage(
        name: String,
        email: String,
        message: String
    ): Result<Unit> = runCatching {
        val user = BuildConfig.SMTP_USER.trim()
        val password = BuildConfig.SMTP_PASSWORD
        val recipient = BuildConfig.SMTP_TO.trim().ifBlank { user }

        require(user.isNotBlank() && password.isNotBlank() && recipient.isNotBlank()) {
            "Configura SMTP_USER, SMTP_PASSWORD y SMTP_TO en local.properties"
        }

        val properties = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", BuildConfig.SMTP_HOST)
            put("mail.smtp.port", BuildConfig.SMTP_PORT)
            put("mail.smtp.connectiontimeout", "10000")
            put("mail.smtp.timeout", "10000")
            put("mail.smtp.writetimeout", "10000")
        }

        val session = Session.getInstance(
            properties,
            object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication =
                    PasswordAuthentication(user, password)
            }
        )

        val mail = MimeMessage(session).apply {
            setFrom(InternetAddress(user, "Petagram"))
            setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
            subject = "Comentario desde Petagram - $name"
            setText(
                """
                Nombre: $name
                Correo: $email

                Mensaje:
                $message
                """.trimIndent(),
                "UTF-8"
            )
        }

        Transport.send(mail)
    }
}
