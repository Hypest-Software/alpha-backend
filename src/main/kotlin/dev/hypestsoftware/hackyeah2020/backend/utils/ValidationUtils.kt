package dev.hypestsoftware.hackyeah2020.backend.utils

import java.util.UUID
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress

class ValidationUtils {
    companion object {
        fun isStringUUID(string: String): Boolean {
            return try {
                UUID.fromString(string)
                true
            } catch (exception: IllegalArgumentException) {
                false
            }
        }

        fun isStringEmail(string: String): Boolean {
            return try {
                InternetAddress(string).validate()
                true
            } catch (ex: AddressException) {
                false
            }
        }
    }
}
