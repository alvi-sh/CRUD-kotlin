package com.example.myapplication.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.ProductModel
import com.example.myapplication.repository.ProductRepository

class ProductViewModel(val repository: ProductRepository) : ViewModel() {

    fun uploadImages(imageUri: Uri, callback: (Boolean, String?, String?) -> Unit) {
        repository.uploadImages(imageUri) { success, imageUrl, imageName ->
            callback(success, imageUrl, imageName)
        }
    }

    fun addProducts(productModel: ProductModel, callback: (Boolean, String?) -> Unit) {
        repository.addProducts(productModel, callback)
    }
}