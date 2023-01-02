package com.example.miniprojetandroid.fragments

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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miniprojetandroid.*
import com.example.miniprojetandroid.entities.UpdateUser
import com.example.miniprojetandroid.entities.UploadImageResponse
import com.example.miniprojetandroid.entities.UserX
import com.example.miniprojetandroid.fragments.userFragments.IS_GRANTED_READ_IMAGES
import com.example.miniprojetandroid.fragments.userFragments.PERMS_REQUEST_CODE
import com.example.miniprojetandroid.fragments.userFragments.PICK_IMAGE_CODE
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
import java.util.concurrent.Executors


class AdminProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    lateinit var mSharedPref: SharedPreferences
    lateinit var name : EditText
    lateinit var nameT : TextView
    lateinit var email: EditText
    lateinit var emailT: TextView
    lateinit var cin : EditText
    lateinit var img : ImageView
    lateinit var uploadImageButton : ImageView

    var multipartImage: MultipartBody.Part? = null

    private var selectedImageUri: Uri? = null
    private  lateinit var path2:String
    private val pickImage = 100
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_profile, container, false)

        val btn = view.findViewById<Button>(R.id.button5)
        val button = view.findViewById<Button>(R.id.button)
        name = view.findViewById(R.id.name_edittext)
        nameT = view.findViewById(R.id.textView39)
        email = view.findViewById(R.id.email_edittext)
        emailT = view.findViewById(R.id.textView40)
        cin = view.findViewById(R.id.cin_edittext)
        img = view.findViewById(R.id.profile_image)





        uploadImageButton = view.findViewById(R.id.imageView20)
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME,AppCompatActivity.MODE_PRIVATE)
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME,AppCompatActivity.MODE_PRIVATE)

        nameT.text = mSharedPref.getString(USER_NAME,"hello")
        emailT.text = mSharedPref.getString(USER_EMAIL,"hello")

        btn.setOnClickListener {
            mSharedPref.edit().clear().commit();

            startActivity(Intent(context, SignInActivity::class.java))
            activity?.finish()

        }

        uploadImageButton.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_CODE)
        }

        button.setOnClickListener {
            modifier()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMS_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mSharedPref.edit().putBoolean(IS_GRANTED_READ_IMAGES, true).apply()
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }


    }

    private fun modifier() {
        val apiInterface = ApiUser.create()
        val map: HashMap<String, String> = HashMap()



        map["name"] = name.text.toString()
        map["CIN"] = cin.text.toString()

        apiInterface.updateUser(map,mSharedPref.getString(USER_ID,"hello")).enqueue(object : Callback<UpdateUser> {

            override fun onResponse(call: Call<UpdateUser>, response: Response<UpdateUser>) {

                val user = response
                Log.e("response",user.toString())


                    Toast.makeText(context, "updated succesfully", Toast.LENGTH_SHORT).show()
                mSharedPref.edit().apply{
                    putString(USER_NAME, name.text.toString())
                    putString(USER_CIN, cin.text.toString())
                }.apply()

                nameT.text = mSharedPref.getString(USER_NAME,"hello")

            }

            override fun onFailure(call: Call<UpdateUser>, t: Throwable) {
                Log.e("error",t.toString())
            }

        })
    }


}