package com.example.countrylist.countriesList

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apollographql.apollo.api.Response
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.base.constants.Utils
import com.example.countrylist.base.repository.CountryRepository
import io.reactivex.rxjava3.core.Observable
import okio.ByteString.Companion.encodeUtf8
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    val COUNTRY_NAME = "Andorra"
    val COUNTRY_CODE = "AD"

    @Mock
    private lateinit var countryRepository: CountryRepository
    private lateinit var activityScenario: ActivityScenario<MainActivity>
    private val responseString = "{\n" +
            "  \"data\": {\n" +
            "    \"countries\": [\n" +
            "      {\n" +
            "        \"__typename\": \"Country\",\n" +
            "        \"code\": \"AD\",\n" +
            "        \"name\": \"Andorra\",\n" +
            "        \"capital\": \"Andorra la Vella\",\n" +
            "        \"continent\": {\n" +
            "          \"__typename\": \"Continent\",\n" +
            "          \"name\": \"Europe\"\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"__typename\": \"Country\",\n" +
            "        \"code\": \"AE\",\n" +
            "        \"name\": \"United Arab Emirates\",\n" +
            "        \"capital\": \"Abu Dhabi\",\n" +
            "        \"continent\": {\n" +
            "          \"__typename\": \"Continent\",\n" +
            "          \"name\": \"Asia\"\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"__typename\": \"Country\",\n" +
            "        \"code\": \"AF\",\n" +
            "        \"name\": \"Afghanistan\",\n" +
            "        \"capital\": \"Kabul\",\n" +
            "        \"continent\": {\n" +
            "          \"__typename\": \"Continent\",\n" +
            "          \"name\": \"Asia\"\n" +
            "        }\n" +
            "      }]" +
            "   }" +
            "}"
    private val response: Response<CountriesListQuery.Data> =
        CountriesListQuery().parse(responseString.encodeUtf8())

    @Before
    fun setUp() {
        countryRepository = mock(CountryRepository::class.java)
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun verifySendingToDetailsPage() {
        `when`(countryRepository.getCountryList()).thenReturn(
            Observable.just(
                response
            )
        )
        onView(withText(COUNTRY_NAME)).perform(click())
        intended(
            allOf(
                hasExtra(Utils.CODE, COUNTRY_CODE),
            )
        )
    }

    @Test
    fun verifyCorrectDataOnDetailsPage() {
        Thread.sleep(2500)
        onView(withText(COUNTRY_NAME)).perform(click())
        Thread.sleep(200)
        onView(withId(R.id.details_country_name)).check(matches(withText(COUNTRY_NAME)))
        onView(withId(R.id.details_capital_name)).check(matches(withText("Andorra la Vella")))
        onView(withId(R.id.details_region_name)).check(matches(withText("Europe")))
    }

    @Test
    fun navToDetailsActivityTest() {
        Thread.sleep(2500)
        onView(withText(COUNTRY_NAME)).perform(click())

        onView(withId(R.id.details_country_name_title)).check(matches(isDisplayed()))
    }

    @Test
    fun backToMainActivityTest() {
        Thread.sleep(2500)
        onView(withText(COUNTRY_NAME)).perform(click())
        Thread.sleep(200)
        onView(withId(R.id.details_country_name)).check(matches(withText(COUNTRY_NAME)))
        pressBack()
        onView(withId(R.id.image1)).check(matches(isDisplayed()))
    }
}