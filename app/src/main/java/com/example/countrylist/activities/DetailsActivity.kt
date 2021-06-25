package com.example.countrylist.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.R
import com.example.countrylist.adapter.LanguageAdapter
import com.example.countrylist.repository.implementation.CountryRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsActivity : AppCompatActivity() {
    private lateinit var languageRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val txtName: TextView = findViewById(R.id.details_country_name)
        val txtCapital: TextView = findViewById(R.id.details_capital_name)
        val txtRegion: TextView = findViewById(R.id.details_region_name)
        val txtCurrency: TextView = findViewById(R.id.details_curency)
        languageRecyclerView = findViewById(R.id.langRV)
        languageRecyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL, false
        )
        val recyclerViewAdapter = LanguageAdapter(ArrayList())
        languageRecyclerView.adapter = recyclerViewAdapter
        intent.extras?.getString("code")?.let {
            getCountry(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    txtName.text = data.country?.name
                    txtCapital.text = data.country?.capital
                    txtRegion.text = data.country?.continent?.name
                    txtCurrency.text = data.country?.currency
                    data.country?.languages?.let { recyclerViewAdapter.setList(it) }
                }, {
                    it.printStackTrace()
                })
        }
    }

    private fun getCountry(countryCode: String): Single<GetCountryQuery.Data> {
        return CountryRepositoryImpl().getCountryDetails(countryCode)
    }
}