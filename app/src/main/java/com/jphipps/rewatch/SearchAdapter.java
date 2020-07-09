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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private ArrayList<Entry> entries;
    private String type;
    private LayoutInflater inflater;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final ImageView image;
        final SearchAdapter adapter;

        SearchViewHolder(View itemView, SearchAdapter adapter) {
            super(itemView);
            title = itemView.findViewById(R.id.itemName);
            image = itemView.findViewById(R.id.itemImage);
            this.adapter = adapter;
        }
    }

    SearchAdapter(Context context, ArrayList<Entry> entries) {
        inflater = LayoutInflater.from(context);
        this.entries = entries;
    }

    @NonNull
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_item, parent, false);
        return new SearchViewHolder(view, this);
    }

    public void onBindViewHolder(@NonNull final SearchViewHolder holder, int position) {
        final ImageUtility imageUtility = new ImageUtility();
        final Entry entry = entries.get(position);
        entry.setType(type);
        String text = entry.getName() + " (" + entry.getYear() + ")";
        holder.title.setText(text);
        new ImageTask(new ImageTask.ImageResponseDelegate() {
            @Override
            public void imageTaskFinished(Bitmap bitmap) {
                entry.setImage(imageUtility.compressImage(bitmap));
                if(bitmap != null) holder.image.setImageBitmap(bitmap);
                else holder.image.setImageResource(R.drawable.imageunavailable);
            }
        }).execute(entry.getImageURL());

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

    public int getItemCount() {
        return entries.size();
    }

    void updateData(ArrayList<Entry> entries, String type) {
        this.entries.clear();
        this.entries.addAll(entries);
        this.type = type;
        notifyDataSetChanged();
    }
}