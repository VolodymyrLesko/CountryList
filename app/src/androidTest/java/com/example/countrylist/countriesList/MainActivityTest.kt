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
import com.example.countrylist.R
import com.example.countrylist.base.constants.Utils
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    val COUNTRY_NAME = "Andorra"
    val COUNTRY_CODE = "AD"

    @Before
    fun setUp() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun verifySendingToDetailsPage() {
        Thread.sleep(2500)
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