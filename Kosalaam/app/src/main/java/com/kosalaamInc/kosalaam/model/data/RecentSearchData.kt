package com.kosalaamInc.kosalaam.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recentsearchdata")
data class RecentSearchData(var text : String){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}