package com.example.secureprogramming

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@CustomTestApplication(HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignInFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun signIn_withValidCredentials_navigatesToHomeFragment() {
        // Launch the SignInFragment
        onView(withId(R.id.signInFragment)).perform(click())

        // Enter valid email and password
        onView(withId(R.id.emailEditText)).perform(typeText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.password_edit_text)).perform(typeText("password123"), closeSoftKeyboard())

        // Click the login button
        onView(withId(R.id.loginButton)).perform(click())

        // Wait for the HomeFragment to appear
        onView(withId(R.id.homeFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun signIn_withInvalidCredentials_displaysErrorMessage() {
        // Launch the SignInFragment
        onView(withId(R.id.signInFragment)).perform(click())

        // Enter invalid email and password
        onView(withId(R.id.emailEditText)).perform(typeText("invalid@example.com"), closeSoftKeyboard())
        onView(withId(R.id.password_edit_text)).perform(typeText("invalidpassword"), closeSoftKeyboard())

        // Click the login button
        onView(withId(R.id.loginButton)).perform(click())

        // Wait for the error message to appear
        onView(withText("asdfasfasdf")).check(matches(isDisplayed()))
    }

}
