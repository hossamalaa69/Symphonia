package com.example.symphonia.Service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
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
import com.example.symphonia.Activities.User_Interface.StartActivity;
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
import com.example.symphonia.Helpers.App;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Class that holds all functions to be used to fill metadata of application
 * using REST APIs implementation
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 11-4-2020
 */

public class RestApi implements APIs {

    private boolean finishedUnFollowing = true;

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
                            Toast.makeText(context, R.string.check_internet, Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(context, R.string.check_internet, Toast.LENGTH_SHORT).show();
                                updateLogin.updateUiLoginFail("exception");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, R.string.check_internet, Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean forgetPassword(final Context context, final String email) {
        final updateUIForgetPassword updateUIforgetpassword = (updateUIForgetPassword) context;
        StringRequest stringrequest = new StringRequest(Request.Method.POST, (Constants.FORGET_PASSWORD_URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            updateUIforgetpassword.updateUIForgetPasswordSuccess();
                        } catch (Exception e) {
                            Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                            updateUIforgetpassword.updateUIForgetPasswordFailed();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, R.string.invalid_email, Toast.LENGTH_SHORT).show();
                        updateUIforgetpassword.updateUIForgetPasswordFailed();
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


    public interface updateUIForgetPassword {
        void updateUIForgetPasswordSuccess();

        void updateUIForgetPasswordFailed();
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
                response -> {
                    try {
                        if (!mType) {
                            Toast.makeText(context, context.getString(R.string.check_mail_activation), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, StartActivity.class);
                            context.startActivity(i);
                            return;
                        }
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
                        Toast.makeText(context, R.string.sign_up_success, Toast.LENGTH_SHORT).show();
                        updateUiSignUp.updateUiSignUpSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, R.string.check_internet, Toast.LENGTH_SHORT).show();
                        updateUiSignUp.updateUiSignUpFailed();
                    }
                },
                error -> {
                    try {
                        Toast.makeText(context, "Error: " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, R.string.failed, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, R.string.failed, Toast.LENGTH_SHORT).show();
                    }
                    updateUiSignUp.updateUiSignUpFailed();
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

        void getCurrPlayingTrackSuccess(String id);

        void getCurrPlayingTrackFailed();

        void updateUiNoTracks(PlaylistFragment playlistFragment);

        void updateUiGetTracksOfPlaylist(PlaylistFragment playlistFragment);

        void updateUicheckSaved(PlaylistFragment playlistFragment);

        void updateUiGetPopularPlaylistsSuccess();

        void updateUiGetPopularPlaylistsFail();

        void updateUiGetRandomPlaylistsSuccess(HomeFragment homeFragment);

        void updateUiGetRandomPlaylistsFail();

        void updateUiGetRecentPlaylistsSuccess(HomeFragment homeFragment);

        void updateUiGetRecentPlaylistsFail();

        void updateUiGetMadeForYouPlaylistsSuccess();

        void updateUiGetMadeForYouPlaylistsFail();

        void updateUiPlayTrack();

        void getTrackSuccess();

        void updateUiGetQueue();

        void getTrackOfQueue();
    }

    /**
     * getter for popular playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return popular  playlist
     */
    @Override
    public ArrayList<com.example.symphonia.Entities.Context> getPopularPlaylists(final Context context, String mToken) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final ArrayList<com.example.symphonia.Entities.Context> popularPlaylists = new ArrayList<>();
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
                        popularPlaylists.add(new com.example.symphonia.Entities.Context(title, id, decs, imageUrl, null, tracksUrl));
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
    public ArrayList<com.example.symphonia.Entities.Context> getMadeForYouPlaylists(final Context context, String mToken) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final ArrayList<com.example.symphonia.Entities.Context> madeForYouPlaylists = new ArrayList<>();
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
                        String type = "playlist";
                        String title = playlist.getString("name");
                        String decs = playlist.getString("description");
                        JSONArray images = playlist.getJSONArray("images");
                        JSONObject image = images.getJSONObject(0);
                        String imageUrl = image.getString("url");
                        String id = playlist.getString("_id");
                        JSONObject tracks = playlist.getJSONObject("tracks");
                        String tracksUrl = tracks.getString("href");
                        madeForYouPlaylists.add(new com.example.symphonia.Entities.Context(title, id, decs, imageUrl, null, tracksUrl,type));
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
    public ArrayList<com.example.symphonia.Entities.Context> getRecentPlaylists(final Context context, final HomeFragment fragment) {
        Log.e("recent", "start");
        final ArrayList<com.example.symphonia.Entities.Context> recentPlaylists = new ArrayList<>();
        //TODO  backend still under working
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_RECENT_PLAYLISTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("recent", "respond");
                    Utils.LoadedPlaylists.recentPlaylists = new ArrayList<>();
                    updateUiPlaylists listener = (updateUiPlaylists) context;
                    JSONObject root = new JSONObject(response);
                    JSONArray playlists = root.getJSONArray("history");
                    for (int i = 0; i < playlists.length(); i++) {
                        JSONObject playlist = playlists.getJSONObject(i);
                        String type = playlist.getString("contextType");
                        String title = playlist.getString("contextName");
                        String decs = playlist.optString("contextDescription");
                        String imageUrl = playlist.getString("contextImage");
                        String id = playlist.getString("contextId");
                        Utils.LoadedPlaylists.recentPlaylists.add(new com.example.symphonia.Entities.Context(title, id, decs, imageUrl,
                                null, Constants.BASE_URL + Constants.GET_PLAYLISTS_TRACKS + id + "/tracks",type));
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
                Toast.makeText(context.getApplicationContext(),"failed to get playlists, please refresh",Toast.LENGTH_SHORT).show();
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
    public ArrayList<com.example.symphonia.Entities.Context> getRandomPlaylists(final Context context, final HomeFragment homeFragment) {
        Log.e("rand", "start");

        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final ArrayList<com.example.symphonia.Entities.Context> randomPlaylists = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_RANDOM_PLAYLISTS.concat("number=10"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("rand", "respond");
                    Utils.LoadedPlaylists.randomPlaylists = new ArrayList<>();
                    JSONArray playlists = new JSONArray(response);
                    for (int i = 0; i < playlists.length(); i++) {
                        JSONObject playlist = playlists.getJSONObject(i);
                        String type = "playlist";
                        String title = playlist.getString("name");
                        String id = playlist.getString("_id");
                        String decs = playlist.optString("description");
                        JSONArray images = playlist.getJSONArray("images");
                        String imageUrl = (String) images.get(0);
                        Utils.LoadedPlaylists.randomPlaylists.add(new com.example.symphonia.Entities.Context(title, id, "", imageUrl,
                                null, Constants.BASE_URL + Constants.GET_PLAYLISTS_TRACKS + id + "/tracks",type));
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
                Toast.makeText(context.getApplicationContext(),"failed to get playlists, please refresh",Toast.LENGTH_SHORT).show();
                Log.e("rand", "error");
            }
        });
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return randomPlaylists;
    }

    /**
     * this function initialize the track to be streamed
     *
     * @param context current activity's context
     */

    @Override
    public void playTrack(final Context context) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final StringRequest request = new StringRequest(Request.Method.POST, Constants.PLAY_TRACK.concat(Utils.currTrack.getId())
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.CurrTrackInfo.loading = false;
                JSONObject obj;
                try {
                    obj = new JSONObject(response);
                    Utils.CurrTrackInfo.trackTocken = obj.getString("data");
                    listener.updateUiPlayTrack();
                    Log.e("play", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("contextId", Utils.playingContext.getId());
                params.put("context_type", Utils.playingContext.getContextType());
                params.put("context_url", "https://thesymphonia.ddns.net/");
                params.put("device", "android");
                return params;
            }
/*
            @Override
            public String getBodyContentType() {*//*Utils.currContextId*//*
                return "{\"contextId\": \"5e701f4d2672a63a60573a02\",\"context_type\": \"album\",\"device\": \"android\"}";
                //"{\"contextId\":\"" +"5e701f4d2672a63a60573a02" + "\",\"context_type\":\"album\",\"context_url\":\"https://thesymphonia.ddns.net/\",\"device\":\"android\"}";
            }*/
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void getCurrPlaying(final Context context) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_CURR_PLAYING
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj;
                try {
                    obj = new JSONObject(response);
                    JSONObject data = obj.getJSONObject("data");
                    String link = data.getString("currentTrack");
                    if (!link.matches("null"))
                        listener.getCurrPlayingTrackSuccess(link.split("tracks/")[1]);
                    else
                        listener.getCurrPlayingTrackFailed();
                    Log.e("curr playing", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.getCurrPlayingTrackFailed();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("curr playing", "error");
                //Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                listener.getCurrPlayingTrackFailed();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                //   headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void getQueue(final Context context) {
        final StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_QUEUE
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj;
                try {
                    obj = new JSONObject(response);
                    JSONObject data = obj.getJSONObject("data");
                    JSONArray devices = data.getJSONArray("devices");
                    for (int i = 0; i < devices.length(); i++) {
                        JSONObject device = (JSONObject) devices.get(i);
                        if (device.getString("devicesName").matches("android")) {
                            String contextId = data.getString("contextId");
                            String contextType = data.getString("contextType");
                            if (contextType.matches("playlist")) {
                                getTracksOfPlaylist(context, contextId, null);
                                break;
                            }
                        }
                    }
                    Log.e("get queue", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("get queue", "error");
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
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void playNext(final Context context) {
        final StringRequest request = new StringRequest(Request.Method.POST, Constants.PLAY_NEXT
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj;
                try {
                    obj = new JSONObject(response);
                    JSONArray data = obj.getJSONArray("data");
                    //      String nextId = data.get(1).toString().split("tracks/")[1];
                    getCurrPlaying(context);
                    Log.e("play next", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("next", "error");
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
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void playPrev(final Context context) {
        final StringRequest request = new StringRequest(Request.Method.POST, Constants.PLAY_PREV
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj;
                try {
                    obj = new JSONObject(response);
                    JSONArray data = obj.getJSONArray("data");
                    getCurrPlaying(context);
                    Log.e("play prev", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("prev", "error");
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
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void removeFromSaved(final Context context, String id) {
        final StringRequest request = new StringRequest(Request.Method.DELETE, Constants.REMOVE_SAVED_TRACK + id
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray obj;
                try {
                    obj = new JSONArray(response);
                    Log.e("remove saved tracks", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("remove saved tracks", "error");
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                //headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void checkSaved(final Context context, String ids, PlaylistFragment playlistFragment) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final StringRequest request = new StringRequest(Request.Method.GET, Constants.CHECK_SAVED_TRACKS + ids
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray obj;
                try {
                    obj = new JSONArray(response);
                    for (int i = 0; i < obj.length(); i++) {
                        Utils.displayedContext.getTracks().get(i).setLiked((boolean) obj.get(i));
                    }
                    listener.updateUicheckSaved(playlistFragment);
                    Log.e("check saved tracks", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("check saved tracks", "error");
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
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void saveTrack(final Context context, String id) {
        final StringRequest request = new StringRequest(Request.Method.PUT, Constants.SAVE_TRACK + id
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj;
                try {
                    obj = new JSONObject(response);
                    Toast.makeText(context, "track saved", Toast.LENGTH_SHORT).show();
                    Log.e("check saved tracks", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("check saved tracks", "error");
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
        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    @Override
    public void getTrack(final Context context, String id) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        final StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_TRACK + id
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj;
                try {
                    //final Context context, String id, String context_id, String context_url, String context_type
                    obj = new JSONObject(response);
                    String Type = obj.getString("type");
                    if (Type.matches("track")) {
                        String id = obj.getString("_id");
                        String name = obj.getString("name");
                        int durationMS = obj.getInt("durationMs");
                        boolean premium = obj.getBoolean("premium");
                        JSONObject album = obj.optJSONObject("album");
                        JSONObject artist = obj.optJSONObject("artist");
                        String artistName = artist.getString("name");
                        String artistID = artist.getString("_id");
                        String context_id = null;
                        String imageUrl = null;
                        String albumName = null;
                        if (album != null) {
                            context_id = album.getString("_id");
                            imageUrl = album.getString("image");
                            albumName = album.getString("name");
                            Utils.currContextType = "album";
                        } else {
                            JSONObject playlist = obj.optJSONObject("playlist");
                            if (playlist != null) {
                                context_id = playlist.getString("_id");
                                imageUrl = playlist.getString("image");
                                albumName = playlist.getString("name");
                                Utils.currContextType = "playlist";
                            }
                        }
                        Utils.currTrack = new Track(name, artistName, albumName, id, premium, 0, durationMS, imageUrl, null, context_id);
                        listener.getTrackSuccess();
                    }
                    Log.e("curr playing", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getTrack", "error");
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
                    JSONObject root = new JSONObject(response);
                    JSONObject tracks = root.getJSONObject("tracks");
                    JSONArray items = tracks.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject track = items.getJSONObject(i);
                        String title = track.getString("name");
                        String id = track.getString("_id");
                        JSONObject album = track.getJSONObject("album");
                        String imageUrl = album.optString("image");
                        String albumId = album.getString("_id");
                        JSONObject artist = track.getJSONObject("artist");
                        String artistName = artist.getString("name");
                        int duration = track.getInt("durationMs");
                        String premium = track.getString("premium");
                        tracksList.add(new Track(title, artistName, Utils.displayedContext.getmContextTitle()
                                , id, (premium.matches("true")), R.drawable.no_image, duration, imageUrl, albumId, Utils.displayedContext.getId()));
                    }
                    Utils.displayedContext.setTracks(tracksList);
                    Log.e("tracks", "success");
                    if (playlistFragment != null)
                        listener.updateUiGetTracksOfPlaylist(playlistFragment);
                    else
                        listener.updateUiGetQueue();
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (playlistFragment != null)
                        listener.updateUiNoTracks(playlistFragment);
                    else
                        listener.updateUiGetQueue();
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

    /**
     * ensure to return empty list when recent searches is required
     *
     * @param context context of the activity
     */
    @Override
    public void removeAllRecentSearches(Context context) {

    }

    /**
     * return a list of popular playlists
     *
     * @param context context of the activity
     * @return a ArrayList of Container of Popular playlists
     */
    @Override
    public ArrayList<Container> getAllPopularPlaylists(Context context) {
        return null;
    }

    /**
     * return four popular playlists
     *
     * @param context context of the activity
     * @return return four popular playlists
     */
    @Override
    public ArrayList<Container> getFourPlaylists(Context context) {
        ArrayList<Container> paylists = new ArrayList<>();
        return paylists;
    }

    /**
     * Get information for a single artist identified by their unique ID
     *
     * @param context activity context
     * @param id      artist id
     * @return artist object
     */
    @Override
    public Artist getArtist(Context context, String id) {
        return null;
    }

    @Override
    public Playlist getPlaylist(RestApi.UpdatePlaylist listener, String id) {

        RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
        RetrofitApi retrofitApi = retrofitSingleton.getRetrofitApi();

        Call<JsonObject> call = retrofitApi.getPlaylist(id);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {

                if (response.code() == 200) {
                    try {
                        JSONObject playlist = new JSONObject(new Gson().toJson(response.body()));

                        String id = playlist.getString("_id");
                        String name = playlist.getString("name");
                        String imageUrl = playlist.getJSONArray("images").optString(0);
                        if (imageUrl == null || imageUrl.contains("default.png")) {
                            imageUrl = "default";
                        }
                        String ownerName = playlist.getJSONObject("owner").getString("name");

                        listener.updatePlaylist(new Playlist(id, name, imageUrl, ownerName));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

            }
        });


        return null;
    }

    public interface UpdatePlaylist {
        void updatePlaylist(Playlist playlist);
    }

    /**
     * Get a list of the albums saved in the current user’s ‘Your Music’ library
     *
     * @param listener
     * @param offset   The index of the first object to return
     * @param limit    The maximum number of objects to return
     * @return List of saved albums
     */
    @Override
    public ArrayList<Album> getUserSavedAlbums(final UpdateAlbumsLibrary listener, int offset, int limit) {

        final ArrayList<Album> savedAlbums = new ArrayList<>();

        Uri.Builder builder = Uri.parse(Constants.ALBUMS_URL).buildUpon();
        builder.appendQueryParameter("limit", String.valueOf(limit));
        builder.appendQueryParameter("offset", String.valueOf(offset));

        final StringRequest request = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONObject albums = root.getJSONObject("Albums");
                    JSONArray albumsArray = albums.getJSONArray("items");
                    for (int i = 0; i < albumsArray.length(); i++) {
                        JSONObject album = albumsArray.getJSONObject(i);
                        String id = album.getString("_id");
                        String name = album.getString("name");
                        /*JSONArray images = artist.getJSONArray("images");
                        JSONObject image = images.getJSONObject(1);*/
                        String imageUrl = album.getString("image");
                        JSONObject artist = album.getJSONObject("artist");
                        String artistId = artist.getString("_id");
                        String artistName = artist.getString("name");
                        ArrayList<Artist> artists = new ArrayList<>(Collections.singletonList(new Artist(artistId, null, artistName)));
                        savedAlbums.add(new Album(id, artists, imageUrl, name));
                    }
                    listener.updateAlbums(savedAlbums);
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
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }

        };

        VolleySingleton.getInstance(App.getContext()).getRequestQueue().add(request);
        return savedAlbums;
    }

    public interface UpdateAlbumsLibrary {
        void updateAlbums(ArrayList<Album> returnedAlbums);
    }

    @Override
    public ArrayList<Playlist> getCurrentUserPlaylists(final UpdatePlaylistsLibrary listener, int offset, int limit) {

        final ArrayList<Playlist> returnedPlaylists = new ArrayList<>();

        Uri.Builder builder = Uri.parse(Constants.Get_Current_User_Profile_playlists).buildUpon();
        builder.appendQueryParameter("limit", String.valueOf(limit));
        builder.appendQueryParameter("offset", String.valueOf(offset));

        final StringRequest request = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONObject playlists = root.getJSONObject("playlists");
                    JSONArray playlistsArray = playlists.getJSONArray("items");
                    for (int i = 0; i < playlistsArray.length(); i++) {
                        JSONObject playlist = playlistsArray.getJSONObject(i);
                        String id = playlist.getString("_id");
                        String name = playlist.getString("name");
                        String imageUrl = playlist.getJSONArray("images").optString(0);
                        if (imageUrl == null || imageUrl.contains("default.png")) {
                            imageUrl = "default";
                        }
                        String ownerName = playlist.getJSONObject("owner").getString("name");
                        returnedPlaylists.add(new Playlist(id, name, imageUrl, ownerName));
                    }
                    listener.updatePlaylists(returnedPlaylists);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }

        };

        VolleySingleton.getInstance(App.getContext()).getRequestQueue().add(request);
        return returnedPlaylists;
    }

    public interface UpdatePlaylistsLibrary {
        void updatePlaylists(ArrayList<Playlist> returnedPlaylists);
    }

    @Override
    public ArrayList<Track> getUserSavedTracks(final RestApi.UpdateSavedTracks listener, int offset, int limit) {
        final ArrayList<Track> returnedTracks = new ArrayList<>();

        Uri.Builder builder = Uri.parse(Constants.SAVED_TRACKS).buildUpon();
        builder.appendQueryParameter("limit", String.valueOf(limit));
        builder.appendQueryParameter("offset", String.valueOf(offset));

        final StringRequest request = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONObject tracks = root.getJSONObject("tracks");
                    JSONArray items = tracks.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject track = items.getJSONObject(i);
                        String title = track.getString("name");
                        String id = track.getString("_id");
                        JSONObject album = track.getJSONObject("album");
                        String imageUrl = album.optString("image");
                        String albumId = album.getString("_id");
                        JSONObject artist = track.getJSONObject("artist");
                        String artistName = artist.getString("name");
                        int duration = track.getInt("durationMs");
                        String premium = track.getString("premium");
                        returnedTracks.add(new Track(title, artistName, ""
                                , id, (premium.matches("true")), R.drawable.no_image, duration, imageUrl, albumId));
                    }
                    listener.updateTracks(returnedTracks);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }

        };

        VolleySingleton.getInstance(App.getContext()).getRequestQueue().add(request);
        return returnedTracks;
    }

    public interface UpdateSavedTracks {
        void updateTracks(ArrayList<Track> returnedTracks);
    }

    @Override
    public ArrayList<Track> getRecommendedTracks(final RestApi.UpdateExtraSongs listener, int offset, int limit) {
        final ArrayList<Track> returnedTracks = new ArrayList<>();

        Uri.Builder builder = Uri.parse(Constants.RANDOM_RECOMMENDATIONS).buildUpon();
        builder.appendQueryParameter("limit", String.valueOf(limit));
        builder.appendQueryParameter("offset", String.valueOf(offset));

        final StringRequest request = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONObject tracks = root.getJSONObject("tracks");
                    JSONArray items = tracks.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject track = items.getJSONObject(i);
                        String title = track.getString("name");
                        String id = track.getString("_id");
                        JSONObject album = track.getJSONObject("album");
                        String imageUrl = album.getString("image");
                        String albumId = album.getString("_id");
                        JSONObject artist = track.getJSONObject("artist");
                        String artistName = artist.getString("name");
                        int duration = track.getInt("durationMs");
                        String premium = track.getString("premium");
                        returnedTracks.add(new Track(title, artistName, ""
                                , id, (premium.matches("true")), R.drawable.no_image, duration, imageUrl, albumId));
                    }
                    listener.updateExtra(returnedTracks);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }

        };

        VolleySingleton.getInstance(App.getContext()).getRequestQueue().add(request);
        return returnedTracks;
    }

    public interface UpdateExtraSongs {
        void updateExtra(ArrayList<Track> returnedTracks);
    }

    @Override
    public int getOwnedPlaylistsNumber(Context context) {

        UpdatePlaylistsNumber listener = (UpdatePlaylistsNumber) context;

        Uri.Builder builder = Uri.parse(Constants.OWNED_PLAYLIST).buildUpon();
        builder.appendQueryParameter("limit", String.valueOf(65353));
        builder.appendQueryParameter("offset", String.valueOf(0));

        final StringRequest request = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    int number = root.getJSONObject("playlists").getInt("total");

                    listener.updateNumber(number);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }

        };

        VolleySingleton.getInstance(App.getContext()).getRequestQueue().add(request);
        return 0;
    }

    public interface UpdatePlaylistsNumber {
        void updateNumber(int number);
    }


    /**
     * Get the current user’s followed artists
     *
     * @param listener
     * @param type     current type, can be artist or user
     * @param limit    he maximum number of items to return
     * @param after    the last artist ID retrieved from the previous request
     * @return list of followed artists
     */
    @Override
    public ArrayList<Artist> getFollowedArtists(final UpdateArtistsLibrary listener, String type, int limit, String after) {

        final ArrayList<Artist> followedArtists = new ArrayList<>();

        Uri.Builder builder = Uri.parse(Constants.FOLLOW_ARTIST_URL).buildUpon();
        builder.appendQueryParameter("type", type);
        builder.appendQueryParameter("limit", String.valueOf(limit));
        builder.appendQueryParameter("after", (after == null) ? "null" : after);

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
                        String imageUrl = artist.getString("imageUrl");
                        followedArtists.add(new Artist(id, imageUrl, name));
                    }
                    listener.updateArtists(followedArtists);
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
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        VolleySingleton.getInstance(App.getContext()).getRequestQueue().add(request);
        return followedArtists;
    }

    public interface UpdateArtistsLibrary {
        void updateArtists(ArrayList<Artist> returnedArtists);
    }

    /**
     * Add the current user as a followers of one or more artists or other users
     *
     * @param context activity context
     * @param type    the type of what will be followed, can be artist or user
     * @param ids     array of users or artists ids
     */
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

    /**
     * Remove the current user as a follower of one or more artists or other users
     *
     * @param context activity context
     * @param type    the type of what will be unFollowed, can be artist or user
     * @param ids     array of users or artists ids
     */
    @Override
    public void unFollowArtistsOrUsers(final Context context, final String type, final ArrayList<String> ids) {

        finishedUnFollowing = false;

        RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
        RetrofitApi retrofitApi = retrofitSingleton.getRetrofitApi();

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
                finishedUnFollowing = true;
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                finishedUnFollowing = true;
            }
        });
    }

    @Override
    public void createPlaylist(Context context, String name) {

        final UpdateCreatePlaylist listener = (UpdateCreatePlaylist) context;

        RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
        RetrofitApi retrofitApi = retrofitSingleton.getRetrofitApi();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + Constants.currentToken);

        Map<String, Object> body = new HashMap<>();
        body.put("collaborative", false);
        body.put("public", true);
        body.put("name", name);

        Call<JsonObject> call = retrofitApi.createPlaylist(headers, Constants.currentUser.get_id(), body);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {
                Log.i("TAG", "onResponse: " + response.code());
                if (response.code() == 200) {
                    try {
                        JSONObject playlist = new JSONObject(new Gson().toJson(response.body()));

                        String id = playlist.getString("_id");
                        String name = playlist.getString("name");
                        String imageUrl = playlist.getJSONArray("images").optString(0);
                        if (imageUrl == null || imageUrl.contains("default.png")) {
                            imageUrl = "default";
                        }
                        String ownerName = playlist.getJSONObject("owner").getString("name");

                        listener.createdSuccessfully(new Playlist(id, name, imageUrl, ownerName));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.i("TAG", "onFailure: ");
            }
        });

    }

    public interface UpdateCreatePlaylist {
        void createdSuccessfully(Playlist createdPlaylist);
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
    public boolean promotePremium(final Context context, View root, final String token) {
        final updateUIPromotePremium uiPromotePremium = (updateUIPromotePremium) context;

        RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
        RetrofitApi retrofitApi = retrofitSingleton.getRetrofitApi();

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        Call<JsonObject> call = retrofitApi.promotePrem(map);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                if (response.code() == 200 || response.code() == 201) {
                    Toast.makeText(context, "Wait for e-mail confirmation", Toast.LENGTH_LONG).show();
                    uiPromotePremium.updateUIPromotePremiumSuccess();
                } else {
                    Toast.makeText(context, "Not valid e-mail", Toast.LENGTH_SHORT).show();
                    uiPromotePremium.updateUIPromotePremiumFailed();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                uiPromotePremium.updateUIPromotePremiumFailed();
            }
        });

        return false;
    }

    @Override
    public boolean checkPremiumToken(Context context, String token) {
        final updateUICheckPremium uiCheckPremium = (updateUICheckPremium) context;

        RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
        RetrofitApi retrofitApi = retrofitSingleton.getRetrofitApi();

        Call<JsonObject> call = retrofitApi.checkPremiumToken(token);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                if (response.code() == 201 || response.code() == 200) {
                    Constants.currentUser.setPremuim(true);
                    uiCheckPremium.updateUICheckPremiumSuccess();
                } else {
                    Toast.makeText(context, "Expired Token", Toast.LENGTH_SHORT).show();
                    uiCheckPremium.updateUICheckPremiumFailed();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                uiCheckPremium.updateUICheckPremiumFailed();
            }
        });

        return false;
    }

    @Override
    public boolean sendRegisterToken(Context context, String register_token) {

        RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
        RetrofitApi retrofitApi = retrofitSingleton.getRetrofitApi();
        Map<String, String> body = new HashMap<>();
        body.put("token", register_token);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + Constants.currentToken);
        Call<JsonObject> call = retrofitApi.sendRegisterToken(headers, body);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                if (response.code() != 200 && response.code() != 201)
                    Toast.makeText(context, "Failed registration token for notifications", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return false;
    }

    @Override
    public boolean getNotificationHistory(Context context, String token) {

        final updateUIGetNotify uiGetNotify = (updateUIGetNotify) context;

        RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
        RetrofitApi retrofitApi = retrofitSingleton.getRetrofitApi();

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + Constants.currentToken);
        Call<JsonObject> call = retrofitApi.getNotifications(map);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                if (response.code() == 200 || response.code() == 201) {
                    try {
                        JSONObject root = new JSONObject(new Gson().toJson(response.body()));
                        uiGetNotify.updateUIGetNotifySuccess(root);
                    } catch (Exception e) {
                        e.printStackTrace();
                        uiGetNotify.updateUIGetNotifyFailed();
                    }
                } else {
                    Toast.makeText(context, "No history", Toast.LENGTH_SHORT).show();
                    uiGetNotify.updateUIGetNotifyFailed();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                uiGetNotify.updateUIGetNotifyFailed();
            }
        });

        return false;
    }

    public interface updateUIGetNotify {
        void updateUIGetNotifySuccess(JSONObject root);

        void updateUIGetNotifyFailed();
    }

    public interface updateUICheckPremium {
        void updateUICheckPremiumSuccess();

        void updateUICheckPremiumFailed();
    }

    public interface updateUIPromotePremium {
        void updateUIPromotePremiumSuccess();

        void updateUIPromotePremiumFailed();
    }

    @Override
    public boolean resetPassword(final Context context, final String password, final String token) {
        final updateUIResetPassword updateuiResetPassword = (updateUIResetPassword) context;

        RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
        RetrofitApi retrofitApi = retrofitSingleton.getRetrofitApi();

        Map<String, String> passwords = new HashMap<>();
        passwords.put("password", password);
        passwords.put("passwordConfirm", password);

        Call<JsonObject> call = retrofitApi.resetPassword(token, passwords);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {

                if (response.code() == 200) {
                    try {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        JSONObject root = new JSONObject(new Gson().toJson(response.body()));
                        Constants.currentToken = root.getString("token");
                        JSONObject user = root.getJSONObject("user");
                        String id = user.getString("_id");
                        String name = user.getString("name");
                        String type = user.getString("type");
                        String image = user.getString("imageUrl");
                        String email = user.getString("email");
                        boolean premium = false;
                        boolean mType = true;
                        if (type.equals("premium-user")) {
                            premium = true;
                            type = "user";
                        } else if (type.equals("artist")) {
                            premium = true;
                            mType = false;
                        }

                        Constants.currentUser = new User(email, id, name, mType, premium);
                        Constants.currentUser.setUserType(type);
                        Constants.currentUser.setImageUrl(image);
                        updateuiResetPassword.updateUIResetSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                        updateuiResetPassword.updateUIResetFailed();
                    }
                } else {
                    Toast.makeText(context, R.string.token_is_expired, Toast.LENGTH_SHORT).show();
                    updateuiResetPassword.updateUIResetFailed();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                updateuiResetPassword.updateUIResetFailed();
            }
        });

        return true;
    }

    @Override
    public boolean applyArtist(Context context, String token) {
        final updateUIApplyArtist uiApplyArtist = (updateUIApplyArtist) context;

        RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
        RetrofitApi retrofitApi = retrofitSingleton.getRetrofitApi();
        Call<JsonObject> call = retrofitApi.applyArtist(token);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {

                if (response.code() >= 200 && response.code() < 300) {
                    try {
                        JSONObject root = new JSONObject(new Gson().toJson(response.body()));
                        Constants.currentToken = root.getString("token");
                        JSONObject user = root.getJSONObject("user");
                        String id = user.getString("_id");
                        String name = user.getString("name");
                        String type = user.getString("type");
                        String image = user.getString("imageUrl");
                        String email = user.getString("email");
                        String gender = user.getString("gender");
                        String DOB = user.getString("dateOfBirth");
                        Constants.currentUser = new User(email, id, false, Utils.convertToBitmap(R.drawable.img_init_profile)
                                , name, DOB, gender, true
                                , 0, 0, new ArrayList<User>(), new ArrayList<User>()
                                , new ArrayList<Playlist>(), new ArrayList<Playlist>()
                                , new ArrayList<Artist>(), new ArrayList<Album>(), new ArrayList<Track>());

                        Constants.currentUser.setUserType(type);
                        Constants.currentUser.setImageUrl(image);
                        uiApplyArtist.updateUIApplyArtistSuccess();

                    } catch (JSONException e) {
                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                        uiApplyArtist.updateUIApplyArtistFailed();
                    }

                } else {
                    Toast.makeText(context, R.string.token_is_expired, Toast.LENGTH_SHORT).show();
                    uiApplyArtist.updateUIApplyArtistFailed();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                uiApplyArtist.updateUIApplyArtistFailed();
            }
        });
        return true;
    }

    public interface updateUIApplyArtist {
        void updateUIApplyArtistSuccess();

        void updateUIApplyArtistFailed();
    }

    public interface updateUIResetPassword {
        void updateUIResetSuccess();

        void updateUIResetFailed();
    }

    /**
     * Check to see if the current user is following an artist or more or other users
     *
     * @param context activity context
     * @param type    the type of the checked objects, can be artist or user
     * @param ids     array of users or artists ids
     * @return array of boolean
     */
    @Override
    public ArrayList<Boolean> isFollowing(Context context, String type, ArrayList<String> ids) {
        return null;
    }

    /**
     * Get a list of recommended artist for the current user
     *
     * @param context activity context
     * @param type    artist or user
     * @param offset  the beginning of the items
     * @param limit   the maximum number of items to return
     * @return list of recommended artists
     */
    @Override
    public ArrayList<Artist> getRecommendedArtists(final Context context, String type, final int offset, final int limit) {

        final UpdateAddArtists listener = (UpdateAddArtists) context;
        final ArrayList<Artist> recommendedArtists = new ArrayList<>();

        Uri.Builder builder = Uri.parse(Constants.GET_RECOMMENDED_ARTISTS).buildUpon();
        builder.appendQueryParameter("limit", String.valueOf(limit));
        builder.appendQueryParameter("offset", String.valueOf(offset));

        final StringRequest request = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
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
                listener.updateFail(offset, limit);
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
        if (finishedUnFollowing)
            VolleySingleton.getInstance(context).getRequestQueue().add(request);
        else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    VolleySingleton.getInstance(context).getRequestQueue().add(request);
                    finishedUnFollowing = false;
                }
            }, 1000);
        }
        return recommendedArtists;
    }

