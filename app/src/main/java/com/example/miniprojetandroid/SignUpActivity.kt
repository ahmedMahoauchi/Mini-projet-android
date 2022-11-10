package com.example.miniprojetandroid


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.miniprojetandroid.entities.UserX
import com.example.miniprojetandroid.network.ApiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    lateinit var cin : EditText
    lateinit var password : EditText
    lateinit var name : EditText
    lateinit var email : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

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
                   // val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                   // startActivity(intent)
                }else{

                    Log.e("test",user.toString())
                    email.error = "email already exists !"

                }
            }

            override fun onFailure(call: Call<UserX>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })

    }


}