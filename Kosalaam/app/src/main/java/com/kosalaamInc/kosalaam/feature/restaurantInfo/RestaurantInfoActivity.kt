package com.kosalaamInc.kosalaam.feature.restaurantInfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityRestaurantInfoBinding

class RestaurantInfoActivity(id: Int) : AppCompatActivity(){

    private lateinit var binding : ActivityRestaurantInfoBinding
    private var idNum : Int = id
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

        viewModel.getRestaurantInfo(idNum)
        viewModel.restaurantData.observe(this, Observer {
            binding!!.tvRestaurantInfoName.text = it.name
            binding!!.tvRestaurantInfoType.text = it.dishType

        })
    }
}