package com.kosalaamInc.kosalaam.feature.hotelSearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityHotelSearchBinding
import com.kosalaamInc.kosalaam.databinding.ActivityPraytimeBinding
import com.kosalaamInc.kosalaam.feature.prayerTime.PrayTimeViewModel

class HotelSearchActivity : AppCompatActivity(){
    private lateinit var binding : ActivityHotelSearchBinding
    private val viewModel: HotelSearchViewModel by lazy {
        ViewModelProvider(this).get(HotelSearchViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityHotelSearchBinding>(
            this, R.layout.activity_hotel_search
        ).apply {
            lifecycleOwner = this@HotelSearchActivity
            searchHotelVm = viewModel
        }
    }
}