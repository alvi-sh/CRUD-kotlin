package com.example.myapplication.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAddProductBinding
import com.example.myapplication.model.ProductModel
import com.example.myapplication.repository.ProductRepository
import com.example.myapplication.repository.ProductRepositoryImpl
import com.example.myapplication.utils.ImageUtils
import com.example.myapplication.viewmodel.ProductViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.util.UUID

class AddProductActivity : AppCompatActivity() {

    lateinit var addProductBinding: ActivityAddProductBinding

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var imageUri: Uri? = null

    lateinit var imageUtils: ImageUtils
    lateinit var productViewModel: ProductViewModel

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            var intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        addProductBinding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(addProductBinding.root)

        imageUtils = ImageUtils(this)
        imageUtils.registerActivity {url ->
            url.let {
                imageUri = it
                Picasso.get().load(it).into(addProductBinding.imageBrowse)
            }
        }

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)



        addProductBinding.imageBrowse.setOnClickListener {
            imageUtils.launchGallery(this)
        }

        addProductBinding.saveButton.setOnClickListener {
            if (imageUri != null) {
                uploadPhoto()
            } else {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addProducts(url: String?, imageName : String?) {
        var name: String = addProductBinding.editTextName.text.toString()
        var price: Int = addProductBinding.editTextPrice.text.toString().toInt()
        var description: String = addProductBinding.editTextDescription.text.toString()

        var data = ProductModel("", name, price, description, url.toString(), imageName.toString())
        productViewModel.addProducts(data) {
            success, message ->
            if (success) {
                finish()
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun uploadPhoto() {
        imageUri?.let {
            productViewModel.uploadImages(it) { success, imageName, imageUrl ->
                if (success) {
                    addProducts(imageUrl, imageName)
                } else {

                }
            }
        }
    }
}