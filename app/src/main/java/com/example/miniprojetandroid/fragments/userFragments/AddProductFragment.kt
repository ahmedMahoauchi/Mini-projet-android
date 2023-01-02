package com.example.miniprojetandroid.fragments.userFragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.miniprojetandroid.PREF_NAME
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.USER_KIOSQUE
import com.example.miniprojetandroid.entities.AddProductResponse
import com.example.miniprojetandroid.entities.AddProductToKiosqueResponse
import com.example.miniprojetandroid.network.ApiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddProductFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var name : EditText
    lateinit var price : EditText
    lateinit var mSharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_product, container, false)
        val back = view.findViewById<ImageView>(R.id.imageView21)


         name = view.findViewById(R.id.name)
         price = view.findViewById(R.id.price)

         mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)

        val btn = view.findViewById<Button>(R.id.button)

        btn.setOnClickListener {
            addProduct()
        }


        back.setOnClickListener {
            val fragment = ProductFragment()
            showFragment(fragment)
        }



        return view
    }

    private fun addProduct() {
        val apiInterface = ApiUser.create()
        val map: HashMap<String, String> = HashMap()
        map["name"] = name.text.toString()
        map["price"] = price.text.toString()

        apiInterface.addProduct(map).enqueue(object : Callback<AddProductResponse> {

            override fun onResponse(call: Call<AddProductResponse>, response: Response<AddProductResponse>) {

                val kiosque = response
                Log.e("response",kiosque.toString())
                //Toast.makeText(this@FormAddKiosqueActivity, "Added succesfully !", Toast.LENGTH_SHORT).show()

                addProductToKiosque(kiosque.body()!!._id)

            }

            override fun onFailure(call: Call<AddProductResponse>, t: Throwable) {
                Toast.makeText(context, "Connexion 1 error!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun addProductToKiosque(_id: String) {
        val apiInterface = ApiUser.create()
        val map: HashMap<String, String> = HashMap()
        map["produitId"] = _id

        apiInterface.addProductToKiosque(map,mSharedPref.getString(USER_KIOSQUE,"hello")).enqueue(object : Callback<AddProductToKiosqueResponse> {

            override fun onResponse(call: Call<AddProductToKiosqueResponse>, response: Response<AddProductToKiosqueResponse>) {

                val kiosque = response
                Log.e("response",kiosque.toString())
                Toast.makeText(context, "added to kiosque", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("id",_id )
                val fragment = AddImageToProductFragment()
                fragment.setArguments(bundle);
                showFragment(fragment)

            }
                override fun onFailure(call: Call<AddProductToKiosqueResponse>, t: Throwable) {
                    Toast.makeText(context, "Connexion 2 error!", Toast.LENGTH_SHORT).show()
                }

            })

    }


    fun showFragment(fragment: Fragment){
        val fram = getParentFragmentManager().beginTransaction()
        fram.replace(R.id.fragmentContainerView4,fragment)
        fram.commit()
    }


}