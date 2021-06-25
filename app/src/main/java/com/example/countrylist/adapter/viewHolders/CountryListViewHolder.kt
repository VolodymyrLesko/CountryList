package com.example.countrylist.adapter.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.R
import com.example.countrylist.adapter.CountryAdapter

class CountryListViewHolder(itemView: View, private val listener: CountryAdapter.RVOnClickListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val txtTitle: TextView = itemView.findViewById(R.id.country_name)
    val txtCapital: TextView = itemView.findViewById(R.id.capital_name)
    val txtRegion: TextView = itemView.findViewById(R.id.region_name)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        listener.onClick(adapterPosition)
    }
}