package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import interfaces.retrofitService;
import objects.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetail extends AppCompatActivity {
    static String TAG = "hehehe";
    private String imdbId;
    final private String apikey = "939bff44";

    ImageView imgPoster;
    TextView tvTitle;
    TextView tvYear;
    TextView tvRated;
    TextView tvReleased;
    TextView tvRuntime;
    TextView tvGenre;
    TextView tvDirector;
    TextView tvWriter;
    TextView tvActors;
    TextView tvPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        imdbId = intent.getStringExtra("imdbId");
        Log.d("hehehe", imdbId);
        findViewById();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService service = retrofit.create(retrofitService.class);
        Call<Movie> callMovie = service.movieDetail(imdbId, "full", apikey);
        callMovie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                tvTitle.setText(tvTitle.getText()+movie.getTitle());
                Picasso.get().load(movie.getPoster()).into(imgPoster);
                tvYear.setText(tvYear.getText()+movie.getYear());
                tvRated.setText(tvRated.getText()+movie.getRated());
                tvReleased.setText(tvReleased.getText()+movie.getReleased());
                tvRuntime.setText(tvRuntime.getText()+movie.getRuntime());
                tvGenre.setText(tvGenre.getText()+movie.getGenre());
                tvDirector.setText(tvDirector.getText()+movie.getDirector());
                tvWriter.setText(tvWriter.getText()+movie.getWriter());
                tvActors.setText(tvActors.getText()+movie.getActors());
                tvPlot.setText(tvPlot.getText()+movie.getPlot());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d(TAG, "onFailure: ");

            }
        });
    }
    public void findViewById(){
        imgPoster = findViewById(R.id.imgPoster);
        tvTitle = findViewById(R.id.tvTitle);
        tvYear = findViewById(R.id.tvYear);
        tvRated = findViewById(R.id.tvRated);
        tvReleased = findViewById(R.id.tvReleased);
        tvRuntime  = findViewById(R.id.tvRuntime);
        tvGenre  = findViewById(R.id.tvGenre);
        tvDirector  = findViewById(R.id.tvDirector);
        tvWriter = findViewById(R.id.tvWriter);
        tvActors = findViewById(R.id.tvActors);
        tvPlot = findViewById(R.id.tvPlot);
    }
}
