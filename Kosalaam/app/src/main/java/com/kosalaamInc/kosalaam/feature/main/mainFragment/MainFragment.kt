package com.kosalaamInc.kosalaam.feature.main.mainFragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.FragmentMainBinding
import com.kosalaamInc.kosalaam.feature.activitySearch.ActivitiesSearchActivity
import com.kosalaamInc.kosalaam.feature.hotelSearch.HotelSearchActivity
import com.kosalaamInc.kosalaam.feature.main.MainActivity
import com.kosalaamInc.kosalaam.feature.prayerTime.PraytimeActivity
import com.kosalaamInc.kosalaam.feature.restaurantSearch.RestaurantSearchActivity
import com.kosalaamInc.kosalaam.feature.restaurantSearch.RestaurantSearchViewModel
import com.kosalaamInc.kosalaam.feature.signUp.SignUpViewModel

class MainFragment : Fragment(){
    private lateinit var viewModel : MainFragViewModel
    private var binding : FragmentMainBinding? = null


    companion object{
        const val Tag = "MainFragment"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding= DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)

            return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainFragViewModel::class.java)
        binding!!.lifecycleOwner = viewLifecycleOwner
        binding!!.mainFragVm = viewModel
        initObserve()

    }

    override fun onDestroyView() {
        binding= null
        super.onDestroyView()
    }

    fun initObserve(){
        with(viewModel){
            prayerRoom_BtEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    findNavController().navigate(R.id.action_mainFragment_to_prayerRoomFragment)
                }
            })
            hotel_BtEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    startActivity(Intent(context,HotelSearchActivity::class.java))
                }
            })
            restaurant_BtEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    startActivity(Intent(context,RestaurantSearchActivity::class.java))
                }
            })
            prayerTime_BtEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    startActivity(Intent(context,PraytimeActivity::class.java))
                }
            })
            activities_BtEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    startActivity(Intent(context,ActivitiesSearchActivity::class.java))
                }
            })
            newMagazine_BtEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    setTextClick()
                    binding!!.tvMainNewMagazine.setTextColor(Color.parseColor("#419070"))
                    binding!!.dotMainNewMagazine.visibility = View.VISIBLE
                }
            })
            mostView_BtEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    setTextClick()
                    binding!!.tvMainMostview.setTextColor(Color.parseColor("#419070"))
                    binding!!.dotMainMostview.visibility = View.VISIBLE
                }
            })
            tour_BtEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    setTextClick()
                    binding!!.tvMainTour.setTextColor(Color.parseColor("#419070"))
                    binding!!.dotMainTour.visibility = View.VISIBLE
                }
            })
            food_BtEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    setTextClick()
                    binding!!.tvMainFood.setTextColor(Color.parseColor("#419070"))
                    binding!!.dotMainFood.visibility = View.VISIBLE
                }
            })
            kpop_BtEvent.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    setTextClick()
                    binding!!.tvMainKpop.setTextColor(Color.parseColor("#419070"))
                    binding!!.dotMainKpop.visibility = View.VISIBLE
                }
            })
        }
    }
    fun setTextClick(){
        with(binding!!){
            tvMainNewMagazine.setTextColor(Color.parseColor("#191919"))
            tvMainMostview.setTextColor(Color.parseColor("#191919"))
            tvMainFood.setTextColor(Color.parseColor("#191919"))
            tvMainTour.setTextColor(Color.parseColor("#191919"))
            tvMainKpop.setTextColor(Color.parseColor("#191919"))
            dotMainFood.visibility = View.INVISIBLE
            dotMainKpop.visibility= View.INVISIBLE
            dotMainMostview.visibility= View.INVISIBLE
            dotMainNewMagazine.visibility= View.INVISIBLE
            dotMainTour.visibility= View.INVISIBLE
        }

    }

}