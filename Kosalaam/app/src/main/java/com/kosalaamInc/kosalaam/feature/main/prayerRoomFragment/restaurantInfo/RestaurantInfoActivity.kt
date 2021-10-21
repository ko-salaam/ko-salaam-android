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
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.ImageRvAdapter
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.PrayerRoomFragment
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.SearchRvAdapter
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.hotelInfo.HotelInfoActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class RestaurantInfoActivity : AppCompatActivity(){

    private lateinit var binding : ActivityRestaurantInfoBinding
    var images : ArrayList<String>? = null
    private var heartStatus : Boolean = false

    private var mapView : MapView? = null
    lateinit var mapViewContainer : RelativeLayout

    companion object{
        var idNum : String? = null
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

        initClickListener()
        mapView = MapView(this)
        binding!!.rlRestaurantInfoMapview.addView(mapView)

        viewModel.restaurantData.observe(this, Observer {
            if(it.isLiked==true){
                binding!!.ivRestaurantInfoHeart.setImageResource(R.drawable.info_heart_fill)
                heartStatus=true
            }

            else{
                binding!!.ivRestaurantInfoHeart.setImageResource(R.drawable.info_heart_default)
                heartStatus=false
            }

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

            if(it.imagesId!!.size!! >0){
                for(i in 0..it.imagesId!!.size!!-1){
                    images?.add(it.imagesId!!.get(i))
                }
                val imageAdapter = ImageRvAdapter(this,it.imagesId)
                binding!!.rvGallery.adapter=imageAdapter
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
                binding!!.tvRestaurantInfoCertify.text = "NONE"
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
        viewModel.getRestaurantInfo(idNum!!)
        viewModel.likeData.observe(this, Observer {
            if(it==true){
                heartStatus=true
                binding!!.ivRestaurantInfoHeart.setImageResource(R.drawable.info_heart_fill)
            }
            else{
                heartStatus=false
                binding!!.ivRestaurantInfoHeart.setImageResource(R.drawable.info_heart_default)
            }
        })
    }

    override fun finish() {
        binding!!.rlRestaurantInfoMapview.removeView(mapView)
        PrayerRoomFragment.pageNum--
        super.finish()
    }
    private fun initClickListener(){
        binding!!.ivRestaurantInfoBackArrow.setOnClickListener {
            this.finish()
        }

        binding!!.ivRestaurantInfoHeart.setOnClickListener {
            if(heartStatus==false){
                viewModel.restaurantLike(idNum)
            }

            else{
                viewModel.restaurantLikeCancel(idNum)
            }
        }
    }
}