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
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentCompassBinding.inflate(inflater, container, false)
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
    fun bearingP1toP2(
        latitude1: Double,
        longitude1: Double,
        latitude2: Double,
        longitude2: Double,
    ) : Double {
        // 현재 위치 : 위도나 경도는 지구 중심을 기반으로 하는 각도이기 때문에 라디안 각도로 변환한다.
        val Cur_Lat_radian = latitude1 * (Math.PI / 180)
        val Cur_Lon_radian = longitude1 * (Math.PI / 180)


        // 목표 위치 : 위도나 경도는 지구 중심을 기반으로 하는 각도이기 때문에 라디안 각도로 변환한다.
        val Dest_Lat_radian = latitude2 * (Math.PI / 180)
        val Dest_Lon_radian = longitude2 * (Math.PI / 180)

        // radian distance
        var radian_distance = 0.0
        radian_distance = Math.acos(Math.sin(Cur_Lat_radian) * Math.sin(Dest_Lat_radian)
                + Math.cos(Cur_Lat_radian) * Math.cos(Dest_Lat_radian) * Math.cos(Cur_Lon_radian - Dest_Lon_radian))

        // 목적지 이동 방향을 구한다.(현재 좌표에서 다음 좌표로 이동하기 위해서는 방향을 설정해야 한다. 라디안값이다.
        val radian_bearing = Math.acos((Math.sin(Dest_Lat_radian) - Math.sin(Cur_Lat_radian)
                * Math.cos(radian_distance)) / (Math.cos(Cur_Lat_radian) * Math.sin(radian_distance))) // acos의 인수로 주어지는 x는 360분법의 각도가 아닌 radian(호도)값이다.
        var true_bearing = 0.0
        if (Math.sin(Dest_Lon_radian - Cur_Lon_radian) < 0) {
            true_bearing = radian_bearing * (180 / Math.PI)
            true_bearing = 360 - true_bearing
        } else {
            true_bearing = radian_bearing * (180 / Math.PI)
        }
        return true_bearing
    }


}