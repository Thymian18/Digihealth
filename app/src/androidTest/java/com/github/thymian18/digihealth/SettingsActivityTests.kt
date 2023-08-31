package com.github.thymian18.digihealth

import android.content.Intent
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsActivityTests {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun settingsShowsCorrectNavigation() {
        val intent = Intent(getApplicationContext(), SettingsActivity::class.java)
        ActivityScenario.launch<SettingsActivity>(intent).use {
            composeTestRule.onNodeWithTag("HomeButton")
                .assertExists()
            composeTestRule.onNodeWithTag("ConfigurationsButton")
                .assertExists()
            composeTestRule.onNodeWithTag("InfoButton")
                .assertExists()
        }
    }

    @Test
    fun settingsCanNavigateToHome() {
        val intent = Intent(getApplicationContext(), SettingsActivity::class.java)
        ActivityScenario.launch<SettingsActivity>(intent).use {
            composeTestRule.onNodeWithTag("HomeButton")
                .performClick()

            Intents.intended(hasComponent(MainActivity::class.java.name))
        }
    }

    @Test
    fun settingsCanNavigateToInfo() {
        val intent = Intent(getApplicationContext(), SettingsActivity::class.java)
        ActivityScenario.launch<SettingsActivity>(intent).use {
            composeTestRule.onNodeWithTag("InfoButton")
                .performClick()

            Intents.intended(hasComponent(InfoActivity::class.java.name))
        }
    }

}