package com.kosalaamInc.kosalaam.Feature.Main.CompassFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kosalaamInc.kosalaam.databinding.FragmentCompassBinding


class CompassFragment : Fragment(){
    private var mBinding : FragmentCompassBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCompassBinding.inflate(inflater,container,false)

        mBinding = binding

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding= null
        super.onDestroyView()
    }
}