package com.kosalaamInc.kosalaam.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.kosalaamInc.kosalaam.R

class LoadingDialog constructor(context : Context) : Dialog(context){
   init {
       window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
       setCanceledOnTouchOutside(false)
       setCancelable(false)
       setContentView(R.layout.dialog_loading)
   }

}