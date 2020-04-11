package com.example.symphonia;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.symphonia.Activities.User_Interface.MainActivity;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MockServiceTest {

    private MockService mockService;
    private Context appContext;
    private User user;


    @Before
    public void setUp() {
        Constants.currentUser = new User();

        mockService = new MockService();

        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(new Artist("1", Utils.convertToBitmap(R.drawable.ragheb), "Ragheb Alama"));
        artists.add(new Artist("2", Utils.convertToBitmap(R.drawable.elissa), "Elissa"));
        artists.add(new Artist("3", Utils.convertToBitmap(R.drawable.angham), "Angham"));
        artists.add(new Artist("4", Utils.convertToBitmap(R.drawable.wael), "Wael Kfoury"));
        artists.add(new Artist("5", Utils.convertToBitmap(R.drawable.wael_gassar), "Wael Jassar"));

        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        user = new User("eslam1092@hotmail.com","f3fgd", true, Utils.convertToBitmap(R.drawable.amr)
                , "Islam Ahmed", "1998-11-24", "male", true
                , 65500, 40, new ArrayList<User>()
                , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                , artists, new ArrayList<Album>(), new ArrayList<Track>());
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
    public void EmailAvailabilityArtistFail() {
        assertFalse(mockService.checkEmailAvailability(appContext, "artist1@symphonia.com", false));
    }

    @Test
    public void EmailAvailabilityArtistSuccess() {
        assertTrue(mockService.checkEmailAvailability(appContext, "artist14@symphonia.com", false));
    }

    @Test
    public void EmailAvailabilityListenerFail() {
        assertFalse(mockService.checkEmailAvailability(appContext, "user1@symphonia.com", true));
    }

    @Test
    public void EmailAvailabilityListenerSuccess() {
        assertTrue(mockService.checkEmailAvailability(appContext, "user14@symphonia.com", true));
    }


    @Test
    public void SignUpSuccessListener(){
        assertTrue(mockService.signUp(appContext,true,"email@gmail.com","password"
                ,"1999","male","Hossam Alaa"));
    }

    @Test
    public void SignUpSuccessArtist(){
        assertTrue(mockService.signUp(appContext,false,"email@gmail.com","password"
                ,"1999","male","Hossam Alaa"));
    }

    @Test
    public void ApplyPremiumSuccess(){
        assertTrue(mockService.promotePremium(appContext,new View(appContext),"Token"));
    }

    @Test
    public void getFollowedArtistsSuccess() {
        Constants.currentUser = user;
        assertEquals(5, mockService.getFollowedArtists(false, "token1", 15).size());
    }

    @Test
    public void getFollowedArtistsFail() {
        Constants.currentUser = user;
        assertNotEquals(5, mockService.getFollowedArtists(false, "token1", 3).size());
    }

    @Test
    public void followArtistSuccess() {
        Constants.currentUser = user;
        mockService.followArtistOrUser(false, "token1", "6");
        assertEquals(6, mockService.getFollowedArtists(false, "token1", 20).size());
    }

    @Test
    public void followArtistFail() {
        Constants.currentUser = user;
        mockService.followArtistOrUser(false, "token1", "3");
        assertNotEquals(6, mockService.getFollowedArtists(false, "token1", 20).size());
    }

    @Test
    public void isFollowingSuccess() {
        Constants.currentUser = user;
        assertTrue(mockService.isFollowing(false, "token1", "2"));
    }

    @Test
    public void isFollowingFail() {
        Constants.currentUser = user;
        assertFalse(mockService.isFollowing(false, "token1", "6"));
    }

    @Test
    public void getRecommendedArtistsSuccess() {
        Constants.currentUser = user;
        assertEquals(5, mockService.getRecommendedArtists(true, "token2", 20).size());
    }

    @Test
    public void getRecommendedArtistsFail() {
        Constants.currentUser = user;
        assertNotEquals(20, mockService.getRecommendedArtists(false, "token1", 20).size());
    }

    @Test
    public void getResultsOfSearchFail() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Gamal", "Album.Little Mix", R.drawable.images3));
        testedData.add(new Container("Samir Abo Elnil", "Artist", R.drawable.download));
        testedData.add(new Container("Amr Diab", "Artist", R.drawable.download));
        testedData.add(new Container("Silence Of Lambs", "Album.Little Mix", R.drawable.download1));
        ArrayList<Container> comingData = mockService.getResultsOfSearch(appContext, "Am");
        assertNotEquals(comingData.size(), testedData.size());
    }

    @Test
    public void getResultsOfSearchSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Amr Diab", appContext.getResources().getString(R.string.Artist), R.drawable.amr));
        ArrayList<Container> comingData = mockService.getResultsOfSearch(appContext, "Am");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(), testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getArtistsFail() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Amr Dia", appContext.getResources().getString(R.string.Artist), R.drawable.download));
        ArrayList<Container> comingData = mockService.getArtists(appContext, "Am");
        assertNotEquals(comingData.get(0).getCat_Name(), testedData.get(0).getCat_Name());
    }

    @Test
    public void getArtistsSuccess() {
        String artist = appContext.getResources().getString(R.string.Artist);
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Adele", artist, R.drawable.adele));
        testedData.add(new Container("Amr Diab", artist, R.drawable.amr));
        testedData.add(new Container("Samir Abo Elnil", artist, R.drawable.download));
        ArrayList<Container> comingData = mockService.getArtists(appContext, "A");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(), testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getSongsFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Cat Sound", appContext.getResources().getString(R.string.Song) + ".Eminem juice WRLD", R.drawable.images));
        ArrayList<Container> comingData = mockService.getSongs(appContext, "Th");
        assertNotEquals(comingData.get(0).getCat_Name(), testedData.get(0).getCat_Name());
    }

    @Test
    public void getSongsSuccess() {
        String song = appContext.getResources().getString(R.string.Song);
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Cat Sound", song + ".Eminem juice WRLD", R.drawable.images));
        testedData.add(new Container("ThunderClouds", song + ".Eminem juice WRLD", R.drawable.images));
        ArrayList<Container> comingData = mockService.getSongs(appContext, "C");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(), testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getAlbumsFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Gamal", appContext.getResources().getString(R.string.Album) + ".Little Mix", R.drawable.images3));
        ArrayList<Container> comingData = mockService.getAlbums(appContext, "G");
        assertNotEquals(comingData.size(), testedData.size());
    }

    @Test
    public void getAlbumsSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("The Shadows", appContext.getResources().getString(R.string.Album) + ".Little Mix", R.drawable.download));
        ArrayList<Container> comingData = mockService.getAlbums(appContext, "T");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(), testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getProfilesFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Gamal", appContext.getResources().getString(R.string.Profile) + ".Little Mix", R.drawable.images3));
        ArrayList<Container> comingData = mockService.getProfiles(appContext, "G");
        assertNotEquals(comingData.size(), testedData.size());
    }

    @Test
    public void getProfilesSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        ArrayList<Container> comingData = mockService.getProfiles(appContext, "G");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(), testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getGenresFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Roc", appContext.getResources().getString(R.string.Genre), R.drawable.images3));
        ArrayList<Container> comingData = mockService.getGenresAndMoods(appContext, "R");
        assertNotEquals(testedData.get(0).getCat_Name(), comingData.get(0).getCat_Name());
    }

    @Test
    public void getGenresSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Rock", appContext.getResources().getString(R.string.Genre), R.drawable.images3));
        ArrayList<Container> comingData = mockService.getGenresAndMoods(appContext, "R");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(), testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getPlaylistsFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("quran", appContext.getResources().getString(R.string.Playlist), R.drawable.images));
        ArrayList<Container> comingData = mockService.getPlaylists(appContext, "Q");
        assertNotEquals(comingData.get(0).getCat_Name(), testedData.get(0).getCat_Name());
    }

     @Test
     public void getPlaylistsSuccess() {
         ArrayList<Container> testedData = new ArrayList<>();
         testedData.add(new Container("Quran", "Playlist", R.drawable.images));
         ArrayList<Container> comingData = mockService.getPlaylists(appContext, "Q");
         assertEquals(comingData.size(), testedData.size());
     }

     @Test
     public void getPlaylistsFails() {
         ArrayList<Container> testedData = new ArrayList<>();
         testedData.add(new Container("quran", "Playlist", R.drawable.images2));
         ArrayList<Container> comingData = mockService.getPlaylists(appContext, "Q");
         assertNotEquals(comingData.size(), testedData.size());
     }


    @Test
    public void getPlaylistsSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Quran", appContext.getResources().getString(R.string.Playlist), R.drawable.download));
        ArrayList<Container> comingData = mockService.getPlaylists(appContext, "Q");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(), testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void removeAllRecentSearchesTest() {
        ArrayList<Container> beforeRemoving = mockService.getResentResult(appContext);
        mockService.removeAllRecentSearches(appContext);
        ArrayList<Container> afterRemoving = mockService.getResentResult(appContext);
        assertEquals(afterRemoving.size(), 0);
        assertNotEquals(beforeRemoving.size(), afterRemoving.size());
    }

    @Test
    public void getAllPopularPlaylistsFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        String followers = appContext.getResources().getString(R.string.Followers);
        testedData.add(new Container("Bahaa Sultan", "2,100 " + followers, R.drawable.bahaa));
        testedData.add(new Container("anghami", "1,100 " + followers, R.drawable.angham));
        testedData.add(new Container("Thunder", "2100 " + followers, R.drawable.halsey));
        testedData.add(new Container("selena", "2800 " + followers, R.drawable.selena));
        testedData.add(new Container("Smoke Grenades", "500 " + followers, R.drawable.jannat));
        testedData.add(new Container("Playlist", "26 " + followers, R.drawable.wael));
        testedData.add(new Container("3 d2at", "773 " + followers, R.drawable.abu));
        ArrayList<Container> comingData = mockService.getAllPopularPlaylists(appContext);
        assertNotEquals(testedData.size(), comingData.size());
    }

    @Test
    public void getAllPopularPlaylistsSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        String followers = appContext.getResources().getString(R.string.Followers);
        testedData.add(new Container("greate playlist", "2,700 " + followers, R.drawable.adele));
        testedData.add(new Container("Amr Diab", "5,200 " + followers, R.drawable.amr));
        testedData.add(new Container("beautiful", "3,300 " + followers, R.drawable.imagine));
        testedData.add(new Container("simple", "800 " + followers, R.drawable.alan));
        testedData.add(new Container("nice songs", "1200 " + followers, R.drawable.ed));
        testedData.add(new Container("araby", "3000 " + followers, R.drawable.assala));
        testedData.add(new Container("Bahaa Sultan", "2,100 " + followers, R.drawable.bahaa));
        testedData.add(new Container("anghami", "1,100 " + followers, R.drawable.angham));
        testedData.add(new Container("Thunder", "2100 " + followers, R.drawable.halsey));
        testedData.add(new Container("selena", "2800 " + followers, R.drawable.selena));
        testedData.add(new Container("Smoke Grenades", "500 " + followers, R.drawable.jannat));
        testedData.add(new Container("Playlist", "26 " + followers, R.drawable.wael));
        testedData.add(new Container("3 d2at", "773 " + followers, R.drawable.abu));
        testedData.add(new Container("Oranges", "470 " + followers, R.drawable.hamza));
        ArrayList<Container> comingData = mockService.getAllPopularPlaylists(appContext);
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(), testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getFourPlaylistsFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        String followers = appContext.getResources().getString(R.string.Followers);
        testedData.add(new Container("greate playlist", "2,700 " + followers, R.drawable.adele));
        testedData.add(new Container("Amr Diab", "5,200 " + followers, R.drawable.amr));
        testedData.add(new Container("beutiful", "3,300 " + followers, R.drawable.imagine));
        testedData.add(new Container("simple", "800 " + followers, R.drawable.alan));
        ArrayList<Container> comingData = mockService.getFourPlaylists(appContext);
        assertNotEquals(testedData.get(2).getCat_Name(), comingData.get(2).getCat_Name());
    }

    @Test
    public void getFourPlaylistsSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        String followers = appContext.getResources().getString(R.string.Followers);
        testedData.add(new Container("greate playlist", "2,700 " + followers, R.drawable.adele));
        testedData.add(new Container("Amr Diab", "5,200 " + followers, R.drawable.amr));
        testedData.add(new Container("beautiful", "3,300 " + followers, R.drawable.imagine));
        testedData.add(new Container("simple", "800 " + followers, R.drawable.alan));
        ArrayList<Container> comingData = mockService.getFourPlaylists(appContext);
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res(), testedData.get(i).getImg_Res());
        }
    }

    @Test
    public void getRecentPlaylistsTest() {
        ArrayList<Playlist> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "OneRepublic", "mood booster", "Rescue Me", R.drawable.rescue_me, null));
        tracks.add(new Track("Freaking Me Out", "Ava Max", "mood booster", null, R.drawable.freaking_me_out, null));
        tracks.add(new Track("You Can't Stop The Girl", "Bebe Rexha", "mood booster", null, R.drawable.you_cant_stop_the_girl, null));
        testPlaylists.add(new Playlist("mood booster", "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks));
        ArrayList<Playlist> comingData = mockService.getPopularPlaylists(appContext, Constants.currentToken);
        assertEquals(testPlaylists.size(), comingData.size());
        for (int i = 0; i < testPlaylists.size(); i++) {
            assertEquals(testPlaylists.get(i), comingData.get(i));
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            for (int j = 0; j < testPlaylists.get(i).getTracks().size(); j++) {
                assertEquals(testPlaylists.get(i).getTracks().get(j), comingData.get(i).getTracks().get(j));
            }
        }
    }

    @Test
    public void getRecentPlaylistsFails() {
        ArrayList<Playlist> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "OneRepublic", "mood booster", "Rescue Me", R.drawable.rescue_me, null));
        testPlaylists.add(new Playlist("mood booster", "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks));
        ArrayList<Playlist> comingData = mockService.getPopularPlaylists(appContext, Constants.currentToken);
        assertNotEquals(testPlaylists.size(), comingData.size());
    }

    @Test
    public void getRandomPlaylists() {
        ArrayList<Playlist> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Intentions", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions, null));
        tracks.add(new Track("Stupid Love", "Lady Gaga", "Daily Left", null, R.drawable.stupid_love, null));
        tracks.add(new Track("Feel Me", "Selena Gomez", "Daily Left", null, R.drawable.feel_me, null));
        testPlaylists.add(new Playlist("Daily Left", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.images), tracks));
        ArrayList<Playlist> comingData = mockService.getRandomPlaylists(appContext, Constants.currentToken);
        assertEquals(testPlaylists.size(), comingData.size());
        for (int i = 0; i < testPlaylists.size(); i++) {
            assertEquals(testPlaylists.get(i), comingData.get(i));
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            for (int j = 0; j < testPlaylists.get(i).getTracks().size(); j++) {
                assertEquals(testPlaylists.get(i).getTracks().get(j), comingData.get(i).getTracks().get(j));
            }
        }
    }

    @Test
    public void getRandomPlaylistsFails() {
        ArrayList<Playlist> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Intentions", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions, null));
        testPlaylists.add(new Playlist("Daily Left", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.images), tracks));
        ArrayList<Playlist> comingData = mockService.getRandomPlaylists(appContext, Constants.currentToken);
        assertNotEquals(testPlaylists.size(), comingData.size());
    }

    @Test
    public void getMadeForYouPlaylists() {
        ArrayList<Playlist> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Intentions", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions, null));
        tracks.add(new Track("Stupid Love", "Lady Gaga", "Daily Left", null, R.drawable.stupid_love, null));
        tracks.add(new Track("Feel Me", "Selena Gomez", "Daily Left", null, R.drawable.feel_me, null));
        testPlaylists.add(new Playlist("Daily Left", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.images), tracks));
        ArrayList<Playlist> comingData = mockService.getMadeForYouPlaylists(appContext, Constants.currentToken);
        assertEquals(testPlaylists.size(), comingData.size());
        for (int i = 0; i < testPlaylists.size(); i++) {
            assertEquals(testPlaylists.get(i), comingData.get(i));
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            for (int j = 0; j < testPlaylists.get(i).getTracks().size(); j++) {
                assertEquals(testPlaylists.get(i).getTracks().get(j), comingData.get(i).getTracks().get(j));
            }
        }
    }

    @Test
    public void getMadeForYouPlaylistsFails() {
        ArrayList<Playlist> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Intentions", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions, null));
        testPlaylists.add(new Playlist("Daily Left", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.images), tracks));
        ArrayList<Playlist> comingData = mockService.getMadeForYouPlaylists(appContext, Constants.currentToken);
        assertNotEquals(testPlaylists.size(), comingData.size());
    }

    @Test
    public void getRecentlyPlayedPlaylists() {
        ArrayList<Playlist> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Little Do You Know", "Alex & Sierra", "Rewind-the sound of 2014", null, R.drawable.rescue_me, null));
        tracks.add(new Track("Wildest Dreams", "Taylor Swift", "Rewind-the sound of 2014", null, R.drawable.freaking_me_out, null));
        tracks.add(new Track("One Last Time", "Ariana Grande", "Rewind-the sound of 2014", null, R.drawable.you_cant_stop_the_girl, null));
        testPlaylists.add(new Playlist("Rewind-the sound of 2014", null,
                Utils.convertToBitmap(R.drawable.rewind_the_sound), tracks));
        ArrayList<Playlist> comingData = mockService.getRecentPlaylists(appContext, null);
        assertEquals(testPlaylists.size(), comingData.size());
        for (int i = 0; i < testPlaylists.size(); i++) {
            assertEquals(testPlaylists.get(i), comingData.get(i));
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            for (int j = 0; j < testPlaylists.get(i).getTracks().size(); j++) {
                assertEquals(testPlaylists.get(i).getTracks().get(j), comingData.get(i).getTracks().get(j));
            }
        }
    }

    @Test
    public void getRecentlyPlayedPlaylistsFails() {
        ArrayList<Playlist> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Little Do You Know", "Alex & Sierra", "Rewind-the sound of 2014", null, R.drawable.rescue_me, null));
        testPlaylists.add(new Playlist("Rewind-the sound of 2014", null,
                Utils.convertToBitmap(R.drawable.rewind_the_sound), tracks));
        ArrayList<Playlist> comingData = mockService.getRecentPlaylists(appContext, null);
        assertNotEquals(testPlaylists.size(), comingData.size());

    }

    @Test
    public void isOnlineTest() {
        MainActivity activity = new MainActivity();
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            assertEquals(networkInfo != null && networkInfo.isConnectedOrConnecting(), activity.isOnline());
            ;
        }
    }

    @Test
    public void isOnlineTestFails() {
        MainActivity activity = new MainActivity();
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            assertNotEquals(networkInfo != null && networkInfo.isConnectedOrConnecting(), activity.isOnline());
            ;
        }
    }


    @Test
    public void getPopularPlaylists() {
        ArrayList<Playlist> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "OneRepublic", "mood booster", "Rescue Me", R.drawable.rescue_me, null));
        tracks.add(new Track("Freaking Me Out", "Ava Max", "mood booster", null, R.drawable.freaking_me_out, null));
        tracks.add(new Track("You Can't Stop The Girl", "Bebe Rexha", "mood booster", null, R.drawable.you_cant_stop_the_girl, null));
        testPlaylists.add(new Playlist("mood booster", "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks));
        ArrayList<Playlist> comingData = mockService.getRecentPlaylists(appContext,null);
        assertEquals(testPlaylists.size(), comingData.size());
        for (int i = 0; i < testPlaylists.size(); i++) {
            assertEquals(testPlaylists.get(i), comingData.get(i));
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            for (int j = 0; j < testPlaylists.get(i).getTracks().size(); j++) {
                assertEquals(testPlaylists.get(i).getTracks().get(j), comingData.get(i).getTracks().get(j));
            }
        }
    }

    @Test
    public void getPopularPlaylistsFails() {
        ArrayList<Playlist> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "OneRepublic", "mood booster", "Rescue Me", R.drawable.rescue_me, null));
        testPlaylists.add(new Playlist("mood booster", "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks));
        ArrayList<Playlist> comingData = mockService.getRecentPlaylists(appContext,null);
        assertNotEquals(testPlaylists.size(), comingData.size());

    }

}





