package com.kosalaamInc.kosalaam.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendMailVerification{

    fun sendEmail(
        title: String,
        body: String,
        dest: String,
        accessPassword : String
    )
    {
        val username = "kosalaamapp@gmail.com"
        val password =accessPassword
        val props = Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com")

        // 비밀번호 인증으로 세션 생성
        val session = Session.getInstance(props,
            object: javax.mail.Authenticator() {
                override  fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(username, password)
                }
            })

        val message = MimeMessage(session)
        message.setFrom(InternetAddress(username))
        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(dest))
        message.subject = title
        message.setText(body)

        // 전송
        CoroutineScope(Dispatchers.IO).launch {
            Transport.send(message)
        }
    }

}