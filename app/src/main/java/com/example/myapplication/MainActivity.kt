package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.ProductAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref: DatabaseReference = firebaseDatabase.reference.child("products")

    var productList = ArrayList<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()

                for (eachData in snapshot.children) {
                    var product = eachData.getValue(ProductModel::class.java)
                    if (product != null) {
                        Log.d("my data", product.name)
                        Log.d("my data", product.price.toString())
                        Log.d("my data", product.description)

                        productList.add(product)
                    }

                    var adapter = ProductAdapter(this@MainActivity, productList)
                    mainBinding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    mainBinding.recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        mainBinding.floatingActionButton.setOnClickListener {
            var intent = Intent(this@MainActivity, AddProductActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}