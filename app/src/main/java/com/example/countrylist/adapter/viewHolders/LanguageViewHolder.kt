package com.example.countrylist.adapter.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.R


class LanguageViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    val txtName: TextView = itemView.findViewById(R.id.lang_name)
}