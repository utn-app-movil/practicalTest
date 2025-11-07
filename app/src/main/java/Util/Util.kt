package Util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import cr.ac.utn.practicaltest.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class Util {
    companion object {
        fun openActivity(context: Context, objClass: Class<*>) {
            val intent = Intent(context, objClass)
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

        fun showDialogCondition(context: Context, questionText: String, callback: () -> Unit) {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage(questionText)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.TextYes),
                    DialogInterface.OnClickListener { dialog, id -> callback() })
                .setNegativeButton(context.getString(R.string.TextNo),
                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

            val alert = dialogBuilder.create()
            alert.setTitle(context.getString(R.string.TextTitleDialogQuestion))
            alert.show()
        }

        fun generateTicketId(): String {
            return "TKT${System.currentTimeMillis()}"
        }
    }
}