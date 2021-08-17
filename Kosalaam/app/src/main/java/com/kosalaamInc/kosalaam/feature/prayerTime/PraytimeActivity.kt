package com.kosalaamInc.kosalaam.feature.prayerTime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityActivitiesSearchBinding
import com.kosalaamInc.kosalaam.databinding.ActivityPraytimeBinding
import com.kosalaamInc.kosalaam.feature.activitySearch.ActivitiesSearchViewModel

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
        }
    }
}