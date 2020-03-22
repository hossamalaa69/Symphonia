package com.example.symphonia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;

import java.util.ArrayList;

public class PremiumAdapter extends RecyclerView.Adapter<PremiumAdapter.Holder> {


    private ArrayList<String> mFeaturesFree = new ArrayList<>();
    private ArrayList<String> mFeaturesPrem = new ArrayList<>();
    private Context mContext;

    public PremiumAdapter(ArrayList<String> mFeaturesFree, ArrayList<String> mFeaturesPrem, Context mContext) {
        this.mFeaturesFree = mFeaturesFree;
        this.mFeaturesPrem = mFeaturesPrem;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_premium, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mFeaturesFree.size();
    }


    public class Holder extends RecyclerView.ViewHolder  {

        TextView text_view_free;
        TextView text_view_premium;

        public Holder(@NonNull View itemView) {
            super(itemView);
            text_view_free = (TextView) itemView.findViewById(R.id.text_free);
            text_view_premium = (TextView) itemView.findViewById(R.id.text_prem);
        }

        public void bind(int position) {
            String txtFree = mFeaturesFree.get(position);
            String txtPrem = mFeaturesPrem.get(position);
            text_view_free.setText(txtFree);
            text_view_premium.setText(txtPrem);
        }
    }
}

