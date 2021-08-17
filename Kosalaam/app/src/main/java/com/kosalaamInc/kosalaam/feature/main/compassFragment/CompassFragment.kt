package com.kosalaamInc.kosalaam.feature.main.compassFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.kosalaamInc.kosalaam.databinding.FragmentCompassBinding


class CompassFragment : Fragment(){

    val TAG : String = "CompassFragment"
    private val REQUEST_CODE_PERMISSION: Int = 2

    private var mBinding : FragmentCompassBinding? = null

    private val viewModel : CompassViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCompassBinding.inflate(inflater,container,false)
        checkPermission()
        viewModel.permissionRequest.observe(viewLifecycleOwner, Observer { permission ->
            checkPermission()
        })

        mBinding = binding

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding= null
        super.onDestroyView()
    }

    @Suppress("DEPRECATION")
    private fun checkPermission(){
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//            if(context?.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//                || context?.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED
//            ){
//                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE_PERMISSION)
//                //viewModel.onPermissionResult("ACCESS_FINE_LOCATION",false)
//                checkPermission()
//
//            }
//            else{
//                //viewModel.onPermissionResult("ACCESS_FINE_LOCATION",true)
//            }
//
//        }
//        else{
//            //viewModel.onPermissionResult("ACCESS_FINE_LOCATION",true)
//        }
   }


}