package com.example.miniprojetandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.miniprojetandroid.entities.ResetResponse
import com.example.miniprojetandroid.entities.UserX
import com.example.miniprojetandroid.network.ApiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendResetPasswordActivity : AppCompatActivity() {
    lateinit var email : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_reset_password)

        email = findViewById(R.id.email_edittext)
        val button = findViewById<Button>(R.id.button)


        button.setOnClickListener {
            sendEmail()
        }
    }

    private fun sendEmail() {
        val apiInterface = ApiUser.create()
        val map: HashMap<String, String> = HashMap()

        map["email"] = email.text.toString()

        apiInterface.sendMail(map).enqueue(object : Callback<ResetResponse> {

            override fun onResponse(call: Call<ResetResponse>, response: Response<ResetResponse>) {

                val user = response.body()?.message
                Log.e("response",user.toString())

                if (user != null){
                    Toast.makeText(this@SendResetPasswordActivity, "hello", Toast.LENGTH_SHORT).show()

                     val intent = Intent(this@SendResetPasswordActivity, NewPasswordActivity::class.java)
                    intent.putExtra("email",email.text.toString())
                     startActivity(intent)
                }else{

                    Log.e("test",user.toString())
                    email.error = "you have to create an account !"

                }
            }

            override fun onFailure(call: Call<ResetResponse>, t: Throwable) {
                Toast.makeText(this@SendResetPasswordActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })
    }
}