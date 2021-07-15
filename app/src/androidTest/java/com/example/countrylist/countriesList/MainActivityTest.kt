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
import androidx.test.filters.LargeTest
import com.apollographql.apollo.api.Response
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.base.constants.Utils
import com.example.countrylist.base.repository.CountryRepository
import io.reactivex.rxjava3.core.Observable
import okio.ByteString.Companion.encodeUtf8
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @Mock
    private val countryRepository = mock(CountryRepository::class.java)
    private lateinit var activityScenario: ActivityScenario<MainActivity>
    private val response: Response<CountriesListQuery.Data> =
        CountriesListQuery().parse(Utils.COUNTRY_LIST_RESPONSE.encodeUtf8())

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java).apply {
            onActivity {
                it.mainActivityPresenter.setRepository(countryRepository)
            }
        }
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
        activityScenario.onActivity {
            it.mainActivityPresenter.getCountriesList()
        }
        onView(withText(Utils.TEST_COUNTRY_NAME)).perform(click())
        intended(
            allOf(
                hasExtra(Utils.CODE, Utils.TEST_COUNTRY_CODE),
            )
        )
    }

    @Test
    fun verifyCorrectDataOnDetailsPage() {
        `when`(countryRepository.getCountryList()).thenReturn(
            Observable.just(
                response
            )
        )
        activityScenario.onActivity {
            it.mainActivityPresenter.getCountriesList()
        }
        onView(withText(Utils.TEST_COUNTRY_NAME)).perform(click())
        Thread.sleep(200)
        onView(withId(R.id.details_country_name)).check(matches(withText(Utils.TEST_COUNTRY_NAME)))
        onView(withId(R.id.details_capital_name)).check(matches(withText("Andorra la Vella")))
        onView(withId(R.id.details_region_name)).check(matches(withText("Europe")))
    }

    @Test
    fun navToDetailsActivityTest() {
        `when`(countryRepository.getCountryList()).thenReturn(
            Observable.just(
                response
            )
        )
        activityScenario.onActivity {
            it.mainActivityPresenter.getCountriesList()
        }
        onView(withText(Utils.TEST_COUNTRY_NAME)).perform(click())
        onView(withId(R.id.details_country_name_title)).check(matches(isDisplayed()))
    }

    @Test
    fun backToMainActivityTest() {
        `when`(countryRepository.getCountryList()).thenReturn(
            Observable.just(
                response
            )
        )
        activityScenario.onActivity {
            it.mainActivityPresenter.getCountriesList()
        }
        onView(withText(Utils.TEST_COUNTRY_NAME)).perform(click())
        Thread.sleep(200)
        onView(withId(R.id.details_country_name)).check(matches(withText(Utils.TEST_COUNTRY_NAME)))
        pressBack()
        onView(withId(R.id.image1))
    }

    @Test
    fun isProgressbarDisplays() {
        onView(withId(R.id.mainProgressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun isProgressbarGone() {
        `when`(countryRepository.getCountryList()).thenReturn(
            Observable.just(
                response
            )
        )
        onView(withId(R.id.mainProgressBar)).check(matches(isDisplayed()))
        activityScenario.onActivity {
            it.mainActivityPresenter.getCountriesList()
        }
        onView(withId(R.id.mainProgressBar)).check(matches(not(isDisplayed())))
    }
}