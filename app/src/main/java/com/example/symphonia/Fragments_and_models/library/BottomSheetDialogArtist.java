
package com.example.symphonia.Fragments_and_models.library;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.example.symphonia.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialogArtist extends BottomSheetDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //inflating layout
        View view = View.inflate(getContext(), R.layout.bottom_sheet_artist_properties, null);
        bottomSheet.setContentView(view);

        bottomSheet.getWindow()
                .findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(R.drawable.background_bottom_sheet);

        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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

        return bottomSheet;
    }
}
