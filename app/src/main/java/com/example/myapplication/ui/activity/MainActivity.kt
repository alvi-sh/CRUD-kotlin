package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.ProductAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.ProductModel
import com.example.myapplication.viewmodel.ProductViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var productAdapter: ProductAdapter
    lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        productAdapter = ProductAdapter(this@MainActivity, ArrayList())

        productViewModel.fetchAllProducts()

        productViewModel.productList.observe(this) {

        }

//        ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                var id = productAdapter.getProductId(viewHolder.adapterPosition)
//                var imageName = productAdapter.getImageName(viewHolder.adapterPosition)
//
//                ref.child(id).removeValue().addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        storageReference.child("products").child(imageName).delete()
//                        Toast.makeText(applicationContext, "Data deleted", Toast.LENGTH_LONG).show()
//                    } else {
//                        Toast.makeText(applicationContext,it.exception?.message, Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//        }).attachToRecyclerView(mainBinding.recyclerView)

//        ref.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                data.clear()
//
//                for (eachData in snapshot.children) {
//                    var product = eachData.getValue(ProductModel::class.java)
//                    if (product != null) {
//                        Log.d("my data", product.name)
//                        Log.d("my data", product.price.toString())
//                        Log.d("my data", product.description)
//
//                        data.add(product)
//                    }
//
//                    var adapter = ProductAdapter(this@MainActivity, data)
//                    mainBinding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
//                    mainBinding.recyclerView.adapter = adapter
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })

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