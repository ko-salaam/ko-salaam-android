package com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostInfo.hostInfoEdit

import android.Manifest
import android.R.attr.data
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityHostInfoEditBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.PrayerRoomImageRvAdapter
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.addressSelect.AddressSelectActivity
import java.io.File
import java.io.InputStream


class HostInfoEditAcitvity : AppCompatActivity() {

    companion object{
        var address : String? = null
        var latitude : Double? = 0.0000000
        var longitude : Double? = 0.0000000
    }
    var currentImageURL = ArrayList<Uri>()
    var imagesPath = ArrayList<String?>()
    var compassBoolean : Boolean = false
    var washingRoomBoolean : Boolean = false
    var quranBoolean  : Boolean = false
    var prayerMatBoolean : Boolean = false
    private var binding: ActivityHostInfoEditBinding? = null
    private val viewModel: HostInfoEditViewModel by lazy {
        ViewModelProvider(this).get(HostInfoEditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityHostInfoEditBinding>(
            this, R.layout.activity_host_info_edit).apply {
            lifecycleOwner = this@HostInfoEditAcitvity
            hostInfoEditVm = viewModel
        }
        // 정보 받아오기
        initObserve()
        initClickListener()
    }

    private fun initObserve() {

    }

    private fun initClickListener() {
        binding!!.ivHostRegistrationInit.setOnClickListener {
            currentImageURL.clear()
            selectGallery()
        }
        binding!!.tvHostInfoSave.setOnClickListener {
            //viewModel.getUserInfo()
        }
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
        binding!!.clHostInfoAddress.setOnClickListener {
            startActivity(Intent(this,AddressSelectActivity::class.java))
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
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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
                    imagesPath.add(getRealPathFromURI(imageUri))
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
                                currentImageURL.add(imageUri) //uri를 list에 담는다.
                                imagesPath.add(getRealPathFromURI(imageUri))
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

    private fun uploadFile(filePath : String){
        var file = File(filePath)
        var inputStream : InputStream? = null
        try{

        }
        catch (e : Exception){

        }
    }

    override fun onResume() {
        super.onResume()
        binding!!.tvHostInfoAddressInit.text = address
    }
}