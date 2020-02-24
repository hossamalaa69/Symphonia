package com.example.symphonia.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.symphonia.R;

public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        libraryViewModel =
                ViewModelProviders.of(this).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_your_library, container, false);
        final TextView textView = root.findViewById(R.id.text_library);
        libraryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}