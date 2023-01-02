package com.example.miniprojetandroid.fragments.userFragments

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.Editable
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
import com.example.miniprojetandroid.*
import com.example.miniprojetandroid.entities.UploadImageResponse
import com.example.miniprojetandroid.entities.User
import com.example.miniprojetandroid.network.ApiUser
import com.example.miniprojetandroid.network.RealPathUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.concurrent.Executors

const val PICK_IMAGE_CODE = 100
const val PERMS_REQUEST_CODE = 101
const val IS_GRANTED_READ_IMAGES = "IS_GRANTED_READ_IMAGES"
const val PREFS_NAME = "APP_PREFS"
const val REQUEST_GALLERY = 9544

class UserProfileDetailsFragment : Fragment() {
    lateinit var mSharedPref: SharedPreferences
    lateinit var name : EditText
    lateinit var email: EditText
    lateinit var cin : EditText
    lateinit var img : ImageView
    lateinit var back : ImageView
    lateinit var productBtn : Button
    lateinit var serviceBtn : Button
    lateinit var uploadImageButton : ImageView

    lateinit var image: ImageView
    var multipartImage: MultipartBody.Part? = null

    private var selectedImageUri: Uri? = null
    private  lateinit var path2:String
    private val pickImage = 100
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_user_profile_details, container, false)

        val button = view.findViewById<Button>(R.id.button)
        name = view.findViewById(R.id.name_edittext)
        email = view.findViewById(R.id.email_edittext)
        cin = view.findViewById(R.id.cin_edittext)
        productBtn = view.findViewById(R.id.cin_edittext)
        serviceBtn = view.findViewById(R.id.cin_edittext)
        img = view.findViewById(R.id.profile_image)
        back = view.findViewById(R.id.imageView22)
        uploadImageButton = view.findViewById(R.id.imageView20)
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME,AppCompatActivity.MODE_PRIVATE)


        back.setOnClickListener {
            var fragment : Fragment
            if (!mSharedPref.getString(USER_KIOSQUE,"hello")?.equals("null")!!){
                fragment = KiosqueDetailsFragment()
            }else{
                fragment = VoidKiosqueFragment()
            }

            showFragment(fragment)
        }

        productBtn.setOnClickListener {
            val fragment = VoidKiosqueFragment()
            showFragment(fragment)
        }

        serviceBtn.setOnClickListener {
            val fragment = VoidKiosqueFragment()
            showFragment(fragment)
        }

        uploadImageButton.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_CODE)
        }


        name.text = Editable.Factory.getInstance().newEditable(mSharedPref.getString(USER_NAME,"hello"))
        email.text = Editable.Factory.getInstance().newEditable(mSharedPref.getString(USER_EMAIL,"hello"))
        cin.text = Editable.Factory.getInstance().newEditable(mSharedPref.getString(USER_CIN,"hello"))

        email.isEnabled = false
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        executor.execute {
            val imageURL = mSharedPref.getString(USER_IMAGE, "null")

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
        button.setOnClickListener {

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
            updateUser()
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

    private fun updateUser() {

        val f= File(path2)
        val reqFile: RequestBody = f.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body: MultipartBody.Part= MultipartBody.Part.createFormData("image",f.name,reqFile)


        val apiInterface = ApiUser.create()



        if (body != null) {
            apiInterface.uploadImage(body,mSharedPref.getString(USER_ID,"hello"))
                .enqueue(object : Callback<UploadImageResponse> {
                    override fun onResponse(
                        call: Call<UploadImageResponse>,
                        response: Response<UploadImageResponse>
                    ) {
                        mSharedPref.edit().apply{
                            putString(USER_IMAGE, response.body()?.user?.image?.url)
                        }.apply()


                        val executor = Executors.newSingleThreadExecutor()
                        val handler = Handler(Looper.getMainLooper())
                        var image: Bitmap? = null
                        executor.execute {
                            val imageURL = response.body()?.user?.image?.url

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
                        Log.e("hello: ", response.toString())
                    }

                    override fun onFailure(call: Call<UploadImageResponse>, t: Throwable) {
                        Log.e("error: ", t.toString())
                    }
                })
        }


    }

    fun showFragment(fragment: Fragment){
        val fram = getParentFragmentManager().beginTransaction()
        fram.replace(R.id.fragmentContainerView4,fragment)
        fram.commit()
    }

}