package com.example.record_tool;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.record_tool.Dao.StartActivity;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<StartActivity> activityTestRule = new ActivityScenarioRule<>(StartActivity.class);
    @Test
    public void useAppContext() {
        // Context of the app under test.
       onView(withId(R.id.loginButton)).perform(click());

    }
}