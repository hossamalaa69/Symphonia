package com.example.symphonia.Fragments_and_models.profile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.symphonia.Entities.Container;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialogProfile extends BottomSheetDialogFragment {

    private Container profile;
    private int chooseLayout;

    private LinearLayout options1;
    private View divider;
    private LinearLayout following;
    private LinearLayout follow;
    private LinearLayout hide;
    private LinearLayout share;
    private LinearLayout homeScreen;
    private LinearLayout options2;
    private LinearLayout whatsAppShare;
    private LinearLayout facebookShare;
    private LinearLayout messengerShare;
    private LinearLayout smsShare;
    private LinearLayout linkShare;
    private LinearLayout moreShare;

    public BottomSheetDialogProfile(Container container,int i) {
        profile=container;
        chooseLayout=i;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //inflating layout
        View view = View.inflate(getContext(), R.layout.bottom_sheet_dialog_profile, null);
        bottomSheet.setContentView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



        bottomSheet.getWindow()
                .findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(R.drawable.background_bottom_sheet);


        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        TextView profileName = view.findViewById(R.id.tv_profile_playlist_name);
        profileName.setText(profile.getCat_Name());
        ImageView profileImage = view.findViewById(R.id.image_profile_or_playlist);
        profileImage.setImageBitmap(profile.getImg_Res());

        LinearLayout imageFrame = view.findViewById(R.id.image_frame);
        ImageView symphoniaImage = view.findViewById(R.id.image_symphonia);
        ImageView soundWave = view.findViewById(R.id.image_sound_wave);

        int dominantColor = Utils.getDominantColor(profile.getImg_Res());
        imageFrame.setBackgroundColor(dominantColor);
        if(!Utils.isColorDark(dominantColor)){
            symphoniaImage.setColorFilter(Color.rgb(0, 0, 0));
            soundWave.setColorFilter(Color.rgb(0, 0, 0));
        }
        else{
            symphoniaImage.setColorFilter(Color.rgb(255, 255, 255));
            soundWave.setColorFilter(Color.rgb(255, 255, 255));
        }

        options1=view.findViewById(R.id.list_of_options1);
        divider=view.findViewById(R.id.divider);
        following = view.findViewById(R.id.layout_following);
        follow=view.findViewById(R.id.layout_follow);
        hide = view.findViewById(R.id.layout_hide_playlist);
        share = view.findViewById(R.id.layout_share);
        homeScreen = view.findViewById(R.id.layout_home_screen);

        options2=view.findViewById(R.id.list_of_options2);
        whatsAppShare=view.findViewById(R.id.whatsapp_share);
        facebookShare=view.findViewById(R.id.facebook_share);
        messengerShare=view.findViewById(R.id.messenger_share);
        smsShare=view.findViewById(R.id.sms_share);
        linkShare=view.findViewById(R.id.link_share);
        moreShare=view.findViewById(R.id.more_share);

        if(chooseLayout==1){
            divider.setVisibility(View.GONE);
            following.setVisibility(View.GONE);
            follow.setVisibility(View.GONE);
            hide.setVisibility(View.GONE);
            homeScreen.setVisibility(View.GONE);
            options2.setVisibility(View.GONE);
        }
        else if(chooseLayout==2) {
            options1.setVisibility(View.GONE);
            options2.setVisibility(View.VISIBLE);
        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.dismiss();
                BottomSheetDialogProfile bottomSheet = new BottomSheetDialogProfile(profile,2);
                assert getParentFragmentManager() != null;
                bottomSheet.show(getParentFragmentManager(),bottomSheet.getTag());

            }
        });

        whatsAppShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast toast=Toast.makeText(getContext(),"no whatsapp",Toast.LENGTH_SHORT);
                }
            }
        });
        messengerShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent massengerIntent = new Intent(Intent.ACTION_SEND);
                massengerIntent.setType("text/plain");
                massengerIntent.setPackage("com.facebook.orca");
                massengerIntent.putExtra(Intent.EXTRA_TEXT, "https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                try {
                    startActivity(massengerIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast toast=Toast.makeText(getContext(),"no massenger",Toast.LENGTH_SHORT);
                }
            }
        });
        facebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent1 = new Intent();
                    intent1.setPackage("com.facebook.katana");
                    intent1.setAction("android.intent.action.SEND");
                    intent1.setType("text/plain");
                    intent1.putExtra(Intent.EXTRA_TEXT, "https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                    startActivity(intent1);
                } catch (Exception e) {
                    Toast toast=Toast.makeText(getContext(),"no facebook app",Toast.LENGTH_SHORT);
                }
            }
        });
        smsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("sms_body","https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                smsIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(smsIntent);
                }catch (Exception e){
                    Toast toast=Toast.makeText(getContext(),"no sms app",Toast.LENGTH_SHORT);
                }
            }
        });
        linkShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", "https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                clipboard.setPrimaryClip(clip);
            }
        });
        moreShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                try {
                    startActivity(shareIntent);
                }catch (Exception e){
                    Toast toast=Toast.makeText(getContext(),"no sharing app",Toast.LENGTH_SHORT);
                }
            }
        });

        return bottomSheet;
    }
}

