package com.example.countrylist.base.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.countrylist.R

class BannerView (context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.banner_view, this)
    }
}