package com.kosalaamInc.kosalaam.feature.activitySearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityActivitiesSearchBinding

class ActivitiesSearchActivity : AppCompatActivity(){

    private lateinit var binding : ActivityActivitiesSearchBinding
    private val viewModel: ActivitiesSearchViewModel by lazy {
        ViewModelProvider(this).get(ActivitiesSearchViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityActivitiesSearchBinding>(
            this, R.layout.activity_activities_search
        ).apply {
            lifecycleOwner = this@ActivitiesSearchActivity
            activitiesVm = viewModel
        }
    }
}