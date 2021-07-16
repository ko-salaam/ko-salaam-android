package com.kosalaamInc.kosalaam.Feature.Main.MyPageFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kosalaamInc.kosalaam.databinding.FragmentMypageBinding


class MyPageFragment : Fragment(){
    private var mBinding : FragmentMypageBinding? = null

    companion object{
        const val Tag = "MyPageFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMypageBinding.inflate(inflater,container,false)
        Log.d(Tag,"OnCreateView")
        mBinding = binding

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding= null
        super.onDestroyView()
    }
}