package com.example.countrylist.countryDetails

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.api.Response
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.R
import com.example.countrylist.base.constants.Utils
import com.example.countrylist.base.repository.implementation.CountryRepositoryImpl
import com.example.countrylist.countryDetails.adapter.LanguageAdapter

class DetailsActivity : AppCompatActivity(), DetailsContract.DetailsView {
    private val detailsActivityPresenter = DetailsPresenter(CountryRepositoryImpl())
    private val txtName: TextView by lazy { findViewById(R.id.details_country_name) }
    private val txtCapital: TextView by lazy { findViewById(R.id.details_capital_name) }
    private val txtRegion: TextView by lazy { findViewById(R.id.details_region_name) }
    private val txtCurrency: TextView by lazy { findViewById(R.id.details_curency) }
    private val languageAdapter = LanguageAdapter()
    private val detailsProgressBar: ProgressBar by lazy { findViewById(R.id.detailsProgressBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        detailsActivityPresenter.attachView(this)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.include)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        initLanguagesList()
        intent.extras?.getString(Utils.CODE)
            ?.let { detailsActivityPresenter.getCountryDetails(it) }
    }

    override fun onDestroy() {
        detailsActivityPresenter.detachView()
        super.onDestroy()
    }

    override fun displayCountryDetails(countryDetails: Response<GetCountryQuery.Data>) {
        countryDetails.data?.country.let {
            txtName.text = it?.name
            txtCapital.text = it?.capital
            txtRegion.text = it?.continent?.name
            txtCurrency.text = it?.currency
            it?.languages?.let { item -> languageAdapter.setList(item) }
        }

    }

    override fun hideProgressBar() {
        detailsProgressBar.visibility = ProgressBar.GONE
    }

    override fun showProgressBar() {
        detailsProgressBar.visibility = ProgressBar.VISIBLE
    }

    override fun showError(message: String) {
        Toast.makeText(
            this, message,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun initLanguagesList() {
        val languageRecyclerView = findViewById<RecyclerView>(R.id.langRV)
        languageRecyclerView.apply {
            languageRecyclerView.layoutManager = LinearLayoutManager(
                this@DetailsActivity, RecyclerView.HORIZONTAL, false
            )
            languageRecyclerView.adapter = languageAdapter
        }
    }
}