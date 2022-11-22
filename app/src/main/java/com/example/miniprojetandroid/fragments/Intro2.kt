package com.example.miniprojetandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.miniprojetandroid.R


class Intro2 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_intro2, container, false)
        val button = view.findViewById<Button>(R.id.start_btn)
        button.setOnClickListener {
            val f3 = Intro3()
            showFragment(f3)
        }
        return view
    }
    fun showFragment(fragment: Fragment){
        val fram = getParentFragmentManager().beginTransaction()
        fram.replace(R.id.fragmentContainerView2,fragment)
        fram.commit()
    }

}