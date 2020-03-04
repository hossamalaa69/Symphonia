package com.example.symphonia;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.example.symphonia.adapters.RvPlaylistsAdapterHome;
import com.example.symphonia.adapters.RvTracksAdapterHome;
import com.example.symphonia.data.Playlist;
import com.example.symphonia.data.Track;
import com.example.symphonia.ui.home.HomeFragment;
import com.example.symphonia.ui.library.LibraryFragment;
import com.example.symphonia.ui.playlist.PlaylistFragment;
import com.example.symphonia.ui.premium.PremiumFragment;
import com.example.symphonia.ui.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RvPlaylistsAdapterHome.OnPlaylistClicked
        , RvTracksAdapterHome.OnTrackClicked
        , Serializable {
    ImageView playBarButton;

    @Override
    public void OnTrackClickedListener(final ArrayList<Track> tracks, final int pos) {
        View view = findViewById(R.id.layout_playing_bar);
        view.setVisibility(View.VISIBLE);
        TextView details = view.findViewById(R.id.tv_track_details_bar);
        details.setText(tracks.get(pos).getmTitle() + " . " + tracks.get(pos).getmDescription());
        ImageView image = view.findViewById(R.id.iv_track_image_bar);
        image.setImageResource(tracks.get(pos).getmImageResources());
        image = view.findViewById(R.id.iv_play_track_bar);
        image.setImageResource(R.drawable.ic_pause_black_24dp);
        playBarButton = image;
        playBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO handle button clicked
            }
        });
        //TODO set on click listenr for like songs in bar
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra(getString(R.string.playlist_send_to_playActivtiy_intent), tracks);
                intent.putExtra(getString(R.string.curr_playing_track_play_acitivity_intent), pos);
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnPlaylistClickedListener(Playlist playlist) {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.nav_host_fragment, new PlaylistFragment(playlist)
                , this.getString(R.string.playlist_fragment))
                .addToBackStack(null)
                .commit();

        // hide setting button
        ImageView imageView = findViewById(R.id.iv_setting_home);
        imageView.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        NavHostFragment navHostFragment = NavHostFragment.create(R.navigation.mobile_navigation);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, navHostFragment)
                .setPrimaryNavigationFragment(navHostFragment)
                .commit();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, new HomeFragment())
                                .commit();
                        return true;
                    case R.id.navigation_library:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, new LibraryFragment())
                                .commit();
                        return true;
                    case R.id.navigation_search:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, new SearchFragment())
                                .commit();
                        return true;
                    case R.id.navigation_premium:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, new PremiumFragment())
                                .commit();
                        return true;
                }
                return false;
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        //check if user online
        if (!isOnline()) {
            connectToInternet();
        } else {
            //TODO load data from internet here and send it to fragments
        }
    }

    /**
     * shows an AlertDialog to go to WIFI settings
     */
    private void connectToInternet() {

        // build title and message views for AlertDialog
        TextView title = new TextView(this);
        title.setText(R.string.network);
        title.setTextColor(getResources().getColor(android.R.color.white));
        title.setTextSize(24/*set test size to 24sp */);
        title.setPadding((int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding));
        TextView body = new TextView(this);
        body.setText(R.string.you_are_not_online);
        body.setTextSize(18/*set test size to 18sp */);
        body.setTextColor(getResources().getColor(android.R.color.white));
        body.setPadding((int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(getString(R.string.connect), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, getString(R.string.only_local_data_show), Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCustomTitle(title);
        dialog.setView(body);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.dark_gray);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(this.getResources().getColor(android.R.color.white));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(this.getResources().getColor(android.R.color.white));
    }

    /**
     * check if user is online
     *
     * @return true if user online otherwise return false
     */
    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnectedOrConnecting())
                return true;
        }
        return false;
    }

}
