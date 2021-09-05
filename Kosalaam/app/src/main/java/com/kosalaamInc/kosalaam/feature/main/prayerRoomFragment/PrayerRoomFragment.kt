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
import android.widget.RelativeLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.FragmentSearchprayerroomBinding
import kotlinx.coroutines.CoroutineScope
import net.daum.android.map.MapViewEventListener
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class PrayerRoomFragment : Fragment(),MapView.MapViewEventListener{
    private lateinit var mapView : MapView
    private var binding : FragmentSearchprayerroomBinding? = null
    private lateinit var viewModel : PrayerRoomViewModel
    lateinit var mapViewContainer : RelativeLayout
    private val TAG = "PrayerRoomFragment"
    val permission = android.Manifest.permission.ACCESS_FINE_LOCATION

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_searchprayerroom,container,false)
        initMapVIew()
        checkPermission()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PrayerRoomViewModel::class.java)
        binding!!.lifecycleOwner = viewLifecycleOwner
        binding!!.prayerRoomVm = viewModel
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
            mapViewContainer.addView(mapView)
            for(i in 1..300){
                addTestPOIItem(i)
            }
            initObserve()
        } else {
            // Failed pass
        }

    }
    private fun checkPermission(){
        permReqLuncher.launch(permission)
    }

    private fun initMapVIew(){
        mapView = MapView(context)
        mapViewContainer = binding!!.searchMapview
        mapView.setMapViewEventListener(this)

    }

    private fun addTestPOIItem(i : Int){
        val marker = MapPOIItem()

        marker.apply{
            itemName = i.toString()
            mapPoint = MapPoint.mapPointWithGeoCoord(37.41+(Math.random()/3),126.73+(Math.random()/2))
        }
        mapView.addPOIItem(marker)
    }

    override fun onMapViewInitialized(p0: MapView?) {

    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        Log.d(TAG,p1.toString())
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {

    }
}