package com.example.miniprojetandroid

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.miniprojetandroid.entities.GetUserByEmailResponse
import com.example.miniprojetandroid.entities.LoginResponse
import com.example.miniprojetandroid.network.ApiUser
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap


const val PREF_NAME = "DATA_PREF";
const val USER_ID = "USER_ID";
const val USER_NAME = "USER_NAME";
const val USER_EMAIL = "USER_EMAIL";
const val USER_CIN = "USER_CIN";
const val USER_IMAGE = "USER_IMAGE";
const val USER_STATUT = "USER_STATUT";
const val USER_KIOSQUE = "USER_KIOSQUE";
const val USER_COORD = "USER_COORD";
const val COOR = "COOR";

class SignInActivity : AppCompatActivity() {
    lateinit var gso : GoogleSignInOptions
    lateinit var gsc : GoogleSignInClient
    lateinit var emailET : EditText
    lateinit var passwordET : EditText
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)





        val signpBtn = findViewById<LinearLayout>(R.id.linearLayout)
        val signinButton = findViewById<Button>(R.id.button)
        val btnGoogle = findViewById<ConstraintLayout>(R.id.google_signin)





        gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        gsc = GoogleSignIn.getClient(this,gso)

        btnGoogle.setOnClickListener {

            signIn()
//            val acct : GoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)!!

          //  if (acct != null){
          //      Toast.makeText(this,acct.displayName,Toast.LENGTH_SHORT).show()
          //  }

        }
         emailET = findViewById(R.id.email_edittext)
         val visitor = findViewById<TextView>(R.id.textView7)

        visitor.setOnClickListener {
            intent = Intent(this,AcceuilActivity::class.java)
            startActivity(intent)
        }
         passwordET = findViewById(R.id.password_edittext)
         val forgetpassword = findViewById<TextView>(R.id.textView13)



        signpBtn.setOnClickListener{
            intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        signinButton.setOnClickListener {
            if(isValide()){
                doLogin()
            }

        }

        forgetpassword.setOnClickListener{
            intent = Intent(this,SendResetPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun signIn() {
       val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent,1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val task : Task<GoogleSignInAccount>

           task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                task.getResult(ApiException::class.java)

            } catch (e :ApiException){
                Toast.makeText(this,"something went wrong !",Toast.LENGTH_SHORT).show()
            }

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        mSharedPref.edit().apply{
            putString(USER_ID, acct?.id)
            putString(USER_NAME, acct?.displayName)
            putString(USER_CIN, null)
            putString(USER_EMAIL, acct?.email)
            putString(USER_IMAGE, acct?.photoUrl.toString())
            putInt(USER_STATUT,0)

        }.apply()


            intent = Intent(this,UserMainActivity::class.java)
            startActivity(intent)
            finish()

    }

    private fun isValide(): Boolean {


        emailET.error = null
        passwordET.error = null
        val textFields = arrayOf(emailET, passwordET)
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
                               mSharedPref.edit().apply{
                                   putString(USER_ID, response.body()!!.user._id)
                                   putString(USER_NAME, response.body()!!.user.name)
                                   putString(USER_CIN, response.body()!!.user.CIN)
                                   putString(USER_EMAIL, response.body()!!.user.email)
                                   putInt(USER_STATUT, response.body()!!.user.role)
                                   putString(USER_IMAGE, response.body()!!.user?.image?.url)
                                   if (!response.body()!!.user.myKiosque.isEmpty()){
                                       putString(USER_KIOSQUE, response.body()!!.user.myKiosque[0].toString())
                                       putString(USER_COORD, response.body()!!.user.myKiosque[0].toString())
                                   }else{
                                       putString(USER_KIOSQUE, "null")
                                   }

                               }.apply()

                                if(response.body()!!.user.role == 0){
                                        val intent = Intent(this@SignInActivity, UserMainActivity::class.java)
                                        startActivity(intent)

                                }else if(response.body()!!.user.role == 1){
                                    val intent = Intent(this@SignInActivity, AdminActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    val intent = Intent(this@SignInActivity,AcceuilActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                            }else{
                                if (checkExistance()){
                                    Toast.makeText(this@SignInActivity,"Mot de passe incorrect !!", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(this@SignInActivity,"You have to create an account", Toast.LENGTH_SHORT).show()
                                }


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

    fun checkExistance() : Boolean{
        val apiInterface = ApiUser.create()
        var reponse:Boolean=true
        apiInterface.getByEmail(emailET.text.toString()).enqueue(object :
            Callback<GetUserByEmailResponse> {

            override fun onResponse(call: Call<GetUserByEmailResponse>, response:
            Response<GetUserByEmailResponse>
            ) {
                val user = response.body()?.user
                Log.e("user : ", user.toString())
                if(user == null)
                {
                     reponse=false

                }

            }

            override fun onFailure(call: Call<GetUserByEmailResponse>, t: Throwable) {
                Toast.makeText(this@SignInActivity,"connexion failed",Toast.LENGTH_SHORT).show()
            }


        })
        return reponse
    }


}