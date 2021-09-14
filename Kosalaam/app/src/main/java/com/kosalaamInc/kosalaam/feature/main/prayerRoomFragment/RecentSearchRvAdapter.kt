package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.R.layout.recycler_item_recentsearch
import com.kosalaamInc.kosalaam.databinding.RecyclerItemRecentsearchBinding
import com.kosalaamInc.kosalaam.model.data.RecentSearchData

class RecentSearchRvAdapter(listener : OnItemClick) : RecyclerView.Adapter<RecentSearchRvAdapter.RecentViewHolder>(){
    private val items = ArrayList<RecentSearchData>()

    inner class RecentViewHolder(private val binding: RecyclerItemRecentsearchBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : RecentSearchData){
            binding!!.rcTvRecentSearch.text = item.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        Log.d("RvAdapter","onCreateViewHolder")
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemRecentsearchBinding.inflate(layoutInflater)
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        Log.d("RvAdapter","onBindViewHolder")
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        Log.d("RvAdapter",items.size.toString())
        return items.size
    }

    fun setList(recentSearch : List<RecentSearchData>){
        items.clear()
        items.addAll(recentSearch)
    }
    fun deleteList(){
        items.clear()
    }

}