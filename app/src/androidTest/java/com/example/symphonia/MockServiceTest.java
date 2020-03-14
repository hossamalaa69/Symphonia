package com.example.symphonia;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.symphonia.Service.MockService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MockServiceTest {

    MockService mockService;
    Context appContext;

    @Before
    public void setUp() {
        mockService = new MockService();
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void LoginListenerFail() {
       assertFalse(mockService.logIn(appContext,"anything","1234",true));
    }

    @Test
    public void LoginListenerSuccess() {
        assertTrue(mockService.logIn(appContext,"user@symphonia.com","12345678",true));
    }

    @Test
    public void LoginArtistFail() {
        assertFalse(mockService.logIn(appContext,"anything","1234",false));
    }

    @Test
    public void LoginArtistSuccess() {
        assertTrue(mockService.logIn(appContext,"artist@symphonia.com","12345678",false));
    }


}
