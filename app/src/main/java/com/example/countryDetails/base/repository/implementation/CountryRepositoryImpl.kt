package com.example.countryDetails.base.repository.implementation

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.example.countryDetails.base.repository.CountryRepository
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.GetCountryQuery
import io.reactivex.rxjava3.core.Observable

class CountryRepositoryImpl(
    private var apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(URL)
        .build()
) : CountryRepository {

    override fun getCountryList(): Observable<Response<CountriesListQuery.Data>> {
        return apolloClient.rxQuery(CountriesListQuery())
    }

    override fun getCountryDetails(countryCode: String): Observable<Response<GetCountryQuery.Data>> {
        return apolloClient.rxQuery(GetCountryQuery(countryCode))
    }

    companion object {
        const val URL = "https://countries.trevorblades.com"
    }
}