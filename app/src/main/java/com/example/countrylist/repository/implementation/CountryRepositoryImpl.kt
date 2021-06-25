package com.example.countrylist.repository.implementation

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.repository.CountryRepository
import io.reactivex.rxjava3.core.Single

class CountryRepositoryImpl(
    private var apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(URL)
        .build()): CountryRepository {

    override fun getCountryList(): Single<List<CountriesListQuery.Country>> {
        return Single.create { emitter ->
            val result: MutableList<CountriesListQuery.Country> = ArrayList()
            apolloClient.query(CountriesListQuery())
                .enqueue(object : ApolloCall.Callback<CountriesListQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.d("ApolloResult", e.localizedMessage ?: "Error")
                        emitter.onError(e)
                    }

                    override fun onResponse(response: Response<CountriesListQuery.Data>) {
                        Log.d("ApolloResult", response.data?.countries.toString())
                        response.data?.countries?.let { result.addAll(it) }
                        emitter.onSuccess(result)
                    }
                })
        }
    }

    override fun getCountryDetails(countryCode: String): Single<GetCountryQuery.Data> {
        return Single.create { emitter ->
            var result: GetCountryQuery.Data? = null
            apolloClient.query(GetCountryQuery(countryCode))
                .enqueue(object : ApolloCall.Callback<GetCountryQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.d("ApolloResult", e.localizedMessage ?: "Error")
                        emitter.onError(e)
                    }

                    override fun onResponse(response: Response<GetCountryQuery.Data>) {
                        Log.d("ApolloResult", response.data.toString())
                        response.data.let { result = it }
                        emitter.onSuccess(result)
                    }
                })
        }
    }
    companion object {
        const val URL = "https://countries.trevorblades.com"
    }
}