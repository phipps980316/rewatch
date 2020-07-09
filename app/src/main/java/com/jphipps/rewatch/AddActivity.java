package com.jphipps.rewatch;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements NetworkHandler.SearchResponseDelegate {
    private EditText searchterm;
    private Spinner option;
    private String type;

    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        this.setTitle("Search");

        searchterm = findViewById(R.id.search_bar);

        option = findViewById(R.id.options);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        option.setAdapter(adapter);

        RecyclerView recyclerView = findViewById(R.id.recyclerSearch);
        searchAdapter = new SearchAdapter(this, new ArrayList<Entry>());
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton floatingActionButton = findViewById(R.id.btnSearch);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchButtonClick();
            }
        });
    }

    public void onSearchButtonClick(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null) {
            View view = getCurrentFocus();
            assert view != null;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        Uri uri;
        if(option.getSelectedItemId()==0) {
            uri = Uri.parse("http://api.themoviedb.org/3/search/movie/?").buildUpon()
                    .appendQueryParameter("api_key", "5f5991348cf5867ddbd25024da90f315")
                    .appendQueryParameter("query", searchterm.getText().toString())
                    .appendQueryParameter("include_adult", "false")
                    .build();
            type = "Movie";
        }
        else {
            uri = Uri.parse("http://api.themoviedb.org/3/search/tv/?").buildUpon()
                    .appendQueryParameter("api_key", "5f5991348cf5867ddbd25024da90f315")
                    .appendQueryParameter("query", searchterm.getText().toString())
                    .appendQueryParameter("include_adult", "false")
                    .build();
            type = "Series";
        }

        String url = uri.toString();
        JSONHandler jsonHandler = new JSONHandler();
        jsonHandler.delegate = this;
        jsonHandler.execute(url);


    }

    @Override
    public void searchTaskFinished(ArrayList<Entry> fetchedData) {
        searchAdapter.updateData(fetchedData, type);
        if(fetchedData.size() == 0) Toast.makeText(this, "No Search Results", Toast.LENGTH_LONG).show();
    }
}
