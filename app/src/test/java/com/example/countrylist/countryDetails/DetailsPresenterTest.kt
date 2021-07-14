package com.example.countrylist.countryDetails

import com.apollographql.apollo.api.Response
import com.example.countrylist.GetCountryQuery
import com.example.countrylist.base.repository.CountryRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import okio.ByteString.Companion.encodeUtf8
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*


class DetailsPresenterTest {
    @Mock
    private lateinit var countryRepository: CountryRepository
    private lateinit var presenter: DetailsPresenter
    private val responseString: String = "{\n" +
            "  \"data\": {\n" +
            "    \"country\": {\n" +
            "      \"__typename\": \"Country\",\n" +
            "      \"name\": \"Ukraine\",\n" +
            "      \"native\": \"Україна\",\n" +
            "      \"phone\": \"380\",\n" +
            "      \"capital\": \"Kyiv\",\n" +
            "      \"continent\": {\n" +
            "        \"__typename\": \"Continent\",\n" +
            "        \"name\": \"Europe\"\n" +
            "      },\n" +
            "      \"emoji\": \"\uD83C\uDDFA\uD83C\uDDE6\",\n" +
            "      \"emojiU\": \"U+1F1FA U+1F1E6\",\n" +
            "      \"currency\": \"UAH\",\n" +
            "      \"languages\": [\n" +
            "        {\n" +
            "          \"__typename\": \"Language\",\n" +
            "          \"name\": \"Ukrainian\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}"

    private val response: Response<GetCountryQuery.Data> =
        GetCountryQuery("UA").parse(responseString.encodeUtf8())

    private lateinit var testObserver: TestObserver<Response<GetCountryQuery.Data>>


    @Before
    fun setUp() {
        countryRepository = mock(CountryRepository::class.java)
        presenter = DetailsPresenter(countryRepository)
    }

    @Test
    fun getCountryDetails() {
        `when`(countryRepository.getCountryDetails("UA")).thenReturn(
            Observable.just(
                response
            )
        )
        testObserver = countryRepository.getCountryDetails("UA").test()
        testObserver
            .assertNoErrors()
            .assertValue(response)
        verify(countryRepository).getCountryDetails("UA")
    }
}
