package com.example.countrylist.countriesList.activity

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
import com.example.countrylist.countriesList.contract.MainActivityContract
import com.example.countrylist.countriesList.presenter.MainActivityPresenter
import com.example.countrylist.countryDetails.activity.DetailsActivity

class MainActivity : AppCompatActivity(), CountryAdapter.RVOnClickListener,
    MainActivityContract.View {

    private var countriesList: MutableList<CountriesListQuery.Country> = ArrayList()

    private lateinit var mainActivityPresenter: MainActivityContract.Presenter
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.mainProgressBar)
        initCountriesList()
        setPresenter(MainActivityPresenter(this, CountryRepositoryImpl()))
        mainActivityPresenter.onViewCreated()
    }

    override fun onDestroy() {
        mainActivityPresenter.onDestroy()
        super.onDestroy()
    }

    override fun setPresenter(presenter: MainActivityContract.Presenter) {
        this.mainActivityPresenter = presenter
    }

    override fun onClick(position: Int) {
        intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(Utils.CODE, countriesList[position].code)
        }
        startActivity(intent)
    }

    override fun displayCountriesList(countriesList: List<CountriesListQuery.Country>) {
        this.countriesList = countriesList as MutableList<CountriesListQuery.Country>
        this.countryAdapter.setList(countriesList)
    }

    override fun hideProgressBar() {
        progressBar.visibility = ProgressBar.GONE
    }

    override fun showProgressBar() {
        progressBar.visibility = ProgressBar.VISIBLE
    }

    override fun showError() {
        Toast.makeText(
            this, "Something goes wrong :(",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun initCountriesList() {
        val countryListRV = findViewById<RecyclerView>(R.id.countriesRV)
        countryAdapter = CountryAdapter(this)
        countryListRV.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = countryAdapter
        }
    }
}
