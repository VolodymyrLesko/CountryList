package com.example.countrylist.countryDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.R
import com.example.countrylist.countryDetails.adapter.viewHolder.LanguageViewHolder

class LanguageAdapter :
    RecyclerView.Adapter<LanguageViewHolder>() {

    private var languagesList = emptyList<GetCountryQuery.Language>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.lang_card, parent, false)
        return LanguageViewHolder(item)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.txtName.text = languagesList[position].name
    }

    override fun getItemCount(): Int {
        return languagesList.size
    }

    fun setList(languageList: List<GetCountryQuery.Language>) {
        this.languagesList = languageList
        notifyDataSetChanged()
    }
}