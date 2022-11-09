package com.example.miniprojetandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signinButton = findViewById<TextView>(R.id.signup_textinput)

        signinButton.setOnClickListener{
            intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }


    }
}