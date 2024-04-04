package com.example.farmguardian;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import static com.google.common.truth.Truth.assertThat;

import com.example.farmguardian.views.LoginActivityFirebase;

import org.junit.Rule;
import org.junit.Test;

public class LoginActivityFirebaseTest  {
    @Rule
    public ActivityScenarioRule<LoginActivityFirebase> activityScenarioRule = new ActivityScenarioRule<>(LoginActivityFirebase.class);

@Test
    public  void testWithInvalidCredentials()
    {     // Type invalid email and password
        Espresso.onView(ViewMatchers.withId(R.id.editTxtEMAIL)).perform(ViewActions.typeText("invalid_email@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.editTextTextPassword)).perform(ViewActions.typeText("password"));


        //keyboard  might be hiding login button
        Espresso.closeSoftKeyboard();

        //click login button

        Espresso.onView(ViewMatchers.withId(R.id.buttonLogin)).perform(ViewActions.click());
        //same delay reasons as above

        try{
            Thread.sleep(3000);

        }
        catch (InterruptedException er)
        {

            er.printStackTrace();
        }

        assertThat(ViewMatchers.withId(R.id.Logout).matches(ViewMatchers.isDisplayed())).isFalse();
    }

    @Test
    public void testLoginWithvalidCredentials() {
        // Type valid email and password
        Espresso.onView(ViewMatchers.withId(R.id.editTxtEMAIL)).perform(ViewActions.typeText("Ndoza@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.editTextTextPassword)).perform(ViewActions.typeText("Bongani@*"));

        // Close soft keyboard (if it's open)
        Espresso.closeSoftKeyboard();

        //login button click
        Espresso.onView(ViewMatchers.withId(R.id.buttonLogin)).perform(ViewActions.click());

        //to avoid flakiness in the test,Delaying some time for navigation

        try {
            Thread.sleep(3500);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Logout card-view will display is successfully logged in  by Checking  if Home page heading text is displayed in HomeActivity
           Espresso.onView(ViewMatchers.withId(R.id.textViewFG))
                 .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


}