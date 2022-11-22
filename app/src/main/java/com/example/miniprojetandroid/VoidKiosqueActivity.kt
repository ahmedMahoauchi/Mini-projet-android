package com.example.miniprojetandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class VoidKiosqueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_void_kiosque)

        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            intent = Intent(this,FormAddKiosqueActivity::class.java)
            startActivity(intent)
        }
    }
}