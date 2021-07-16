package com.example.countrylist.base.repository.implementation

import com.apollographql.apollo.api.Response
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.base.repository.CountryRepository
import io.reactivex.rxjava3.core.Observable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CountryRepositoryImplTest {

    @Mock
    private lateinit var countryRepository: CountryRepository
    private lateinit var observable: Observable<Response<CountriesListQuery.Data>>
    private lateinit var detailsObservable: Observable<Response<GetCountryQuery.Data>>

    @Before
    fun init() {
        countryRepository = mock(CountryRepository::class.java)
        observable = Observable.empty()
        detailsObservable = Observable.empty()
    }

    @Test
    fun getCountryList() {
        `when`(countryRepository.getCountryList()).thenReturn(observable)
        assertEquals(countryRepository.getCountryList(), observable)
    }

    @Test
    fun getCountryDetails() {
        `when`(countryRepository.getCountryDetails("AD")).thenReturn(detailsObservable)
        assertEquals(countryRepository.getCountryDetails("AD"), detailsObservable)
    }
}