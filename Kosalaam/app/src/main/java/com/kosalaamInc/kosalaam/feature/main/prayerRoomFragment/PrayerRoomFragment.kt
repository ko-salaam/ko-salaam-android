package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.FragmentSearchprayerroomBinding
import com.kosalaamInc.kosalaam.feature.main.MainActivity
import com.kosalaamInc.kosalaam.model.data.RecentSearchData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.daum.android.map.MapViewEventListener
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class PrayerRoomFragment : Fragment(), MapView.MapViewEventListener,OnItemClick {

    private lateinit var callback: OnBackPressedCallback
    private lateinit var adapter : RecentSearchRvAdapter
    private lateinit var mapView: MapView
    private var binding: FragmentSearchprayerroomBinding? = null
    private lateinit var viewModel: PrayerRoomViewModel
    lateinit var mapViewContainer: RelativeLayout
    private val TAG = "PrayerRoomFragment"


    val permission = android.Manifest.permission.ACCESS_FINE_LOCATION

    companion object {
        var margin: Float = 0.0f
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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_searchprayerroom, container, false)
        viewModel = ViewModelProvider(this).get(PrayerRoomViewModel::class.java)
        binding!!.lifecycleOwner = viewLifecycleOwner
        binding!!.prayerRoomVm = viewModel
        initRecentRecyclerView()
        setMarginBottom()
        initMapVIew()
        checkPermission()
        initBottomSheetView()
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

                    } else {
                        Log.d("SearchPrayerRoomCheck", "false")
                    }
                }
            })

            back_bt.observe(this@PrayerRoomFragment, Observer {
                it.getContentIfNotHandled()?.let {
                    hideKeyboard()
                    changeDisplay(1)
                }

            })

            searchKey_bt.observe(this@PrayerRoomFragment, Observer {
                it.getContentIfNotHandled()?.let {
                    changeDisplay(3)
                    hideKeyboard()
                    lifecycleScope.launch(Dispatchers.IO){
                        viewModel.insert(RecentSearchData(binding!!.etSearchSearchEdit.text.toString()))
                    }
                }
            })
            recentDelete_bt.observe(this@PrayerRoomFragment, Observer {
                it.getContentIfNotHandled()?.let {
                    binding!!.ivSearchDefault.visibility = View.VISIBLE
                    binding!!.tvSearchDefault.visibility = View.VISIBLE
                    binding!!.rvRecentSearch.visibility = View.GONE
                    adapter.deleteList()
                    binding!!.rvRecentSearch.adapter=adapter
                    adapter.notifyDataSetChanged()
                    binding!!.rvRecentSearch.adapter!!.notifyDataSetChanged()
                }
            })



        }
    }

    private fun changeDisplay(status: Int) {
        if (status == 1) {
            displayStatus = 1
            setMarginBottom()
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
            hideKeyboard()
        } else if (status == 2) {
            displayStatus = 2
            binding!!.clSearchWhite.visibility = View.VISIBLE
            binding!!.searchMapview.visibility = View.INVISIBLE
            binding!!.ivSearchCurrentLocation.visibility = View.INVISIBLE
            binding!!.tvSearchRecent.visibility = View.VISIBLE

            binding!!.searchBottomSheet.visibility = View.GONE
            binding!!.tvSearchDelete.visibility=View.GONE
            //

            viewModel.getAll().observe(this@PrayerRoomFragment, Observer {
                if(it.size==0){
                    binding!!.ivSearchDefault.visibility = View.VISIBLE
                    binding!!.tvSearchDefault.visibility = View.VISIBLE
                    binding!!.rvRecentSearch.visibility = View.GONE
                }
                else{
                    adapter.setList(it)
                    binding!!.ivSearchDefault.visibility = View.GONE
                    binding!!.tvSearchDefault.visibility = View.GONE
                    binding!!.rvRecentSearch.visibility = View.VISIBLE
                    binding!!.rvRecentSearch.adapter=adapter
                    adapter.notifyDataSetChanged()
                    binding!!.rvRecentSearch.adapter!!.notifyDataSetChanged()
                    binding!!.tvSearchDelete.visibility=View.VISIBLE
                }
                Log.d(TAG,"This is getAll")

            })
        } else if (status == 3) {
            displayStatus = 3
            binding!!.etSearchSearchEdit.clearFocus()
            binding!!.flSearch.requestFocus()
            setMarginBottom()
            binding!!.tvSearchRecent.visibility = View.INVISIBLE
            binding!!.ivSearchDefault.visibility = View.INVISIBLE
            binding!!.tvSearchDefault.visibility = View.INVISIBLE
            binding!!.clSearchWhite.visibility = View.INVISIBLE
            binding!!.searchMapview.visibility = View.VISIBLE
            binding!!.ivSearchCurrentLocation.visibility = View.VISIBLE
            binding!!.searchBottomSheet.visibility = View.VISIBLE
            binding!!.rvRecentSearch.visibility = View.GONE
            binding!!.tvSearchDelete.visibility=View.GONE

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
            mapViewContainer.addView(mapView)
            for (i in 1..10) {
                addTestPOIItem(i)

            }
            initObserve()

        } else {
            // Failed pass
        }
    }

    private fun checkPermission() {
        permReqLuncher.launch(permission)
    }

    private fun initMapVIew() {
        mapView = MapView(context)
        mapViewContainer = binding!!.searchMapview
        mapView.setMapViewEventListener(this)

    }

    private fun addTestPOIItem(i: Int) {
        val marker = MapPOIItem()

        marker.apply {
            itemName = i.toString()
            mapPoint = MapPoint.mapPointWithGeoCoord(37.41 + (Math.random() / 3),
                126.73 + (Math.random() / 2))
        }
        mapView.addPOIItem(marker)
    }

    private fun initBottomSheetView() {
        var bottomsheet = BottomSheetBehavior.from(binding!!.searchBottomSheet)
        bottomsheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomsheet: View, newState: Int) {
                // BottomSheetBehavior의 5가지 상태
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
                    changeDisplay(2)
                } else if (displayStatus == 2) {
                    changeDisplay(1)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    fun initRecentRecyclerView(){
        adapter = RecentSearchRvAdapter(this)
        binding!!.rvRecentSearch.adapter= adapter
        binding!!.rvRecentSearch.layoutManager = LinearLayoutManager(this.activity)
    }

    override fun deleteRecent(recentSearch: RecentSearchData) {
        lifecycleScope.launch(Dispatchers.IO){
            viewModel.delete(recentSearch)
        }
    }

}