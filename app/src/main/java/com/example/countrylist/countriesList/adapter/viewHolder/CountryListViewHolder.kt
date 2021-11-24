package com.example.countrylist.countriesList.adapter.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.R
import com.example.countrylist.countriesList.adapter.CountryAdapter

class CountryListViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView){
    val txtTitle: TextView
    val txtCapital: TextView
    val txtRegion: TextView

    init {
        txtTitle = itemView.findViewById(R.id.country_name)
        txtCapital = itemView.findViewById(R.id.capital_name)
        txtRegion = itemView.findViewById(R.id.region_name)
    }
}