    public interface UpdateAddArtists {
        void updateGetRecommendedArtists(ArrayList<Artist> returnedArtists);

        void updateFail(int offset, int limit);
    }


    /**
     * Get information about artists similar to a given artist.
     *
     * @param context activity context
     * @param id      artist id
     * @return Arraylist of similar artists
     */
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
    public ArrayList<Artist> searchArtist(Context context, final String q, final int offset, final int limit) {
        final UpdateSearchArtists listener = (UpdateSearchArtists) context;
        final ArrayList<Artist> result = new ArrayList<>();

        Uri.Builder builder = Uri.parse(Constants.SEARCH_URL).buildUpon();
        builder.appendQueryParameter("type", "artist");
        builder.appendQueryParameter("q", q);
        builder.appendQueryParameter("limit", String.valueOf(limit));
        builder.appendQueryParameter("offset", String.valueOf(offset));

        final StringRequest request = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONObject artists = root.getJSONObject("artist");
                    JSONArray artistArray = artists.getJSONArray("items");
                    for (int i = 0; i < artistArray.length(); i++) {
                        JSONObject artist = artistArray.getJSONObject(i);
                        String id = artist.getString("_id");
                        String name = artist.getString("name");
                        /*JSONArray images = artist.getJSONArray("images");
                        JSONObject image = images.getJSONObject(1);*/
                        String imageUrl = artist.getString("imageUrl");
                        result.add(new Artist(id, imageUrl, name));
                    }
                    listener.updateSuccess(result, q);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.updateFail(q, offset, limit);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }

        };
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return result;
    }

    public interface UpdateSearchArtists {
        void updateSuccess(ArrayList<Artist> result, String q);

        void updateFail(String q, int offset, int limit);
    }


    /**
     * Get information for a single album.
     *
     * @param context activity context
     * @param id      album id
     * @return album object
     */
    @Override
    public Album getAlbum(Context context, String id) {
        return null;
    }

    /**
     * Get information about an album’s tracks.
     * Optional parameters can be used to limit the number of tracks returned.
     *
     * @param context activity context
     * @param id      album id
     * @param offset  the beginning of the tracks list
     * @param limit   the maximum number of tracks to get
     * @return array of album tracks
     */
    @Override
    public ArrayList<Track> getAlbumTracks(Context context, String id, int offset, int limit) {
        final ArrayList<Track> tracksList = new ArrayList<>();
        final StringRequest request = new StringRequest(Request.Method.GET
                , Constants.GET_PLAYLISTS_TRACKS.concat(id).concat("/tracks"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("tracks", "respond");
                    Utils.LoadedPlaylists.randomPlaylists = new ArrayList<>();
                    JSONObject root = new JSONObject(response);
                    JSONObject tracks = root.getJSONObject("tracks");
                    JSONArray items = tracks.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject track = items.getJSONObject(i);
                        String title = track.getString("name");
                        String id = track.getString("_id");
                        JSONObject album = track.getJSONObject("album");
                        String imageUrl = album.optString("image");
                        String albumId = album.getString("_id");
                        JSONObject artist = track.getJSONObject("artist");
                        String artistName = artist.getString("name");
                        int duration = track.getInt("durationMs");
                        String premium = track.getString("premium");
                        tracksList.add(new Track(title, artistName, Utils.displayedContext.getmContextTitle()
                                , id, (premium.matches("true")), R.drawable.no_image, duration, imageUrl, albumId));
                    }
                    Utils.displayedContext.setTracks(tracksList);
                    Log.e("tracks", "success");
                } catch (JSONException e) {
                    e.printStackTrace();
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

    /**
     * Save one or more albums to the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids     array of albums ids
     */
    @Override
    public void saveAlbumsForUser(Context context, ArrayList<String> ids) {

    }

    /**
     * Remove one or more albums from the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids     array of albums ids
     */
    @Override
    public void removeAlbumsForUser(Context context, ArrayList<String> ids) {

    }

    /**
     * Check if one or more albums is already saved in the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids     array of albums ids
     * @return array of booleans, true for found and false for not found
     */
    @Override
    public ArrayList<Boolean> checkUserSavedAlbums(Context context, ArrayList<String> ids) {
        return null;
    }


    @Override
    public ArrayList<Container> getProfileFollowers(Context context) {
        return null;
    }

    @Override
    public ArrayList<Container> getProfileFollowing(Context context) {
        return null;
    }


    /**
     * make request to get current user profile
     *
     * @param context          context of the activity
     * @param settingsFragment the fragment which called this function
     * @return user profile
     */
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
    public int getNumberOfLikedSongs(final UpdateLikedSongsNumber listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.Get_Current_User_Profile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject root = new JSONObject(response);
                            JSONArray tracks = root.getJSONArray("followedTracks");
                            listener.updateNumber(tracks.length());
                        } catch (Exception e) {
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
                headers.put("Authorization", "Bearer " + Constants.currentToken);
                return headers;
            }

        };

        VolleySingleton.getInstance(App.getContext()).getRequestQueue().add(stringRequest);
        return 0;
    }

    public interface UpdateLikedSongsNumber {
        void updateNumber(int noOfTracks);
    }

    /**
     * make request to get current user playlists
     *
     * @param context         context of the activity
     * @param fragmentProfile the fragment which called this function
     * @return ArrayList of Container of User's playlists
     */
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

    /**
     * make request to get users who the current user follow
     *
     * @param context                  context of the activity
     * @param profileFollowersFragment the fragment which called this function
     * @return ArrayList of Container current user following
     */
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

    /**
     * make request to get a list of current user followers
     *
     * @param context                  context of the activity
     * @param profileFollowersFragment the fragment the function is called from
     * @return ArrayList of Container of Followers
     */
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

    /**
     * make request to get number of user followers
     *
     * @param context         context of the activity
     * @param fragmentProfile the fragment the function is called from
     * @return string of the number of followers
     */
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

    /**
     * make request to get number of users that user follow
     *
     * @param context         context of the activity
     * @param fragmentProfile the fragment the function is called from
     * @return string of the number of following
     */
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

    /**
     * make request to get current user playlists
     *
     * @param context                  context of the activity
     * @param profilePlaylistsFragment the fragment the function is called from
     * @return current user playlists
     */
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

    /**
     * make request to follow playlist
     *
     * @param context                  context of the activity
     * @param bottomSheetDialogProfile the fragment the function is called from
     */
    @Override
    public void followPlaylist(Context context, BottomSheetDialogProfile bottomSheetDialogProfile) {

    }

    /**
     * make a request to get number of artists in search result
     *
     * @return int artists Count
     */
    @Override
    public int getArtistsCount() {
        return 0;
    }

    /**
     * make a request to get number of profiles in search result
     *
     * @return int profiles Count
     */
    @Override
    public int getProfilessCount() {
        return 0;
    }

    /**
     * make a request to get number of playlists in search result
     *
     * @return int playlists Count
     */
    @Override
    public int getPlaylistsCount() {
        return 0;
    }

    /**
     * make a request to get number of genres in search result
     *
     * @return int genres Count
     */
    @Override
    public int getGenresCount() {
        return 0;
    }

    /**
     * make a request to get number of songs in search result
     *
     * @return int songs Count
     */
    @Override
    public int getSongsCount() {
        return 0;
    }

    /**
     * get number of albums in search result
     *
     * @return int albums Count
     */
    @Override
    public int getAlbumsCount() {
        return 0;
    }


    /**
     * update ui in the profile fragment
     */
    public interface updateUiProfileInProfileFragment {
        public void getCurrentProfilePlaylists(ArrayList<Container> playlists, FragmentProfile fragmentProfile);

        public void getCurrentUserFollowingNumber(String f, FragmentProfile fragmentProfile);

        public void getCurrentUserFollowersNumber(String s, FragmentProfile fragmentProfile);
    }

    /**
     * update ui of profile in settings fragment
     */
    public interface updateUiProfileInSetting {
        public void getCurrentProfile(Profile profile, SettingsFragment settingsFragment);
    }

    /**
     * update ui in profileplaylistfragmnet
     */
    public interface updateProfilePlaylists {
        public void getAllUserPlaylists(ArrayList<Container> p, ProfilePlaylistsFragment profilePlaylistsFragment);
    }

    /**
     * update follow and ujnfollow
     */
    public interface updateProfileFollow {
        public void getUserFollowers(ArrayList<Container> f, ProfileFollowersFragment profileFollowersFragment);

        public void getUserFollowing(ArrayList<Container> f, ProfileFollowersFragment profileFollowersFragment);

    }

    /**
     *
     */
    public interface updateAfterActions {

    }

}