package com.kosalaamInc.kosalaam.model.data

data class RestaurantSearchData(
    var id: String?, var name: String?,
    var address: String?, var price: Int, var mulsimFriendly: String?,
    var images : ArrayList<String>?, var domain : String?
)