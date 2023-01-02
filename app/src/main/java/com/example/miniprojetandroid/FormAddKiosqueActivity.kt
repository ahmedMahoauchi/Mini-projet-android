package com.example.miniprojetandroid

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.miniprojetandroid.entities.AddKiosqueResponse
import com.example.miniprojetandroid.entities.AddKiosqueToUserResponse
import com.example.miniprojetandroid.entities.UserX
import com.example.miniprojetandroid.network.ApiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormAddKiosqueActivity : AppCompatActivity(), LocationListener {

    lateinit var  kiosqueName : EditText
    lateinit var mSharedPref: SharedPreferences
    lateinit var  kiosqueAddress : EditText
    lateinit var  kiosqueLoocation : EditText
    lateinit var  getLocationbtn : Button
    lateinit var  submit : Button
    lateinit var  back : ImageView
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_add_kiosque)

        kiosqueName = findViewById(R.id.name)
        kiosqueAddress = findViewById(R.id.address)
        kiosqueLoocation = findViewById(R.id.location)
        getLocationbtn = findViewById(R.id.locationBtn)
        back = findViewById(R.id.imageView23)
        submit = findViewById(R.id.button)
        mSharedPref = getSharedPreferences(PREF_NAME,AppCompatActivity.MODE_PRIVATE)
        kiosqueLoocation.isEnabled = false

        getLocationbtn.setOnClickListener {
            getLocation()
        }

        back.setOnClickListener {
            val intent = Intent(this@FormAddKiosqueActivity,UserMainActivity::class.java)
            startActivity(intent)
        }



        submit.setOnClickListener {
            addKiosque()
        }



    }

    private fun addKiosque() {
        val apiInterface = ApiUser.create()
        val map: HashMap<String, String> = HashMap()
        map["name"] = kiosqueName.text.toString()
        map["adresse"] = kiosqueAddress.text.toString()
        map["coordenation"] = kiosqueLoocation.text.toString()

        apiInterface.addKiosque(map).enqueue(object : Callback<AddKiosqueResponse> {

            override fun onResponse(call: Call<AddKiosqueResponse>, response: Response<AddKiosqueResponse>) {

                val kiosque = response
                Log.e("response",kiosque.toString())
                Toast.makeText(this@FormAddKiosqueActivity, "Added succesfully !", Toast.LENGTH_SHORT).show()

                addKiosqueToUser(kiosque.body()!!._id)

            }

            override fun onFailure(call: Call<AddKiosqueResponse>, t: Throwable) {
                Toast.makeText(this@FormAddKiosqueActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun addKiosqueToUser( kiosqueId : String) {
        val apiInterface = ApiUser.create()
        val map: HashMap<String, String> = HashMap()
        map["kiosqueId"] = kiosqueId

        apiInterface.addKiosqueToUser(map,mSharedPref.getString(USER_ID,"hello")).enqueue(object : Callback<AddKiosqueToUserResponse> {

            override fun onResponse(call: Call<AddKiosqueToUserResponse>, response: Response<AddKiosqueToUserResponse>) {

                val kiosque = response
                Log.e("response",kiosque.toString())
                Toast.makeText(this@FormAddKiosqueActivity, "Added to user succesfully !", Toast.LENGTH_SHORT).show()
                intent = Intent(this@FormAddKiosqueActivity,UserMainActivity::class.java)
                startActivity(intent)



            }

            override fun onFailure(call: Call<AddKiosqueToUserResponse>, t: Throwable) {
                Toast.makeText(this@FormAddKiosqueActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {
        kiosqueLoocation.text = Editable.Factory.getInstance().newEditable(location.latitude.toString()+","+location.longitude)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}