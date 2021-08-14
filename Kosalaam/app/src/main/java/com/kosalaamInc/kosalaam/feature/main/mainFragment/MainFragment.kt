package com.kosalaamInc.kosalaam.feature.main.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.FragmentMainBinding

class MainFragment : Fragment(){

    companion object{
        const val Tag = "MainFragment"
    }

    private var mBinding : FragmentMainBinding? = null
    val MainActivity = com.kosalaamInc.kosalaam.feature.main.MainActivity()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater,container,false)


        mBinding = binding

        initPrayerBt()

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding= null
        super.onDestroyView()
    }

    private fun initPrayerBt(){
        mBinding!!.btMainTest.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_prayerRoomFragment)
        }
    }
}