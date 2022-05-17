package com.example.cryptocurrency.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.hide(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun Context.showToast(msg : String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}