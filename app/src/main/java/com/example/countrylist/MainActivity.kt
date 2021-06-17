package com.example.countrylist

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.countrylist.adapter.CountryAdapter

class MainActivity : AppCompatActivity(), CountryAdapter.RVOnClickListener {

    private lateinit var mainResponse: Response<CountriesListQuery.Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val apolloConfig = ApolloConfig()
//        mainResponse = apolloConfig.getCountryList()
//        runOnUiThread {
//            bindToRecyclerview(mainResponse)
//        }
        val apolloClient = ApolloClient.builder()
            .serverUrl("https://countries.trevorblades.com")
            .build()

        apolloClient.query(CountriesListQuery())
            .enqueue(object : ApolloCall.Callback<CountriesListQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d("ApolloResult", e.localizedMessage ?: "Error")
                }

                override fun onResponse(response: Response<CountriesListQuery.Data>) {
                    mainResponse = response
                    Log.d("ApolloResult", response.data?.countries.toString())
                    runOnUiThread {
                        response.data?.countries?.let { bindToRecyclerview(it) }
                    }
                }
            })
    }

    @SuppressLint("WrongConstant")
    fun bindToRecyclerview(countriesList: List<CountriesListQuery.Country>) {
        val recyclerView = findViewById<RecyclerView>(R.id.countriesRV)
        recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayout.VERTICAL, false)
        val recyclerViewAdapter = CountryAdapter(countriesList, this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onClick(position: Int) {
        intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("code", mainResponse.data?.countries?.get(position)?.code)
        startActivity(intent)
    }

}
