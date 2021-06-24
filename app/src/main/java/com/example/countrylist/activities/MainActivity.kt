package com.example.countrylist.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.adapter.CountryAdapter
import com.example.countrylist.config.ApolloConfig
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity(), CountryAdapter.RVOnClickListener {

    private var countriesList: MutableList<CountriesListQuery.Country> = ArrayList()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.countriesRV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val recyclerViewAdapter = CountryAdapter(countriesList, this)
        recyclerView.adapter = recyclerViewAdapter

        val dispose = getList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                countriesList = it as MutableList<CountriesListQuery.Country>
                recyclerViewAdapter.setList(it)
            }, {

            })
    }

    private fun getList(): Single<List<CountriesListQuery.Country>> {
        return Single.create { subscriber ->
            subscriber.onSuccess(ApolloConfig().getCountryList())
        }
    }

    override fun onClick(position: Int) {
        intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(CODE, countriesList[position].code)
        startActivity(intent)
    }

    companion object {
        const val CODE = "code"
    }
}
