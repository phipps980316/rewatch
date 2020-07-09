package com.jphipps.rewatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {

    private ArrayList<Entry> entries;
    private LayoutInflater inflater;

    class CollectionViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final ImageView image;
        final ImageView netflix;
        final ImageView disney;
        final ImageView prime;
        final ImageView crunchyroll;
        final ImageView bluray;
        final ImageView bbc;
        final ImageView itv;
        final ImageView all4;
        final ImageView my5;
        final ImageView uktv;

        final CollectionAdapter adapter;

        CollectionViewHolder(View itemView, CollectionAdapter adapter) {
            super(itemView);
            title = itemView.findViewById(R.id.entryName);
            image = itemView.findViewById(R.id.entryImage);
            netflix = itemView.findViewById(R.id.entryNetflix);
            disney = itemView.findViewById(R.id.entryDisney);
            prime = itemView.findViewById(R.id.entryPrime);
            crunchyroll = itemView.findViewById(R.id.entryCrunchyroll);
            bluray = itemView.findViewById(R.id.entryBluray);
            bbc = itemView.findViewById(R.id.entryBBC);
            itv = itemView.findViewById(R.id.entryITV);
            all4 = itemView.findViewById(R.id.entryALL4);
            my5 = itemView.findViewById(R.id.entryMY5);
            uktv = itemView.findViewById(R.id.entryUKTV);
            this.adapter = adapter;
        }
    }

    //Constructor to set the recycler view's inflater and data source
    CollectionAdapter(Context context, ArrayList<Entry> entries) {
        inflater = LayoutInflater.from(context);
        this.entries = entries;
    }

    //Function to add a new entry to the recycler view by inflating the view
    @NonNull
    public CollectionAdapter.CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.collection_item, parent, false);
        return new CollectionViewHolder(view, this);
    }

    //Function to set the information in the recycler view entry based on the position value given as a parameter
    public void onBindViewHolder(@NonNull final CollectionViewHolder holder, int position) {
        final Entry entry = entries.get(position);
        String text = entry.getName() + " (" + entry.getYear() + ")";
        holder.title.setText(text);

        ImageUtility imageUtility = new ImageUtility();
        Bitmap bitmap = imageUtility.uncompressImage(entry.getImage());
        if(bitmap != null) holder.image.setImageBitmap(bitmap);
        else holder.image.setImageResource(R.drawable.imageunavailable);

        if(entry.getNetflix() == 0) holder.netflix.setVisibility(View.GONE);
        else holder.netflix.setVisibility(View.VISIBLE);

        if(entry.getDisney() == 0) holder.disney.setVisibility(View.GONE);
        else holder.disney.setVisibility(View.VISIBLE);

        if(entry.getPrimeVideo() == 0) holder.prime.setVisibility(View.GONE);
        else holder.prime.setVisibility(View.VISIBLE);

        if(entry.getCrunchyroll() == 0) holder.crunchyroll.setVisibility(View.GONE);
        else holder.crunchyroll.setVisibility(View.VISIBLE);

        if(entry.getBluray() == 0) holder.bluray.setVisibility(View.GONE);
        else holder.bluray.setVisibility(View.VISIBLE);

        if(entry.getBBC() == 0) holder.bbc.setVisibility(View.GONE);
        else holder.bbc.setVisibility(View.VISIBLE);

        if(entry.getITV() == 0) holder.itv.setVisibility(View.GONE);
        else holder.itv.setVisibility(View.VISIBLE);

        if(entry.getAll4() == 0) holder.all4.setVisibility(View.GONE);
        else holder.all4.setVisibility(View.VISIBLE);

        if(entry.getMy5() == 0) holder.my5.setVisibility(View.GONE);
        else holder.my5.setVisibility(View.VISIBLE);

        if(entry.getUKTV() == 0) holder.uktv.setVisibility(View.GONE);
        else holder.uktv.setVisibility(View.VISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("entry", entry);
                context.startActivity(intent);
            }
        });
    }

    //Function to get the number of items in the recycler view
    public int getItemCount() {
        return entries.size();
    }

    //Function to update the data in the recycler view and refresh the view so that the new data is displayed
    void updateData(ArrayList<Entry> entries) {
        this.entries.clear();
        this.entries.addAll(entries);
        notifyDataSetChanged();
    }

    ArrayList<Entry> getData(){
        return this.entries;
    }
}