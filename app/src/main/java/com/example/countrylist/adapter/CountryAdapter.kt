package com.example.countrylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.adapter.viewHolders.CountryListViewHolder

class CountryAdapter(
    private val listener: RVOnClickListener
) :
    RecyclerView.Adapter<CountryListViewHolder>() {

    private var countriesList = emptyList<CountriesListQuery.Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.country_card, parent, false)
        return CountryListViewHolder(item, listener)
    }

    fun setList(countriesList: MutableList<CountriesListQuery.Country>) {
        this.countriesList = countriesList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        with(holder) {
            countriesList[position].apply {
                txtTitle.text = name
                txtCapital.text = capital
                txtRegion.text = continent.name
            }
        }
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    interface RVOnClickListener {
        fun onClick(position: Int)
    }
}