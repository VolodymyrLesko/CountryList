package com.example.countrylist.countryDetails.activity

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
import com.example.countrylist.countryDetails.contract.DetailsActivityContract
import com.example.countrylist.countryDetails.presenter.DetailsActivityPresenter

class DetailsActivity : AppCompatActivity(), DetailsActivityContract.View {
    private lateinit var detailsActivityPresenter: DetailsActivityContract.Presenter
    private lateinit var txtName: TextView
    private lateinit var txtCapital: TextView
    private lateinit var txtRegion: TextView
    private lateinit var txtCurrency: TextView
    private lateinit var languageAdapter: LanguageAdapter
    private lateinit var detailsProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        detailsProgressBar = findViewById(R.id.detailsProgressBar)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.include)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        txtName = findViewById(R.id.details_country_name)
        txtCapital = findViewById(R.id.details_capital_name)
        txtRegion = findViewById(R.id.details_region_name)
        txtCurrency = findViewById(R.id.details_curency)
        initLanguagesList()
        setPresenter(DetailsActivityPresenter(this, CountryRepositoryImpl()))
        intent.extras?.getString(Utils.CODE)
            ?.let { detailsActivityPresenter.onLoadCountryDetails(it) }
    }

    override fun onDestroy() {
        detailsActivityPresenter.onDestroy()
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

    override fun setPresenter(presenter: DetailsActivityContract.Presenter) {
        this.detailsActivityPresenter = presenter
    }

    override fun hideProgressBar() {
        detailsProgressBar.visibility = ProgressBar.GONE
    }

    override fun showProgressBar() {
        detailsProgressBar.visibility = ProgressBar.VISIBLE
    }

    override fun showError() {
        Toast.makeText(
            this, "Something goes wrong :(",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun initLanguagesList() {
        val languageRecyclerView = findViewById<RecyclerView>(R.id.langRV)
        languageAdapter = LanguageAdapter()
        languageRecyclerView.apply {
            languageRecyclerView.layoutManager = LinearLayoutManager(
                this@DetailsActivity, RecyclerView.HORIZONTAL, false
            )
            languageRecyclerView.adapter = languageAdapter
        }
    }
}