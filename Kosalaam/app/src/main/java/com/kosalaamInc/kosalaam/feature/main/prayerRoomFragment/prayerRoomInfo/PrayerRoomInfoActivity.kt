package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.prayerRoomInfo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityPrayerroomInfoBinding
import com.kosalaamInc.kosalaam.databinding.ActivityRestaurantInfoBinding
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.ImageRvAdapter
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.PrayerRoomFragment
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.restaurantInfo.RestaurantInfoActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class PrayerRoomInfoActivity  : AppCompatActivity() {
    var images : ArrayList<String>? = null

    private var mapView : MapView? = null
    lateinit var mapViewContainer : RelativeLayout
    private var heartStatus : Boolean = false

    companion object{
        var idNum : String? = null
    }

    private lateinit var binding: ActivityPrayerroomInfoBinding
    private val viewModel: PrayerRoomInfoViewModel by lazy {
        ViewModelProvider(this).get(PrayerRoomInfoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPrayerroomInfoBinding>(
            this, R.layout.activity_prayerroom_info
        ).apply {
            lifecycleOwner = this@PrayerRoomInfoActivity
            prayerRoomInfoVm = viewModel
        }
        initClickListener()
        mapView = MapView(this)
        binding!!.rlRestaurantInfoMapview.addView(mapView)

        viewModel.prayerData.observe(this, Observer {
            if(it.isLiked==true){
                binding!!.ivRestaurantInfoHeart.setImageResource(R.drawable.info_heart_fill)
                heartStatus=true
            }

            else{
                binding!!.ivRestaurantInfoHeart.setImageResource(R.drawable.info_heart_default)
                heartStatus=false
            }

            Log.d("prayerRoomInfo",it.name.toString())
            mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(it.latitude!!,
                it.longitude!!), true)

            var marker = MapPOIItem()

            marker.apply{
                itemName = it.name
                markerType=MapPOIItem.MarkerType.RedPin
                mapPoint = MapPoint.mapPointWithGeoCoord(it.latitude,
                    it.longitude)
                setCustomImageAnchor(0.5f, 1.0f)
            }

//            if(it.imagesId?.size!! >0){
//                for(i in 0..it.imagesId!!.size!!-1){
//                    images?.add(it.imagesId!!.get(i))
//                }
//                val imageAdapter = ImageRvAdapter(this,it.imagesId)
//                binding!!.rvGallery.adapter=imageAdapter
//            }

            mapView!!.addPOIItem(marker)
            binding!!.tvRestaurantInfoName.text = it.name
            binding!!.tvRestaurantInfoAddress.text = it.address
            binding!!.tvRestaurantInfoAddress2.text = it.address

            // Affinity none?
            if(it.phoneNumber==null){
                binding!!.tvRestaurantInfoPhoneNum.text = ""
            }

            else{
                binding!!.tvRestaurantInfoPhoneNum.text = it.phoneNumber
            }

            if(it.isParkingLot==true){
                binding!!.tvRestaurantInfoParkAbleInsert.text = "Available"
            }

            else{
                binding!!.tvRestaurantInfoParkAbleInsert.text = "Unavailable"
            }
            if(it.hostId==null){
                binding!!.view7RestaurantInfo.visibility=View.GONE
                binding!!.clHostInfoHost.visibility=View.GONE
                binding!!.view35RestaurantInfo.visibility=View.GONE
                binding!!.view36RestaurantInfo.visibility=View.GONE
                binding!!.view14RestaurantInfo.visibility=View.GONE
                binding!!.view15RestaurantInfo.visibility=View.GONE
                binding!!.view16RestaurantInfo.visibility=View.GONE
                binding!!.clRestaurantInfoOpenTime.visibility=View.GONE
                binding!!.tvPrayerRoomBookNow.visibility=View.GONE
            }

            else{

            }

        })
        viewModel.getPrayerRoomInfo(idNum!!)
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
        binding!!.ivHostInfoBackArrow.setOnClickListener {
            this.finish()
        }

        binding!!.ivRestaurantInfoHeart.setOnClickListener {
            if(heartStatus==false){
                viewModel.prayerRoomLike(idNum)
            }

            else{
                viewModel.prayerRoomLikeCancel(idNum)
            }
        }
    }
}