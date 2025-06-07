package com.rezen.videoplayer.common

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.rezen.videoplayer.R
import java.lang.ref.WeakReference

open class BaseActivity : AppCompatActivity() {
    private var loaderDialog: AlertDialog? = null
    private var weakActivity: WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakActivity = WeakReference(this)
    }

    override fun onDestroy() {
        hideLoader()
        super.onDestroy()
    }

    fun showLoader() {
        if (loaderDialog == null) {
            loaderDialog = getLoaderDialog()
        }
        loaderDialog?.show()
    }

    fun hideLoader() {
        loaderDialog?.dismiss()
        loaderDialog = null
    }

    private fun getLoaderDialog(): AlertDialog? {
        return getActivityInstance()?.let { activity ->
            val dialogView = activity.layoutInflater.inflate(R.layout.loader_view, null)
            val dialog = AlertDialog.Builder(activity)
                .setView(dialogView)
                .setCancelable(false)
                .create()

            // Add a transparent background
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            return@let dialog
        }
    }

    private fun getActivityInstance(): Activity? {
        return weakActivity?.get() // Safely retrieve the activity
    }

}