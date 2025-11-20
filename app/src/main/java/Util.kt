import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import cr.ac.utn.practicaltest.R

class Util {
    companion object {
        fun openActivity(context: Context, objClass: Class<*>) {
            val intent = Intent(context, objClass)
            context.startActivity(intent)
        }

        fun showDialogCondition(context: Context, questionText: String, callback: () -> Unit) {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage(questionText)
                .setCancelable(false)
                .setPositiveButton(
                    context.getString(R.string.TextYes),
                    DialogInterface.OnClickListener { dialog, id ->
                        callback()
                    })
                .setNegativeButton(
                    context.getString(R.string.TextNo),
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            val alert = dialogBuilder.create()
            alert.setTitle(context.getString(R.string.TextTitleDialogQuestion))
            alert.show()
        }
    }
}