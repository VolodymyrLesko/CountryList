package com.example.countrylist.repository.implementation

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.repository.CountryRepository
import io.reactivex.rxjava3.core.Single

class CountryRepositoryImpl(
    private var apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(URL)
        .build()
) : CountryRepository {

    override fun getCountryList(): Single<Response<CountriesListQuery.Data>> {
        return Single.fromObservable(apolloClient.rxQuery(CountriesListQuery()))
    }

    override fun getCountryDetails(countryCode: String): Single<Response<GetCountryQuery.Data>> {
        val observableDetails = apolloClient.rxQuery(GetCountryQuery(countryCode))
        return Single.fromObservable(observableDetails)
    }

    companion object {
        const val URL = "https://countries.trevorblades.com"
    }
}