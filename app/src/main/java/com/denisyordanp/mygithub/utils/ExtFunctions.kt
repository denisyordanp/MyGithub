package com.denisyordanp.mygithub.utils

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.denisyordanp.mygithub.R

fun ImageView.load(url: String) {
    val placeHolder = CircularProgressDrawable(this.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
    Glide.with(this.context)
        .load(url)
        .error(R.drawable.ic_broken_image)
        .placeholder(placeHolder)
        .into(this)
}