package com.example.miniprojetandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val startBtn = findViewById<Button>(R.id.start_btn)
        intent = Intent(this,LoginActivity::class.java)


        startBtn.setOnClickListener {
            startActivity(intent)
        }
    }
}