package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment

import android.content.Context
import android.content.Intent
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


class SearchRvAdapter(var mContext : Context, var data: List<RestaurantSearchData>) :
    RecyclerView.Adapter<SearchRvAdapter.ViewHolder>() {

    interface OnSearchItemClickListener {
        fun onItemClick(v:View, data: RestaurantSearchData, pos : Int)
    }
    var listener1 : OnSearchItemClickListener? = null
    fun setOnItemClickListener(listener : OnSearchItemClickListener) {
        this.listener1 = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_search,parent,false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item =data[position]
        val listener = View.OnClickListener {
            mContext.startActivity(Intent(mContext,RestaurantInfoActivity::class.java))
        }
        holder.apply {
            bind(listener,item)
            itemView.tag= item
        }
    }

    override fun getItemCount() =data.size

    inner class ViewHolder(v:View) : RecyclerView.ViewHolder(v){
        private val name : TextView = itemView.findViewById(R.id.tv_searchList_name)
        private val address : TextView = itemView.findViewById(R.id.tv_searchList_address)
        private val muslimFreindy : TextView = itemView.findViewById(R.id.tv_searchList_muslimFriendly)
        private val check : ImageView = itemView.findViewById(R.id.iv_searchList_check)
        private val image1 : ImageView = itemView.findViewById(R.id.iv_searchList_image1)
        private val image2 : ImageView = itemView.findViewById(R.id.iv_searchList_image2)
        private val image3 : ImageView = itemView.findViewById(R.id.iv_searchList_image3)
        private val image4 : ImageView = itemView.findViewById(R.id.iv_searchList_image4)
        private val linear : LinearLayout = itemView.findViewById(R.id.linear_searchList)
//        private var view : View = v

        fun bind(listener : View.OnClickListener,item : RestaurantSearchData ){
            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener1?.onItemClick(itemView,item,pos)
                }
            }
            name.text = item.name
            address.text = item.address
            if(item.domain=="ACCOMMODATION"){
                if(item.mulsimFriendly=="false" || item.mulsimFriendly==null){
                    check.visibility=View.GONE
                    muslimFreindy.visibility=View.GONE
                }
                else{
                    muslimFreindy.text="Muslim Friendly"
                }
            }

            else if(item.domain=="RESTAURANT"){
                if(item.mulsimFriendly=="NONE"){
                    check.visibility=View.GONE
                    muslimFreindy.visibility=View.GONE
                }
                else{
                    muslimFreindy.text = item.mulsimFriendly
                }
            }

            else{
                check.visibility=View.GONE
                muslimFreindy.visibility=View.GONE
            }
            if(item.images?.get(0) ==null &&
                item.images?.get(1) ==null &&
                item.images?.get(2) ==null &&
                item.images?.get(3) ==null ){
                linear.visibility=View.GONE
            }
            else{
                if(item.images?.get(0) ==null){
                    image1.visibility= View.GONE
                }
                else{
                    Glide.with(mContext).load(item.images?.get(0)).transform(CenterCrop(),RoundedCorners(20)).into(image1)
                }
                if(item.images?.get(1) ==null){
                    image2.visibility= View.GONE
                }
                else{
                    Glide.with(mContext).load(item.images?.get(1)).transform(CenterCrop(),RoundedCorners(20)).into(image2)
                }

                if(item.images?.get(2) ==null){
                    image3.visibility= View.GONE
                }

                else{
                    Glide.with(mContext).load(item.images?.get(2)).transform(CenterCrop(),RoundedCorners(20)).into(image3)
                }

                if(item.images?.get(3) ==null){
                    image4.visibility= View.GONE
                }

                else{
                    Glide.with(mContext).load(item.images?.get(3)).transform(CenterCrop(),RoundedCorners(20)).into(image4)
                }
            }

        }
    }
}