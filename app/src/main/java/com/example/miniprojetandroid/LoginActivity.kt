package com.example.miniprojetandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val signinBtn = findViewById<Button>(R.id.signin_button)
        val signupBtn = findViewById<Button>(R.id.signup_button)
        val visitorBtn = findViewById<LinearLayout>(R.id.linearLayout)


        signinBtn.setOnClickListener {
            intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        signupBtn.setOnClickListener {
            intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        visitorBtn.setOnClickListener {
            intent = Intent(this,AcceuilActivity::class.java)
            startActivity(intent)
        }


    }
}