package com.example.miniprojetandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.miniprojetandroid.entities.GetUserByEmailResponse
import com.example.miniprojetandroid.entities.ResetResponse
import com.example.miniprojetandroid.network.ApiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewPasswordActivity : AppCompatActivity() {
    lateinit var new_password : EditText
    lateinit var email : String
    lateinit var reponse : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)
        val decomteur = findViewById<TextView>(R.id.decompteur)

        object : CountDownTimer(1200000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val timeResult =
                    "${(millisUntilFinished / 1000 / 60).toString().padStart(2, '0')}:" +
                            "${(millisUntilFinished / 1000 % 60).toString().padStart(2, '0')} "
                decomteur.text =(timeResult)
            }

            override fun onFinish() {
                decomteur.setText("You Link Has Expired !")
            }
        }.start()


         email = intent.getStringExtra("email").toString()

        reponse = ""
        new_password = findViewById(R.id.new_password)
        getResetLinks()
        val button = findViewById<Button>(R.id.button)
        //Toast.makeText(this@NewPasswordActivity,reponse, Toast.LENGTH_SHORT).show()
        button.setOnClickListener {
            Log.e("ramzi",reponse.toString())
            sendPassword()
        }


    }

    private fun getResetLinks() {
        val apiInterface = ApiUser.create()
        apiInterface.getByEmail(email).enqueue(object :
            Callback<GetUserByEmailResponse> {

            override fun onResponse(call: Call<GetUserByEmailResponse>, response:
            Response<GetUserByEmailResponse>
            ) {

                val user = response.body()?.user
                Log.e("user : ", user.toString())
                if(user != null)
                {
                    this@NewPasswordActivity.reponse = user.resetLink

                }else{
                    Log.e("usersssss",response.body().toString())
                }

            }

            override fun onFailure(call: Call<GetUserByEmailResponse>, t: Throwable) {
                Toast.makeText(this@NewPasswordActivity,"connexion failed",Toast.LENGTH_SHORT).show()
            }


        })

    }

    private fun sendPassword() {
        val apiInterface = ApiUser.create()
        val map: HashMap<String, String> = HashMap()

        map["resetLink"] = reponse
        map["newPass"] = new_password.text.toString()

        apiInterface.resetPassword(map).enqueue(object : Callback<ResetResponse> {

            override fun onResponse(call: Call<ResetResponse>, response: Response<ResetResponse>) {

                val user = response.body()?.message
                Log.e("response",user.toString())

                if (user != null){
                    Toast.makeText(this@NewPasswordActivity, "Your Password changer succesfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@NewPasswordActivity, SignInActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@NewPasswordActivity, "OOOOOOPPPPSSSS", Toast.LENGTH_SHORT).show()


                }
            }

            override fun onFailure(call: Call<ResetResponse>, t: Throwable) {
                Toast.makeText(this@NewPasswordActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })

    }
}