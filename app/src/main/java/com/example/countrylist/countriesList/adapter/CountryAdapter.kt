package com.example.countrylist.countriesList.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.countriesList.adapter.viewHolder.CountryListViewHolder

class CountryAdapter(var countriesList: List<CountriesListQuery.Country>,
                     var listener: RVOnClickListener) :
    RecyclerView.Adapter<CountryListViewHolder>() {

    fun setOnClickListener(listener: RVOnClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_card, parent, false)
        return CountryListViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(countriesList: MutableList<CountriesListQuery.Country>) {
        this.countriesList = countriesList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
            countriesList[position].apply {
                holder.txtTitle.text = name
                holder.txtCapital.text = capital
                holder.txtRegion.text = continent.name
            }
            holder.itemView.setOnClickListener {
                listener.onClick(position)
            }
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    interface RVOnClickListener {
        fun onClick(position: Int)
    }
}