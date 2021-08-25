package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.FragmentSearchprayerroomBinding
import kotlinx.coroutines.CoroutineScope
import net.daum.mf.map.api.MapView


class PrayerRoomFragment : Fragment(){
    private var binding : FragmentSearchprayerroomBinding? = null
    private lateinit var viewModel : PrayerRoomViewModel
    private val REQUEST_PERMISSION_LOCATION =10
    private val TAG = "PrayerRoomFragment"
    val permission = android.Manifest.permission.ACCESS_FINE_LOCATION

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO(initPermission)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_searchprayerroom,container,false)
        checkPermission()
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PrayerRoomViewModel::class.java)
        binding!!.lifecycleOwner=viewLifecycleOwner
        binding!!.prayerRoomVm= viewModel
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
    // 위치 권한이 있는지 확인하는 메서드

    // 사용자에게 권한 요청 후 결과에 대한 처리 로직
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.d(TAG, "onRequestPermissionsResult()")
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult() _ 권한 허용 클릭")
                //startLocationUpdates()

            } else {
                Log.d(TAG, "onRequestPermissionsResult() _ 권한 허용 거부")
                //Toast.makeText(this@PrayerRoomFragment, "check location permission", Toast.LENGTH_SHORT).show()
            }
        }
    }
    val permReqLuncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it) {
            val mapView = MapView(context)
            val mapViewContainer = binding!!.searchMapview
            mapViewContainer.addView(mapView)
        } else {
            // Failed pass
        }
    }
    fun checkPermission(){
        permReqLuncher.launch(permission)
    }



}