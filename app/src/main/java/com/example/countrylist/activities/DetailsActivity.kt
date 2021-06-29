package com.example.countrylist.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.R
import com.example.countrylist.adapter.LanguageAdapter
import com.example.countrylist.contracts.DetailsActivityContract
import com.example.countrylist.presenters.DetailsActivityPresenter
import com.example.countrylist.repository.implementation.CountryRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsActivity : AppCompatActivity(), DetailsActivityContract.View {
    private lateinit var languageRecyclerView: RecyclerView
    private lateinit var presenter: DetailsActivityContract.Presenter
    private lateinit var txtName: TextView
    private lateinit var txtCapital: TextView
    private lateinit var txtRegion: TextView
    private lateinit var txtCurrency: TextView
    lateinit var recyclerViewAdapter: LanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        txtName = findViewById(R.id.details_country_name)
        txtCapital = findViewById(R.id.details_capital_name)
        txtRegion = findViewById(R.id.details_region_name)
        txtCurrency = findViewById(R.id.details_curency)
        languageRecyclerView = findViewById(R.id.langRV)
        languageRecyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL, false
        )
        recyclerViewAdapter = LanguageAdapter(ArrayList())
        languageRecyclerView.adapter = recyclerViewAdapter
        setPresenter(DetailsActivityPresenter(this, CountryRepositoryImpl()))
        intent.extras?.getString("code")?.let { presenter.onLoadCountryDetails(it) }

    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun displayCountryDetails(countryDetails: Single<GetCountryQuery.Data>) {
        countryDetails
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

    override fun setPresenter(presenter: DetailsActivityContract.Presenter) {
        this.presenter = presenter
    }
}