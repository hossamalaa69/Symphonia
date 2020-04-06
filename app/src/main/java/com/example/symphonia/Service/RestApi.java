package com.example.symphonia.Service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.example.symphonia.Fragments_and_models.settings.SettingsFragment;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestApi implements APIs {
    /**
     * holds logging user in, creation of user object and sets token
     *
     * @param context  holds context of activity that called this method
     * @param username email or username of user
     * @param password password of userhaledali
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
                        updateLogin.updateUiLoginFail("input");
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
    public boolean signUp(final Context context, boolean mType, String email, String password, String DOB, String gender, String name) {
        return false;
    }

    public interface updateUiSignUp {
        void updateUiSignUpSuccess();
    }


    public interface updateUiPlaylists {
        void getCategoriesSuccess();

        void updateUiGetPopularPlaylistsSuccess();

        void updateUiGetPopularPlaylistsFail();

        void updateUiGetRandomPlaylistsSuccess();

        void updateUiGetRandomPlaylistsFail();

        void updateUiGetRecentPlaylistsSuccess();

        void updateUiGetRecentPlaylistsFail();

        void updateUiGetMadeForYouPlaylistsSuccess();

        void updateUiGetMadeForYouPlaylistsFail();
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
                        Bitmap playlistImage = fetchImage(context, imageUrl);
                        JSONObject tracks = playlist.getJSONObject("tracks");
                        String tracksUrl = tracks.getString("href");
                        popularPlaylists.add(new Playlist(title, decs, playlistImage, null, tracksUrl));
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
                        Bitmap playlistImage = fetchImage(context, imageUrl);
                        JSONObject tracks = playlist.getJSONObject("tracks");
                        String tracksUrl = tracks.getString("href");
                        madeForYouPlaylists.add(new Playlist(title, decs, playlistImage, null, tracksUrl));
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
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return recently-player  playlist
     */
    @Override
    public ArrayList<Playlist> getRecentPlaylists(final Context context, String mToken) {
        Log.e("recent", "start");

        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final ArrayList<Playlist> recentPlaylists = new ArrayList<>();
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
                        Bitmap playlistImage = fetchImage(context, imageUrl);
                        JSONObject tracks = playlist.getJSONObject("tracks");
                        String tracksUrl = tracks.getString("href");
                        recentPlaylists.add(new Playlist(title, decs, playlistImage, null, tracksUrl));
                        listener.updateUiGetRecentPlaylistsSuccess();
                    }
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
        return recentPlaylists;
    }

    /**
     * getter for random playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return random  playlist
     */
    @Override
    public ArrayList<Playlist> getRandomPlaylists(final Context context, String mToken) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final ArrayList<Playlist> randomPlaylists = new ArrayList<>();
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
                        Bitmap playlistImage = fetchImage(context, imageUrl);
                        JSONObject tracks = playlist.getJSONObject("tracks");
                        String tracksUrl = tracks.getString("href");
                        randomPlaylists.add(new Playlist(title, decs, playlistImage, null, tracksUrl));
                        listener.updateUiGetRandomPlaylistsSuccess();
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.currentToken);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("limit", "10");
                return params;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return randomPlaylists;
    }

    @Override
    public ArrayList<Container> getResentResult(Context context) {
        return null;
    }

    @Override
    public ArrayList<Container> getResultsOfSearch(Context context, String searchWord) {
        return null;
    }


   /* @Override
    public ArrayList<Category> getCategories(final Context context) {
        final updateUiGetCategories listener=(updateUiGetCategories) context;
        final ArrayList<Category> categories=new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.GET_ALL_CATEGORIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject root=new JSONObject(response);
                            JSONObject data = root.getJSONObject("data");
                            JSONArray catArr=data.optJSONArray("categorys");
                            for(int i=0;i<catArr.length();i++){
                                JSONObject jsonObject=catArr.getJSONObject(i);
                                String href=jsonObject.getString("href");
                                String id=jsonObject.getString("id");
                                String name=jsonObject.getString("name");
                                JSONArray imgArr=jsonObject.getJSONArray("icons");
                                JSONObject imgObj=imgArr.getJSONObject(0);
                                String imgUrl=imgObj.getString("url");
                                Bitmap imgBitmap=fetchImage(context,imgUrl);
                                categories.add(new Category(name,imgBitmap,id,href));
                            }
                            listener.getCategoriesSuccess(categories);

                        }catch (Exception e){
                            e.fillInStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"afavvavav",Toast.LENGTH_SHORT);
            }
        });
        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
        return categories;
    }*/

    public interface updateUiGetCategories {
        void getCategoriesSuccess(ArrayList<Category> c);
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

    /*public interface updateUigetGenres{
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
        return null;
    }

    /**
     * Get information for a single artist identified by their unique ID
     *
     * @param context activity context
     * @param mToken  user's access token
     * @param id      artist id
     * @return artist object
     */
    @Override
    public Artist getArtist(Context context, String mToken, String id) {
        return null;
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

    /**
     * Get a list of the albums saved in the current user’s ‘Your Music’ library
     *
     * @param context Activity context
     * @param mToken  User's access token
     * @param offset  The index of the first object to return
     * @param limit   The maximum number of objects to return
     * @return List of saved albums
     */
    @Override
    public ArrayList<Album> getUserSavedAlbums(Context context, String mToken, int offset, int limit) {
        return null;
    }

    /**
     * Get the current user’s followed artists
     *
     * @param type   true for user and false for artist
     * @param mToken user's access token
     * @param limit  he maximum number of items to return
     * @return list of followed artists
     */
    @Override
    public ArrayList<Artist> getFollowedArtists(Boolean type, String mToken, int limit) {
        return null;
    }

    /**
     * Add the current user as a follower of one artist or other users
     *
     * @param type   true for user and false for artist
     * @param mToken user's access token
     * @param id     user or artist id
     */
    @Override
    public void followArtistOrUser(Boolean type, String mToken, String id) {

    }

    /**
     * Remove the current user as a follower of one artist or other users
     *
     * @param type   true for user and false for artist
     * @param mToken user's access token
     * @param id     user or artist id
     */
    @Override
    public void unFollowArtistOrUser(Boolean type, String mToken, String id) {

    }

    /**
     * Check to see if the current user is following an artist or other users
     *
     * @param type   true for user and false for artist
     * @param mToken user's access token
     * @param id     user or artist id
     * @return true if following and false if not
     */
    @Override
    public boolean isFollowing(Boolean type, String mToken, String id) {
        return false;
    }

    /**
     * Get a list of recommended artist for the current user
     *
     * @param type   true for user and false for artist
     * @param mToken user's access token
     * @param limit  he maximum number of items to return
     * @return list of recommended artists
     */
    @Override
    public ArrayList<Artist> getRecommendedArtists(Boolean type, String mToken, int limit) {
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
    public Profile getCurrentUserProfile(Context context, SettingsFragment settingsFragment) {
        updateUiProfileInSetting listener = (updateUiProfileInSetting) context;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Get_Current_User_Profile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                        }catch (Exception e){
                            e.fillInStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Profile", "" + error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.currentToken);
                return headers;            }
        }
        ;

        VolleySingleton.getInstance(context).getRequestQueue().add(stringRequest);
        Profile profile=new Profile("avad",Utils.convertToBitmap(R.drawable.blue_image));
        return profile;
    }

    public interface updateUiProfileInSetting{
        public void getCurrentProfile(Profile profile,SettingsFragment settingsFragment);
    }
}