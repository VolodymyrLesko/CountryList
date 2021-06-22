package com.example.countrylist.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.adapter.CountryAdapter
import com.example.countrylist.config.ApolloConfig

class MainActivity : AppCompatActivity(), CountryAdapter.RVOnClickListener {

    private lateinit var countriesList: List<CountriesListQuery.Country>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getCountriesList = GetCountriesList()
        getCountriesList.execute()
//        val apolloClient = ApolloClient.builder()
//            .serverUrl("https://countries.trevorblades.com")
//            .build()
//
//        apolloClient.query(CountriesListQuery())
//            .enqueue(object : ApolloCall.Callback<CountriesListQuery.Data>() {
//                override fun onFailure(e: ApolloException) {
//                    Log.d("ApolloResult", e.localizedMessage ?: "Error")
//                }
//
//                override fun onResponse(response: Response<CountriesListQuery.Data>) {
//                    countriesList = response.data?.countries?.toList() ?: ArrayList()
//                    Log.d("ApolloResult", response.data?.countries.toString())
//                    runOnUiThread {
//                        response.data?.countries?.let { bindToRecyclerview(it) }
//                    }
//                }
//            })
    }

    fun initializeRV() {
        recyclerView = findViewById<RecyclerView>(R.id.countriesRV) // тут падає така помилка
        //Attempt to invoke virtual method 'android.content.pm.ApplicationInfo
        // android.content.Context.getApplicationInfo()' on a null object reference
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun bindToRecyclerview(countriesList: List<CountriesListQuery.Country>) {
        val recyclerViewAdapter = CountryAdapter(countriesList, this)
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onClick(position: Int) {
        intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(CODE, countriesList[position].code)
        startActivity(intent)
    }

    companion object {
        const val CODE = "code"
    }

    private class GetCountriesList : AsyncTask<Unit, Int, List<CountriesListQuery.Country>>() {
        override fun doInBackground(vararg params: Unit?): List<CountriesListQuery.Country> {
            val apollo = ApolloConfig()
            return apollo.getCountryList()
        }

        override fun onPostExecute(result: List<CountriesListQuery.Country>?) {
            MainActivity().initializeRV()
            if (result != null) {
                MainActivity().bindToRecyclerview(result)
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }
    }
}
