package com.example.myapplication.repository

import android.net.Uri
import com.example.myapplication.model.ProductModel
import com.squareup.picasso.Callback

interface ProductRepository {
    fun addProducts(productModel: ProductModel, callback: (Boolean, String?) -> Unit)
    fun uploadImages(imageUri: Uri, callback: (Boolean, String?, String?) -> Unit)
}