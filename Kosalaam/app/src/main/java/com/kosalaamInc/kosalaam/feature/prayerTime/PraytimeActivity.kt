package com.kosalaamInc.kosalaam.feature.prayerTime

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.azan.Azan
import com.azan.Method
import com.azan.astrologicalCalc.Location
import com.azan.astrologicalCalc.SimpleDate
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityActivitiesSearchBinding
import com.kosalaamInc.kosalaam.databinding.ActivityPraytimeBinding
import com.kosalaamInc.kosalaam.feature.activitySearch.ActivitiesSearchViewModel
import java.util.*

class PraytimeActivity : AppCompatActivity(){
    private lateinit var binding : ActivityPraytimeBinding
    private val viewModel: PrayTimeViewModel by lazy {
        ViewModelProvider(this).get(PrayTimeViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPraytimeBinding>(
            this, R.layout.activity_praytime
        ).apply {
            lifecycleOwner = this@PraytimeActivity
            prayTimeVm = viewModel
            val today = SimpleDate(GregorianCalendar())
            val location = Location(35.143432, 129.095391, 9.0, 0)
            val azan = Azan(location, Method.NONE)
            val prayerTimes = azan.getPrayerTimes(today)
            val imsaak = azan.getImsaak(today)
            Log.d("praytime11",prayerTimes.fajr().toString())
            Log.d("praytime11",prayerTimes.shuruq().toString())
            Log.d("praytime11",prayerTimes.thuhr().toString())
            Log.d("praytime113",prayerTimes.assr().toString())
            Log.d("praytime113",prayerTimes.maghrib().toString())
            Log.d("praytime111",prayerTimes.ishaa().toString())
        }
    }
}