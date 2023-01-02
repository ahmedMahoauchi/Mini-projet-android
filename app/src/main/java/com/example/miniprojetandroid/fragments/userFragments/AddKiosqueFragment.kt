package com.example.miniprojetandroid.fragments.userFragments

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.miniprojetandroid.PREF_NAME
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.USER_KIOSQUE
import com.example.miniprojetandroid.UserMainActivity

class AddKiosqueFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var back : ImageView
    lateinit var mSharedPref: SharedPreferences



    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_add_kiosque, container, false)
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val button: Button = view.findViewById(R.id.locationBtn)

        button.setOnClickListener {

        }
        back = view.findViewById(R.id.imageView23)

        back.setOnClickListener {
            var fragment : Fragment
            if (!mSharedPref.getString(USER_KIOSQUE,"hello")?.equals("null")!!){
                fragment = KiosqueDetailsFragment()
            }else{
                fragment = VoidKiosqueFragment()
            }


            showFragment(fragment)
        }
        return view
    }




    fun showFragment(fragment: Fragment){
        val fram = getParentFragmentManager().beginTransaction()
        fram.replace(R.id.fragmentContainerView4,fragment)
        fram.commit()
    }


}