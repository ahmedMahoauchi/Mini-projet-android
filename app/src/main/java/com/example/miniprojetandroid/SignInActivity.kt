package com.example.miniprojetandroid

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
<<<<<<< Updated upstream
import androidx.recyclerview.widget.LinearLayoutManager
=======
import com.example.miniprojetandroid.entities.GetUserByEmailResponse
>>>>>>> Stashed changes
import com.example.miniprojetandroid.entities.LoginResponse
import com.example.miniprojetandroid.network.ApiUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val PREF_NAME = "DATA_PREF";
const val USER_ID = "USER_ID";
const val USER_NAME = "USER_NAME";
const val USER_EMAIL = "USER_EMAIL";
const val USER_CIN = "USER_CIN";
const val USER_KIOSQUE = "USER_KIOSQUE";

class SignInActivity : AppCompatActivity() {
<<<<<<< Updated upstream
    lateinit var emailET : TextInputLayout
    lateinit var passwordET : TextInputLayout

=======
    lateinit var emailET : EditText
    lateinit var passwordET : EditText
    lateinit var mSharedPref: SharedPreferences
>>>>>>> Stashed changes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        val signpBtn = findViewById<LinearLayout>(R.id.linearLayout)
        val signinButton = findViewById<Button>(R.id.button)
        val visitor = findViewById<TextView>(R.id.textView7)

        visitor.setOnClickListener{
            intent = Intent(this,AcceuilActivity::class.java)
            startActivity(intent)
        }
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
        val textFields = arrayOf<TextInputLayout>(emailET, passwordET,)
        if(emailET.editText?.text.toString().equals("") || passwordET.editText?.text.toString().equals("")){
            for (i in textFields.indices){
                if (textFields[i].editText?.text.toString().equals("")){
                    textFields[i].error = "Must not be empty !"
                }
            }
            return false
        }else if (!Patterns.EMAIL_ADDRESS.matcher(emailET.editText?.text.toString()).matches()){
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

                map["email"] = emailET.editText?.text.toString()
                map["password"] = passwordET.editText?.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    apiInterface.login(map).enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response:
                        Response<LoginResponse>
                        ) {
                            val user = response.body()?.user?._id
                            Log.d("success: ", response.body()?.user.toString())
                            if(user != null){
                               mSharedPref.edit().apply{
                                   putString(USER_ID, response.body()!!.user._id)
                                   putString(USER_NAME, response.body()!!.user.name)
                                   putString(USER_CIN, response.body()!!.user.CIN)
                                   putString(USER_EMAIL, response.body()!!.user.email)
                                   if (!response.body()!!.user.myKiosque.isEmpty()){
                                       putString(USER_KIOSQUE, response.body()!!.user.myKiosque[0])
                                   }else{
                                       putString(USER_KIOSQUE, "null")
                                   }

                               }.apply()

<<<<<<< Updated upstream
                                val intent = Intent(this@SignInActivity, AcceuilActivity::class.java)
                                startActivity(intent)
                                finish()
=======
                                if(response.body()!!.user.role == 0){
                                    if (!response.body()!!.user.myKiosque.isEmpty()){
                                        val intent = Intent(this@SignInActivity, KiosqueDetailsActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }else{
                                        val intent = Intent(this@SignInActivity, VoidKiosqueActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                }else{
                                    val intent = Intent(this@SignInActivity, AdminActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

>>>>>>> Stashed changes
                            }else{
                                Log.e("error : ", user.toString())
                                Toast.makeText(this@SignInActivity,"Username or Password wrong !!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(this@SignInActivity,"Connexion error !", Toast.LENGTH_SHORT).show()
                            Log.e("connexionError",t.message.toString())
                        }



                    })
                }
            }


    }


}