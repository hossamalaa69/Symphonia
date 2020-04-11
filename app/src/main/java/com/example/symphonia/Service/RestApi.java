package com.example.symphonia.Service;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Category;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Profile;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Entities.User;
import com.example.symphonia.Fragments_and_models.home.HomeFragment;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;
import com.example.symphonia.Fragments_and_models.profile.BottomSheetDialogProfile;
import com.example.symphonia.Fragments_and_models.profile.FragmentProfile;
import com.example.symphonia.Fragments_and_models.profile.ProfileFollowersFragment;
import com.example.symphonia.Fragments_and_models.profile.ProfilePlaylistsFragment;
import com.example.symphonia.Fragments_and_models.settings.SettingsFragment;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Class that holds all functions to be used to fill metadata of application
 * using REST APIs implementation
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 11-4-2020
 */

public class RestApi implements APIs {

    /**
     * holds logging user in, creation of user object and sets token
     *
     * @param context  holds context of activity that called this method
     * @param username email or username of user
     * @param password password of user
     * @param mType    type of user, true for listener and false for artist
     * @return return true if data is matched
     */
    @Override
    public boolean logIn(final Context context, final String username, final String password, final boolean mType) {
        final updateUiLogin updateLogin = (updateUiLogin) context;
        StringRequest stringrequest = new StringRequest(Request.Method.POST, (Constants.LOG_IN_URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject root = new JSONObject(response);
                            Constants.currentToken = root.getString("token");
                            JSONObject user = root.getJSONObject("user");
                            String id = user.getString("_id");
                            String name = user.getString("name");
                            String type = user.getString("type");
                            String image = user.getString("imageUrl");
                            boolean premium = false;
                            if (type.equals("premium-user")) {
                                premium = true;
                                type = "user";
                            } else if (type.equals("artist")) {
                                premium = true;
                            }

                            if ((type.equals("user") && !mType) || (type.equals("artist") && mType))
                                updateLogin.updateUiLoginFail("type");
                            else {
                                Constants.currentUser = new User(username, id, name, type.equals("user"), premium);
                                Constants.currentUser.setUserType(type);
                                Constants.currentUser.setImageUrl(image);

                                updateLogin.updateUiLoginSuccess();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show();
                            updateLogin.updateUiLoginFail("exception");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            if (error.networkResponse.statusCode == 401)
                                updateLogin.updateUiLoginFail("input");
                            else {
                                Toast.makeText(context, "Error" + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show();
                                updateLogin.updateUiLoginFail("exception");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show();
                            updateLogin.updateUiLoginFail("exception");
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(stringrequest);
        return true;
    }


    public interface updateUiLogin {
        void updateUiLoginSuccess();

        void updateUiLoginFail(String reason);
    }

    /**
     * checks if email is already signed in database or not
     *
     * @param context holds context of activity that called this method
     * @param email   email of user
     * @param mType   type of user, true for listener and false for artist
     * @return returns true if email is new, false if it's signed before
     */
    @Override
    public boolean checkEmailAvailability(final Context context, final String email, final boolean mType) {
        final updateUiEmailValidity updateUiEmailValidity = (updateUiEmailValidity) context;
        StringRequest stringrequest = new StringRequest(Request.Method.POST, (Constants.EMAIL_EXISTS_URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject root = new JSONObject(response);
                            boolean exists = root.getBoolean("exists");
                            if (!exists) {
                                updateUiEmailValidity.updateUiEmailValiditySuccess();
                                return;
                            }
                            String type = root.getString("type");
                            updateUiEmailValidity.updateUiEmailValidityFail(type);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(stringrequest);
        return true;
    }

    public interface updateUiEmailValidity {
        void updateUiEmailValiditySuccess();

        void updateUiEmailValidityFail(String type);
    }

    /**
     * handles that user is signing up, initializes new user object
     * fill database with new user
     *
     * @param context  holds context of activity that called this method
     * @param mType    type of user, true for listener and false for artist
     * @param email    email of user
     * @param password password of user
     * @param DOB      date of birth of user
     * @param gender   gender of user
     * @param name     name of user
     * @return returns true if sign up is done
     */
    @Override
    public boolean signUp(final Context context, final boolean mType, final String email, final String password
            , final String DOB, final String gender, final String name) {
        final updateUiSignUp updateUiSignUp = (updateUiSignUp) context;
        StringRequest stringrequest = new StringRequest(Request.Method.POST, (Constants.SIGN_UP_URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject root = new JSONObject(response);
                            Constants.currentToken = root.getString("token");
                            JSONObject user = root.getJSONObject("user");
                            String id = user.getString("_id");
                            String image = user.getString("imageUrl");
                            String type = user.getString("type");
                            Constants.currentUser = new User(email, id, mType, Utils.convertToBitmap(R.drawable.img_init_profile)
                                    , name, DOB, gender, type.equals("artist")
                                    , 0, 0, new ArrayList<User>(), new ArrayList<User>()
                                    , new ArrayList<Playlist>(), new ArrayList<Playlist>()
                                    , new ArrayList<Artist>(), new ArrayList<Album>(), new ArrayList<Track>());

                            Constants.currentUser.setUserType(type);
                            Constants.currentUser.setImageUrl(image);
                            Toast.makeText(context, "Signed up successfully", Toast.LENGTH_SHORT).show();
                            updateUiSignUp.updateUiSignUpSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show();
                            updateUiSignUp.updateUiSignUpFailed();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Toast.makeText(context, "Error: " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        }
                        updateUiSignUp.updateUiSignUpFailed();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("emailConfirm", email);
                params.put("password", password);
                params.put("dateOfBirth", DOB);
                params.put("gender", gender);
                if (mType)
                    params.put("type", "user");
                else
                    params.put("type", "artist");
                return params;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(stringrequest);
        return true;
    }

    public interface updateUiSignUp {
        void updateUiSignUpSuccess();

        void updateUiSignUpFailed();
    }


    /**
     * this interface includes listeners to update ui
     */
    public interface updateUiPlaylists {
        void getCategoriesSuccess();

        void updateUiNoTracks();

        void updateUiGetTracksOfPlaylist(PlaylistFragment playlistFragment);

        void updateUiGetPopularPlaylistsSuccess();

        void updateUiGetPopularPlaylistsFail();

        void updateUiGetRandomPlaylistsSuccess(HomeFragment homeFragment);

        void updateUiGetRandomPlaylistsFail();

        void updateUiGetRecentPlaylistsSuccess(HomeFragment homeFragment);

        void updateUiGetRecentPlaylistsFail();

        void updateUiGetMadeForYouPlaylistsSuccess();

        void updateUiGetMadeForYouPlaylistsFail();

        void updateUiPlayTrack();
    }

    /**
     * getter for popular playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return popular  playlist
     */
    @Override
    public ArrayList<Playlist> getPopularPlaylists(final Context context, String mToken) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final ArrayList<Playlist> popularPlaylists = new ArrayList<>();
        //TODO  backend still under working
        StringRequest request = new StringRequest(Request.Method.GET, Utils.categories.get(0).getCat_Name(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONObject playlistsObj = root.getJSONObject("playlists");
                    JSONArray playlists = playlistsObj.getJSONArray("items");
                    for (int i = 0; i < playlists.length(); i++) {
                        JSONObject playlist = playlists.getJSONObject(i);
                        String title = playlist.getString("name");
                        String decs = playlist.getString("description");
                        JSONArray images = playlist.getJSONArray("images");
                        JSONObject image = images.getJSONObject(0);
                        String imageUrl = image.getString("url");
                        String id = playlist.getString("_id");
                        JSONObject tracks = playlist.getJSONObject("tracks");
                        String tracksUrl = tracks.getString("href");
                        popularPlaylists.add(new Playlist(title, id, decs, imageUrl, null, tracksUrl));
                        listener.updateUiGetPopularPlaylistsSuccess();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.currentToken);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("limit", "10");
                return params;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return popularPlaylists;
    }

    private Bitmap image;

    private Bitmap fetchImage(final Context context, String url) {
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        image = bitmap;
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "avdvadv", Toast.LENGTH_SHORT);
                    }
                });
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return image;
    }

    /**
     * getter for made-for-you playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return made-for-you  playlist
     */
    @Override
    public ArrayList<Playlist> getMadeForYouPlaylists(final Context context, String mToken) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final ArrayList<Playlist> madeForYouPlaylists = new ArrayList<>();
        //TODO  backend still under working
        StringRequest request = new StringRequest(Request.Method.GET, Utils.categories.get(0).getCat_Name(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONObject playlistsObj = root.getJSONObject("playlists");
                    JSONArray playlists = playlistsObj.getJSONArray("items");
                    for (int i = 0; i < playlists.length(); i++) {
                        JSONObject playlist = playlists.getJSONObject(i);
                        String title = playlist.getString("name");
                        String decs = playlist.getString("description");
                        JSONArray images = playlist.getJSONArray("images");
                        JSONObject image = images.getJSONObject(0);
                        String imageUrl = image.getString("url");
                        String id = playlist.getString("_id");
                        JSONObject tracks = playlist.getJSONObject("tracks");
                        String tracksUrl = tracks.getString("href");
                        madeForYouPlaylists.add(new Playlist(title, id, decs, imageUrl, null, tracksUrl));
                        listener.updateUiGetMadeForYouPlaylistsSuccess();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.currentToken);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("limit", "10");
                return params;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return madeForYouPlaylists;
    }


    /**
     * getter for recently-player playlist
     *
     * @param context  context of hosting activity
     * @param fragment token of user
     * @return recently-player  playlist
     */
    @Override
    public ArrayList<Playlist> getRecentPlaylists(final Context context, final HomeFragment fragment) {
        Log.e("recent", "start");
        final ArrayList<Playlist> recentPlaylists = new ArrayList<>();
        //TODO  backend still under working
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_RECENT_PLAYLISTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("recent", "respond");
                    Utils.LoadedPlaylists.recentPlaylists = new ArrayList<>();
                    updateUiPlaylists listener = (updateUiPlaylists) context;
                    JSONObject root = new JSONObject(response);
                    JSONObject playlistsObj = root.getJSONObject("history");
                    JSONArray playlists = playlistsObj.getJSONArray("items");
                    for (int i = 0; i < playlists.length(); i++) {
                        JSONObject playlist = playlists.getJSONObject(i);
                        JSONObject context = playlist.optJSONObject("context");
                        if (context != null) {
                            String title = context.getString("name");
                            String decs = context.optString("description");
                            JSONArray images = context.getJSONArray("images");
                            String imageUrl = (String) images.get(0);
                            String id = context.getString("_id");
                            Utils.LoadedPlaylists.recentPlaylists.add(new Playlist(title, id, decs, imageUrl,
                                    null, Constants.BASE_URL + Constants.GET_PLAYLISTS_TRACKS + id + "/tracks"));
                        }
                    }
                    Log.e("recent", "success");
                    listener.updateUiGetRecentPlaylistsSuccess(fragment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("recent", "error");

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("limit", "10");
                return params;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return recentPlaylists;
    }


    /**
     * getter for random playlist
     *
     * @param context      context of hosting activity
     * @param homeFragment token of user
     * @return random  playlist
     */
    @Override
    public ArrayList<Playlist> getRandomPlaylists(final Context context, final HomeFragment homeFragment) {
        Log.e("rand", "start");

        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final ArrayList<Playlist> randomPlaylists = new ArrayList<>();
        //TODO  backend still under working
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_RANDOM_PLAYLISTS.concat("number=10"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("rand", "respond");
                    Utils.LoadedPlaylists.randomPlaylists = new ArrayList<>();
                    JSONArray playlists = new JSONArray(response);
                    for (int i = 0; i < playlists.length(); i++) {
                        JSONObject playlist = playlists.getJSONObject(i);
                        String title = playlist.getString("name");
                        String id = playlist.getString("_id");
                        String decs = playlist.optString("description");
                        JSONArray images = playlist.getJSONArray("images");
                        String imageUrl = (String) images.get(0);
                        Utils.LoadedPlaylists.randomPlaylists.add(new Playlist(title, id, "", imageUrl,
                                null, Constants.BASE_URL + Constants.GET_PLAYLISTS_TRACKS + id + "/tracks"));
                    }
                    Log.e("rand", "success");
                    listener.updateUiGetRandomPlaylistsSuccess(homeFragment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("rand", "error");


            }
        });
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return randomPlaylists;
    }

    /**
     * this function initialize the track to be streamed
     *
     * @param context      current activity's context
     * @param id           id of track
     * @param context_id   id of context
     * @param context_url  url of context
     * @param context_type type of context
     */
    @Override
    public void playTrack(final Context context, String id, String context_id, String context_url, String context_type) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;

        final StringRequest request = new StringRequest(Request.Method.GET, Constants.PLAY_TRACK.concat("5e8a1e0f7937ec4d40c6deba")
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.CurrTrackInfo.currPlaylistTracks.get(Utils.CurrTrackInfo.TrackPosInPlaylist).setUri(Uri.parse(response));
                Utils.CurrTrackInfo.loading = false;
                listener.updateUiPlayTrack();
                Log.e("play", "success");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("play", "error");
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                //   headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "{\"contextId\": \"5e701fdf2672a63a60573a06\",\"context_type\": \"album\",\"context_url\": \"https://thesymphonia.ddns.net/\",\"device\": \"android\"}";
            }
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    /**
     * thif function load tracks of certain playlist
     *
     * @param context          context of activity
     * @param id               id of playlist
     * @param playlistFragment fragment for response update
     * @return array list of tracks
     */
    @Override
    public ArrayList<Track> getTracksOfPlaylist(Context context, String id, final PlaylistFragment playlistFragment) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final ArrayList<Track> tracksList = new ArrayList<>();
        final StringRequest request = new StringRequest(Request.Method.GET
                , Constants.GET_PLAYLISTS_TRACKS.concat(id).concat("/tracks"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("tracks", "respond");
                    Utils.LoadedPlaylists.randomPlaylists = new ArrayList<>();
                    JSONArray root = new JSONArray(response);
                    JSONObject firstElement = root.getJSONObject(0);
                    JSONArray tracks = firstElement.getJSONArray("tracks");
                    for (int i = 0; i < tracks.length(); i++) {
                        JSONObject track = tracks.getJSONObject(i);
                        String title = track.getString("name");
                        String id = track.getString("_id");
                        JSONObject album = track.getJSONObject("album");
                        String imageUrl = album.getString("image");
                        JSONObject artist = track.getJSONObject("artist");
                        String artistName = artist.getString("name");
                        int duration = track.getInt("durationMs");
                        String premium = track.getString("premium");
                        tracksList.add(new Track(title, artistName, Utils.CurrPlaylist.playlist.getmPlaylistTitle()
                                , id, (premium.matches("true")), R.drawable.no_image, duration, imageUrl));
                    }
                    Utils.CurrPlaylist.playlist.setTracks(tracksList);
                    Log.e("tracks", "success");
                    listener.updateUiGetTracksOfPlaylist(playlistFragment);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.updateUiNoTracks();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tracks", "error");


            }
        });
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return tracksList;
    }

    @Override
    public ArrayList<Container> getResentResult(Context context) {
        return null;
    }

    @Override
    public ArrayList<Container> getResultsOfSearch(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Category> getCategories(Context context) {
        Log.e("Category", "start fetching");
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final ArrayList<Category> categoriesList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_ALL_CATEGORIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONObject data = root.getJSONObject("data");
                    JSONArray categories = data.getJSONArray("categorys");
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject category = categories.getJSONObject(i);
                        String link = category.getString("href");
                        categoriesList.add(new Category(link));
                    }
                    Log.e("Category", "loaded");
                    Utils.categories = categoriesList;
                    listener.getCategoriesSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Category", "exception");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Category", "" + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("limit", "10");
                return params;
            }
        };
        Log.e("Category", "add request");
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return categoriesList;
    }


    /**
     * get genres for the current user
     *
     * @param context activity context
     * @return arraylist of container which has the genres
     */
    @Override
    public ArrayList<Category> getGenres(Context context) {
        return null;
    }

   /*
    public interface updateUigetGenres {
>>>>>>> 598a3dc644c95c2e4a950f5d68f49279662ee492
        void updateUigetGenresSuccess();

        void updateUigetGenresFail();
    }*/


    @Override
    public ArrayList<Container> getArtists(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Container> getSongs(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Container> getAlbums(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Container> getGenresAndMoods(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Container> getPlaylists(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Container> getProfiles(Context context, String searchWord) {
        return null;
    }

    @Override
    public void removeOneRecentSearch(Context context, int position) {

    }

    @Override
    public void removeAllRecentSearches(Context context) {

    }

    @Override
    public ArrayList<Container> getAllPopularPlaylists(Context context) {
        return null;
    }

    @Override
    public ArrayList<Container> getFourPlaylists(Context context) {
        ArrayList<Container> paylists = new ArrayList<>();
        return paylists;
    }


    @Override
    public Artist getArtist(Context context, String id) {
        return null;
    }

    @Override
    public ArrayList<Album> getUserSavedAlbums(Context context, int offset, int limit) {
        return null;
    }

    @Override
    public ArrayList<Artist> getFollowedArtists(Context context, String type, int limit, String after) {
        return null;
    }

    @Override
    public void followArtistsOrUsers(Context context, final String type, final ArrayList<String> ids) {

        Uri.Builder builder = Uri.parse(Constants.FOLLOW_ARTIST_URL).buildUpon();
        builder.appendQueryParameter("type", type);
        builder.appendQueryParameter("ids", TextUtils.join(",", ids));

        StringRequest request = new StringRequest(Request.Method.PUT, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("TAG", "onResponse: ");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("recent", "error");
            }
        }) {
/*            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("type", type);
                params.put("ids", TextUtils.join(",", ids));
                return params;
            }*/

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);

    }

    @Override
    public void unFollowArtistsOrUsers(final Context context, final String type, final ArrayList<String> ids) {

/*        Uri.Builder builder = Uri.parse(Constants.FOLLOW_ARTIST_URL).buildUpon();
        builder.appendQueryParameter("type", type);
        builder.appendQueryParameter("ids", TextUtils.join(",", ids));

        StringRequest request = new StringRequest(Request.Method.DELETE, Constants.FOLLOW_ARTIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("type", type);
                params.put("ids", TextUtils.join(",", ids));
                return params;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + Constants.currentToken);
        headers.put("Content-Type", "application/json");

        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("ids", TextUtils.join(",", ids));

        Call<Void> call = retrofitApi.unFollowArtists(headers, params);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull retrofit2.Response<Void> response) {
                //Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public ArrayList<Boolean> isFollowing(Context context, String type, ArrayList<String> ids) {
        return null;
    }

    @Override
    public ArrayList<Artist> getRecommendedArtists(final Context context, String type, final int offset, final int limit) {

        final UpdateAddArtists listener = (UpdateAddArtists) context;
        final ArrayList<Artist> recommendedArtists = new ArrayList<>();

        Uri.Builder builder = Uri.parse(Constants.GET_RECOMMENDED_ARTISTS).buildUpon();
        builder.appendQueryParameter("limit", String.valueOf(limit));
        builder.appendQueryParameter("offset", String.valueOf(offset));

        StringRequest request = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONObject artists = root.getJSONObject("artists");
                    JSONArray artistArray = artists.getJSONArray("items");
                    for (int i = 0; i < artistArray.length(); i++) {
                        JSONObject artist = artistArray.getJSONObject(i);
                        String id = artist.getString("_id");
                        String name = artist.getString("name");
                        /*JSONArray images = artist.getJSONArray("images");
                        JSONObject image = images.getJSONObject(1);*/
                        String imageUrl = artist.getString("imageUrl");
                        recommendedArtists.add(new Artist(id, imageUrl, name));
                    }
                    listener.updateGetRecommendedArtists(recommendedArtists);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return recommendedArtists;
    }

    public interface UpdateAddArtists {
        void updateGetRecommendedArtists(ArrayList<Artist> returnedArtists);
    }


    /**
     * Get information about artists similar to a given artist.
     *
     * @param context activity context
     * @param id      artist id
     * @return Arraylist of similar artists
     */
    @Override
    public ArrayList<Artist> getArtistRelatedArtists(Context context, String id) {
        return null;
    }

    /**
     * Search for a specific artist
     *
     * @param context Activity context
     * @param q       Query to search for
     * @param offset  The index of the first result to return
     * @param limit   Maximum number of results to return
     * @return List of search result artists
     */
    @Override
    public ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit) {
        return null;
    }

    @Override
    public Album getAlbum(Context context, String id) {
        return null;
    }

    @Override
    public ArrayList<Track> getAlbumTracks(Context context, String id, int offset, int limit) {
        return null;
    }

    @Override
    public void saveAlbumsForUser(Context context, ArrayList<String> ids) {

    }

    @Override
    public void removeAlbumsForUser(Context context, ArrayList<String> ids) {

    }

    @Override
    public ArrayList<Boolean> checkUserSavedAlbums(Context context, ArrayList<String> ids) {
        return null;
    }

    /**
     * handles promoting user to premium
     *
     * @param context holds context of activity
     * @param root    holds root view of fragment
     * @param token   holds token of user
     * @return returns true if promoted
     */
    @Override
    public boolean promotePremium(final Context context, View root, String token) {
        return false;
    }

    @Override
    public ArrayList<Container> getProfileFollowers(Context context) {
        return null;
    }

    @Override
    public ArrayList<Container> getProfileFollowing(Context context) {
        return null;
    }

    @Override
    public Profile getCurrentUserProfile(final Context context, final SettingsFragment settingsFragment) {
        final updateUiProfileInSetting listener = (updateUiProfileInSetting) context;
        final Profile[] profile = new Profile[1];
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Get_Current_User_Profile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String name = jsonObject.getString("name");
                            String imgUrl = jsonObject.getString("imageUrl");
                            profile[0] = new Profile(name, imgUrl);
                            listener.getCurrentProfile(profile[0], settingsFragment);
                        } catch (Exception e) {
                            e.fillInStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Profile", "" + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }

           /* @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", "exampleuser01");
                return params;
            }*/
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
        return profile[0];
    }

    @Override
    public ArrayList<Container> getCurrentUserPlaylists(final Context context, final FragmentProfile fragmentProfile) {
        final updateUiProfileInProfileFragment listener = (updateUiProfileInProfileFragment) context;
        final ArrayList<Container> playlists = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Get_Current_User_Profile_playlists
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray playlistsArr = jsonObject.getJSONArray("ownedPlaylists");
                    for (int i = 0; i < playlistsArr.length(); i++) {
                        JSONObject playlist = playlistsArr.getJSONObject(i);
                        String name = playlist.getString("name");
                        String id = playlist.getString("id");
                        JSONArray imageArr = playlist.getJSONArray("images");
                        String imageUrl = imageArr.getString(0);
                        //String imageUrl=imageUrlObj.toString();
                        JSONArray followers = playlist.getJSONArray("followers");
                        playlists.add(new Container(name, imageUrl, followers));
                    }
                    listener.getCurrentProfilePlaylists(playlists, fragmentProfile);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Profile", "" + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }
        };


        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
        return playlists;

    }

    /*private Container getPlaylist(Context context,String s){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Constants.Get_playlist+"/"+s
                ,new Response.Listener<String>() {
||||||| 0fb18ee
    private Container getPlaylist(Context context,String s){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Constants.Get_playlist+"/"+s
                ,new Response.Listener<String>() {
=======
    private Container getPlaylist(Context context, String s) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Get_playlist + "/" + s
                , new Response.Listener<String>() {
>>>>>>> cec5f31be5ee509f21ce7e00c479917d08c0bf77
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Profile", "" + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }
        };


        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
        Container container = new Container("ava", Utils.convertToBitmap(R.drawable.amr));
        return container;

    }*/


    @Override
    public ArrayList<Container> getCurrentUserFollowing(final Context context, final ProfileFollowersFragment profileFollowersFragment) {
        final updateProfileFollow listener = (updateProfileFollow) context;
        final ArrayList<Container> following = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Get_User_Following
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject artists = jsonObject.getJSONObject("artists");
                    JSONArray jsonArray = artists.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String id = item.getString("_id");
                        String name = item.getString("name");
                        String imageUrl = item.getString("imageUrl");
                        JSONArray followArtist = item.getJSONArray("followedUsers");
                        following.add(new Container(name, imageUrl, followArtist));
                    }
                    listener.getUserFollowing(following, profileFollowersFragment);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Profile", "" + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }
        };


        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
        return following;
    }

    @Override
    public ArrayList<Container> getCurrentUserFollowers(Context context, final ProfileFollowersFragment profileFollowersFragment) {
        final updateProfileFollow listener = (updateProfileFollow) context;
        final ArrayList<Container> followers = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.BASE_URL + "api/v1/Artists/" + Constants.currentUser.get_id() + "/followers"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("followers");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject follower = jsonArray.getJSONObject(i);
                        String id = follower.getString("_id");
                        String name = follower.getString("name");
                        String imgUrl = follower.getString("imageUrl");
                        JSONArray artistFollowers = follower.getJSONArray("followedUsers");
                        followers.add(new Container(name, imgUrl, artistFollowers));
                    }
                    listener.getUserFollowers(followers, profileFollowersFragment);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Profile", "" + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }
        };


        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
        return followers;

    }

    @Override
    public String getNumbersoUserFollowers(Context context, final FragmentProfile fragmentProfile) {
        final updateUiProfileInProfileFragment listener = (updateUiProfileInProfileFragment) context;
        final String[] count = new String[1];
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.BASE_URL + "api/v1/Artists/" + Constants.currentUser.get_id() + "/followers"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    count[0] = String.valueOf(jsonObject.getInt("followers_count"));
                    listener.getCurrentUserFollowersNumber(count[0], fragmentProfile);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Profile", "" + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }
        };


        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
        return count[0];
    }

    @Override
    public String getNumbersoUserFollowing(Context context, final FragmentProfile fragmentProfile) {
        final updateUiProfileInProfileFragment listener = (updateUiProfileInProfileFragment) context;
        final String[] count = new String[1];
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Get_User_Following
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    count[0] = String.valueOf(jsonObject.getInt("totalFollowed"));
                    listener.getCurrentUserFollowingNumber(count[0], fragmentProfile);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Profile", "" + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }
        };


        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
        return count[0];
    }

    @Override
    public String getNumberofUserPlaylists(Context context, FragmentProfile fragmentProfile) {
        return "";
    }

    @Override
    public ArrayList<Container> getAllCurrentUserPlaylists(Context context, final ProfilePlaylistsFragment profilePlaylistsFragment) {
        final updateProfilePlaylists listener = (updateProfilePlaylists) context;
        final ArrayList<Container> playlists = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Get_Current_User_Profile_playlists
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray playlistsArr = jsonObject.getJSONArray("ownedPlaylists");
                    for (int i = 0; i < playlistsArr.length(); i++) {
                        JSONObject playlist = playlistsArr.getJSONObject(i);
                        String name = playlist.getString("name");
                        String id = playlist.getString("id");
                        JSONArray imageArr = playlist.getJSONArray("images");
                        String imageUrl = imageArr.getString(0);
                        //String imageUrl=imageUrlObj.toString();
                        JSONArray followers = playlist.getJSONArray("followers");
                        playlists.add(new Container(name, imageUrl, followers));
                    }
                    listener.getAllUserPlaylists(playlists, profilePlaylistsFragment);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Profile", "" + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }
        };


        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
        return playlists;

    }

    @Override
    public void followPlaylist(Context context, BottomSheetDialogProfile bottomSheetDialogProfile) {

    }


    @Override
    public int getArtistsCount() {
        return 0;
    }

    @Override
    public int getProfilessCount() {
        return 0;
    }

    @Override
    public int getPlaylistsCount() {
        return 0;
    }

    @Override
    public int getGenresCount() {
        return 0;
    }

    @Override
    public int getSongsCount() {
        return 0;
    }

    @Override
    public int getAlbumsCount() {
        return 0;
    }


    public interface updateUiProfileInProfileFragment {
        public void getCurrentProfilePlaylists(ArrayList<Container> playlists, FragmentProfile fragmentProfile);

        public void getCurrentUserFollowingNumber(String f, FragmentProfile fragmentProfile);

        public void getCurrentUserFollowersNumber(String s, FragmentProfile fragmentProfile);
    }

    public interface updateUiProfileInSetting {
        public void getCurrentProfile(Profile profile, SettingsFragment settingsFragment);
    }

    public interface updateProfilePlaylists {
        public void getAllUserPlaylists(ArrayList<Container> p, ProfilePlaylistsFragment profilePlaylistsFragment);
    }

    public interface updateProfileFollow {
        public void getUserFollowers(ArrayList<Container> f, ProfileFollowersFragment profileFollowersFragment);

        public void getUserFollowing(ArrayList<Container> f, ProfileFollowersFragment profileFollowersFragment);

    }

    public interface updateAfterActions {

    }

}