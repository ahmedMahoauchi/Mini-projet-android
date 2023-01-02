package com.example.miniprojetandroid.fragments.userFragments

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miniprojetandroid.*
import com.example.miniprojetandroid.entities.SingleKiosqueRes
import com.example.miniprojetandroid.network.ApiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors


class KiosqueDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var idKiosque : String
    lateinit var nomKiosque : TextView
    lateinit var nomKiosque1 : TextView
    lateinit var addressKiosque : TextView
    lateinit var addressKiosque1 : TextView
    lateinit var coordonnee : TextView
    lateinit var img : ImageView
    lateinit var product : Button
    lateinit var service : Button
    lateinit var mSharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_kiosque_details, container, false)
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        idKiosque = mSharedPref.getString(USER_KIOSQUE, "null").toString()

        nomKiosque = view.findViewById(R.id.textView43)
        nomKiosque = view.findViewById(R.id.textView43)
        nomKiosque1 = view.findViewById(R.id.textView47)
        addressKiosque = view.findViewById(R.id.textView44)
        addressKiosque1 = view.findViewById(R.id.textView48)
        product = view.findViewById(R.id.button3)
        service = view.findViewById(R.id.button4)
        coordonnee = view.findViewById(R.id.textView49)
        img = view.findViewById(R.id.profile_image)
        charge()


        product.setOnClickListener {
            val fragment = ProductFragment()
            showFragment(fragment)
        }

        service.setOnClickListener {
            val fragment = ServiceFragment()
            showFragment(fragment)
        }

       // Toast.makeText(context, idKiosque , Toast.LENGTH_SHORT).show()

        return view
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
                    putString(USER_COORD, response.body()?.kiosque?.coordenation)
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
                Toast.makeText(context, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })

    }
    fun showFragment(fragment: Fragment){
        val fram = getParentFragmentManager().beginTransaction()
        fram.replace(R.id.fragmentContainerView4,fragment)
        fram.commit()
    }

}