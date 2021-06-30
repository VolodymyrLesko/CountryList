package com.example.countrylist.activities

import android.content.Intent
import android.os.Bundle
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
    private lateinit var recyclerView: RecyclerView

    private lateinit var presenter: MainActivityContract.Presenter
    lateinit var recyclerViewAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.countriesRV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = CountryAdapter(countriesList, this)
        recyclerView.adapter = recyclerViewAdapter

        setPresenter(MainActivityPresenter(this, CountryRepositoryImpl()))
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setPresenter(presenter: MainActivityContract.Presenter) {
        this.presenter = presenter
    }

    override fun onClick(position: Int) {
        intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(CODE, countriesList[position].code)
        startActivity(intent)
    }

    companion object {
        const val CODE = "code"
    }

    override fun displayCountriesList(countriesList: List<CountriesListQuery.Country>) {
        this.countriesList = countriesList as MutableList<CountriesListQuery.Country>
        this.recyclerViewAdapter.setList(countriesList)
    }
}
