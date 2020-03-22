package com.example.symphonia;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.symphonia.Activities.UserUI.MainActivity;
import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Entities.User;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.Service.MockService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest


public class MockServiceTest2 {


    MockService mockService;
    Context appContext;
    User user;


    @Before
    public void setUp() {
        mockService = new MockService();
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        user = new User("eslam1092@hotmail.com", true, Utils.convertToBitmap(R.drawable.amr)
                , "Islam Ahmed", "1998-11-24", "male", true
                , 65500, 40, new ArrayList<User>()
                , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                , new ArrayList<Artist>(), new ArrayList<Album>(), new ArrayList<Track>());
    }


    @Test
    public void LoginListenerFail() {
        assertFalse(mockService.logIn(appContext, "anything", "1234", true));
    }

    @Test
    public void LoginListenerSuccess() {
        assertTrue(mockService.logIn(appContext, "user1@symphonia.com", "12345678", true));
    }

    @Test
    public void LoginArtistFail() {
        assertFalse(mockService.logIn(appContext, "anything", "1234", false));
    }

    @Test
    public void LoginArtistSuccess() {
        assertTrue(mockService.logIn(appContext, "artist1@symphonia.com", "12345678", false));
    }

    @Test
    public void EmailAvailabilityArtistFail(){
        assertFalse(mockService.checkEmailAvailability(appContext, "artist1@symphonia.com",false));
    }

    @Test
    public void EmailAvailabilityArtistSuccess(){
        assertTrue(mockService.checkEmailAvailability(appContext, "artist14@symphonia.com",false));
    }

    @Test
    public void EmailAvailabilityListenerFail(){
        assertFalse(mockService.checkEmailAvailability(appContext, "user1@symphonia.com",true));
    }

    @Test
    public void EmailAvailabilityListenerSuccess(){
        assertTrue(mockService.checkEmailAvailability(appContext, "user14@symphonia.com",true));
    }


}