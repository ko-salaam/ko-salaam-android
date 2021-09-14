package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.model.data.SearchListData

class SearchRvAdapter(var data : LiveData<ArrayList<SearchListData>>) :
    RecyclerView.Adapter<SearchRvAdapter.ViewHolder>() {
    inner class ViewHolder constructor(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_search, parent, false)
    ) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}