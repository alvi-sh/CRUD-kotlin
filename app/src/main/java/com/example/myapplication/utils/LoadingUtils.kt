package com.example.myapplication.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.myapplication.R

class LoadingUtils(val activity: Activity) {
    lateinit var dialog: AlertDialog

    fun showLoading() {
        var dialogView = activity.layoutInflater.inflate(R.layout.loading_dialog, null)

        var builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}