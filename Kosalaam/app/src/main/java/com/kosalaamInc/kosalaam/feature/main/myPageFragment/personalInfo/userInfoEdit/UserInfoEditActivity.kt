package com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityPersonalInfoEditBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.PrayerRoomImageRvAdapter
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit.changePassword.ChangePasswordActivity
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit.changePassword.ChangePasswordViewModel
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.phoneNumRegister.PhoneNumRegisterActivity
import com.kosalaamInc.kosalaam.model.data.UserData
import com.kosalaamInc.kosalaam.util.CheckInternet
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

@AndroidEntryPoint
class UserInfoEditActivity : AppCompatActivity() {

    companion object {
        var password = ""
        var phoneNum = ""
    }
    var uri : Uri? = null
    var name : String? = null
    private var binding: ActivityPersonalInfoEditBinding? = null
    private val viewModel: UserInfoEditViewModel by lazy {
        ViewModelProvider(this).get(UserInfoEditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPersonalInfoEditBinding>(
            this, R.layout.activity_personal_info_edit).apply {
            lifecycleOwner = this@UserInfoEditActivity
            userInfoEditVm = viewModel
        }

        if (CheckInternet().checkInternet(this)) {
            viewModel.getUserInfo()
        } else {
            Toast.makeText(this, "Check your internet", Toast.LENGTH_SHORT).show()
        }
        password = com.kosalaamInc.kosalaam.global.Application.prefs.getString("password",
            "") // password change verify 후?
        if (com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform",
                "") != "email"
        ) {
            binding!!.clHostInfoPassword.visibility = View.GONE
            binding!!.view15HostInfo.visibility = View.GONE
            binding!!.view16HostInfo.visibility = View.GONE
            binding!!.view17HostInfo.visibility = View.GONE

        } else {
            var passwordText: String? = ""
            for (i in 0..password.length - 1) {
                passwordText += "●"
            }
            binding!!.tvHostInfoPasswordInit.text = passwordText
        }
        initClickListener()
        initObserve()
    }

    private fun initObserve() {
        with(viewModel) {
            userData.observe(this@UserInfoEditActivity, Observer {
                if (it.profileImg != null) {
                    Glide.with(this@UserInfoEditActivity).load(it.profileImg).circleCrop()
                        .into(binding!!.cvHostInfoProfileImage)
                }
                // name,phoneNum plus -
                phoneNum = it.phoneNumber.toString()
                binding!!.tvHostInfoEmail.text = it.email
                binding!!.tvHostInfoEmailSetInit.text = it.email
                binding!!.tvHostInfoPhoneNumInit.text = it.phoneNumber
                binding!!.tvHostInfoName.text = it.name
                binding!!.tvHostInfoNameSetInit.hint = it.name
                name = it.name
            })
        }
    }

    private fun initClickListener() {
        binding!!.ivHostInfoBackArrow.setOnClickListener {
            this.finish()
        }

        binding!!.clHostInfoPassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
        binding!!.tvHostInfoEdit.setOnClickListener {
            if (CheckInternet().checkInternet(this)) {
                if(binding!!.tvHostInfoNameSetInit.text!=null){
                    name=binding!!.tvHostInfoNameSetInit.text.toString()
                }

                var phoneNumber : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),binding!!.tvHostInfoPhoneNumInit.text.toString())
                val hashMap  = HashMap<String,RequestBody>()

                    viewModel.updateUserInfo(UserData(phoneNum,name))

                if (com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform",
                        "") == "email"
                ) {
                    com.kosalaamInc.kosalaam.global.Application.user!!.updatePassword(
                        password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                com.kosalaamInc.kosalaam.global.Application.prefs.setString("password",
                                    password)
                            } else {
                                Toast.makeText(this, "Password change fail", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                this.finish()
            }
            else{

            }

        }
        binding!!.clHostInfoPhoneNum.setOnClickListener {
            startActivity(Intent(this, PhoneNumRegisterActivity::class.java))
            PhoneNumRegisterActivity.status = 1
        }

        binding!!.cvHostInfoProfileImage.setOnClickListener {
            selectGallery()
        }
    }

    override fun onResume() {
        super.onResume()
        binding!!.tvHostInfoPasswordInit.text = password
        binding!!.tvHostInfoPhoneNumInit.text = phoneNum

    }

    @Suppress("DEPRECATION")
    private fun selectGallery() {
        var writePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var readPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE),
                1007)
        } else {
            var intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 105)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 105 && resultCode == Activity.RESULT_OK && intent != null) {
            if (data == null) {   // 어떤 이미지도 선택하지 않은 경우
            } else {   // 이미지를 하나라도 선택한 경우
                if (data.getClipData() == null) {     // 이미지를 하나만 선택한 경우
                    val imageUri: Uri = data?.data!!
                    uri= imageUri
//                    currentImageURL.add(imageUri)
//                    imagesPath.add(getRealPathFromURI(imageUri))
                    Glide.with(applicationContext).load(imageUri)
                        .transform(CenterCrop(), RoundedCorners(10))
                        .into(binding!!.cvHostInfoProfileImage)

                }
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri): String? {

        var buildName = Build.MANUFACTURER
        if (buildName.equals("Xiaomi")) {
            return uri.path.toString()
        }
        var columnIndex = 0
        var proj = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = contentResolver.query(uri, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }

    private fun uploadFile(filePath: String) : MultipartBody.Part?{
        if (filePath != null) {
            var file = File(filePath)
            var inputStream : InputStream? = null

            try{
                inputStream = applicationContext.contentResolver.openInputStream(uri!!)
            }

            catch (e: IOException){
                e.printStackTrace()
            }

            var bitmap : Bitmap = BitmapFactory.decodeStream(inputStream)
            var byteArrayOutputStream : ByteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,40,byteArrayOutputStream)
            var requestBody: RequestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), byteArrayOutputStream.toByteArray())
            var fileToUpload: MultipartBody.Part =
                MultipartBody.Part.createFormData("images[]", file.name,requestBody)
            return fileToUpload
        }
        else{
            return null
        }
    }
}