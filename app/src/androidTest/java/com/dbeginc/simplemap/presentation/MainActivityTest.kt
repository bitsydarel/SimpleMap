package com.dbeginc.simplemap.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.dbeginc.simplemap.R
import com.dbeginc.simplemap.domain.repository.LocationsRepository
import com.dbeginc.simplemap.espressoDaggerMockRule
import io.reactivex.Flowable
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class MainActivityTest {
    @get:Rule var espressoDagger = espressoDaggerMockRule()
    @get:Rule var activityRule = ActivityTestRule(MainActivity::class.java, false, false)
    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var locationsRepo: LocationsRepository

    @Test
    fun testThatActivityDoesNotCrashOnError() {
        org.mockito.Mockito.`when`(locationsRepo.getAllLocations()).thenReturn(Flowable.error(RuntimeException()))

        activityRule.launchActivity(null)

        Mockito.verify(locationsRepo, Mockito.atLeastOnce()).getAllLocations()

        onView(withText(R.string.could_not_load_locations))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun testThatActivityDoesNotCrashOnRefresh() {
        org.mockito.Mockito.`when`(locationsRepo.getAllLocations()).thenReturn(Flowable.error(RuntimeException()))

        activityRule.launchActivity(null)

        // Click on refresh menu item
        onView(withId(R.id.action_refresh)).perform(click())

        Mockito.verify(locationsRepo, Mockito.atLeastOnce()).getAllLocations()

        onView(withText(R.string.could_not_load_locations))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

}