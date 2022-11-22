package com.example.miniprojetandroid.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.SignInActivity


class Intro3 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intro3, container, false)

        val button = view.findViewById<Button>(R.id.hello)
        button.setOnClickListener {
            activity?.let{
                val intent = Intent (it, SignInActivity::class.java)
                it.startActivity(intent)
            it.finish()}

            }
        return view

    }


}