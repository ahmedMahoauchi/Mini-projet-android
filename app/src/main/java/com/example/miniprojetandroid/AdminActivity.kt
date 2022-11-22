package com.example.miniprojetandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.miniprojetandroid.fragments.AdminKiosqueFragment
import com.example.miniprojetandroid.fragments.AdminPendingKiosqueFragment
import com.example.miniprojetandroid.fragments.AdminUsersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

       bottomNavigation.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.kiosques -> {
                    val fragment = AdminKiosqueFragment()
                    showFragment(fragment)
                    return@setOnItemSelectedListener true
                }
                R.id.users -> {
                    val fragment = AdminUsersFragment()
                    showFragment(fragment)
                    return@setOnItemSelectedListener true
                }
                R.id.pending -> {
                    val fragment = AdminPendingKiosqueFragment()
                    showFragment(fragment)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }


    }

    fun showFragment(fragment: Fragment){
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.fragmentContainerView3,fragment)
        fram.commit()
    }
}