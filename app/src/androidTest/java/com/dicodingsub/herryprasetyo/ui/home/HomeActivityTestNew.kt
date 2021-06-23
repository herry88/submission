package com.dicodingsub.herryprasetyo.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.util.EspressoIdlingResource
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTestNew {

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovie() {
        onView(withId(R.id.recycler_view_movies)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.recycler_view_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                10
            )
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.recycler_view_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.layout_error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.layout_error_no_content)).check(matches(not(isDisplayed())))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))

    }


    @Test
    fun loadTvShow() {
        onView(ViewMatchers.withText("TV SHOW")).perform(click())
        onView(withId(R.id.recycler_view_tv_shows)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.recycler_view_tv_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                10
            )
        )
    }

    @Test
    fun loadDetailTvShow() {
        onView(ViewMatchers.withText("TV SHOW")).perform(click())
        onView(withId(R.id.recycler_view_tv_shows)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.recycler_view_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.layout_error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.layout_error_no_content)).check(matches(not(isDisplayed())))

        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))
    }
}
