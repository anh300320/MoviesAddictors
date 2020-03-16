package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import fundamental.MyAdapter;
import interfaces.ItemClickListener;
import interfaces.retrofitService;
import objects.Brief;
import objects.Movie;
import objects.Search;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    static String TAG = "hehehe";
    SearchView searchView;
    Button btnSearch;
    static String apikey = "939bff44";
    String movieName = new String();
    Brief[] briefs;
    //ArrayList<Movie> listMovie = new ArrayList<>();

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    retrofitService service = retrofit.create(retrofitService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Log.d(TAG, "onCreate: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovie(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //searchView.setOnSearchClickListener(new Sea);

        return true;
    }

    protected void findViewById(){
    }
    public void searchMovie(String movieName){
        Call<Search> callMovie = service.groupList(movieName, apikey);
        callMovie.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                Search search = response.body();
                briefs = search.getBriefs().toArray(new Brief[0]);
                int size = briefs.length;
                mAdapter = new MyAdapter(briefs);
                mAdapter.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        String imdbId = briefs[position].getImdbID().toString();
                        Intent intent = new Intent(getBaseContext(), MovieDetail.class);
                        //Call<Movie> callMovie = service.movieDetail(imdbId, "full", apikey);
                        intent.putExtra("imdbId", imdbId);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }
}
