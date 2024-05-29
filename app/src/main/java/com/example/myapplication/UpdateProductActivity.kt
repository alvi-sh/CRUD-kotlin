package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityUpdateProductBinding
import com.example.myapplication.model.ProductModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateProductActivity : AppCompatActivity() {

    lateinit var updateProductBinding: ActivityUpdateProductBinding
    var firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref : DatabaseReference = firebaseDatabase.reference.child("products")
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        updateProductBinding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(updateProductBinding.root)

        var product: ProductModel? = intent.getParcelableExtra("product")

        updateProductBinding.updateName.setText(product?.name)
        updateProductBinding.updateName.setText(product?.price.toString())
        updateProductBinding.updateName.setText(product?.description)

        id = product?.id.toString()

        updateProductBinding.updateButton.setOnClickListener {
            var updatedName : String = updateProductBinding.updateName.text.toString()
            var updatedPrice : Int = updateProductBinding.updatePrice.text.toString().toInt()
            var updatedDescription : String = updateProductBinding.updateDescription.text.toString()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}