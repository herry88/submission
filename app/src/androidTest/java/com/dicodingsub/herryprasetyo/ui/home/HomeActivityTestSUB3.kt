package com.dicodingsub.herryprasetyo.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.util.EspressoIdlingResource
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTestSUB3 {


    @get:Rule
    var activity = ActivityScenarioRule(HomeActivity::class.java)

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
    fun loadDetailMovieTesting() {
        onView(withId(R.id.recycler_view_movies)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.recycler_view_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.layout_error_movie_detail)).check(matches(Matchers.not(isDisplayed())))
        onView(withId(R.id.layout_error_no_content_movie)).check(matches(Matchers.not(isDisplayed())))

        onView(withId(R.id.image_postertes)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date_movie)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genres_movie)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_rating_movie)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_desc_movie)).check(matches(isDisplayed()))
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
        onView(withId(R.id.layout_error)).check(matches(Matchers.not(isDisplayed())))
        onView(withId(R.id.layout_error_no_content)).check(matches(Matchers.not(isDisplayed())))

        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))
    }


    @Test
    fun loadFavMovie() {
        onView(withId(R.id.recycler_view_movies)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.recycler_view_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_fav)).perform(click())
        onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.recycler_view_movies)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.recycler_view_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))
        onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.fab_fav)).perform(click())
    }


    @Test
    fun loadFavTv() {
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
        onView(withId(R.id.fab_fav)).perform(click())
        onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.menu_favorite)).perform(click())

        onView(ViewMatchers.withText("TV SHOW FAV")).perform(click())
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
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))

        onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.fab_fav)).perform(click())
    }
}