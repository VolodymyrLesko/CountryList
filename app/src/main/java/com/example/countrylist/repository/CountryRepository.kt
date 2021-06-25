package com.example.countrylist.repository

import com.example.countrylist.CountriesListQuery
import com.example.countrylist.GetCountryQuery
import io.reactivex.rxjava3.core.Single

interface CountryRepository {
    fun getCountryList(): Single<List<CountriesListQuery.Country>>
    fun getCountryDetails(countryCode: String): Single<GetCountryQuery.Data>
}