package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.restaurantInfo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityRestaurantInfoBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class RestaurantInfoActivity : AppCompatActivity(){

    private lateinit var binding : ActivityRestaurantInfoBinding

    private var mapView : MapView? = null
    lateinit var mapViewContainer : RelativeLayout

    companion object{
        var idNum : Int = 0
        var uuid : String? = ""
    }

    private val viewModel: RestaurantInfoViewModel by lazy {
        ViewModelProvider(this).get(RestaurantInfoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityRestaurantInfoBinding>(
            this, R.layout.activity_restaurant_info
        ).apply {
            lifecycleOwner = this@RestaurantInfoActivity
            restaurantInfoVm = viewModel
        }

        mapView = MapView(this)
        binding!!.rlRestaurantInfoMapview.addView(mapView)


        viewModel.restaurantData.observe(this, Observer {
            Log.d("prayerRoomInfo",it.name.toString())
            mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(it.latitude,
                it.longitude), true)
            var marker = MapPOIItem()
            marker.apply{
                itemName = it.name
                markerType=MapPOIItem.MarkerType.RedPin
                mapPoint = MapPoint.mapPointWithGeoCoord(it.latitude,
                    it.longitude)
                setCustomImageAnchor(0.5f, 1.0f)
            }

            mapView!!.addPOIItem(marker)
            binding!!.tvRestaurantInfoOpenTimeInsert.text = it.openingHours
            binding!!.tvRestaurantInfoName.text = it.name
            binding!!.tvRestaurantInfoType.text = it.dishType
            binding!!.tvRestaurantInfoAddress.text = it.address
            binding!!.tvRestaurantInfoAddress2.text = it.address
            // Affinity none?
            if(it.muslimFriendly=="NONE"){
                binding!!.ivRestaurantInfoCertifyCheck.visibility= View.GONE
                binding!!.tvRestaurantInfoCertify.visibility = View.INVISIBLE
            }

            else{
                binding!!.tvRestaurantInfoCertify.text = it.muslimFriendly
            }

            if(it.phoneNumber==null){
                binding!!.tvRestaurantInfoPhoneNum.text = ""
            }

            else{
                binding!!.tvRestaurantInfoPhoneNum.text = it.phoneNumber
            }

            if(it.isParkingLog==true){
                binding!!.tvRestaurantInfoParkAbleInsert.text = "Available"
            }

            else{
                binding!!.tvRestaurantInfoParkAbleInsert.text = "Unavailable"
            }

        })
        viewModel.getRestaurantInfo(idNum)
    }

    override fun finish() {
        binding!!.rlRestaurantInfoMapview.removeView(mapView)
        super.finish()
    }
}