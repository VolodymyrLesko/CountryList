package com.example.countryDetails.countryDetails

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.api.Response
import com.example.countryDetails.MyBroadcastReceiver
import com.example.countryDetails.R
import com.example.countryDetails.base.constants.Utils
import com.example.countryDetails.base.repository.implementation.CountryRepositoryImpl
import com.example.countryDetails.countryDetails.adapter.LanguageAdapter
import com.example.countrylist.GetCountryQuery

class MainActivity : AppCompatActivity(), DetailsContract.DetailsView {
    private val detailsActivityPresenter = DetailsPresenter(
        this, CountryRepositoryImpl()
    )
    private val txtName: TextView by lazy { findViewById(R.id.details_country_name) }
    private val txtCapital: TextView by lazy { findViewById(R.id.details_capital_name) }
    private val txtRegion: TextView by lazy { findViewById(R.id.details_region_name) }
    private val txtCurrency: TextView by lazy { findViewById(R.id.details_curency) }
    private val languageAdapter = LanguageAdapter()
    private val detailsProgressBar: ProgressBar by lazy { findViewById(R.id.detailsProgressBar) }
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.include)
        setSupportActionBar(toolbar)
        broadcastReceiver = MyBroadcastReceiver(detailsActivityPresenter)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        initLanguagesList()
        intent.extras?.getString(Utils.CODE)
            ?.let { detailsActivityPresenter.getCountryDetails(it) }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(Intent.ACTION_SEND)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
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
                this@MainActivity, RecyclerView.HORIZONTAL, false
            )
            languageRecyclerView.adapter = languageAdapter
        }
    }
}