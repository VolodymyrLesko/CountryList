package com.example.countrylist.config

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.countrylist.CountriesListQuery

class ApolloConfig(
    private var apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(URL)
        .build()) {

    fun getCountryList(): List<CountriesListQuery.Country> {
        val result: MutableList<CountriesListQuery.Country> = ArrayList()
        apolloClient.query(CountriesListQuery())
            .enqueue(object : ApolloCall.Callback<CountriesListQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d("ApolloResult", e.localizedMessage ?: "Error")
                }

                override fun onResponse(response: Response<CountriesListQuery.Data>) {
                    Log.d("ApolloResult", response.data?.countries.toString())
                    response.data?.countries?.let { result.addAll(it) }
                    Log.d("ApolloResult1", result.toString())
                }
            })
        return result
    }
    companion object {
        const val URL = "https://countries.trevorblades.com"
    }
}