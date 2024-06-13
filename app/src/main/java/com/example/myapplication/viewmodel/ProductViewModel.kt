package com.example.myapplication.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.ProductModel
import com.example.myapplication.repository.ProductRepository

class ProductViewModel(val repository: ProductRepository) : ViewModel() {

    fun updateProducts(id: String, data: MutableMap<String, Any>?, callback: (Boolean, String?) -> Unit) {
        repository.updateProducts(id, data, callback)
    }

    fun uploadImages(imageName: String, imageUri: Uri, callback: (Boolean, String?) -> Unit) {
        repository.uploadImages(imageName, imageUri) { success, imageUrl ->
            callback(success, imageUrl)
        }
    }

    fun addProducts(productModel: ProductModel, callback: (Boolean, String?) -> Unit) {
        repository.addProducts(productModel, callback)
    }

    var _productList = MutableLiveData<List<ProductModel>?>()

    var productList = MutableLiveData<List<ProductModel>?>()
        get() = _productList

    var _loadingState = MutableLiveData<Boolean>()

    var loadingState = MutableLiveData<Boolean>()
        get() = _loadingState

    fun fetchAllProducts() {
        _loadingState.value = true
        repository.getAllProduct { products, success, message ->
            if (products != null) {
                _loadingState.value = false
                _productList.value = products
            }
        }
    }
}