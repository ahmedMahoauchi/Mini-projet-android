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
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.miniprojetandroid.entities.AddKiosqueToUserResponse
import com.example.miniprojetandroid.entities.SingleKiosqueRes
import com.example.miniprojetandroid.fragments.userFragments.KiosqueDetailsFragment
import com.example.miniprojetandroid.fragments.userFragments.UserProfileDetailsFragment
import com.example.miniprojetandroid.fragments.userFragments.WaitingKiosqueFragment
import com.example.miniprojetandroid.network.ApiUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class UserMainActivity : AppCompatActivity() {

    lateinit var mSharedPref: SharedPreferences
    lateinit var gso : GoogleSignInOptions
    lateinit var gsc : GoogleSignInClient
    lateinit var name : TextView
    lateinit var img : ImageView
    lateinit var settings : ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)

         name = findViewById(R.id.profile_name)
         img = findViewById(R.id.profile_image)
         settings = findViewById(R.id.settings)

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        //Toast.makeText(this,mSharedPref.getString(USER_KIOSQUE,"hello"),Toast.LENGTH_SHORT).show()
         if (!mSharedPref.getString(USER_KIOSQUE,"hello")?.equals("null")!!){

             checkStatut(mSharedPref.getString(USER_KIOSQUE,"hello")!!)
            //val fragment = KiosqueDetailsFragment()
            //showFragment(fragment)
         }



        initializeUserInfos()

        gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        gsc = GoogleSignIn.getClient(this,gso)


        settings.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this,settings)
            popupMenu.menuInflater.inflate(R.menu.popup,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.profile_details -> {
                        val fragment = UserProfileDetailsFragment()
                        showFragment(fragment)
                    }
                    R.id.logout ->{
                    gsc.signOut().addOnCompleteListener {
                        mSharedPref.edit().clear().commit();
                        finish()
                        startActivity(Intent(this,SignInActivity::class.java))
                    }
                    }
                }
                true
            })
            popupMenu.show()
        }




        }

    private fun checkStatut(kiosqueId: String) {
        val apiInterface = ApiUser.create()

        apiInterface.getKiosqueById(kiosqueId).enqueue(object :
            Callback<SingleKiosqueRes> {

            override fun onResponse(call: Call<SingleKiosqueRes>, response: Response<SingleKiosqueRes>) {

                val kiosque = response
                Log.e("response",kiosque.toString())
                if (kiosque.body()?.kiosque?.Status == 0){
                    val fragment = WaitingKiosqueFragment()
                    showFragment(fragment)
                }else{
                    val fragment =KiosqueDetailsFragment()
                    showFragment(fragment)
                }
                Toast.makeText(this@UserMainActivity, kiosque.body()?.kiosque?.Status.toString(), Toast.LENGTH_SHORT).show()



            }

            override fun onFailure(call: Call<SingleKiosqueRes>, t: Throwable) {
                Toast.makeText(this@UserMainActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun showFragment(fragment: Fragment){
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.fragmentContainerView4,fragment)
        fram.commit()
    }

    private fun initializeUserInfos() {
        name.text =mSharedPref.getString(USER_NAME,"hello")

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        executor.execute {
            val imageURL = mSharedPref.getString(USER_IMAGE,"null")
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
                handler.post {
                    img.setImageBitmap(image)
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}


