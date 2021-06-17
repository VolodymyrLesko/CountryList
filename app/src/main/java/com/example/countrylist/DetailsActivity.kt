package com.example.countrylist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.countrylist.adapter.LanguageAdapter

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val txtName: TextView = findViewById(R.id.details_country_name)
        val txtCapital: TextView = findViewById(R.id.details_capital_name)
        val txtRegion: TextView = findViewById(R.id.details_region_name)
        val txtCurrency: TextView = findViewById(R.id.details_curency)
//        val txtLanguages: TextView = findViewById(R.id.details_lang)

        val apolloClient = ApolloClient.builder()
            .serverUrl("https://countries.trevorblades.com")
            .build()

        if (intent.extras != null) {
            apolloClient.query(GetCountryQuery(intent.extras!!.getString("code", "")))
                .enqueue(object : ApolloCall.Callback<GetCountryQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.d("ApolloResult", e.localizedMessage ?: "Error")
                    }

                    override fun onResponse(response: Response<GetCountryQuery.Data>) {
                        Log.d("ApolloResult", response.data.toString())
                        runOnUiThread {
                            txtName.text = response.data?.country?.name
                            txtCapital.text = response.data?.country?.capital
                            txtRegion.text = response.data?.country?.continent?.name
                            txtCurrency.text = response.data?.country?.currency
                            runOnUiThread {
                                response.data?.country?.languages?.let { bindToRecyclerview(it) }
                            }
                        }
                    }
                })
        }
    }

    @SuppressLint("WrongConstant")
    fun bindToRecyclerview(languageList: List<GetCountryQuery.Language>) {
        val recyclerView = findViewById<RecyclerView>(R.id.langRV)
        recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayout.HORIZONTAL, false)
        val recyclerViewAdapter = LanguageAdapter(languageList)
        recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()
    }
}