package com.example.gfg_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gfg_firebase.Product_RecyclerView.Product
import com.example.gfg_firebase.Product_RecyclerView.ProductAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var homeAdapter: ProductAdapter
    private lateinit var float_btn: FloatingActionButton
    private lateinit var database_ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()



        recyclerView.layoutManager =  GridLayoutManager(this, 2)
        read_data()

        float_btn.setOnClickListener()
        {
            val intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init()
    {
        float_btn = findViewById(R.id.float_btn)
        recyclerView = findViewById(R.id.home_rcy)
        database_ref = Firebase.database.getReference("products")

    }

    private fun read_data() { // DataSnapshot of data is something like user_1(Data of one user )
        database_ref.addListenerForSingleValueEvent(object: ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                var list = mutableListOf<Product>()
                for(dataSnapshot in snapshot.children)
                {
                    val currUser = dataSnapshot.getValue(Product::class.java)
                    if (currUser != null) {
                        list.add(currUser)
                    }
                }
                homeAdapter = ProductAdapter(list, this@HomeActivity)
                recyclerView.adapter = homeAdapter
            }

            override fun onCancelled(error: DatabaseError)
            {

            }
        })
    }
}