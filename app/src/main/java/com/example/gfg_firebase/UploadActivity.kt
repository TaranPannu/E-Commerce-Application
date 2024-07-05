package com.example.gfg_firebase

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.gfg_firebase.Product_RecyclerView.Product
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime

class UploadActivity : AppCompatActivity() {

    private lateinit var get_img_btn:Button
    private lateinit var upload_img_btn:Button
    private lateinit var image:ImageView
    private lateinit var prog_bar:ProgressBar

    private lateinit var Prod_name:TextView
    private lateinit var Prod_price:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        init()

        get_img_btn.setOnClickListener()
        {
            get_Img_from_gallery()
        }

        upload_img_btn.setOnClickListener()
        {
            prog_bar.visibility = View.VISIBLE
            uploadProduct("","","")

        }
    }
    private fun get_Img_from_gallery()
    {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent,101)
    }
    @RequiresApi(Build.VERSION_CODES.O)// for Local_Date_time
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK)
        {
            image.setImageURI(data?.data)
        }
        upload_img_btn.setOnClickListener()
        {
            prog_bar.visibility = View.VISIBLE
            Upload_Image(data?.data)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O) // for Local_Date_time
    fun Upload_Image(data: Uri?)
    {
        val fileName = LocalDateTime.now().toString()+".jpg"
        val StorageRef = FirebaseStorage.getInstance().reference.child("products/$fileName")
        StorageRef.putFile(data!!).addOnSuccessListener {
            Toast.makeText(this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show()
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener {
                uploadProduct(Prod_name.text.toString(),Prod_price.text.toString(),it.toString())
            }
        }
    }

    private fun uploadProduct(Name: String, Price: String, ImageUrl: String)
    {
        Firebase.database.getReference("products").child(Name).setValue(
          Product(Name,Price,ImageUrl)
        ).addOnSuccessListener{
            Toast.makeText(applicationContext,"Uploaded Successfully",Toast.LENGTH_SHORT).show()
            prog_bar.visibility = View.GONE
        }.addOnFailureListener(){
            Toast.makeText(applicationContext,"Uploading Failed",Toast.LENGTH_SHORT).show()
        }
    }

    private fun init()
    {
        get_img_btn = findViewById(R.id.upload_get_img_btn)
        upload_img_btn = findViewById(R.id.upload_img_btn)
        image = findViewById(R.id.upload_img)
        prog_bar = findViewById(R.id.upload_prog_bar)

        Prod_name = findViewById(R.id.Prod_name)
        Prod_price = findViewById(R.id.Prod_pice)
    }
}