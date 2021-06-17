package com.example.countrylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R

class CountryAdapter(
    private val countriesList: List<CountriesListQuery.Country>,
    private val listener: RVOnClickListener
) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, private val listener: RVOnClickListener) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.country_card, parent, false)
        return ViewHolder(v, listener);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
