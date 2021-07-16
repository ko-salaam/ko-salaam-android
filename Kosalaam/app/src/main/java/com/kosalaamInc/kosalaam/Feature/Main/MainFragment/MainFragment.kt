package com.kosalaamInc.kosalaam.Feature.Main.MainFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kosalaamInc.kosalaam.databinding.FragmentMainBinding

class MainFragment : Fragment(){

    companion object{
        const val Tag = "MainFragment"
    }

    private var mBinding : FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater,container,false)
        Log.d(Tag,"OnCreateView")

        mBinding = binding

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding= null
        super.onDestroyView()
    }
}