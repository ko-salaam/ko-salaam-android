package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.model.data.RestaurantSearchData
import com.kosalaamInc.kosalaam.model.network.response.RestauarntResponse


class SearchRvAdapter(var data: List<RestaurantSearchData>) :
    RecyclerView.Adapter<SearchRvAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_search,parent,false)
        return ViewHolder(inflatedView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item =data[position]
        val listener = View.OnClickListener {

        }
        holder.apply {
            bind(listener,item)
            itemView.tag= item
        }
    }

    override fun getItemCount() =data.size

    class ViewHolder(v:View) : RecyclerView.ViewHolder(v){
        private val name : TextView = itemView.findViewById(R.id.tv_searchList_name)
        private var view : View = v

        fun bind(listener : View.OnClickListener,item : RestaurantSearchData ){

        }
    }
    // binding 끝내고 dataSet 연결
}