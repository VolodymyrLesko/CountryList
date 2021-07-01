package com.example.countrylist.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.adapter.CountryAdapter
import com.example.countrylist.contracts.MainActivityContract
import com.example.countrylist.presenters.MainActivityPresenter
import com.example.countrylist.repository.implementation.CountryRepositoryImpl

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
        val countryListRV = findViewById<RecyclerView>(R.id.countriesRV)
        countryAdapter = CountryAdapter(this)
        countryListRV.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = countryAdapter
        }
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
            putExtra(CODE, countriesList[position].code)
        }
        startActivity(intent)
    }

    companion object {
        const val CODE = "code"
    }

    override fun displayCountriesList(countriesList: List<CountriesListQuery.Country>) {
        this.countriesList = countriesList as MutableList<CountriesListQuery.Country>
        this.countryAdapter.setList(countriesList)
    }

    override fun hideProgressBar() {
        progressBar.visibility = ProgressBar.GONE
    }
}
