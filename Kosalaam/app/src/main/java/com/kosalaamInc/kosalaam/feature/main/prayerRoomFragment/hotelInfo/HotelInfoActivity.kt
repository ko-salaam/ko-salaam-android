package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.hotelInfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityHotelInfoBinding

class HotelInfoActivity : AppCompatActivity(){
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
    }
}