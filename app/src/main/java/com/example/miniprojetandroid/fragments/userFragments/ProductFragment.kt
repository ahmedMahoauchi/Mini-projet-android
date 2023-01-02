package com.example.miniprojetandroid.fragments.userFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.miniprojetandroid.R

class ProductFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        val back = view.findViewById<ImageView>(R.id.imageView21)
        val add = view.findViewById<ImageView>(R.id.imageView24)


        back.setOnClickListener {
            val fragment = KiosqueDetailsFragment()
            showFragment(fragment)
        }

        add.setOnClickListener {
            val fragment = AddProductFragment()
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