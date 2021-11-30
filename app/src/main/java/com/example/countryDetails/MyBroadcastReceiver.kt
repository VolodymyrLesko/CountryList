package com.example.countryDetails

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.countryDetails.base.constants.Utils
import com.example.countryDetails.base.repository.implementation.CountryRepositoryImpl
import com.example.countryDetails.countryDetails.DetailsPresenter
import com.example.countryDetails.countryDetails.MainActivity

class MyBroadcastReceiver(val detailsPresenter: DetailsPresenter): BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.getStringExtra(Utils.CODE)?.let {
            detailsPresenter
                .getCountryDetails(it)
        }
    }
}