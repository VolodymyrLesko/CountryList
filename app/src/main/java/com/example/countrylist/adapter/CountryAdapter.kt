package com.example.countrylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.adapter.viewHolders.CountryListViewHolder

class CountryAdapter(
    var countriesList: MutableList<CountriesListQuery.Country>,
    private val listener: RVOnClickListener
) :
    RecyclerView.Adapter<CountryListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.country_card, parent, false)
        return CountryListViewHolder(v, listener);
    }

    fun setList(countriesList: MutableList<CountriesListQuery.Country>) {
        this.countriesList = countriesList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.txtTitle.text = countriesList[position].name
        holder.txtCapital.text = countriesList[position].capital
        holder.txtRegion.text = countriesList[position].continent.name
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    interface RVOnClickListener {
        fun onClick(position: Int)
    }
}
