package com.example.miniprojetandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signinButton = findViewById<TextView>(R.id.signup_textinput)
        val signupButton = findViewById<Button>(R.id.button_signup)
        val cin = findViewById<TextInputLayout>(R.id.cin_edittext)
        val password = findViewById<TextInputLayout>(R.id.password_edittext)
        val name = findViewById<TextInputLayout>(R.id.name_edittext)
        val email = findViewById<TextInputLayout>(R.id.email_edittext)

        signinButton.setOnClickListener{
            intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        signupButton.setOnClickListener {

        }


    }


}