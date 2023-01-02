package com.example.miniprojetandroid.fragments.userFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.miniprojetandroid.FormAddKiosqueActivity
import com.example.miniprojetandroid.R



class VoidKiosqueFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_void_kiosque, container, false)

        val button = view.findViewById<Button>(R.id.button2)

        button.setOnClickListener {
           val intent = Intent(activity,FormAddKiosqueActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }



        return view
    }

    fun showFragment(fragment: Fragment){
        val fram = getParentFragmentManager().beginTransaction()
        fram.replace(R.id.fragmentContainerView4,fragment)
        fram.commit()
    }
}