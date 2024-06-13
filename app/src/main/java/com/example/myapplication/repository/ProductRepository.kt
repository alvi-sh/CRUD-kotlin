package com.example.myapplication.repository

import android.net.Uri
import com.example.myapplication.model.ProductModel
import com.squareup.picasso.Callback

interface ProductRepository {
    fun addProducts(productModel: ProductModel, callback: (Boolean, String?) -> Unit)
    fun uploadImages(imageName: String, imageUri: Uri, callback: (Boolean, String?) -> Unit)

    fun getAllProduct(callback: (List<ProductModel>?, Boolean, String?) -> Unit)

    fun updateProducts(id: String, data: MutableMap<String, Any>?, callback: (Boolean, String?) -> Unit)

    fun deleteProducts(id: String, callback: (Boolean, String?) -> Unit)

    fun deleteImage(imageName: String, callback: (Boolean, String?) -> Unit)
}