package com.example.countrylist.countryDetails

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apollographql.apollo.api.Response
import com.example.countrylist.CountriesListQuery
import com.example.countrylist.R
import com.example.countrylist.base.constants.Utils
import com.example.countrylist.base.repository.CountryRepository
import com.example.countrylist.countriesList.MainActivity
import io.reactivex.rxjava3.core.Observable
import okio.ByteString.Companion.encodeUtf8
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class DetailsActivityTest {
    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>
    @Mock
    private val countryRepository = Mockito.mock(CountryRepository::class.java)
    private val response: Response<CountriesListQuery.Data> =
        CountriesListQuery().parse(Utils.COUNTRY_LIST_RESPONSE.encodeUtf8())

    @Before
    fun setUp() {
        mainActivityScenario = ActivityScenario.launch(MainActivity::class.java).apply {
            onActivity {
                it.mainActivityPresenter.setRepository(countryRepository)
            }
        }
    }

    @Test
    fun verifyToolbarIsDisplayed() {
        ActivityScenario.launch(DetailsActivity::class.java)
        onView(withText(R.string.app_name)).check(
            matches(
                allOf(
                    withParent(withId(R.id.include)),
                    isDisplayed()
                )
            )
        )
    }

    @Test
    fun backToMainActivityTest() {
        `when`(countryRepository.getCountryList()).thenReturn(
            Observable.just(
                response
            )
        )
        mainActivityScenario.onActivity {
            it.mainActivityPresenter.getCountriesList()
        }
        onView(withText("Andorra")).perform(click())
        pressBack()
        onView(withId(R.id.image1)).check(matches(isDisplayed()))
    }

    @Test
    fun isProgressbarDisplays() {
        `when`(countryRepository.getCountryList()).thenReturn(
            Observable.just(
                response
            )
        )
        mainActivityScenario.onActivity {
            it.mainActivityPresenter.getCountriesList()
        }
        onView(withText("Andorra")).perform(click())
        onView(withId(R.id.detailsProgressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun isProgressbarGone() {
        `when`(countryRepository.getCountryList()).thenReturn(
            Observable.just(
                response
            )
        )
        mainActivityScenario.onActivity {
            it.mainActivityPresenter.getCountriesList()
        }
        onView(withText("Andorra")).perform(click())
        onView(withId(R.id.detailsProgressBar)).check(matches(isDisplayed()))
        Thread.sleep(100)
        onView(withId(R.id.detailsProgressBar)).check(matches(not(isDisplayed())))
    }
}