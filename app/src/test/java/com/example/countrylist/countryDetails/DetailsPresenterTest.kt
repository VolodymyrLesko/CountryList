package com.example.countrylist.countryDetails

import com.apollographql.apollo.api.Response
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.base.repository.CountryRepository
import io.reactivex.rxjava3.core.Observable
import okio.ByteString.Companion.encodeUtf8
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class DetailsPresenterTest {
    @Mock
    private lateinit var countryRepository: CountryRepository
    private lateinit var presenter: DetailsPresenter
    private val string: String = "data:{" +
            "Data(country=Country(__typename=Country, name=Andorra," +
            " capital=Andorra la Vella, continent=Continent(__typename=Continent," +
            " name=Europe), currency=EUR, languages=[Language(__typename=Language," +
            " name=Catalan)]))" +
            "}"

    private val response: Response<GetCountryQuery.Data> =
        GetCountryQuery(anyString()).parse(string.encodeUtf8()) // не парсяться дані


    @Before
    fun setUp() {
        countryRepository = mock(CountryRepository::class.java)
        presenter = DetailsPresenter(countryRepository)
    }

    @Test
    fun getCountryDetails() {
        `when`(countryRepository.getCountryDetails("AD")).thenReturn(
            Observable.just(
                response
            )
        )
        presenter.getCountryDetails("AD")
        verify(countryRepository, times(1)).getCountryDetails("AD")
    }
}
