package com.example.miniprojetandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class VoidKiosqueActivity : AppCompatActivity() {

    lateinit var gso : GoogleSignInOptions
    lateinit var gsc : GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_void_kiosque)

        gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        gsc = GoogleSignIn.getClient(this,gso)
        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            intent = Intent(this,FormAddKiosqueActivity::class.java)
            startActivity(intent)
        }

        val button1 = findViewById<Button>(R.id.logout)

        val email = findViewById<TextView>(R.id.email)
        val name = findViewById<TextView>(R.id.name)
        val id = findViewById<TextView>(R.id.id)


        val acct = GoogleSignIn.getLastSignedInAccount(this)

        name.text = acct?.displayName
        email.text = acct ?.email
        id.text = acct ?.photoUrl.toString()


        button1.setOnClickListener {
            gsc.signOut().addOnCompleteListener {
                finish()
                startActivity(Intent(this,SignInActivity::class.java))
            }
        }

    }
}