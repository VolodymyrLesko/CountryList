package com.example.countrylist.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.api.Response
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.R
import com.example.countrylist.adapter.LanguageAdapter
import com.example.countrylist.contracts.DetailsActivityContract
import com.example.countrylist.presenters.DetailsActivityPresenter
import com.example.countrylist.repository.implementation.CountryRepositoryImpl

class DetailsActivity : AppCompatActivity(), DetailsActivityContract.View {
    private lateinit var detailsActivityPresenter: DetailsActivityContract.Presenter
    private lateinit var txtName: TextView
    private lateinit var txtCapital: TextView
    private lateinit var txtRegion: TextView
    private lateinit var txtCurrency: TextView
    lateinit var languageAdapter: LanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.include)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        txtName = findViewById(R.id.details_country_name)
        txtCapital = findViewById(R.id.details_capital_name)
        txtRegion = findViewById(R.id.details_region_name)
        txtCurrency = findViewById(R.id.details_curency)
        val languageRecyclerView = findViewById<RecyclerView>(R.id.langRV)
        languageAdapter = LanguageAdapter()
        languageRecyclerView.apply {
            languageRecyclerView.layoutManager = LinearLayoutManager(
                this@DetailsActivity, RecyclerView.HORIZONTAL, false
            )
            languageRecyclerView.adapter = languageAdapter
        }
        setPresenter(DetailsActivityPresenter(this, CountryRepositoryImpl()))
        intent.extras?.getString("code")?.let { detailsActivityPresenter.onLoadCountryDetails(it) }
    }

    override fun onDestroy() {
        detailsActivityPresenter.onDestroy()
        super.onDestroy()
    }

    override fun displayCountryDetails(countryDetails: Response<GetCountryQuery.Data>) {
        txtName.text = countryDetails.data?.country?.name
        txtCapital.text = countryDetails.data?.country?.capital
        txtRegion.text = countryDetails.data?.country?.continent?.name
        txtCurrency.text = countryDetails.data?.country?.currency
        countryDetails.data?.country?.languages?.let { languageAdapter.setList(it) }
    }

    override fun setPresenter(presenter: DetailsActivityContract.Presenter) {
        this.detailsActivityPresenter = presenter
    }
}