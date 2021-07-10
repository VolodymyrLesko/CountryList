package com.example.countrylist.countriesList

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.countrylist.R
import com.example.countrylist.base.constants.Utils
import com.example.countrylist.countryDetails.DetailsActivity
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)
    @get:Rule
    val detailsRule = ActivityScenarioRule(DetailsActivity::class.java)
    val PACKAGE_NAME = "com.example.countrylist.countriesList"
    val COUNTRY_NAME = "Andorra"

    @Before
    fun setUp() {
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
        intended(allOf(
            hasExtra(Utils.CODE, "AD"),
            toPackage(PACKAGE_NAME),
            hasComponent(hasShortClassName(".DetailsActivity"))
        ))
    }

    @Test
    fun verifyCorrectDataOnDetailsPage() {
        Thread.sleep(1000)
        onView(withText(COUNTRY_NAME)).perform(click()) // проблема тут, може не правильно налаштований -
        // експресо бо без переходів на інші актівіті все відпрацьовує
        onView(withId(R.id.details_country_name)).check(matches(withText(COUNTRY_NAME)))
        onView(withId(R.id.details_capital_name)).check(matches(withText("Andorra la Vella")))
        onView(withId(R.id.details_region_name)).check(matches(withText("Europe")))
    }

    @Test
    fun test() {
        Thread.sleep(1000)
        onView(withText(COUNTRY_NAME)).perform(click())
    }
}