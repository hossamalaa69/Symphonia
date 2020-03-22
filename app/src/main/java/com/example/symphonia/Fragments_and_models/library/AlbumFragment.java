package com.example.symphonia.Fragments_and_models.library;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.symphonia.Adapters.RvListArtistSearchAdapter;
import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Copyright;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment implements RvListArtistSearchAdapter.ListItemClickListener {

    private Album mAlbum;
    private RecyclerView mArtistsList;
    private RvListArtistSearchAdapter mAdapter;
    private ArrayList<Artist> mAlbumArtists;

    public AlbumFragment() {
        // Required empty public constructor
    }

    public AlbumFragment(Album album){
        this.mAlbum = album;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_album, container, false);

        ImageView backIcon = rootView.findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ImageView albumImage = rootView.findViewById(R.id.image_album);
        albumImage.setImageBitmap(mAlbum.getAlbumImage());

        NestedScrollView viewContainer = rootView.findViewById(R.id.container);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Drawable drawable = Utils.createAlbumBackground(getContext(), mAlbum.getAlbumImage());
            viewContainer.setBackground(drawable);
        }


        TextView albumName = rootView.findViewById(R.id.text_album_name);
        TextView toolbarTitle = rootView.findViewById(R.id.title_toolbar);
        albumName.setText(mAlbum.getAlbumName());
        toolbarTitle.setText(mAlbum.getAlbumName());

        mAlbumArtists = mAlbum.getAlbumArtists();
        mArtistsList = rootView.findViewById(R.id.rv_artists_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mArtistsList.setLayoutManager(layoutManager);
        mAdapter = new RvListArtistSearchAdapter(mAlbumArtists, this);
        mArtistsList.setAdapter(mAdapter);

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

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

    private String getYear(String date) {
        String[] dateComponents = date.split("-");
        return dateComponents[0];
    }

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
}
