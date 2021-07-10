package com.example.countrylist.base.repository

import com.apollographql.apollo.api.Response
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.GetCountryQuery
import io.reactivex.rxjava3.core.Observable

interface CountryRepository {
    fun getCountryList(): Observable<Response<CountriesListQuery.Data>>
    fun getCountryDetails(countryCode: String): Observable<Response<GetCountryQuery.Data>>
}