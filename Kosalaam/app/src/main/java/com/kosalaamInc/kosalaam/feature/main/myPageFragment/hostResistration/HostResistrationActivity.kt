package com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostResistration

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityHostResistrationBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.PrayerRoomImageRvAdapter
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.addressSelect.AddressSelectActivity
import com.kosalaamInc.kosalaam.model.data.HostRegisterData
import com.kosalaamInc.kosalaam.model.data.PraySuppliesData
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

@AndroidEntryPoint
class HostResistrationActivity : AppCompatActivity(){

    companion object{
        var address : String? = null
        var latitude : Double? = 0.0000000
        var longitude : Double? = 0.0000000
        var phoneNum : String? = null
    }
    var praySupplies = PraySuppliesData(false, false, false, false)
    var data : HostRegisterData? = null
    var uri : Uri? = null

    var filePaths = ArrayList<MultipartBody.Part>()
    var currentImageURL = ArrayList<Uri>()
    var imagesPath = ArrayList<String?>()
    var compassBoolean : Boolean = false
    var washingRoomBoolean : Boolean = false
    var quranBoolean  : Boolean = false
    var prayerMatBoolean : Boolean = false
    private var binding : ActivityHostResistrationBinding? = null
    private val viewModel : HostResistrationViewModel by lazy{
        ViewModelProvider(this).get(HostResistrationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityHostResistrationBinding>(
            this, R.layout.activity_host_resistration
        ).apply {
            lifecycleOwner = this@HostResistrationActivity
            hostResistrationVm = viewModel
        }
        binding!!.tvHostRegistrationRegister.background=getDrawable(R.drawable.login_mainoval)
        initClickListener()
        initObserve()
        binding!!.tvHostRegistrationEnterPhoneNumInit.text = phoneNum
    }

    private fun initClickListener() {
        binding!!.tvHostRegistrationRegister.setOnClickListener {
            praySupplies = PraySuppliesData(quranBoolean,
                prayerMatBoolean,
                compassBoolean,
                washingRoomBoolean)
            data = HostRegisterData(address.toString(), true, latitude, longitude,
                binding!!.etHostRegistrationPlaceName.text.toString(), phoneNum,
                "PRAYER_ROOM", praySupplies, binding!!.price.text.toString().toInt())
            var place : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                binding!!.etHostRegistrationPlaceName.text.toString())

            var hostId : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                "")

            var address : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                address!!)

            var phoneNumber : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                phoneNum!!)
            var placeType : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                "PRAYER_ROOM")
            var id : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                "")
            var detailInfo : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                "")
            var isKosalaamRoom : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                "")
            var isLiked : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                false.toString())
            var isParkingLot : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                false.toString())
            var latitude : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                latitude.toString())

            var likeCount : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                0.toString())

            var longtitude : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                longitude.toString())
            var managingType : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                "")
            var openingHours : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                "")
            var praySupplies : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                "")
            var price : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),
                "")
            val hashMap  = HashMap<String, RequestBody>()

            hashMap.put("name", place)
            hashMap.put("address", address)
            hashMap.put("placeType", placeType)
            hashMap.put("phoneNumber", phoneNumber)
            hashMap.put("id",id)
            hashMap.put("detailInfo",detailInfo)
            hashMap.put("isKosalaamRoom",isKosalaamRoom)
            hashMap.put("isLiked",isLiked)
            hashMap.put("isParkingLot",isParkingLot)
            hashMap.put("latitude",latitude)
            hashMap.put("longitude",longtitude)
            hashMap.put("likeCount",likeCount)
            hashMap.put("managingType",managingType)
            hashMap.put("openingHours",openingHours)
            hashMap.put("praySupplies",praySupplies)
            hashMap.put("price",price)
            hashMap.put("hostId",hostId)
            Toast.makeText(this,"Sorry we're preparing",Toast.LENGTH_SHORT).show()
//            viewModel.postPrayerRoomInfo(filePaths, hashMap, data!!)
            this.finish()
            //viewModel.postPrayerRoomInfoTest(data!!)
        }

        binding!!.ivHostRegistrationInit.setOnClickListener {
            currentImageURL.clear()
            selectGallery()
        }

        binding!!.tvHostRegistrationEnterAddressInit.setOnClickListener {
            startActivity(Intent(this, AddressSelectActivity::class.java))
        }
