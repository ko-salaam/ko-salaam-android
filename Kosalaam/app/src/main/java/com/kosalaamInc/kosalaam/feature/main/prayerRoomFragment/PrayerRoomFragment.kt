package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.FragmentSearchprayerroomBinding
import com.kosalaamInc.kosalaam.feature.main.MainActivity
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.hotelInfo.HotelInfoActivity
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.prayerRoomInfo.PrayerRoomInfoActivity
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.restaurantInfo.RestaurantInfoActivity
import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.data.RecentSearchData
import com.kosalaamInc.kosalaam.model.data.RestaurantSearchData
import com.kosalaamInc.kosalaam.util.LoadingDialog
import kotlinx.coroutines.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class PrayerRoomFragment : Fragment(), MapView.MapViewEventListener {

    private var searchText: String = ""
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private lateinit var locationManager : LocationManager
    private var restaurantMuslimFriendly : ArrayList<String>? = null

    // filter status
    private var filterMode: Boolean = false // 필터창 확인

    private var filterAll : Boolean = false
    private var filterRestaurant : Boolean = false
    private var filterHotel : Boolean = false
    private var filterPrayer : Boolean = false
    private var halalCertified : Boolean =false
    private var selfCertified : Boolean = false
    private var muslimFriendly : Boolean = false
    private var porkFree : Boolean = false
    private var muslimFriendlyHotel : Boolean = false

    private var currentLat: Double = 0.000000
    private var currentLon: Double = 0.000000

    private lateinit var callback: OnBackPressedCallback
    private lateinit var adapter: RecentSearchRvAdapter
    private var mapView: MapView? = null
    private var binding: FragmentSearchprayerroomBinding? = null
    private lateinit var viewModel: PrayerRoomViewModel
    lateinit var mapViewContainer: ViewGroup
    private val TAG = "PrayerRoomFragment"
    private var fusedLocationClient: FusedLocationProviderClient? = null
    var latitude: Double = 37.498095
    var longitude: Double = 127.02761
    private lateinit var loadingDialog: LoadingDialog

    val list = ArrayList<RestaurantSearchData>()

    val permission = android.Manifest.permission.ACCESS_FINE_LOCATION

    companion object {
        var margin: Float = 0.0f
        var displayHeightDp: Int = 0
        var pageNum = 0
    }

    private var displayStatus: Int = 1
    // 1 : default
    // 2 : doing search
    // 3 : end search

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        loadingDialog = LoadingDialog(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            loadingDialog.show()
            delay(1500)
            loadingDialog.dismiss()
        }
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_searchprayerroom, container, false)
        viewModel = ViewModelProvider(this).get(PrayerRoomViewModel::class.java)
        binding!!.lifecycleOwner = viewLifecycleOwner
        binding!!.prayerRoomVm = viewModel
        locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        initRecentRecyclerView()
        setMarginBottom()
