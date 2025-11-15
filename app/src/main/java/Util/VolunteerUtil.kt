package Util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.UUID

class VolunteerUtil {

    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun stringToDate(dateString: String): LocalDate? {
        return try {
            LocalDate.parse(dateString, dateFormatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    fun dateToString(date: LocalDate?): String {
        return date?.format(dateFormatter) ?: ""
    }


    fun generateUniqueId(prefix: String = "VOL-"): String {

        return "$prefix${UUID.randomUUID()}"
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhone(phone: String): Boolean {
        return phone.isNotBlank() && phone.length >= 8 && phone.all { it.isDigit() }
    }
}
