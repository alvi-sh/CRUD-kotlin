package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityUpdateProductBinding
import com.example.myapplication.model.ProductModel

class UpdateProductActivity : AppCompatActivity() {

    lateinit var updateProductBinding: ActivityUpdateProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        updateProductBinding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(updateProductBinding.root)

        var product: ProductModel? = intent.getParcelableExtra("product")

        updateProductBinding.updateName.setText(product?.name)
        updateProductBinding.updateName.setText(product?.price.toString())
        updateProductBinding.updateName.setText(product?.description)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}