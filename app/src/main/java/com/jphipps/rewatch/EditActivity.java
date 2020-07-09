package com.jphipps.rewatch;

import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity{
    private Entry entry;
    private DatabaseHelper databaseHelper;

    private CheckBox netflix;
    private CheckBox disney;
    private CheckBox prime;
    private CheckBox crunchyroll;
    private CheckBox bluray;
    private CheckBox bbc;
    private CheckBox itv;
    private CheckBox all4;
    private CheckBox my5;
    private CheckBox uktv;
    private CheckBox collection;
    private CheckBox watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        entry = (Entry) getIntent().getSerializableExtra("entry");
        TextView title = findViewById(R.id.editTitle);

        databaseHelper = new DatabaseHelper(this);

        String text = entry.getName() + " (" + entry.getYear() + ")";
        title.setText(text);
        this.setTitle(entry.getName());

        ImageView image = findViewById(R.id.editImage);
        ImageUtility imageUtility = new ImageUtility();

        Bitmap bitmap = imageUtility.uncompressImage(entry.getImage());
        if(bitmap != null) image.setImageBitmap(bitmap);
        else image.setImageResource(R.drawable.imageunavailable);

        netflix = findViewById(R.id.editNetflix);
        if(entry.getNetflix() == 0) netflix.setChecked(false);
        else netflix.setChecked(true);

        disney = findViewById(R.id.editDisney);
        if(entry.getDisney() == 0) disney.setChecked(false);
        else disney.setChecked(true);

        prime = findViewById(R.id.editPrime);
        if(entry.getPrimeVideo() == 0) prime.setChecked(false);
        else prime.setChecked(true);

        crunchyroll = findViewById(R.id.editCrunchyroll);
        if(entry.getCrunchyroll() == 0) crunchyroll.setChecked(false);
        else crunchyroll.setChecked(true);

        bluray = findViewById(R.id.editBluray);
        if(entry.getBluray() == 0) bluray.setChecked(false);
        else bluray.setChecked(true);

        bbc = findViewById(R.id.editBBC);
        if(entry.getBBC() == 0) bbc.setChecked(false);
        else bbc.setChecked(true);

        itv = findViewById(R.id.editITV);
        if(entry.getITV() == 0) itv.setChecked(false);
        else itv.setChecked(true);

        all4 = findViewById(R.id.editALL4);
        if(entry.getAll4() == 0) all4.setChecked(false);
        else all4.setChecked(true);

        my5 = findViewById(R.id.editMy5);
        if(entry.getMy5() == 0) my5.setChecked(false);
        else my5.setChecked(true);

        uktv = findViewById(R.id.editUKTV);
        if(entry.getUKTV() == 0) uktv.setChecked(false);
        else uktv.setChecked(true);

        collection = findViewById(R.id.editCollection);
        if(entry.getCollection() == 0) collection.setChecked(false);
        else collection.setChecked(true);

        watchlist = findViewById(R.id.editWatchlist);
        if(entry.getWatchlist() == 0) watchlist.setChecked(false);
        else watchlist.setChecked(true);

        FloatingActionButton saveButton = findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClick();
            }
        });

        FloatingActionButton deleteButton = findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteButtonClick();
            }
        });
    }

    private boolean validStorageSelection(){
        return (collection.isChecked() || watchlist.isChecked());
    }

    private void onSaveButtonClick() {
        if (validStorageSelection()) {
            if(netflix.isChecked()) entry.setNetflix(1);
            else entry.setNetflix(0);

            if(disney.isChecked()) entry.setDisney(1);
            else entry.setDisney(0);

            if(prime.isChecked()) entry.setPrimeVideo(1);
            else entry.setPrimeVideo(0);

            if(crunchyroll.isChecked()) entry.setCrunchyroll(1);
            else entry.setCrunchyroll(0);

            if(bluray.isChecked()) entry.setBluray(1);
            else entry.setBluray(0);

            if(bbc.isChecked()) entry.setBBC(1);
            else entry.setBBC(0);

            if(itv.isChecked()) entry.setITV(1);
            else entry.setITV(0);

            if(all4.isChecked()) entry.setAll4(1);
            else entry.setAll4(0);

            if(my5.isChecked()) entry.setMy5(1);
            else entry.setMy5(0);

            if(uktv.isChecked()) entry.setUKTV(1);
            else entry.setUKTV(0);

            if(collection.isChecked()) entry.setCollection(1);
            else entry.setCollection(0);

            if(watchlist.isChecked()) entry.setWatchlist(1);
            else entry.setWatchlist(0);

            if (entry.getID() > -1) {
                if (entry.getType().equals(DatabaseHelper.MOVIE_TYPE)) {
                    databaseHelper.updateEntry(DatabaseHelper.MOVIES_TABLE, entry);
                } else if (entry.getType().equals(DatabaseHelper.TV_TYPE)) {
                    databaseHelper.updateEntry(DatabaseHelper.TV_TABLE, entry);
                }
            } else {
                if (entry.getType().equals(DatabaseHelper.MOVIE_TYPE)) {
                    databaseHelper.newEntry(DatabaseHelper.MOVIES_TABLE, entry);
                } else if (entry.getType().equals(DatabaseHelper.TV_TYPE)) {
                    databaseHelper.newEntry(DatabaseHelper.TV_TABLE, entry);
                }
            }

            Toast.makeText(this, "Entry Saved", Toast.LENGTH_LONG).show();
            finish();
        } else if (!validStorageSelection()) {
            Toast.makeText(this, "One Storage Option Must Be Selected", Toast.LENGTH_LONG).show();
        }
    }

    private void onDeleteButtonClick(){
        if(entry.getID() > -1){
            if(entry.getType().equals(DatabaseHelper.MOVIE_TYPE)){
                databaseHelper.deleteEntry(DatabaseHelper.MOVIES_TABLE, entry);
            }else if(entry.getType().equals(DatabaseHelper.TV_TYPE)){
                databaseHelper.deleteEntry(DatabaseHelper.TV_TABLE, entry);
            }
            Toast.makeText(this, "Entry Deleted", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
