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
import androidx.activity.result.contract.ActivityResultContracts
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
    private val TAG = "PrayerRoomFragment"
    val permission = android.Manifest.permission.ACCESS_FINE_LOCATION

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    private fun initObserve(){
        with(viewModel){

        }
    }

    // TODO Neither network gps ->  ?
    val permReqLuncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it) {
            val mapView = MapView(context)
            val mapViewContainer = binding!!.searchMapview
            mapViewContainer.addView(mapView)
            initObserve()
        } else {
            // Failed pass
        }
    }
    fun checkPermission(){
        permReqLuncher.launch(permission)
    }



}