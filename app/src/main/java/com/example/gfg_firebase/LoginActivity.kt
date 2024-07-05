package com.example.gfg_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var email: TextView
    private lateinit var pass: TextView
    private lateinit var button: Button
    private lateinit var btn_sign_up:TextView
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

       button.setOnClickListener()
       {
           login(email.text.toString(),pass.text.toString())
       }

        btn_sign_up.setOnClickListener()
        {
            val intent = Intent(applicationContext,SignUpActivity::class.java)
            startActivity(intent)
        }

        val user = auth.currentUser
        if (user!= null){
            val intent  = Intent(applicationContext,UploadActivity::class.java)
            startActivity(intent)
        }

    }


    private fun login(email: String, password: String)
    {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this)
        {
                task ->
            if (task.isSuccessful)
            {
                val intent  = Intent(applicationContext,UploadActivity::class.java)
                startActivity(intent)

                Toast.makeText(this,"Login in Successfully", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun init()
    {
        email = findViewById(R.id.login_user_name)
        pass = findViewById(R.id.login_password)
        button = findViewById(R.id.login_btn_login)
        btn_sign_up = findViewById(R.id.btn_sign_up)
        auth = Firebase.auth
    }

}