package com.kosalaamInc.kosalaam.Feature.Main.QuranFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kosalaamInc.kosalaam.databinding.FragmentQuranBinding

class QuranFragment : Fragment(){
    private var mBinding : FragmentQuranBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    val binding = FragmentQuranBinding.inflate(inflater,container,false)

        mBinding = binding

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding= null
        super.onDestroyView()
    }
}