//        binding!!.tvHostInfoSave.setOnClickListener {
//            //viewModel.getUserInfo()
//        }

        binding!!.ivHostInfoCompassCheck.setOnClickListener{
            if(compassBoolean){
                compassBoolean=false
                binding!!.ivHostInfoCompassCheck.setImageResource(R.drawable.checkbox_default)
            }
            else{
                compassBoolean=true
                binding!!.ivHostInfoCompassCheck.setImageResource(R.drawable.checkbox_check)
            }
        }

        binding!!.ivHostInfoPrayerMatCheck.setOnClickListener {
            if(prayerMatBoolean){
                binding!!.ivHostInfoPrayerMatCheck.setImageResource(R.drawable.checkbox_default)
                prayerMatBoolean=false
            }

            else{
                prayerMatBoolean= true
                binding!!.ivHostInfoPrayerMatCheck.setImageResource(R.drawable.checkbox_check)
            }
        }

        binding!!.ivHostInfoQuranCheck.setOnClickListener {
            if(quranBoolean){
                quranBoolean = false
                binding!!.ivHostInfoQuranCheck.setImageResource(R.drawable.checkbox_default)
            }

            else{
                quranBoolean = true
                binding!!.ivHostInfoQuranCheck.setImageResource(R.drawable.checkbox_check)
            }
        }

        binding!!.ivHostInfoWashingRoomCheck.setOnClickListener {
            if(washingRoomBoolean){
                binding!!.ivHostInfoWashingRoomCheck.setImageResource(R.drawable.checkbox_default)
                washingRoomBoolean =false
            }

            else{
                binding!!.ivHostInfoWashingRoomCheck.setImageResource(R.drawable.checkbox_check)
                washingRoomBoolean=true
            }
        }
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
                1004)
        } else {
            var intent = Intent()
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.setAction(Intent.ACTION_GET_CONTENT);
            Toast.makeText(applicationContext,
                "Long Click, then you can upload multiple image ",
                Toast.LENGTH_LONG).show()
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 102)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 102 && resultCode == Activity.RESULT_OK && intent != null) {
            if (data == null) {   // 어떤 이미지도 선택하지 않은 경우

            } else {   // 이미지를 하나라도 선택한 경우
                if (data.getClipData() == null) {     // 이미지를 하나만 선택한 경우
                    val imageUri: Uri = data?.data!!
                    currentImageURL.add(imageUri)
                    binding!!.rvGallery.adapter = PrayerRoomImageRvAdapter(this, currentImageURL)

                } else {      // 이미지를 여러장 선택한 경우
                    val clipData: ClipData = data.clipData!!
                    Log.e("clipData", clipData.itemCount.toString())

                    if (clipData.itemCount > 20) {
                        Toast.makeText(applicationContext,
                            "you can select image 20 and under",
                            Toast.LENGTH_LONG)
                            .show()

                    } else {   // 선택한 이미지가 1장 이상 10장 이하인 경우
//                        Log.e(TAG, "multiple choice")
                        for (i in 0 until clipData.itemCount) {
                            val imageUri = clipData.getItemAt(i).uri // 선택한 이미지들의 uri를 가져온다.
                            try {
                                uri = imageUri
                                currentImageURL.add(imageUri)
                                filePaths.add(uploadFile(getFullPathFromUri(this,imageUri))!!)
//
                            } catch (e: Exception) {
//                                Log.e(TAG, "File select error", e)
                            }
                        }
                        binding!!.rvGallery.adapter =
                            PrayerRoomImageRvAdapter(this, currentImageURL)
                    }
                }
            }
        }
    }


    private fun uploadFile(filePath: String?) : MultipartBody.Part? {

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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
            Log.d("token2", filePath + "sd")
            var requestBody: RequestBody = RequestBody.create("image/jpg".toMediaTypeOrNull(),
                filePath)//byteArrayOutputStream.toByteArray())
            var fileToUpload: MultipartBody.Part =
                MultipartBody.Part.createFormData("imageFiles", file.name, requestBody)
            return fileToUpload
        }
        else{
            return null
        }
    }

    override fun onResume() {
        Log.d("ResumeAddress", address.toString())
        binding!!.tvHostRegistrationEnterAddressInit.text = address
        super.onResume()
    }
    private fun initObserve(){
        viewModel.postPrayerRoom.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                this.finish()
            }
        })
    }
    fun getFullPathFromUri(ctx: Context?, fileUri: Uri?): String? {
        var fullPath: String? = null
        val column = "_data"
        var cursor: Cursor? = ctx!!.getContentResolver().query(fileUri!!, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            var document_id = cursor.getString(0)
            if (document_id == null) {
                for (i in 0 until cursor.columnCount) {
                    if (column.equals(cursor.getColumnName(i), ignoreCase = true)) {
                        fullPath = cursor.getString(i)
                        break
                    }
                }
            } else {
                document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
                cursor.close()
                val projection = arrayOf(column)
                try {
                    cursor = ctx.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Images.Media._ID + " = ? ",
                        arrayOf(document_id),
                        null)
                    if (cursor != null) {
                        cursor.moveToFirst()
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column))
                    }
                } finally {
                    if (cursor != null) cursor.close()
                }
            }
        }
        return fullPath
    }
}