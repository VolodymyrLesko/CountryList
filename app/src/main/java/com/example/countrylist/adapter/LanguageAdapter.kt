package com.example.countrylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.R
import com.example.countrylist.adapter.viewHolders.LanguageViewHolder

class LanguageAdapter(private var languageList: List<GetCountryQuery.Language>) :
    RecyclerView.Adapter<LanguageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.lang_card, parent, false)
        return LanguageViewHolder(v);
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.txtName.text = languageList[position].name
    }

    override fun getItemCount(): Int {
        return languageList.size
    }

    fun setList(languageList: List<GetCountryQuery.Language>) {
        this.languageList = languageList
        notifyDataSetChanged()
    }
}