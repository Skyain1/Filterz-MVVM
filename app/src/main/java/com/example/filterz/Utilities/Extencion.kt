package com.example.filterz.Utilities

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.displayToast(message: String?){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}
fun View.show(){
    this.visibility = View.VISIBLE
}