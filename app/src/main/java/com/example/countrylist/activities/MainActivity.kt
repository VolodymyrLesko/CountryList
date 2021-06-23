package com.example.countrylist.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.adapter.CountryAdapter
import com.example.countrylist.config.ApolloConfig

class MainActivity : AppCompatActivity(), CountryAdapter.RVOnClickListener {

    private var countriesList: MutableList<CountriesListQuery.Country> = ArrayList()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.countriesRV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val recyclerViewAdapter = CountryAdapter(countriesList, this)

        val getCountriesList = GetCountriesList(recyclerViewAdapter)
        getCountriesList.execute()
    }

    override fun onClick(position: Int) {
        intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(CODE, countriesList[position].code)
        startActivity(intent)
    }

    companion object {
        const val CODE = "code"
    }
    //add adapter
    private class GetCountriesList(var countryAdapter: CountryAdapter) : AsyncTask<Unit, Int, List<CountriesListQuery.Country>>() {
        override fun doInBackground(vararg params: Unit?): List<CountriesListQuery.Country> {
            val apollo = ApolloConfig()
            return apollo.getCountryList()
        }

        override fun onPostExecute(result: List<CountriesListQuery.Country>?) {
//            set country list
            if (!result.isNullOrEmpty()) {
                countryAdapter.setList(result as MutableList<CountriesListQuery.Country>)
            }
            MainActivity().recyclerView.adapter = countryAdapter
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }
    }
}
