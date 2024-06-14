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
import com.example.myapplication.databinding.ActivityUpdateProductBinding
import com.example.myapplication.model.ProductModel
import com.example.myapplication.repository.ProductRepositoryImpl
import com.example.myapplication.utils.ImageUtils
import com.example.myapplication.viewmodel.ProductViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.UUID

class UpdateProductActivity : AppCompatActivity() {

    lateinit var updateProductBinding: ActivityUpdateProductBinding
    var id = ""
    var imageName = ""

    lateinit var imageUtils: ImageUtils
    lateinit var productViewModel: ProductViewModel

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var imageUri: Uri? = null

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

        updateProductBinding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(updateProductBinding.root)

        imageUtils = ImageUtils(this)

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        imageUtils.registerActivity { url ->
            imageUri = url
            Picasso.get().load(imageUri).into(updateProductBinding.updateImageView)
        }

        updateProductBinding.updateImageView.setOnClickListener {
            imageUtils.launchGallery(this)
        }

        var product: ProductModel? = intent.getParcelableExtra("product")

        updateProductBinding.updateName.setText(product?.name)
        updateProductBinding.updatePrice.setText(product?.price.toString())
        updateProductBinding.updateDescription.setText(product?.description)

        Picasso.get().load(product?.url).into(updateProductBinding.updateImageView)

        id = product?.id.toString()
        imageName = product?.imageName.toString()

        updateProductBinding.updateButton.setOnClickListener {
            uploadPhoto()
        }

        updateProductBinding.updateImageView.setOnClickListener {
            imageUtils.launchGallery(this)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun registerActivityForResult() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->

                var resultCode = result.resultCode
                var imageData = result.data
                if (resultCode == RESULT_OK && imageData != null) {
                    imageUri = imageData.data
                    imageUri?.let {
                        Picasso.get().load(it).into(updateProductBinding.updateImageView)
                    }
                }
            })
    }

    private fun uploadPhoto() {
        imageUri?.let {
            productViewModel.uploadImages(imageName, it) { success, imageUrl ->
                if (success) {
                    updateProduct(imageUrl.toString())
                } else {

                }
            }
        }
    }

    fun updateProduct(url : String) {
        var updatedName : String = updateProductBinding.updateName.text.toString()
        var updatedPrice : Int = updateProductBinding.updatePrice.text.toString().toInt()
        var updatedDescription : String = updateProductBinding.updateDescription.text.toString()

        var updatedMap = mutableMapOf<String, Any>()
        updatedMap["name"] = updatedName
        updatedMap["price"] = updatedPrice
        updatedMap["description"] = updatedDescription
        updatedMap["id"] = id
        updatedMap["url"] = url

        productViewModel.updateProducts(id, updatedMap) {
            success, message ->
            if (success) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}