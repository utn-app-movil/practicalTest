package cr.ac.utn.practicaltest.Util

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import cr.ac.utn.practicaltest.R

fun Context.toast(res: Int) = Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
fun Context.toastText(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

inline fun Context.confirm(
    titleRes: Int = R.string.TextTitleDialogQuestion,
    messageRes: Int,
    crossinline onYes: () -> Unit
) {
    AlertDialog.Builder(this)
        .setTitle(titleRes)
        .setMessage(messageRes)
        .setPositiveButton(R.string.TextYes) { d, _ -> d.dismiss(); onYes() }
        .setNegativeButton(R.string.TextNo) { d, _ -> d.dismiss() }
        .show()
}
