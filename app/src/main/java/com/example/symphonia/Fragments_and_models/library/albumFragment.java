package com.example.symphonia.Fragments_and_models.library;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.symphonia.Adapters.RvListArtistSearchAdapter;
import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Copyright;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class albumFragment extends Fragment implements RvListArtistSearchAdapter.ListItemClickListener {

    private Album album;
    private RecyclerView artistsList;
    private RvListArtistSearchAdapter adapter;
    private ArrayList<Artist> albumArtists;

    public albumFragment() {
        // Required empty public constructor
    }

    public albumFragment(Album album){
        this.album = album;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_album, container, false);

        ImageView albumImage = rootView.findViewById(R.id.album_image);
        albumImage.setImageBitmap(album.getAlbumImage());

        TextView albumName = rootView.findViewById(R.id.album_name);
        TextView toolbarTitle = rootView.findViewById(R.id.toolbar_title);
        albumName.setText(album.getAlbumName());
        toolbarTitle.setText(album.getAlbumName());

        albumArtists = album.getAlbumArtists();
        artistsList = rootView.findViewById(R.id.rv_artists_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        artistsList.setLayoutManager(layoutManager);
        adapter = new RvListArtistSearchAdapter(albumArtists, this);
        artistsList.setAdapter(adapter);

        String albumType = album.getAlbumType();
        albumType = albumType.substring(0, 1).toUpperCase() + albumType.substring(1);
        StringBuilder typeArtistYearText = new StringBuilder();
        typeArtistYearText.append(albumType).append(" by ");

        for (int i = 0; i < albumArtists.size(); i++) {
            typeArtistYearText.append(albumArtists.get(i).getArtistName());
            if(i != albumArtists.size() - 1)
                typeArtistYearText.append(", ");
            else
                typeArtistYearText.append(" • ");
        }
        typeArtistYearText.append(getYear(album.getReleaseDate()));

        TextView typeArtistYearTextView = rootView.findViewById(R.id.type_artist_year);
        typeArtistYearTextView.setText(typeArtistYearText.toString());

        TextView releaseDate = rootView.findViewById(R.id.release_date);
        releaseDate.setText(formatDate(album.getReleaseDate()));

        TextView copyrightsTextView = rootView.findViewById(R.id.copyrights);
        copyrightsTextView.setText(getCopyrightsString(album.getCopyrights()));

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
