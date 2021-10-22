package com.salih.weatherapp.util

import android.content.Context
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.salih.weatherapp.R

fun ImageView.downloadIcon(url : String?,placeholder: CircularProgressDrawable){                          // icon indir
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)            //progressbar
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun makeplaceholder(context : Context) : CircularProgressDrawable{             //Progressbar

    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}