//        initMapVIew()
        checkPermission()
        initBottomSheetView()
        bottomSheetSetHeight()
        recentItemClick()
        startLocationUpdates()
        initClickListner()
        bottomNavDestination()
        if (Application.searchKeyword == "prayerRoom") {
            binding!!.hsvDetailFilter.visibility = View.GONE
            binding!!.ivSearchFilterPrayer.setImageResource(R.drawable.filter_prayer_green)
            binding!!.tvSearchFilterPrayer.setTextColor(Color.parseColor("#419070"))
        } else if (Application.searchKeyword == "hotel") {
            binding!!.tvDetailHotelMuslim.visibility = View.VISIBLE
            //binding!!.hsvFilter.visibility=View.GONE
            binding!!.hsvDetailFilter.visibility = View.VISIBLE
            binding!!.tvDetailHalalCertified.visibility = View.GONE
            binding!!.tvDetailSelfCertified.visibility = View.GONE
            binding!!.tvDetailMuslimFriendly.visibility = View.GONE
            binding!!.tvDetailPorkFree.visibility = View.GONE
            binding!!.ivSearchFilterHotel.setImageResource(R.drawable.filter_hotel_green)
            binding!!.tvSearchFilterHotel.setTextColor(Color.parseColor("#419070"))
        } else if (Application.searchKeyword == "restaurant") {
            //binding!!.hsvFilter.visibility=View.GONE
            binding!!.hsvDetailFilter.visibility = View.VISIBLE
            binding!!.tvDetailHalalCertified.visibility = View.VISIBLE
            binding!!.tvDetailSelfCertified.visibility = View.VISIBLE
            binding!!.tvDetailMuslimFriendly.visibility = View.VISIBLE
            binding!!.tvDetailPorkFree.visibility = View.VISIBLE
            binding!!.tvDetailHotelMuslim.visibility = View.GONE
            binding!!.ivSearchFilterRestaurant.setImageResource(R.drawable.filter_restaurant_green)
            binding!!.tvSearchFilterRestaurant.setTextColor(Color.parseColor("#419070"))
        } else {
            //binding!!.hsvFilter.visibility=View.VISIBLE
            binding!!.hsvDetailFilter.visibility = View.VISIBLE
            binding!!.ivSearchFilterAll.setImageResource(R.drawable.filter_all_green)
            binding!!.tvSearchFilterAll.setTextColor(Color.parseColor("#419070"))
            binding!!.tvDetailHalalCertified.visibility = View.GONE
            binding!!.tvDetailSelfCertified.visibility = View.GONE
            binding!!.tvDetailMuslimFriendly.visibility = View.GONE
            binding!!.tvDetailPorkFree.visibility = View.GONE
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initObserve() {
        with(viewModel) {
            focus_et.observe(this@PrayerRoomFragment, Observer {
                it.getContentIfNotHandled()?.let {
                    if (it == true) {
                        changeDisplay(2)
                        Log.d(TAG, "focusOn")
                    }
                }
            })

            back_bt.observe(this@PrayerRoomFragment, Observer {
                it.getContentIfNotHandled()?.let {
                    if (displayStatus == 3) {
                        changeDisplay(2)
                    } else if (displayStatus == 2) {
                        searchText = ""
                        pageNum = 0
                        getSearchList(Application.searchKeyword)
                        changeDisplay(1)
                    }
                }

            })

            searchKey_bt.observe(this@PrayerRoomFragment, Observer {
                it.getContentIfNotHandled()?.let {
                    if (binding!!.etSearchSearchEdit.text.toString().length != 0) {
                        hideKeyboard()
                        pageNum = 0
                        CoroutineScope(Dispatchers.Main).launch() {
                            CoroutineScope(Dispatchers.IO).async {
                                viewModel.insert(RecentSearchData(binding!!.etSearchSearchEdit.text.toString()))
                                binding!!.etSearchSearchEdit.clearFocus()
                                binding!!.flSearch.requestFocus()
                            }.join()
                            changeDisplay(3)
                            searchText = binding!!.etSearchSearchEdit.text.toString()
                            getSearchList(Application.searchKeyword)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Enter search keyword", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })

            recentDelete_bt.observe(this@PrayerRoomFragment, Observer {
                it.getContentIfNotHandled()?.let {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.deleteAll()
                    }
                    binding!!.ivSearchDefault.visibility = View.VISIBLE
                    binding!!.tvSearchDefault.visibility = View.VISIBLE
                    binding!!.rvRecentSearch.visibility = View.GONE
                    binding!!.tvSearchDelete.visibility = View.GONE
                    adapter.deleteList()
                    binding!!.rvRecentSearch.adapter = adapter
                    adapter.notifyDataSetChanged()
                    binding!!.rvRecentSearch.adapter!!.notifyDataSetChanged()
                }
            })

            restaurantData.observe(this@PrayerRoomFragment, Observer {
                Log.d("PrayerRoom Test","restaurant data observe")
                mapView!!.removeAllPOIItems()
                list.clear()
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("PrayerRoom Test","restaurnat data coroutine")
                    loadingDialog.show()
                    delay(1000)
                    for (i in 0..it.size - 1) {
                        list.add(RestaurantSearchData(it[i].id,
                            (i + 1).toString() + ". " + it[i].name,
                            it[i].address,
                            0,
                            it[i].muslimFriendly,it[i].imagesId,it[i].placeType))
                        addPOIItem(i,
                            it[i].latitude,
                            it[i].longitude,
                            it[i].name,
                            it[i].id!!)

                        Log.d("prayerRoomInfo", latitude.toString() + " " + longitude.toString())
                    }

                    Log.d(TAG, list.size.toString())
                    val searchAdapter = SearchRvAdapter(requireContext(), list)
                    searchAdapter.setOnItemClickListener(object :
                        SearchRvAdapter.OnSearchItemClickListener {
                        override fun onItemClick(v: View, data: RestaurantSearchData, pos: Int) {
                            startActivity(Intent(requireActivity(),
                                RestaurantInfoActivity::class.java))
                            RestaurantInfoActivity.idNum = data.id
                            binding!!.searchMapview.removeView(mapView)
                        }
                    })

                    binding!!.rvSearch.adapter = searchAdapter
                    pageNum++
                    loadingDialog.dismiss()
                }

            })


            hotelData.observe(this@PrayerRoomFragment, Observer {
                Log.d("PrayerRoom Test","restaurant data observe")
                mapView!!.removeAllPOIItems()
                list.clear()
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("PrayerRoom Test","restaurnat data coroutine")
                    loadingDialog.show()
                    delay(1000)
                    for (i in 0..it.size - 1) {
                        list.add(RestaurantSearchData(it[i].id,
                            (i + 1).toString() + ". " + it[i].name,
                            it[i].address,
                            0,
                            it[i].isMuslimFriendly.toString(),it[i].imagesId,"ACCOMMODATION"))
                        addPOIItem(i,
                            it[i].latitude,
                            it[i].longitude,
                            it[i].name,
                            it[i].id!!)

                        Log.d("prayerRoomInfo", latitude.toString() + " " + longitude.toString())
                    }

                    Log.d(TAG, list.size.toString())
                    val searchAdapter = SearchRvAdapter(requireContext(), list)
                    searchAdapter.setOnItemClickListener(object :
                        SearchRvAdapter.OnSearchItemClickListener {
                        override fun onItemClick(v: View, data: RestaurantSearchData, pos: Int) {
                            startActivity(Intent(requireActivity(),
                                HotelInfoActivity::class.java))
                            HotelInfoActivity.idNum = data.id
                            binding!!.searchMapview.removeView(mapView)
                        }
                    })

                    binding!!.rvSearch.adapter = searchAdapter
                    pageNum++
                    loadingDialog.dismiss()
                }

            })

            prayerData.observe(this@PrayerRoomFragment, Observer {
                Log.d("PrayerRoom Test","restaurant data observe")
                mapView!!.removeAllPOIItems()
                list.clear()
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("PrayerRoom Test","restaurnat data coroutine")
                    loadingDialog.show()
                    delay(1000)
                    for (i in 0..it.size - 1) {
                        list.add(RestaurantSearchData(it[i].id,
                            (i + 1).toString() + ". " + it[i].name,
                            it[i].address,
                            0,
                            null,it[i].imagesId,it[i].placeType))
                        addPOIItem(i,
                            it[i].latitude!!,
                            it[i].longitude!!,
                            it[i].name,
                            it[i].id!!)

                        Log.d("prayerRoomInfo", latitude.toString() + " " + longitude.toString())
                    }

                    Log.d(TAG, list.size.toString())
                    val searchAdapter = SearchRvAdapter(requireContext(), list)
                    searchAdapter.setOnItemClickListener(object :
                        SearchRvAdapter.OnSearchItemClickListener {
                        override fun onItemClick(v: View, data: RestaurantSearchData, pos: Int) {
                            startActivity(Intent(requireActivity(),
                                PrayerRoomInfoActivity::class.java))
                            PrayerRoomInfoActivity.idNum = data.id
                            binding!!.searchMapview.removeView(mapView)
                        }
                    })

                    binding!!.rvSearch.adapter = searchAdapter
                    pageNum++
                    loadingDialog.dismiss()
                }

            })
            commonData.observe(this@PrayerRoomFragment, Observer {
                mapView!!.removeAllPOIItems()
                list.clear()
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("PrayerRoom Test","restaurnat data coroutine")
                    loadingDialog.show()
                    delay(1000)
                    for (i in 0..it.size - 1) {
                        if(it[i].placeType=="RESTAURANT"){
                            list.add(RestaurantSearchData(it[i].id,
                                (i + 1).toString() + ". " + it[i].name,
                                it[i].address,
                                0,
                                it[i].muslimFriendly,it[i].imagesId,it[i].placeType))
                        }
                        else if(it[i].placeType=="ACCOMMODATION"){
                            list.add(RestaurantSearchData(it[i].id,
                                (i + 1).toString() + ". " + it[i].name,
                                it[i].address,
                                0,
                                it[i].isMuslimFriendly.toString(),it[i].imagesId,it[i].placeType))
                        }
                        else{
                            list.add(RestaurantSearchData(it[i].id,
                                (i + 1).toString() + ". " + it[i].name,
                                it[i].address,
                                0,
                                null,it[i].imagesId,it[i].placeType))
                        }

                        addPOIItem(i,
                            it[i].latitude!!,
                            it[i].longitude!!,
                            it[i].name,
                            it[i].id!!)

                        Log.d("prayerRoomInfo", latitude.toString() + " " + longitude.toString())
                    }

                    Log.d(TAG, list.size.toString())
                    val searchAdapter = SearchRvAdapter(requireContext(), list)
                    searchAdapter.setOnItemClickListener(object :
                        SearchRvAdapter.OnSearchItemClickListener {
                        override fun onItemClick(v: View, data: RestaurantSearchData, pos: Int) {
                            if(data.domain=="ACCOMMODATION"){
                                startActivity(Intent(requireActivity(),
                                    HotelInfoActivity::class.java))
                                HotelInfoActivity.idNum = data.id
                            }
                            else if(data.domain=="RESTAURANT"){
                                startActivity(Intent(requireActivity(),
                                    RestaurantInfoActivity::class.java))
                                RestaurantInfoActivity.idNum = data.id
                            }
                            else{
                                startActivity(Intent(requireActivity(),
                                    PrayerRoomInfoActivity::class.java))
                                PrayerRoomInfoActivity.idNum = data.id
                            }

                            binding!!.searchMapview.removeView(mapView)
                        }
                    })
                    binding!!.rvSearch.adapter = searchAdapter
                    pageNum++
                    loadingDialog.dismiss()
                }
            })

            location_bt.observe(this@PrayerRoomFragment, Observer {
                it.getContentIfNotHandled()?.let {
                    if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        // 위치정보 설정 Intent
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                    else{
                        if (ActivityCompat.checkSelfPermission(requireContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        ) {
                            Toast.makeText(requireContext(),
                                "Check your location permission",
                                Toast.LENGTH_SHORT).show()
                            Log.d("prayerthis", "it1")
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.

                        } else {
                            fusedLocationClient?.lastLocation?.addOnSuccessListener { location: Location? ->
                                Log.d("prayerthis", location?.latitude.toString())
                                if (location != null) {
                                    Log.d("prayer", location.latitude.toString())
                                    latitude = location.latitude
                                    longitude = location.longitude
                                    mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude,
                                        longitude), true)
                                } else {
                                    latitude = currentLat
                                    longitude = currentLon
                                    mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude,
                                        longitude), true)
                                }
                            }
                        }
                    }

                    Log.d("prayerthis", "it2")

                }
            })

            redo_bt.observe(this@PrayerRoomFragment, Observer {
                it.getContentIfNotHandled()?.let {
                    getSearchList(Application.searchKeyword)
                }
            })
        }
    }

    private fun changeDisplay(status: Int) {
        if (status == 1) {
            displayStatus = 1
            Log.d(TAG, "This is 1status")
            setMarginBottom()
            hideKeyboard()
            binding!!.etSearchSearchEdit.clearFocus()
            binding!!.flSearch.requestFocus()
            binding!!.etSearchSearchEdit.setText("")
            binding!!.tvSearchRecent.visibility = View.INVISIBLE
            binding!!.ivSearchDefault.visibility = View.INVISIBLE
            binding!!.tvSearchDefault.visibility = View.INVISIBLE
            binding!!.clSearchWhite.visibility = View.INVISIBLE
            binding!!.searchMapview.visibility = View.VISIBLE
            binding!!.ivSearchCurrentLocation.visibility = View.VISIBLE
            binding!!.searchBottomSheet.visibility = View.VISIBLE
            binding!!.rvRecentSearch.visibility = View.GONE
            binding!!.tvSearchDelete.visibility = View.GONE
            binding!!.rvSearch.visibility = View.VISIBLE
            binding!!.viewSearch5.visibility = View.VISIBLE
            binding!!.tvSearchRedo.visibility = View.VISIBLE
            binding!!.ivSearchFilter.visibility = View.VISIBLE
            if(filterMode){
                binding!!.ivSearchFilter.setImageResource(R.drawable.search_filter)
                filterMode=true
                binding!!.hsvFilter.visibility=View.VISIBLE
                binding!!.tvSearchRedo.visibility = View.GONE
            }
            else{
                binding!!.ivSearchFilter.setImageResource(R.drawable.search_filter)
                filterMode=false
                binding!!.hsvFilter.visibility=View.GONE
            }
            binding!!.ivSearchCancel.visibility=View.GONE

        } else if (status == 2) {
            Log.d(TAG, "This is 2status")
            displayStatus = 2
            binding!!.hsvFilter.visibility = View.GONE
            binding!!.rvSearch.visibility = View.GONE
            binding!!.viewSearch5.visibility = View.GONE
            binding!!.clSearchWhite.visibility = View.VISIBLE
            binding!!.searchMapview.visibility = View.INVISIBLE
            binding!!.ivSearchCurrentLocation.visibility = View.INVISIBLE
            binding!!.tvSearchRecent.visibility = View.VISIBLE
            binding!!.searchBottomSheet.visibility = View.GONE
            binding!!.rvRecentSearch.visibility = View.VISIBLE
            binding!!.tvSearchDelete.visibility = View.VISIBLE
            binding!!.tvSearchRedo.visibility = View.GONE
            binding!!.ivSearchCancel.visibility=View.GONE
            if(filterMode){
                binding!!.ivSearchFilter.setImageResource(R.drawable.search_filter)
                filterMode=true
                binding!!.hsvFilter.visibility=View.VISIBLE
                binding!!.tvSearchRedo.visibility = View.GONE
            }
            else{
                binding!!.ivSearchFilter.setImageResource(R.drawable.search_filter)
                filterMode=false
                binding!!.hsvFilter.visibility=View.GONE
            }
            viewModel.getAll().observe(viewLifecycleOwner, Observer {
                if (displayStatus == 2) {
                    if (it.size == 0) {
                        binding!!.ivSearchDefault.visibility = View.VISIBLE
                        binding!!.tvSearchDefault.visibility = View.VISIBLE
                        binding!!.rvRecentSearch.visibility = View.GONE
                        binding!!.tvSearchDelete.visibility = View.GONE
                    } else {
                        binding!!.rvRecentSearch.visibility = View.VISIBLE
                        binding!!.tvSearchDelete.visibility = View.VISIBLE
                        adapter.setList(it)
                        binding!!.ivSearchDefault.visibility = View.GONE
                        binding!!.tvSearchDefault.visibility = View.GONE
                        binding!!.rvRecentSearch.adapter = adapter
                        adapter.notifyDataSetChanged()
                        binding!!.rvRecentSearch.adapter!!.notifyDataSetChanged()
                    }
                    Log.d(TAG, "This is getAll")
                } else {
                    binding!!.rvSearch.visibility = View.VISIBLE
                    binding!!.viewSearch5.visibility = View.VISIBLE
                    binding!!.tvSearchRecent.visibility = View.INVISIBLE
                    binding!!.ivSearchDefault.visibility = View.INVISIBLE
                    binding!!.tvSearchDefault.visibility = View.INVISIBLE
                    binding!!.clSearchWhite.visibility = View.INVISIBLE
                    binding!!.searchMapview.visibility = View.VISIBLE
                    binding!!.ivSearchCurrentLocation.visibility = View.VISIBLE
                    binding!!.etSearchSearchEdit.clearFocus()
                    binding!!.flSearch.requestFocus()
                    binding!!.searchBottomSheet.visibility = View.VISIBLE
                    binding!!.rvRecentSearch.visibility = View.GONE
                    binding!!.tvSearchDelete.visibility = View.GONE
                    binding!!.tvSearchRedo.visibility = View.VISIBLE
                }
            })

        } else if (status == 3) {
            Log.d(TAG, "This is 3status")
            displayStatus = 3
            hideKeyboard()
            binding!!.etSearchSearchEdit.clearFocus()
            binding!!.flSearch.requestFocus()
            setMarginBottom()
            binding!!.rvSearch.visibility = View.VISIBLE
            binding!!.viewSearch5.visibility = View.VISIBLE
            binding!!.tvSearchRecent.visibility = View.INVISIBLE
            binding!!.ivSearchDefault.visibility = View.INVISIBLE
            binding!!.tvSearchDefault.visibility = View.INVISIBLE
            binding!!.clSearchWhite.visibility = View.INVISIBLE
            binding!!.searchMapview.visibility = View.VISIBLE
            binding!!.ivSearchCurrentLocation.visibility = View.VISIBLE
            binding!!.searchBottomSheet.visibility = View.VISIBLE
            binding!!.rvRecentSearch.visibility = View.GONE
            binding!!.tvSearchDelete.visibility = View.GONE
            binding!!.tvSearchRedo.visibility = View.VISIBLE
            binding!!.ivSearchFilter.visibility=View.GONE
            binding!!.ivSearchCancel.visibility=View.VISIBLE
        }

    }

    private fun hideKeyboard() {
//        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val activity = activity as MainActivity

        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
        }
    }

    private fun setMarginBottom() {
        val params = binding!!.ivSearchCurrentLocation.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(0,
            0,
            getPixel(requireContext(), 25f).toInt(),
            getPixel(requireContext(), 70f).toInt())
        binding!!.ivSearchCurrentLocation.layoutParams = params
    }

    private fun getPixel(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics)
    }

    // TODO Neither network gps ->  ?
    val permReqLuncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            initObserve()
        } else {
            // Failed pass
        }
    }

    private fun checkPermission() {
        permReqLuncher.launch(permission)
    }

    private fun initMapVIew() {
        mapView = MapView(requireContext())
        mapViewContainer = binding!!.searchMapview
        mapViewContainer.addView(mapView)
        mapView!!.setMapViewEventListener(this)
    }

    private fun addPOIItem(
        i: Int,
        latitude: Double,
        longitude: Double,
        name: String?,
        tagInfo: String,
        ) {
        val marker = MapPOIItem()
        marker.apply {
//            tag = tagInfo.toInt()
            itemName = name
            mapPoint = MapPoint.mapPointWithGeoCoord(latitude,
                longitude)
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = resources.getIdentifier("marker_" + (i + 1).toString(),
                "drawable",
                requireContext().packageName)
            isCustomImageAutoscale = false
            setCustomImageAnchor(0.5f, 1.0f)
        }
        mapView!!.addPOIItem(marker)
    }

    private fun initBottomSheetView() {
        var bottomsheet = BottomSheetBehavior.from(binding!!.searchBottomSheet)
        bottomsheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomsheet: View, newState: Int) {
                when (newState) {
                    // 사용자가 BottomSheet를 위나 아래로 드래그 중인 상태
                    BottomSheetBehavior.STATE_DRAGGING -> {

                    }

                    // 드래그 동작 후 BottomSheet가 특정 높이로 고정될 때의 상태
                    // SETTLING 후 EXPANDED, SETTLING 후 COLLAPSED, SETTLING 후 HIDDEN
                    BottomSheetBehavior.STATE_SETTLING -> {

                    }
                    // 최대 높이로 보이는 상태
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }

                    // peek 높이 만큼 보이는 상태
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }

                    // 숨김 상태
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                }
            }

            override fun onSlide(p0: View, slideOffset: Float) {
                // slideOffset 범위: -1.0 ~ 1.0
                // -1.0 HIDDEN, 0.0 COLLAPSED, 1.0 EXPANDED
            }
        })
    }

    // bottom nav height in activity
    override fun onMapViewInitialized(p0: MapView?) {

    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        latitude = p1?.mapPointGeoCoord!!.latitude
        longitude = p1.mapPointGeoCoord!!.longitude
        pageNum = 0
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        Log.d(TAG, p1.toString())
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (displayStatus == 3) {
                    hideKeyboard()
                    changeDisplay(2)
                } else if (displayStatus == 2) {
                    searchText = ""
                    pageNum = 0
                    getSearchList(Application.searchKeyword)
                    changeDisplay(1)
                } else if (displayStatus == 1) {
                    Application.searchKeyword = null
                    findNavController().navigate(R.id.action_prayerFragment_to_mainFragment)

                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun bottomNavDestination() {
        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id != R.id.prayerRoomFragment) {
                mapView = null
                this.onDetach()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
        mapView = null
        Application.searchKeyword = null
        Log.d(TAG, "detach")
    }

    fun initRecentRecyclerView() {
        var linearLayoutManager = LinearLayoutManager(this.activity)
//        linearLayoutManager.reverseLayout=true
//        linearLayoutManager.stackFromEnd=true
        adapter = RecentSearchRvAdapter()
        binding!!.rvRecentSearch.adapter = adapter
        binding!!.rvRecentSearch.layoutManager = linearLayoutManager
    }

    private fun recentItemClick() {
        adapter.itemClick = object : RecentSearchRvAdapter.OnItemClick {
            override fun onClick(view: View, position: Int, text: String) {
                hideKeyboard()
                pageNum = 0
                binding!!.etSearchSearchEdit.setText(text)
                binding!!.etSearchSearchEdit.clearFocus()
                binding!!.flSearch.requestFocus()
                searchText = text
                changeDisplay(3)
                getSearchList(Application.searchKeyword)
            }
        }
    }

    private fun bottomSheetSetHeight() {
        var height: Float
        height =
            (displayHeightDp - (binding!!.clSearchSearch.height / MainActivity.desity) - (52.4 * MainActivity.desity) - margin).toFloat()
        Log.d(TAG, height.toString())
        binding!!.searchBottomSheet!!.layoutParams.height =
            getPixel(requireContext(), height).toInt() //getPixel(requireContext(),height).toInt()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(requireContext(), "Check your location permission", Toast.LENGTH_SHORT)
                .show()
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        else {
            locationRequest = LocationRequest.create()
            locationRequest?.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            locationRequest?.setInterval((20 * 1000).toLong())
            locationCallback = object : LocationCallback() {

                override fun onLocationResult(locationResult: LocationResult) {
                    if (locationResult == null) {
                        return
                    }
                    for (location in locationResult.locations) {
                        if (location != null) {
                            currentLat = location.latitude
                            currentLon = location.longitude
                        }
                    }
                }
            }
            LocationServices.getFusedLocationProviderClient(requireContext())
                .requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private fun getSearchList(domain: String?) {
        if (checkInternet() == true) {
            if (domain == "restaurant") {
                viewModel.getRestaurantSearch(domain,
                    5,
                    searchText,
                    latitude,
                    longitude,
                    restaurantMuslimFriendly,
                    pageNum,
                    20)
                Log.d(TAG,"this restaurant")
            } else if (domain == "hotel") {
                viewModel.getHotelSearch(domain,
                    false,
                    5,
                    searchText,
                    latitude,
                    longitude,
                    pageNum,20)
                Log.d(TAG,"this hotel")
            } else if (domain == "prayerRoom") {
                viewModel.getPrayerRoomSearch(domain,
                    500,
                    searchText,
                    latitude,
                    longitude,
                    pageNum,20)
                Log.d(TAG,"this prayerRoom")
            }
            else{
                viewModel.getCommonSearch(domain,
                    false,
                    5,
                    searchText,
                    latitude,
                    longitude,
                    pageNum,20)
            }
        } else {
            Toast.makeText(context,"Check your Internet",Toast.LENGTH_SHORT).show()
        }
        //
    }

    @Suppress("DEPRECATION")
    fun checkInternet(): Boolean {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    override fun onPause() {
        mapViewContainer.removeView(mapView)
        super.onPause()
    }

    override fun onResume() {
        initMapVIew()
        mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude,
            longitude), true)
        getSearchList(Application.searchKeyword)
        super.onResume()
    }

    fun initClickListner() {
        binding!!.ivSearchFilter.setOnClickListener {
            if (filterMode == false) {
                binding!!.tvSearchRedo.visibility = View.GONE
                binding!!.hsvFilter.visibility = View.VISIBLE
                binding!!.ivSearchFilter.setImageResource(R.drawable.search_uparrow)
                // filter 이미지 변경
                filterMode = true
            } else {
                binding!!.tvSearchRedo.visibility = View.VISIBLE
                binding!!.hsvFilter.visibility = View.GONE
                binding!!.ivSearchFilter.setImageResource(R.drawable.search_filter)
                if(displayStatus==2){
                    binding!!.tvSearchRedo.visibility = View.GONE
                }
                filterMode = false
            }
        }

        binding!!.ivSearchCancel.setOnClickListener {
            if(displayStatus==3) {
                searchText = ""
                pageNum = 0
                getSearchList(Application.searchKeyword)
                changeDisplay(1)
            }
        }

        binding!!.clSearchFilterAll.setOnClickListener {
            setFilterDrawableDefault()
            binding!!.tvSearchFilterAll.setTextColor(Color.parseColor("#419070"))
            binding!!.ivSearchFilterAll.setImageResource(R.drawable.filter_all_green)
            Application.searchKeyword=null
            setDetailFilterDefault()
            binding!!.tvDetailHotelMuslim.visibility =View.VISIBLE
            binding!!.tvDetailHalalCertified.visibility = View.GONE
            binding!!.tvDetailSelfCertified.visibility = View.GONE
            binding!!.tvDetailMuslimFriendly.visibility = View.GONE
            binding!!.tvDetailPorkFree.visibility = View.GONE
            getSearchList(Application.searchKeyword)
            //필터변경
            //detail 부분도 초기화(객체 이미지 전부 후에 보여줄것들 확인
            // Application.keyword
            // viewmodel 호출
        }

        binding!!.clSearchFilterHotel.setOnClickListener {
            setFilterDrawableDefault()
            binding!!.tvSearchFilterHotel.setTextColor(Color.parseColor("#419070"))
            binding!!.ivSearchFilterHotel.setImageResource(R.drawable.filter_hotel_green)
            Application.searchKeyword="hotel"
            setDetailFilterDefault()
            binding!!.tvDetailSelfCertified.visibility=View.GONE
            binding!!.tvDetailPorkFree.visibility =View.GONE
            binding!!.tvDetailMuslimFriendly.visibility= View.GONE
            binding!!.tvDetailHotelMuslim.visibility =View.VISIBLE
            binding!!.tvDetailHalalCertified.visibility = View.GONE
            getSearchList(Application.searchKeyword)
        }

        binding!!.clSearchFilterPrayer.setOnClickListener {
            setFilterDrawableDefault()
            binding!!.tvSearchFilterPrayer.setTextColor(Color.parseColor("#419070"))
            binding!!.ivSearchFilterPrayer.setImageResource(R.drawable.filter_prayer_green)
            Application.searchKeyword="prayerRoom"
            setDetailFilterDefault()
            binding!!.tvDetailSelfCertified.visibility=View.GONE
            binding!!.tvDetailPorkFree.visibility =View.GONE
            binding!!.tvDetailMuslimFriendly.visibility= View.GONE
            binding!!.tvDetailHotelMuslim.visibility =View.GONE
            binding!!.tvDetailHalalCertified.visibility = View.GONE
            getSearchList(Application.searchKeyword)
        }

        binding!!.clSearchFilterRestaurant.setOnClickListener {
            setFilterDrawableDefault()
            binding!!.tvSearchFilterRestaurant.setTextColor(Color.parseColor("#419070"))
            binding!!.ivSearchFilterRestaurant.setImageResource(R.drawable.filter_restaurant_green)
            Application.searchKeyword="restaurant"
            setDetailFilterDefault()
            binding!!.tvDetailSelfCertified.visibility=View.VISIBLE
            binding!!.tvDetailPorkFree.visibility =View.VISIBLE
            binding!!.tvDetailMuslimFriendly.visibility= View.VISIBLE
            binding!!.tvDetailHotelMuslim.visibility =View.GONE
            binding!!.tvDetailHalalCertified.visibility = View.VISIBLE
            getSearchList(Application.searchKeyword)
        }

        binding!!.tvDetailHalalCertified.setOnClickListener {
            if(halalCertified==false){
                binding!!.tvDetailHalalCertified.setTextColor(Color.parseColor("#419070"))
                binding!!.tvDetailHalalCertified.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_green)
                halalCertified=true
                // 감색로직 추가
            }
            else{
                binding!!.tvDetailHalalCertified.setTextColor(Color.parseColor("#999999"))
                binding!!.tvDetailHalalCertified.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_default)
                halalCertified=false
                // 검색로직추가
            }
        }

        binding!!.tvDetailHotelMuslim.setOnClickListener {
            if(muslimFriendlyHotel==false){
                binding!!.tvDetailHotelMuslim.setTextColor(Color.parseColor("#419070"))
                binding!!.tvDetailHotelMuslim.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_green)
                muslimFriendlyHotel=true
            }
            else{
                binding!!.tvDetailHotelMuslim.setTextColor(Color.parseColor("#999999"))
                binding!!.tvDetailHotelMuslim.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_default)
                muslimFriendlyHotel=false
            }
        }

        binding!!.tvDetailMuslimFriendly.setOnClickListener {
            if(muslimFriendly==false){
                binding!!.tvDetailMuslimFriendly.setTextColor(Color.parseColor("#419070"))
                binding!!.tvDetailMuslimFriendly.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_green)
                muslimFriendly=true
            }
            else{
                binding!!.tvDetailMuslimFriendly.setTextColor(Color.parseColor("#999999"))
                binding!!.tvDetailMuslimFriendly.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_default)
                muslimFriendly=false
            }
        }

        binding!!.tvDetailPorkFree.setOnClickListener {
            if(porkFree==false){
                binding!!.tvDetailPorkFree.setTextColor(Color.parseColor("#419070"))
                binding!!.tvDetailPorkFree.background = ContextCompat.getDrawable(requireActivity(),R.drawable.filter_green)
                porkFree=true
            }
            else{
                binding!!.tvDetailPorkFree.setTextColor(Color.parseColor("#999999"))
                binding!!.tvDetailPorkFree.background = ContextCompat.getDrawable(requireActivity(),R.drawable.filter_default)
                porkFree=false
            }
        }

        binding!!.tvDetailSelfCertified.setOnClickListener {
            if(selfCertified==false){
                binding!!.tvDetailSelfCertified.setTextColor(Color.parseColor("#419070"))
                binding!!.tvDetailSelfCertified.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_green)
                selfCertified=true
            }
            else{
                binding!!.tvDetailSelfCertified.setTextColor(Color.parseColor("#999999"))
                binding!!.tvDetailSelfCertified.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_default)
                selfCertified=false
            }
        }
    }

    private fun setFilterDrawableDefault() {
        binding!!.tvSearchFilterAll.setTextColor(Color.parseColor("#999999"))
        binding!!.ivSearchFilterAll.setImageResource(R.drawable.filter_all_default)
        binding!!.tvSearchFilterRestaurant.setTextColor(Color.parseColor("#999999"))
        binding!!.ivSearchFilterRestaurant.setImageResource(R.drawable.filter_restaurant_default)
        binding!!.tvSearchFilterHotel.setTextColor(Color.parseColor("#999999"))
        binding!!.ivSearchFilterHotel.setImageResource(R.drawable.filter_hotel_default)
        binding!!.tvSearchFilterPrayer.setTextColor(Color.parseColor("#999999"))
        binding!!.ivSearchFilterPrayer.setImageResource(R.drawable.filter_prayer_default)
    }

    private fun setDetailFilterDefault(){
        binding!!.tvDetailHotelMuslim.setTextColor(Color.parseColor("#999999"))
        binding!!.tvDetailHotelMuslim.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_default)
        binding!!.tvDetailSelfCertified.setTextColor(Color.parseColor("#999999"))
        binding!!.tvDetailSelfCertified.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_default)
        binding!!.tvDetailPorkFree.setTextColor(Color.parseColor("#999999"))
        binding!!.tvDetailPorkFree.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_default)
        binding!!.tvDetailMuslimFriendly.setTextColor(Color.parseColor("#999999"))
        binding!!.tvDetailMuslimFriendly.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_default)
        binding!!.tvDetailHalalCertified.setTextColor(Color.parseColor("#999999"))
        binding!!.tvDetailHalalCertified.background= ContextCompat.getDrawable(requireActivity(),R.drawable.filter_default)
    }
}

