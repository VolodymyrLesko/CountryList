package com.example.countrylist.countryDetails

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.countrylist.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsActivityTest {
    //    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>
    private lateinit var detailsActivityScenario: ActivityScenario<DetailsActivity>

    @Before
    fun setUp() {
//        mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)
        detailsActivityScenario = ActivityScenario.launch(DetailsActivity::class.java)
    }

    @Test
    fun verifyToolbarIsDisplayed() {
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
        onView(withParent(withText(R.string.app_name))).perform(click())
        onView(withId(R.id.image1)).check(matches(isDisplayed()))
    }
}