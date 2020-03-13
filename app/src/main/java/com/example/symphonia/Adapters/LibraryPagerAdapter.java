package com.example.symphonia.Adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.symphonia.Fragments_and_models.library.LibraryAlbumsFragment;
import com.example.symphonia.Fragments_and_models.library.LibraryArtistsFragment;
import com.example.symphonia.Fragments_and_models.library.LibraryPlaylistsFragment;
import com.example.symphonia.R;

public class LibraryPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.library_tab_playlists, R.string.library_tab_artists, R.string.library_tab_albums};
    private final Context mContext;

    public LibraryPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new LibraryPlaylistsFragment();
        } else if (position == 1) {
            return new LibraryArtistsFragment();
        } else if (position == 2) {
            return new LibraryAlbumsFragment();
        } else
            return new LibraryPlaylistsFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
