package com.example.countrylist.countriesList

import com.apollographql.apollo.api.Response
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.base.constants.Utils
import com.example.countrylist.base.repository.CountryRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import okio.ByteString.Companion.encodeUtf8
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class MainPresenterTest {
    @Mock
    private lateinit var countryRepository: CountryRepository
    private lateinit var presenter: MainPresenter
    private val response: Response<CountriesListQuery.Data> =
        CountriesListQuery().parse(Utils.COUNTRY_LIST_RESPONSE.encodeUtf8())

    private lateinit var testObserver: TestObserver<Response<CountriesListQuery.Data>>

    @Before
    fun setUp() {
        countryRepository = mock(CountryRepository::class.java)
        presenter = MainPresenter(countryRepository)
    }

    @Test
    fun getCountriesListTest() {
        `when`(countryRepository.getCountryList()).thenReturn(
            Observable.just(
                response
            )
        )
        testObserver = countryRepository.getCountryList().test()
        testObserver
            .assertNoErrors()
            .assertValue(response)
        verify(countryRepository).getCountryList()
    }
}