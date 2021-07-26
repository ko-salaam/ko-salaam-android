package com.kosalaamInc.kosalaam.feature.Main.PrayerRoomFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kosalaamInc.kosalaam.databinding.FragmentSearchprayerroomBinding


class PrayerRoomFragment : Fragment(){
    private var mBinding : FragmentSearchprayerroomBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchprayerroomBinding.inflate(inflater,container,false)

        mBinding = binding

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding= null
        super.onDestroyView()
    }
}