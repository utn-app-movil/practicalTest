package cr.ac.utn.practicaltest.MyUtil

import android.app.AlertDialog
import android.content.Context
import cr.ac.utn.practicaltest.R

object NewsUtil {
    fun showConfirm(
        ctx: Context,
        msg: String,
        onYes: () -> Unit
    ) {
        AlertDialog.Builder(ctx)
            .setTitle("Confirm")
            .setMessage(msg)
            .setPositiveButton("Yes") { _, _ -> onYes() }
            .setNegativeButton("No") { d, _ -> d.cancel() }
            .show()
    }
}