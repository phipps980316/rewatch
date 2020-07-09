package com.jphipps.rewatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private CollectionAdapter collectionAdapter;
    private DatabaseHelper databaseHelper;
    EditText search;

    boolean movieSelected = true;
    boolean tvSelected = false;

    boolean collectionSelected = true;
    boolean watchlistSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("ReWatch");

        databaseHelper = new DatabaseHelper(this);

        TabLayout tabLayout = findViewById(R.id.topNavigation);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String option = Objects.requireNonNull(tab.getText()).toString();
                if (option.equals("Collection")) {
                    collectionSelected = true;
                    watchlistSelected = false;
                    search.setText("");
                    updateList();
                }
                else if (option.equals("Watchlist")) {
                    collectionSelected = false;
                    watchlistSelected = true;
                    search.setText("");
                    updateList();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        search = findViewById(R.id.collectionSearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchEntry(search.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        FloatingActionButton addButton = findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton randomButton = findViewById(R.id.btnRandom);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomEntry();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerCollection);
        collectionAdapter = new CollectionAdapter(this, new ArrayList<Entry>());
        recyclerView.setAdapter(collectionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView navigation = findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_film:
                        movieSelected = true;
                        tvSelected = false;
                        search.setText("");
                        updateList();
                        return true;
                    case R.id.navigation_tv:
                        movieSelected = false;
                        tvSelected = true;
                        search.setText("");
                        updateList();
                        return true;
                }
                return false;
            }
        });
        navigation.setSelectedItemId(0);
        updateList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
        search.setText("");
    }

    private ArrayList<Entry> updateList(){
        ArrayList<Entry> entries = new ArrayList<>();
        if(movieSelected){
            ArrayList<Entry> results = databaseHelper.fetchAllEntries(DatabaseHelper.MOVIES_TABLE, DatabaseHelper.MOVIE_TYPE);
            if(collectionSelected){
                for(int i = 0; i < results.size(); i++){
                    if(results.get(i).getCollection() == 1) entries.add(results.get(i));
                }
            }
            else if(watchlistSelected){
                for(int i = 0; i < results.size(); i++){
                    if(results.get(i).getWatchlist() == 1) entries.add(results.get(i));
                }
            }
        }
        else if(tvSelected){
            ArrayList<Entry> results = databaseHelper.fetchAllEntries(DatabaseHelper.TV_TABLE, DatabaseHelper.TV_TYPE);
            if(collectionSelected){
                for(int i = 0; i < results.size(); i++){
                    if(results.get(i).getCollection() == 1) entries.add(results.get(i));
                }
            }
            else if(watchlistSelected){
                for(int i = 0; i < results.size(); i++){
                    if(results.get(i).getWatchlist() == 1) entries.add(results.get(i));
                }
            }
        }

        Collections.sort(entries);
        collectionAdapter.updateData(entries);
        return entries;
    }

    private void searchEntry(String term){
        ArrayList<Entry> entries = updateList();
        ArrayList<Entry> results = new ArrayList<>();
        if(!term.equals("")){
            for(int i = 0; i < entries.size(); i++){
                if(entries.get(i).getName().toLowerCase().contains(term.toLowerCase())){
                    results.add(entries.get(i));
                }
            }
        }
        else results = entries;
        Collections.sort(results);
        collectionAdapter.updateData(results);
    }

    private void randomEntry(){
        ArrayList<Entry> entries = collectionAdapter.getData();
        if(entries.size() > 0){
            Random r = new Random();
            int randomIndex = r.nextInt(entries.size());
            Entry entry = entries.get(randomIndex);

            ImageView image = new ImageView(this);
            ImageUtility imageUtility = new ImageUtility();
            Bitmap bitmap = imageUtility.uncompressImage(entry.getImage());
            if(bitmap != null) image.setImageBitmap(bitmap);
            else image.setImageResource(R.drawable.imageunavailable);

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this).
                            setMessage("Random Pick: " + entry.getName()).
                            setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).
                            setNegativeButton("Choose Another", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    randomEntry();
                                }
                            }).
                            setView(image);
            builder.create().show();
        }
        else Toast.makeText(this, "Nothing To Choose From", Toast.LENGTH_LONG).show();
    }
}
