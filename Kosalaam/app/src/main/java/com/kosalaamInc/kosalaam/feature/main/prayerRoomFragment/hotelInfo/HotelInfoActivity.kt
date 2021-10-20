package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.hotelInfo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityHotelInfoBinding
import com.kosalaamInc.kosalaam.databinding.ActivityRestaurantInfoBinding
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.ImageRvAdapter
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.PrayerRoomFragment
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.restaurantInfo.RestaurantInfoActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class HotelInfoActivity : AppCompatActivity(){
    var images : ArrayList<String>? = null

    private var mapView : MapView? = null
    lateinit var mapViewContainer : RelativeLayout
    private var heartStatus : Boolean = false

    companion object{
        var idNum : String? = null
        var uuid : String? = ""
    }
    private lateinit var binding : ActivityHotelInfoBinding
    private val viewModel: HotelInfoViewModel by lazy {
        ViewModelProvider(this).get(HotelInfoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityHotelInfoBinding>(
            this, R.layout.activity_hotel_info
        ).apply {
            lifecycleOwner = this@HotelInfoActivity
            hotelInfoVm = viewModel
        }
        initClickListener()
        mapView = MapView(this)
        binding!!.rlRestaurantInfoMapview.addView(mapView)
        viewModel.hotelData.observe(this, Observer {
            Log.d("prayerRoomInfo",it.name.toString())
            mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(it.latitude,
                it.longitude), true)
            var marker = MapPOIItem()
            marker.apply{
                itemName = it.name
                markerType= MapPOIItem.MarkerType.RedPin
                mapPoint = MapPoint.mapPointWithGeoCoord(it.latitude,
                    it.longitude)
                setCustomImageAnchor(0.5f, 1.0f)
            }
            if(it.isLiked==true){
                binding!!.ivRestaurantInfoHeart.setImageResource(R.drawable.info_heart_fill)
                heartStatus=true
            }
            else{
                binding!!.ivRestaurantInfoHeart.setImageResource(R.drawable.info_heart_default)
                heartStatus=false
            }
            Log.d("Hotelinfomation",it.imagesId?.size.toString())

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
            binding!!.tvRestaurantInfoAddress.text = it.address
            binding!!.tvRestaurantInfoAddress2.text = it.address
            // Affinity none?

            if(it.isMuslimFriendly==null || it.isMuslimFriendly==false){
                binding!!.clRestaurantInfoAffinity.visibility=View.GONE
                binding!!.view8RestaurantInfo.visibility=View.GONE
                binding!!.view9RestaurantInfo.visibility=View.GONE
                binding!!.view10RestaurantInfo.visibility=View.GONE
                binding!!.view43RestaurantInfo.visibility=View.GONE
                binding!!.view44RestaurantInfo.visibility=View.GONE
                binding!!.clRoomEquip.visibility=View.GONE
                binding!!.tvPrayerRoomRoomEquip.visibility=View.GONE
                binding!!.view41RestaurantInfo.visibility=View.GONE
                binding!!.view42RestaurantInfo.visibility=View.GONE
            }

            else{
                if(it.praySupplies!!.isKoran==true){
                    binding!!.tvPrayerRoomQuran.setTextColor(Color.parseColor("#f1f1f5"))
                    binding!!.tvPrayerRoomQuran.background = getDrawable(R.drawable.login_defaultback)
                }
                else{
                    binding!!.tvPrayerRoomQuran.setTextColor(Color.parseColor("#999999"))
                    binding!!.tvPrayerRoomQuran.background = getDrawable(R.drawable.prayer_uncheck_button)
                }
                if(it.praySupplies!!.isMat==true){
                    binding!!.tvPrayerRoomPrayerMat.setTextColor(Color.parseColor("#f1f1f5"))
                    binding!!.tvPrayerRoomPrayerMat.background = getDrawable(R.drawable.login_defaultback)
                }
                else{
                    binding!!.tvPrayerRoomPrayerMat.setTextColor(Color.parseColor("#999999"))
                    binding!!.tvPrayerRoomPrayerMat.background = getDrawable(R.drawable.prayer_uncheck_button)
                }
                if(it.praySupplies!!.isQibla==true){
                    binding!!.tvPrayerRoomCompass.setTextColor(Color.parseColor("#f1f1f5"))
                    binding!!.tvPrayerRoomCompass.background = getDrawable(R.drawable.login_defaultback)
                }
                else{
                    binding!!.tvPrayerRoomCompass.setTextColor(Color.parseColor("#999999"))
                    binding!!.tvPrayerRoomCompass.background = getDrawable(R.drawable.prayer_uncheck_button)
                }
                if(it.praySupplies!!.isWashingRoom==true){
                    binding!!.tvPrayerRoomWashingRoom.setTextColor(Color.parseColor("#f1f1f5"))
                    binding!!.tvPrayerRoomWashingRoom.background = getDrawable(R.drawable.login_defaultback)
                }
                else{
                    binding!!.tvPrayerRoomWashingRoom.setTextColor(Color.parseColor("#999999"))
                    binding!!.tvPrayerRoomWashingRoom.background = getDrawable(R.drawable.prayer_uncheck_button)
                }

                binding!!.tvRestaurantInfoCertify.text = "Muslim Friendly"
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
        viewModel.getHotelInfo(idNum!!)
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
                viewModel.hotelLike(idNum)
            }

            else{
                viewModel.hotelLikeCancel(idNum)
            }
        }
    }
}