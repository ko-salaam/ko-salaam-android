package com.kosalaamInc.kosalaam.feature.main.myPageFragment.addressSelect

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostInfo.hostInfoEdit.HostInfoEditAcitvity
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostResistration.HostResistrationActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapReverseGeoCoder
import net.daum.mf.map.api.MapReverseGeoCoder.ReverseGeoCodingResultListener
import net.daum.mf.map.api.MapView

class AddressSelectActivity : AppCompatActivity(), MapView.MapViewEventListener,
    MapReverseGeoCoder.ReverseGeoCodingResultListener{

    companion object{
        var status = 1
        // 호스트 등록 // 호스트 수정
    }
    var latitude: Double = 37.498095
    var longitude: Double = 127.02761
    var address : String? = null
    private var mapView: MapView? = null
    private var mapPoint : MapPoint? = null
    var marker = MapPOIItem()
    lateinit var reverseGeoCoder : MapReverseGeoCoder
    lateinit var mapViewContainer: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prayer_room_select)
        mapView = MapView(this)
        mapViewContainer = findViewById(R.id.rl_restaurantInfo_mapview)
        mapViewContainer.addView(mapView)
        mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude),true)
        var selectText : TextView = findViewById(R.id.tv_select_select)
        mapView!!.setMapViewEventListener(this)

        selectText.setOnClickListener {
            reverseGeoCoder.startFindingAddress()


        }
    }

    private fun setMarker(setMapPoint : MapPoint?){
        marker.apply {
            itemName = "Current Location"
            markerType = MapPOIItem.MarkerType.RedPin
            mapPoint = setMapPoint
        }
        mapView!!.addPOIItem(marker)
    }

    override fun onMapViewInitialized(p0: MapView?) {

    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        mapView!!.removeAllPOIItems()
        reverseGeoCoder = MapReverseGeoCoder(getString(R.string.kakaoSDK),p1,this,this)
        setMarker(p1)
        latitude = p1?.mapPointGeoCoord!!.latitude
        longitude = p1?.mapPointGeoCoord!!.longitude
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

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

    override fun onReverseGeoCoderFoundAddress(p0: MapReverseGeoCoder?, p1: String?) {
        address=p1
        HostInfoEditAcitvity.address = address
        HostInfoEditAcitvity.latitude = latitude
        HostInfoEditAcitvity.longitude = longitude
        HostResistrationActivity.address= address
        HostResistrationActivity.latitude = latitude
        HostResistrationActivity.longitude = longitude
        Log.d("GeoCoderThis",address.toString())
        this.finish()
        Log.d("GeoGoderThis","success")
    }

    override fun onReverseGeoCoderFailedToFindAddress(p0: MapReverseGeoCoder?) {
        Log.d("GeoGoderThis","fail")

    }

}