package com.example.symphonia.Fragments_and_models.library;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Adapters.RvListArtistSearchAdapter;
import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Copyright;
import com.example.symphonia.Helpers.SnackbarHelper;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * Fragment to show all the data of a specific album
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class AlbumFragment extends Fragment implements RvListArtistSearchAdapter.ListItemClickListener
, BottomSheetDialogAlbumFragment.BottomSheetListener {

    /**
     * Final value to get the album id
     */
    private static final String ALBUM_ID = "ALBUM_ID";
    /**
     * object to control between mock service and server
     */
    private ServiceController serviceController;
    /**
     * save album icon
     */
    private ImageView saveIcon;
    /**
     * the container of the view
     */
    private NestedScrollView viewContainer;
    /**
     * object from album contains all the data
     */
    private Album mAlbum;
    /**
     * the first y when the user puts his finger on the screen
     * used to animate the touch of the views
     */
    private float firstY = 0;
    /**
     * the current touched view
     */
    private View touchedView = null;

    public AlbumFragment() {
        // Required empty public constructor
    }

    public AlbumFragment(Album mAlbum){
        this.mAlbum = mAlbum;
    }

    /**
     * fill all the view with album data
     *
     * @param inflater inflate the fragment
     * @param container viewgroup of the fragment
     * @param savedInstanceState saved data
     * @return fragment view
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_album, container, false);

        serviceController = ServiceController.getInstance();

        ImageView backIcon = rootView.findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        saveIcon = rootView.findViewById(R.id.save_icon);
        if(serviceController.checkUserSavedAlbums(getContext(),
                new ArrayList<String>(Collections.singletonList(mAlbum.getAlbumId()))).get(0))
            saveIcon.setImageResource(R.drawable.ic_favorite_green_24dp);
        else
            saveIcon.setImageResource(R.drawable.ic_favorite_border_white_24dp);

        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(serviceController.checkUserSavedAlbums(getContext(),
                        new ArrayList<String>(Collections.singletonList(mAlbum.getAlbumId()))).get(0)){
                    saveIcon.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    serviceController.removeAlbumsForUser(getContext(),
                            new ArrayList<String>(Collections.singletonList(mAlbum.getAlbumId())));

                    Snackbar snack = Snackbar.make(viewContainer, R.string.remove_album_snackbar_text, Snackbar.LENGTH_LONG);
                    SnackbarHelper.configSnackbar(getContext(), snack, R.drawable.custom_snackbar, Color.BLACK);
                    snack.show();

                }
                else{
                    saveIcon.setImageResource(R.drawable.ic_favorite_green_24dp);
                    serviceController.saveAlbumsForUser(getContext(),
                            new ArrayList<String>(Collections.singletonList(mAlbum.getAlbumId())));

                    Snackbar snack = Snackbar.make(viewContainer, R.string.add_album_snackbar_text, Snackbar.LENGTH_LONG);
                    SnackbarHelper.configSnackbar(getContext(), snack, R.drawable.custom_snackbar, Color.BLACK);
                    snack.show();
                }
            }
        });

        ImageView settingsIcon = rootView.findViewById(R.id.settings_icon);
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogAlbumFragment bottomSheet = new BottomSheetDialogAlbumFragment(AlbumFragment.this);
                Bundle arguments = new Bundle();
                arguments.putString( ALBUM_ID , mAlbum.getAlbumId());
                bottomSheet.setArguments(arguments);
                bottomSheet.show(((MainActivity)getActivity()).getSupportFragmentManager(), bottomSheet.getTag());
            }
        });

        ImageView albumImage = rootView.findViewById(R.id.image_album);
        albumImage.setImageBitmap(mAlbum.getAlbumImage());

        viewContainer = rootView.findViewById(R.id.container);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Drawable drawable = Utils.createAlbumBackground(getContext(), mAlbum.getAlbumImage());
            viewContainer.setBackground(drawable);
        }

        final TextView albumTracks = rootView.findViewById(R.id.album_tracks);
        final Button shuffleButton = rootView.findViewById(R.id.button_shuffle);

        albumTracks.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(v, 0.98f, 0.8f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(v);
                        return true;
                }

                return false;
            }
        });

        shuffleButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(v, 0.95f, 0.5f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(v);
                        return true;
                }
                return false;
            }

        });

        viewContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        firstY = currentY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(currentY - firstY) > 3){
                            Utils.cancelTouchAnimation(albumTracks);
                            Utils.cancelTouchAnimation(shuffleButton);
                        }
                }
                return false;
            }
        });

        TextView albumName = rootView.findViewById(R.id.text_album_name);
        TextView toolbarTitle = rootView.findViewById(R.id.title_toolbar);
        albumName.setText(mAlbum.getAlbumName());
        toolbarTitle.setText(mAlbum.getAlbumName());

        ArrayList<Artist> mAlbumArtists = mAlbum.getAlbumArtists();
        final RecyclerView mArtistsList = rootView.findViewById(R.id.rv_artists_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mArtistsList.setLayoutManager(layoutManager);
        RvListArtistSearchAdapter mAdapter = new RvListArtistSearchAdapter(mAlbumArtists, this);
        mArtistsList.setAdapter(mAdapter);

        mArtistsList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                float currentX = e.getX();
                float currentY = e.getY();

                View newTouchedView = mArtistsList.findChildViewUnder(currentX, currentY);
                if(touchedView != null && touchedView != newTouchedView){
                    Utils.cancelTouchAnimation(touchedView);
                }

                touchedView = newTouchedView;
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(touchedView, 0.98f, 0.5f);
                        firstY = currentY;
                        break;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(touchedView);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(currentY - firstY) > 3){
                            Utils.cancelTouchAnimation(touchedView);
                        }
                        break;
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        String albumType = mAlbum.getAlbumType();
        albumType = albumType.substring(0, 1).toUpperCase() + albumType.substring(1);
        StringBuilder typeArtistYearText = new StringBuilder();
        typeArtistYearText.append(albumType).append(" by ");

        for (int i = 0; i < mAlbumArtists.size(); i++) {
            typeArtistYearText.append(mAlbumArtists.get(i).getArtistName());
            if(i != mAlbumArtists.size() - 1)
                typeArtistYearText.append(", ");
            else
                typeArtistYearText.append(" • ");
        }
        typeArtistYearText.append(getYear(mAlbum.getReleaseDate()));

        TextView typeArtistYearTextView = rootView.findViewById(R.id.text_type_artist_year);
        typeArtistYearTextView.setText(typeArtistYearText.toString());

        TextView releaseDate = rootView.findViewById(R.id.text_release_date);
        releaseDate.setText(formatDate(mAlbum.getReleaseDate()));

        TextView copyrightsTextView = rootView.findViewById(R.id.text_copyrights);
        copyrightsTextView.setText(getCopyrightsString(mAlbum.getCopyrights()));




        return rootView;
    }

    /**
     * handles the click of the recyclerview items
     * the artists of the album
     * @param clickedItemIndex index of the clicked item
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

    /**
     * takes the date string in format yyyy-MM-dd (e.g. 2020-03-22)
     * and returns the year in a string (2020 in this example)
     *
     * @param date string date in format yyyy-MM-dd
     * @return year string
     */
    private String getYear(String date) {
        String[] dateComponents = date.split("-");
        return dateComponents[0];
    }

    /**
     * takes a string of date in format yyyy-MM-dd (e.g. 2020-03-22)
     * and return the string in format MMMM dd, yyyy (e.g. March 22, 2020)
     *
     * @param date string date in format yyyy-MM-dd
     * @return date string in format MMMM dd, yyyy
     */
    @SuppressLint("SimpleDateFormat")
    private String formatDate(String date) {
        Date dateObject = null;
        try {
            dateObject = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        assert dateObject != null;
        return dateFormat.format(dateObject);
    }

    /**
     * takes the copyrights array and convert it
     * to a simple string to show in the view
     *
     * @param copyrights array of copyright object
     * @return string copyrights
     */
    private String getCopyrightsString(ArrayList<Copyright> copyrights){
        StringBuilder copyrightsText = new StringBuilder();
        boolean c = false;
        boolean p = false;
        String text = "";
        for (Copyright copyright: copyrights) {
            text = copyright.getCopyrightText();
            if(copyright.getType().equals("C"))
                c = true;
            else if(copyright.getType().equals("P"))
                p = true;
        }
        if(c)
            copyrightsText.append("© ");
        if(p)
            copyrightsText.append("℗ ");

        copyrightsText.append(text);

        return copyrightsText.toString();
    }

    /**
     * update the ui of the fragment
     * called when the user clicks the liked layout in the bottom sheet
     */
    @Override
    public void onLikedLayoutClicked() {
        if(serviceController.checkUserSavedAlbums(getContext(),
                new ArrayList<String>(Collections.singletonList(mAlbum.getAlbumId()))).get(0)) {
            saveIcon.setImageResource(R.drawable.ic_favorite_green_24dp);
            Snackbar snack = Snackbar.make(viewContainer, R.string.add_album_snackbar_text, Snackbar.LENGTH_LONG);
            SnackbarHelper.configSnackbar(getContext(), snack, R.drawable.custom_snackbar, Color.BLACK);
            snack.show();
        } else {
            saveIcon.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            Snackbar snack = Snackbar.make(viewContainer, R.string.remove_album_snackbar_text, Snackbar.LENGTH_LONG);
            SnackbarHelper.configSnackbar(getContext(), snack, R.drawable.custom_snackbar, Color.BLACK);
            snack.show();
        }
    }
}
