package com.example.countrylist.countriesList

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.base.constants.Utils
import com.example.countrylist.base.repository.implementation.CountryRepositoryImpl
import com.example.countrylist.countriesList.adapter.CountryAdapter
import com.example.countrylist.countryDetails.DetailsActivity

class MainActivity : AppCompatActivity(),
    MainContract.MainView {

    private val mainActivityPresenter = MainPresenter(CountryRepositoryImpl())
    private val countryAdapter = CountryAdapter()
    private val progressBar: ProgressBar by lazy { findViewById(R.id.mainProgressBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityPresenter.attachView(this)
        initCountriesList()
        mainActivityPresenter.getCountriesList()
    }

    override fun onDestroy() {
        mainActivityPresenter.detachView()
        super.onDestroy()
    }

    override fun displayCountriesList(countriesList: List<CountriesListQuery.Country>) {
        this.countryAdapter.setList(countriesList as MutableList<CountriesListQuery.Country>)
    }

    override fun startNewActivity(code: String) {
        intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(Utils.CODE, code)
        }
        startActivity(intent)
    }

    override fun hideProgressBar() {
        progressBar.visibility = ProgressBar.GONE
    }

    override fun showProgressBar() {
        progressBar.visibility = ProgressBar.VISIBLE
    }

    override fun showError(message: String) {
        Toast.makeText(
            this, message,
            Toast.LENGTH_LONG
        ).show()
    }


    private fun initCountriesList() {
        val countryListRV = findViewById<RecyclerView>(R.id.countriesRV)
        countryAdapter.setOnClickListener(mainActivityPresenter)
        countryListRV.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = countryAdapter
        }
    }
}
