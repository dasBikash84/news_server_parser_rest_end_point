package com.dasbikash.news_server_parser_rest_end_point.utills


import com.dasbikash.news_server_parser_rest_end_point.model.EmailAuth
import com.dasbikash.news_server_parser_rest_end_point.model.EmailTargets
import com.dasbikash.news_server_parser_rest_end_point.model.database.AuthToken
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.io.InputStreamReader
import java.util.*
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


object EmailUtils {

    private const val EMAIL_AUTH_FILE_LOCATION = "/email_details_auth.json"
    private const val EMAIL_TARGET_DETAILS_FILE_LOCATION = "/email_details_targets.json"
    private const val HTML_CONTENT_TYPE = "text/html; charset=utf-8"
    private const val PLAIN_TEXT_CONTENT_TYPE = "text/plain; charset=utf-8"

    val emailAuth: EmailAuth
    val emailTargets: EmailTargets

    private lateinit var properties: Properties
    private lateinit var session: Session

    init {
        val authReader = InputStreamReader(javaClass.getResourceAsStream(EMAIL_AUTH_FILE_LOCATION))
        emailAuth = ObjectMapper().readValue(authReader, EmailAuth::class.java)

        val targetReader = InputStreamReader(javaClass.getResourceAsStream(EMAIL_TARGET_DETAILS_FILE_LOCATION))
        emailTargets = ObjectMapper().readValue(targetReader, EmailTargets::class.java)
    }

    fun <T> emailAuthTokenToAdmin(authToken: AuthToken, requetingClass:Class<T>){
        sendEmail("New Token for ${requetingClass.simpleName} of Parser Rest Service","Token:\t${authToken.token}\nExpires on:\t${authToken.expiresOn}")
    }

    private fun getProperties():Properties{
        if (!::properties.isInitialized){
            properties = Properties()

            emailAuth.properties!!.keys.asSequence().forEach {
                properties.put(it, emailAuth.properties!!.get(it)!!)
            }
        }
        return properties
    }
    private fun getSession():Session{
        if (!::session.isInitialized){
            session = Session.getInstance(getProperties(),
                    object : javax.mail.Authenticator() {
                        override fun getPasswordAuthentication(): PasswordAuthentication {
                            return PasswordAuthentication(emailAuth.userName, emailAuth.passWord)
                        }
                    })
        }
        return session
    }

    fun sendEmail(subject:String, body:String, filePath:String?=null):Boolean{

        try {
            val message = MimeMessage(getSession())

            message.setFrom(InternetAddress(emailAuth.userName))
            setEmailRecipients(message)
            message.subject = subject

            val messageBodyTextPart: MimeBodyPart = MimeBodyPart()

            messageBodyTextPart.setContent(body.replace("\n","<br>"),HTML_CONTENT_TYPE)

            val multipart = MimeMultipart()
            multipart.addBodyPart(messageBodyTextPart)

            if (filePath!=null && File(filePath).exists()){
                val messageBodyAttachmentPart: MimeBodyPart = MimeBodyPart()
                val source = FileDataSource(filePath)
                messageBodyAttachmentPart.setDataHandler(DataHandler(source))
                messageBodyAttachmentPart.setFileName(filePath.split(Regex("/")).last())
                multipart.addBodyPart(messageBodyAttachmentPart)
            }

            message.setContent(multipart)

            Transport.send(message)
            return true
        } catch (e: MessagingException) {
            e.printStackTrace()
            return false
        }
    }

    private fun setEmailRecipients(message: MimeMessage) {
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(getToAddressString())
        )

        getCcAddressString()?.let {
            message.setRecipients(
                    Message.RecipientType.CC,
                    InternetAddress.parse(it)
            )
        }

        getBccAddressString()?.let {
            message.setRecipients(
                    Message.RecipientType.BCC,
                    InternetAddress.parse(it)
            )
        }
    }

    private fun getToAddressString():String{
        return emailTargets.toAddresses!!
    }

    private fun getCcAddressString():String?{
        return emailTargets.ccAddresses
    }

    private fun getBccAddressString():String?{
        return emailTargets.bccAddresses
    }
}

