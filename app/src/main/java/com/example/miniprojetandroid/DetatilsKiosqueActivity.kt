package com.example.miniprojetandroid

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.miniprojetandroid.entities.DeleteKiosqueResponse
import com.example.miniprojetandroid.entities.SingleKiosqueRes
import com.example.miniprojetandroid.entities.UpdateKiosqueResponse
import com.example.miniprojetandroid.entities.UserX
import com.example.miniprojetandroid.network.ApiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class DetatilsKiosqueActivity : AppCompatActivity() {
    lateinit var idKiosque : String
    lateinit var nomKiosque : TextView
    lateinit var nomKiosque1 : TextView
    lateinit var addressKiosque : TextView
    lateinit var addressKiosque1 : TextView
    lateinit var coordonnee : TextView
    lateinit var img : ImageView
    lateinit var back : ImageView

    lateinit var mSharedPref: SharedPreferences
    lateinit var refuseBtn : Button
    lateinit var acceptBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detatils_kiosque)

        idKiosque = intent.getStringExtra("id").toString()


        mSharedPref = getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        nomKiosque = findViewById(R.id.textView43)
        refuseBtn = findViewById(R.id.button3)
        acceptBtn = findViewById(R.id.button4)
        nomKiosque = findViewById(R.id.textView43)
        nomKiosque1 = findViewById(R.id.textView47)
        addressKiosque = findViewById(R.id.textView44)
        addressKiosque1 = findViewById(R.id.textView48)
        coordonnee = findViewById(R.id.textView49)
        back = findViewById(R.id.imageView19)
        img = findViewById(R.id.profile_image)
        charge()

        back.setOnClickListener {
            intent = Intent(this,AdminActivity::class.java)
            startActivity(intent)
            finish()
        }

        refuseBtn.setOnClickListener{
            deleteKiosque()
        }

        acceptBtn.setOnClickListener {
            acceptKiosque()
        }

    }

    private fun acceptKiosque() {
        val apiInterface = ApiUser.create()
        val map: HashMap<String, Int> = HashMap()

        map["Status"] = 1
        apiInterface.updateKiosque(map,idKiosque).enqueue(object : Callback<UpdateKiosqueResponse> {

            override fun onResponse(call: Call<UpdateKiosqueResponse>, response: Response<UpdateKiosqueResponse>) {

                val kiosque = response.body()
                intent = Intent(this@DetatilsKiosqueActivity,AdminActivity::class.java)
                startActivity(intent)

            }

            override fun onFailure(call: Call<UpdateKiosqueResponse>, t: Throwable) {
                Toast.makeText(this@DetatilsKiosqueActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun deleteKiosque() {
        val apiInterface = ApiUser.create()

        apiInterface.deleteKiosque(idKiosque).enqueue(object : Callback<DeleteKiosqueResponse> {

            override fun onResponse(call: Call<DeleteKiosqueResponse>, response: Response<DeleteKiosqueResponse>) {

                val kiosque = response.body()
               intent = Intent(this@DetatilsKiosqueActivity,AdminActivity::class.java)
                startActivity(intent)

            }

            override fun onFailure(call: Call<DeleteKiosqueResponse>, t: Throwable) {
                Toast.makeText(this@DetatilsKiosqueActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun charge() {
        val apiInterface = ApiUser.create()

        apiInterface.getKiosqueById(idKiosque).enqueue(object : Callback<SingleKiosqueRes> {

            override fun onResponse(call: Call<SingleKiosqueRes>, response: Response<SingleKiosqueRes>) {

                val kiosque = response.body()
                Log.e("yosri",response.body().toString())
                nomKiosque.text = kiosque?.kiosque?.name.toString()
                nomKiosque1.text = kiosque?.kiosque?.name.toString()
                addressKiosque.text = kiosque?.kiosque?.adresse.toString()
                addressKiosque1.text = kiosque?.kiosque?.adresse.toString()
                mSharedPref.edit().apply{
                    putString(COOR, response.body()?.kiosque?.coordenation)
                }.apply()
                val executor = Executors.newSingleThreadExecutor()
                val handler = Handler(Looper.getMainLooper())
                var image: Bitmap? = null
                executor.execute {
                    val imageURL = kiosque?.kiosque?.image?.url
                    try {
                        val `in` = java.net.URL(imageURL).openStream()
                        image = BitmapFactory.decodeStream(`in`)
                        handler.post {
                            img.setImageBitmap(image)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<SingleKiosqueRes>, t: Throwable) {
                Toast.makeText(this@DetatilsKiosqueActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })

    }


}