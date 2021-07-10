package com.example.countrylist.countriesList

import com.example.countrylist.base.repository.CountryRepository
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class MainPresenterTest {
    @Mock
    private lateinit var countryRepository: CountryRepository
    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        countryRepository = mock(CountryRepository::class.java)
        presenter = MainPresenter(countryRepository)
    }

    @Test
    fun getCountriesListTest() {
        `when`(countryRepository.getCountryList()).thenReturn(Observable.empty())
        presenter.getCountriesList()
        verify(countryRepository, times(1)).getCountryList()
    }
}