package com.example.symphonia;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.symphonia.Entities.Container;
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

    @Test
    public void getResultsOfSearchFail(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("Gamal", "Album.Little Mix", R.drawable.images3));
        testedData.add(new Container("Samir Abo Elnil", "Artist", R.drawable.download));
        testedData.add(new Container("Amr Diab", "Artist", R.drawable.download));
        testedData.add(new Container("Silence Of Lambs", "Album.Little Mix", R.drawable.download1));
        ArrayList<Container>comingData=mockService.getResultsOfSearch(appContext,"Am");
        assertNotEquals(comingData.size(), testedData.size());
    }
    @Test
    public void getResultsOfSearchSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Amr Diab", "Artist", R.drawable.download));
        ArrayList<Container>comingData=mockService.getResultsOfSearch(appContext,"Am");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(),comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(),testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getArtistsFail(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("Amr Dia", "Artist", R.drawable.download));
        ArrayList<Container>comingData=mockService.getArtists(appContext,"Am");
        assertNotEquals(comingData.get(0).getCat_Name(),testedData.get(0).getCat_Name());
    }

    @Test
    public void getArtistsSuccess(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("Adele", "Artist", R.drawable.download));
        testedData.add(new Container("Amr Diab", "Artist", R.drawable.download));
        testedData.add(new Container("Samir Abo Elnil", "Artist", R.drawable.download));
        ArrayList<Container>comingData=mockService.getArtists(appContext,"A");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(),comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(),testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getSongsFails(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("Cat Sound", "Song.Eminem juice WRLD", R.drawable.images));
        ArrayList<Container>comingData=mockService.getSongs(appContext,"Th");
        assertNotEquals(comingData.get(0).getCat_Name(),testedData.get(0).getCat_Name());
    }

    @Test
    public void getSongsSuccess(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("Cat Sound", "Song.Eminem juice WRLD", R.drawable.images));
        testedData.add(new Container("ThunderClouds", "Song.Eminem juice WRLD", R.drawable.images));
        ArrayList<Container>comingData=mockService.getSongs(appContext,"C");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(),comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(),testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getAlbumsFails(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("Gamal", "Album.Little Mix", R.drawable.images3));
        ArrayList<Container>comingData=mockService.getAlbums(appContext,"G");
        assertNotEquals(comingData.size(),testedData.size());
    }

    @Test
    public void getAlbumsSuccess(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("The Shadows", "Album.Little Mix", R.drawable.download));
        ArrayList<Container>comingData=mockService.getAlbums(appContext,"T");
        assertEquals(comingData.size(),testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(),comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(),testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getProfilesFails(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("Gamal", "Profile.Little Mix", R.drawable.images3));
        ArrayList<Container>comingData=mockService.getProfiles(appContext,"G");
        assertNotEquals(comingData.size(),testedData.size());
    }

    @Test
    public void getProfilesSuccess(){
        ArrayList<Container>testedData=new ArrayList<>();
        ArrayList<Container>comingData=mockService.getProfiles(appContext,"G");
        assertEquals(comingData.size(),testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(),comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(),testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getGenresFails(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("Roc", "Genre", R.drawable.images3));
        ArrayList<Container>comingData=mockService.getGenresAndMoods(appContext,"R");
        assertNotEquals(testedData.get(0).getCat_Name(),comingData.get(0).getCat_Name());
    }

    @Test
    public void getGenresSuccess(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("Rock", "Genre", R.drawable.images3));
        ArrayList<Container>comingData=mockService.getGenresAndMoods(appContext,"R");
        assertEquals(comingData.size(),testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(),comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(),testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getPlaylistsFails(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("quran", "Playlist", R.drawable.images2));
        ArrayList<Container>comingData=mockService.getPlaylists(appContext,"Q");
        assertNotEquals(comingData.size(),testedData.size());
    }

    @Test
    public void getPlaylistsSuccess(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("Quran", "Playlist", R.drawable.images2));
        ArrayList<Container>comingData=mockService.getPlaylists(appContext,"Q");
        assertEquals(comingData.size(),testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(),comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(),testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void removeAllRecentSearchesTest(){
        ArrayList<Container>beforeRemoving=mockService.getResentResult(appContext);
        mockService.removeAllRecentSearches(appContext);
        ArrayList<Container>afterRemoving=mockService.getResentResult(appContext);
        assertEquals(afterRemoving.size(),0);
        assertNotEquals(beforeRemoving.size(),afterRemoving.size());
    }
}
