package com.example.secureprogramming.core.utils
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar

object ProgressDialogUtil {

    private var dialog: Dialog? = null

    fun showProgressDialog(context: Context) {
        if (dialog == null) {
            dialog = Dialog(context)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setCancelable(false)
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val progressBar = ProgressBar(context)
            dialog!!.addContentView(progressBar, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ))
            dialog!!.show()
        }
    }

    fun hideProgressDialog() {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
    }
}






