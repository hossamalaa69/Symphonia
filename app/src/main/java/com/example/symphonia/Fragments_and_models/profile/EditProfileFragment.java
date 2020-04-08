package com.example.symphonia.Fragments_and_models.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;

public class EditProfileFragment extends Fragment {
    private Container profile;
    private EditText editText;
    private ImageView imageView;
    private ImageView backToProfile;
    public EditProfileFragment(Container p){profile=p;}
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.edit_profile, container, false);
        editText=root.findViewById(R.id.edit_text_profile);
        imageView=root.findViewById(R.id.profile_img);
        backToProfile=root.findViewById(R.id.img_back_Edit_profile);
        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
        editText.setText(profile.getCat_Name());
        imageView.setImageBitmap(profile.getImg_Res());
        return root;
    }
}
