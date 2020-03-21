package com.example.symphonia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.Entities.Track;

import java.util.ArrayList;

public class PremiumAdapter extends RecyclerView.Adapter<PremiumAdapter.Holder> {


    private ArrayList<String> featuresFree = new ArrayList<>();
    private ArrayList<String> featuresPrem = new ArrayList<>();
    private Context mContext;

    public PremiumAdapter(ArrayList<String> featuresFree, ArrayList<String> featuresPrem, Context mContext) {
        this.featuresFree = featuresFree;
        this.featuresPrem = featuresPrem;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_premium, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return featuresFree.size();
    }


    public class Holder extends RecyclerView.ViewHolder  {

        TextView t1;
        TextView t2;

        public Holder(@NonNull View itemView) {
            super(itemView);
            t1 = (TextView) itemView.findViewById(R.id.text_free);
            t2 = (TextView) itemView.findViewById(R.id.text_prem);
        }

        public void bind(int position) {
            String txtFree = featuresFree.get(position);
            String txtPrem = featuresPrem.get(position);
            t1.setText(txtFree);
            t2.setText(txtPrem);
        }
    }

}

