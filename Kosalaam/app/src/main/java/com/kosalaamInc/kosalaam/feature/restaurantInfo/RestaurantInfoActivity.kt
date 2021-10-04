package com.kosalaamInc.kosalaam.feature.restaurantInfo

import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityRestaurantInfoBinding
import net.daum.mf.map.api.MapView

class RestaurantInfoActivity : AppCompatActivity(){

    private lateinit var binding : ActivityRestaurantInfoBinding
    private var idNum : Int = 0
    private var mapView : MapView? = null
    lateinit var mapViewContainer : RelativeLayout

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



//        viewModel.getRestaurantInfo(idNum)
//        viewModel.restaurantData.observe(this, Observer {
//            binding!!.tvRestaurantInfoName.text = it.name
//            binding!!.tvRestaurantInfoType.text = it.dishType
//
//        })
    }

    override fun finish() {
        binding!!.rlRestaurantInfoMapview.removeView(mapView)
        super.finish()

    }
}