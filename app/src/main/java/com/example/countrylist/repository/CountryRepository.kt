package com.example.countrylist.repository

import com.apollographql.apollo.api.Response
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.GetCountryQuery
import io.reactivex.rxjava3.core.Single

interface CountryRepository {
    fun getCountryList(): Single<Response<CountriesListQuery.Data>>
    fun getCountryDetails(countryCode: String): Single<Response<GetCountryQuery.Data>>
}