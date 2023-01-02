package com.example.miniprojetandroid.fragments.userFragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miniprojetandroid.PREF_NAME
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.entities.UploadImageToProductResponse
import com.example.miniprojetandroid.entities.UploadImageToServiceResponse
import com.example.miniprojetandroid.network.ApiUser
import com.example.miniprojetandroid.network.RealPathUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddImageToServiceFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var uploadImageButton : ImageView
    lateinit var id : String
    lateinit var img: ImageView
    private var selectedImageUri: Uri? = null
    private  lateinit var path2:String
    private val pickImage = 100
    lateinit var mSharedPref: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_image_to_service, container, false)

        val bundle = this.arguments

        id = bundle?.getString("id").toString()


        uploadImageButton = view.findViewById(R.id.imageView28)
        img = view.findViewById(R.id.profile_image4)
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)

        uploadImageButton.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_CODE)
        }


        val btn = view.findViewById<Button>(R.id.button)

        btn.setOnClickListener {
            val fragment = ServiceFragment()
            showFragment(fragment)
        }

        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode,resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            val rl= RealPathUtil()
            val p: String? = data?.data?.let { this.context?.let { it1 -> rl.getRealPath(it1, it) } }
            if(!p.isNullOrEmpty()){
                path2=p;
            }
            img.setImageURI(data?.data)
            selectedImageUri= Uri.parse(data?.dataString!!)
            addImageToKiosque()
        }


    }

    private fun addImageToKiosque() {


        val f= File(path2)
        val reqFile: RequestBody = f.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body: MultipartBody.Part= MultipartBody.Part.createFormData("image",f.name,reqFile)


        val apiInterface = ApiUser.create()



        if (body != null) {
            apiInterface.uploadImageToService(body,id)
                .enqueue(object : Callback<UploadImageToServiceResponse> {
                    override fun onResponse(
                        call: Call<UploadImageToServiceResponse>,
                        response: Response<UploadImageToServiceResponse>
                    ) {
                        Log.e("kalech",response.toString())
                        Toast.makeText(context, "uploaded", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<UploadImageToServiceResponse>, t: Throwable) {
                        Log.e("error: ", t.toString())
                    }
                })
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMS_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mSharedPref.edit().putBoolean(IS_GRANTED_READ_IMAGES, true).apply()
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }


    }
    fun showFragment(fragment: Fragment){
        val fram = getParentFragmentManager().beginTransaction()
        fram.replace(R.id.fragmentContainerView4,fragment)
        fram.commit()
    }
}