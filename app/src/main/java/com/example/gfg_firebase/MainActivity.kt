package com.example.gfg_firebase

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storageMetadata
import java.time.LocalDate
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var login_btn:Button
    private lateinit var sign_up:TextView
    private lateinit var send_data_btn:Button
    private lateinit var read_btn:Button
    private lateinit var get_img_gallery:Button
    private lateinit var image_prev:ImageView
    private lateinit var upload_img:Button
    private lateinit var read_img:Button
    private lateinit var get_url_img_prev:ImageView

    private lateinit var database_ref:DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        val Email = email.text.toString()
        val Password = password.text.toString()

        login_btn.setOnClickListener()
        {
            login(email.text.toString(),password.text.toString())
        }
        sign_up.setOnClickListener(){
            sign_up(Email,Password)
        }
        send_data_btn.setOnClickListener()
        {
            send_data()
        }
        read_btn.setOnClickListener()
        {
            read_data()
        }
        get_img_gallery.setOnClickListener()
        {
            get_Img_from_gallery()
        }
        read_img.setOnClickListener()
        {
          downloadImg()
        }

    }

    private fun downloadImg() {
        Glide.with(this).
        load("https://buffer.com/library/content/images/size/w1200/2023/10/free-images.jpg")
            .into(get_url_img_prev)
    }

    private fun get_Img_from_gallery()
    {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent,101)
    }
    @RequiresApi(Build.VERSION_CODES.O) // for Local_Date_time
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK)
        {
            image_prev.setImageURI(data?.data)
        }
        upload_img.setOnClickListener()
        {
            Upload_Image(data?.data)
        }
    }
@RequiresApi(Build.VERSION_CODES.O) // for Local_Date_time
fun Upload_Image(data: Uri?)
{
    val fileName = LocalDateTime.now().toString()+".jpg"
    val StorageRef = FirebaseStorage.getInstance().reference.child("images/$fileName")
    StorageRef.putFile(data!!).addOnSuccessListener {
        Toast.makeText(this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show()
    }
    }

    private fun read_data() { // DataSnapshot of data is something like user_1(Data of one user )
        database_ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(dataSnapshot in snapshot.children)
                {
                    val currUser = dataSnapshot.getValue(User::class.java)
                    Log.d("currUser",currUser.toString())
                }
            }

            override fun onCancelled(error: DatabaseError)
            {

            }
        })
    }

    private fun send_data() {
        val user1 = User("Taran",22,"abc@gmail.com")
        database_ref.child("user_1").setValue(user1)
    }

    private fun login(email: String, password: String)
    {
    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this)
     {
    task ->
    if (task.isSuccessful)
        {
        Toast.makeText(this,"Login in Successfully",Toast.LENGTH_SHORT).show()
        }
    else
        {
        Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
        }
      }
    }

    private fun sign_up(email: String, password: String) {
auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) {
    task ->
    if (task.isSuccessful)
    {
Toast.makeText(this,"Sign Up Successfully",Toast.LENGTH_SHORT).show()
    }
    else
    {
Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
    }
}
    }

    private fun init()
    {
        email = findViewById(R.id.user_name)
        password = findViewById(R.id.password)
        login_btn = findViewById(R.id.btn_login)
        sign_up = findViewById(R.id.btn_sign_up)
        send_data_btn = findViewById(R.id.send_btn)
        read_btn = findViewById(R.id.read_btn)
        image_prev = findViewById(R.id.img_prev)
        get_img_gallery = findViewById(R.id.get_img_gallery)
        upload_img = findViewById(R.id.upload_img)
        read_img = findViewById(R.id.read_img)
        get_url_img_prev = findViewById(R.id.img_prev_get)

        database_ref = Firebase.database.getReference("users")
        auth = Firebase.auth
    }

    data class User(var name:String = "",var age:Int = 0,var email: String = "")
}