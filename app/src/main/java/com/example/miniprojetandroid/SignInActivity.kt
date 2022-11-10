package com.example.miniprojetandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniprojetandroid.entities.LoginResponse
import com.example.miniprojetandroid.entities.UserX
import com.example.miniprojetandroid.network.ApiUser
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    lateinit var emailET : EditText
    lateinit var passwordET : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val signpBtn = findViewById<LinearLayout>(R.id.linearLayout)
        val signinButton = findViewById<Button>(R.id.button)
         emailET = findViewById(R.id.email_edittext)
         passwordET = findViewById(R.id.password_edittext)


        signpBtn.setOnClickListener{
            intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        signinButton.setOnClickListener {
            if(isValide()){
                doLogin()
            }

        }


    }

    private fun isValide(): Boolean {

        emailET.error = null
        passwordET.error = null
        val textFields = arrayOf<EditText>(emailET, passwordET)
        if(emailET.text.toString().equals("") || passwordET.text.toString().equals("")){
            for (i in textFields.indices){
                if (textFields[i].text.toString().equals("")){
                    textFields[i].error = "Must not be empty !"
                }
            }
            return false
        }else if (!Patterns.EMAIL_ADDRESS.matcher(emailET.text.toString()).matches()){
            emailET.error = "Check your email !"
            return false
        }else{
            return true
        }
        return true

    }

    private fun doLogin() {
            if (isValide()){
                val apiInterface = ApiUser.create()
                val map: HashMap<String, String> = HashMap()

                map["email"] = emailET.text.toString()
                map["password"] = passwordET.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    apiInterface.login(map).enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response:
                        Response<LoginResponse>
                        ) {
                            val user = response.body()?.user?._id
                            Log.d("success: ", response.body()?.user.toString())
                            if(user != null){
                               // Toast.makeText(this@SignInActivity,user.toString(),Toast.LENGTH_SHORT).show()

                                val intent = Intent(this@SignInActivity, AcceuilActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                Log.e("error : ", user.toString())
                                Toast.makeText(this@SignInActivity,"Username or Password wrong !!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(this@SignInActivity,"Connexion error!", Toast.LENGTH_SHORT).show()
                        }



                    })
                }
            }


    }


}