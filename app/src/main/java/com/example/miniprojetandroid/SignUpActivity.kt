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

<<<<<<< Updated upstream
        val signinButton = findViewById<TextView>(R.id.signup_textinput)
        val signupButton = findViewById<Button>(R.id.button_signup)
        val cin = findViewById<TextInputLayout>(R.id.cin_edittext)
        val password = findViewById<TextInputLayout>(R.id.password_edittext)
        val name = findViewById<TextInputLayout>(R.id.name_edittext)
        val email = findViewById<TextInputLayout>(R.id.email_edittext)
=======
     val signinButton = findViewById<TextView>(R.id.textView14)
     val signupButton = findViewById<Button>(R.id.button)
     cin = findViewById(R.id.cin)
     password = findViewById(R.id.password)
     name = findViewById(R.id.name)
     email = findViewById(R.id.email)

      signinButton.setOnClickListener{
          intent = Intent(this,SignInActivity::class.java)
          startActivity(intent)
      }

     signupButton.setOnClickListener {

         doRegister()
     }


    }

    private fun doRegister() {
        val apiInterface = ApiUser.create()
        val map: HashMap<String, String> = HashMap()

        map["name"] = name.text.toString()
        map["email"] = email.text.toString()
        map["password"] = password.text.toString()
        map["CIN"] = cin.text.toString()

        apiInterface.register(map).enqueue(object : Callback<UserX> {

            override fun onResponse(call: Call<UserX>, response: Response<UserX>) {

                val user = response
                Log.e("response",user.toString())

                if (user != null){
                    Toast.makeText(this@SignUpActivity, user.toString(), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
>>>>>>> Stashed changes

        signinButton.setOnClickListener{
            intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        signupButton.setOnClickListener {

        }


    }


}