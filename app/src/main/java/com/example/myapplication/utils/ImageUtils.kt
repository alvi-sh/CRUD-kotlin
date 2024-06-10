package com.example.myapplication.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ImageUtils(val activity: AppCompatActivity) {

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    fun registerActivity(callback: (Uri?) -> Unit) {
        activityResultLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->

                var resultCode = result.resultCode
                var imageData = result.data
                if (resultCode == AppCompatActivity.RESULT_OK && imageData != null) {
                    var imageUri = imageData.data
                    callback(imageUri)
                }
            })
    }

    fun launchGallery(context: Context) {
        var permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }
        if (ContextCompat.checkSelfPermission(context, permissions) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(permissions), 1)
        } else {
            var intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }
}