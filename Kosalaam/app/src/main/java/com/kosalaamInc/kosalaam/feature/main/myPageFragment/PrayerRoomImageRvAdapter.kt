package com.kosalaamInc.kosalaam.feature.main.myPageFragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.restaurantInfo.RestaurantInfoActivity
import com.kosalaamInc.kosalaam.model.data.RestaurantSearchData


class PrayerRoomImageRvAdapter(var mContext : Context, var data: ArrayList<Uri>?) :
    RecyclerView.Adapter<PrayerRoomImageRvAdapter.ViewHolder>() {

    interface OnSearchItemClickListener {
        fun onItemClick(v:View, pos : Int)
    }

    var listener1 : OnSearchItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.rv_images,parent,false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)
        holder.apply {
            bind(item)
            itemView.tag= item
        }
    }

    override fun getItemCount() =data!!.size

    inner class ViewHolder(v:View) : RecyclerView.ViewHolder(v){
        private val image : ImageView = itemView.findViewById(R.id.iv_image)
        fun bind(item : Uri? ){
            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener1?.onItemClick(itemView,pos)
                }
            }
            Glide.with(mContext).load(item).transform(CenterCrop(),RoundedCorners(10)).into(image)

        }
    }
    // binding 끝내고 dataSet 연결
}