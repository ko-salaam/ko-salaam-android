package com.kosalaamInc.kosalaam.Feature.Main.CompassFragment

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kosalaamInc.kosalaam.Util.LocationUtil

import com.kosalaamInc.kosalaam.databinding.FragmentCompassBinding


class CompassFragment : Fragment(){
    private var mBinding : FragmentCompassBinding? = null
    private lateinit var location : Location

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCompassBinding.inflate(inflater,container,false)

        mBinding = binding
        LocationUtil().getInstance(container!!.context)
        LocationUtil().getLocation().observe(viewLifecycleOwner, Observer {loc: Location? ->
            location = loc!!
            // Yay! location recived. Do location related work here
            Log.i("Compass","Location: ${location.latitude}  ${location.longitude}")

        })

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding= null
        super.onDestroyView()
    }
}