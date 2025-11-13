package Util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import cr.ac.utn.practicaltest.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Duration
import java.util.Date
import java.util.Locale

class Util {
    companion object{
        fun openActivity(context: Context
                         , objClass: Class<*>){
            val intent= Intent(context
                    , objClass)
            context.startActivity(intent)
        }

        fun parseStringToDateModern(dateString: String, pattern: String): LocalDate? {
            return try {
                val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
                LocalDate.parse(dateString, formatter)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun parseStringToDateTimeModern(dateTimeString: String, pattern: String): LocalDateTime? {
            return try {
                val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
                LocalDateTime.parse(dateTimeString, formatter)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun parseStringToDateLegacy(dateString: String, pattern: String): Date? {
            return try {
                val formatter = SimpleDateFormat(pattern, Locale.getDefault())
                return formatter.parse(dateString)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun showDialogCondition(context: Context, questionText: String, callback: () ->  Unit){
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage(questionText)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.TextYes), DialogInterface.OnClickListener{
                        dialog, id -> callback()
                })
                .setNegativeButton(context.getString(R.string.TextNo), DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            val alert = dialogBuilder.create()
            alert.setTitle(context.getString(R.string.TextTitleDialogQuestion))
            alert.show()
        }

        // Utility functions for Reservations

        fun formatDateTime(dateTime: LocalDateTime, pattern: String = "dd/MM/yyyy HH:mm"): String {
            return try {
                val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
                dateTime.format(formatter)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        fun formatDate(date: LocalDate, pattern: String = "dd/MM/yyyy"): String {
            return try {
                val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
                date.format(formatter)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        fun isDateTimeInPast(dateTime: LocalDateTime): Boolean {
            return dateTime.isBefore(LocalDateTime.now())
        }

        fun isDateTimeInFuture(dateTime: LocalDateTime): Boolean {
            return dateTime.isAfter(LocalDateTime.now())
        }

        fun calculateDurationInHours(start: LocalDateTime, end: LocalDateTime): Long {
            return Duration.between(start, end).toHours()
        }

        fun calculateDurationInMinutes(start: LocalDateTime, end: LocalDateTime): Long {
            return Duration.between(start, end).toMinutes()
        }

        fun isValidTimeRange(start: LocalDateTime, end: LocalDateTime): Boolean {
            return start.isBefore(end)
        }

        fun doDateTimeRangesOverlap(
            start1: LocalDateTime,
            end1: LocalDateTime,
            start2: LocalDateTime,
            end2: LocalDateTime
        ): Boolean {
            return (start1 < end2 && end1 > start2)
        }

        fun formatPrice(price: Double): String {
            return String.format(Locale.getDefault(), "%.2f", price)
        }

        fun showInfoDialog(context: Context, title: String, message: String){
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = dialogBuilder.create()
            alert.show()
        }

        fun showErrorDialog(context: Context, message: String){
            showInfoDialog(context, "Error", message)
        }

        fun showSuccessDialog(context: Context, message: String){
            showInfoDialog(context, "Éxito", message)
        }

        fun generateReservationId(): String {
            return "RES${System.currentTimeMillis()}"
        }

        fun generateSpaceId(): String {
            return "SPC${System.currentTimeMillis()}"
        }

        fun isWorkingHours(dateTime: LocalDateTime): Boolean {
            val hour = dateTime.hour
            val dayOfWeek = dateTime.dayOfWeek.value
            // Lunes a Viernes (1-5), de 8:00 a 18:00
            return dayOfWeek in 1..5 && hour in 8..17
        }

        fun roundToNearestHour(dateTime: LocalDateTime): LocalDateTime {
            val minute = dateTime.minute
            return if (minute < 30) {
                dateTime.withMinute(0).withSecond(0).withNano(0)
            } else {
                dateTime.plusHours(1).withMinute(0).withSecond(0).withNano(0)
            }
        }
    }
}