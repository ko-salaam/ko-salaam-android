package com.kosalaamInc.kosalaam.feature.restaurantSearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityActivitiesSearchBinding
import com.kosalaamInc.kosalaam.databinding.ActivityRestaurantSearchBinding
import com.kosalaamInc.kosalaam.feature.activitySearch.ActivitiesSearchViewModel

class RestaurantSearchActivity : AppCompatActivity(){
    private lateinit var binding : ActivityRestaurantSearchBinding
    private val viewModel: RestaurantSearchViewModel by lazy {
        ViewModelProvider(this).get(RestaurantSearchViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityRestaurantSearchBinding>(
            this, R.layout.activity_restaurant_search
        ).apply {
            lifecycleOwner = this@RestaurantSearchActivity
            searchRestaurantVm = viewModel
        }
    }
}