package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        private val address : TextView = itemView.findViewById(R.id.tv_searchList_address)
        private val muslimFreindy : TextView = itemView.findViewById(R.id.tv_searchList_muslimFriendly)
        private val image1 : ImageView = itemView.findViewById(R.id.iv_searchList_image1)
        private val image2 : ImageView = itemView.findViewById(R.id.iv_searchList_image2)
        private val image3 : ImageView = itemView.findViewById(R.id.iv_searchList_image3)
        private val image4 : ImageView = itemView.findViewById(R.id.iv_searchList_image4)
//        private var view : View = v

        fun bind(listener : View.OnClickListener,item : RestaurantSearchData ){
            name.text = item.name
            address.text = item.address
            muslimFreindy.text = item.mulsimFriendly
        }
    }
    // binding 끝내고 dataSet 연결
}