package com.example.adyu.moviesremakejson3.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.adyu.moviesremakejson3.R;
import com.example.adyu.moviesremakejson3.model.HttpClient;
import com.example.adyu.moviesremakejson3.presenter.OnHttpResponse;
import com.example.adyu.moviesremakejson3.pojo.Movie;
import com.google.gson.Gson;

import static com.example.adyu.moviesremakejson3.app.AppValues.HTTP_POST;
import static com.example.adyu.moviesremakejson3.app.AppValues.HTTP_PUT;
import static com.example.adyu.moviesremakejson3.app.AppValues.LINK;
import static com.example.adyu.moviesremakejson3.app.AppValues.REGISTER_PARAMETER;

public class MovieRegisterView extends AppCompatActivity {

    private EditText tiTitle, tiDirector, tiDuration, tiYear;
    private OnHttpResponse httpResponse;
    private int flag;

    private void init() {
        Button btAddMovie = findViewById(R.id.btAddMovie);
        tiTitle = findViewById(R.id.tiTitle);
        tiDirector = findViewById(R.id.tiDirector);
        tiYear = findViewById(R.id.tiYear);
        tiDuration = findViewById(R.id.tiDuration);

        flag = getIntent().getIntExtra("action", 0);
        if (flag == 1) {
            Movie oldmovie = getIntent().getParcelableExtra(REGISTER_PARAMETER);
//                Log.v("xyzyx", movie.getTitle());
            tiTitle.setText(oldmovie.getTitle());
            tiDirector.setText(oldmovie.getDirector());
            tiYear.setText(String.valueOf(oldmovie.getYear()));
            tiDuration.setText(String.valueOf(oldmovie.getDuration()));
        }
        httpResponse = new OnHttpResponse() {
            @Override
            public void setResult(String s) {
                Gson gson = new Gson();
                Movie movie;
                movie = gson.fromJson(s, Movie.class);
//                Log.v("xyzyx", movie.getTitle());
                Intent i = new Intent();
                Bundle b = new Bundle();
                b.putParcelable(REGISTER_PARAMETER, movie);
                b.putInt("flag", flag);
//                 flag knows if POST or PUT
                i.putExtras(b);
                MovieRegisterView.this.setResult(Activity.RESULT_OK, i);
                finish();
            }
        };

        btAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String method;
                Movie movie = new Movie(
                        tiTitle.getText().toString(),
                        tiDirector.getText().toString(),
                        Integer.parseInt(tiYear.getText().toString()),
                        Integer.parseInt(tiDuration.getText().toString()));
                method = (flag == 1) ? HTTP_PUT : HTTP_POST;
                Gson gson = new Gson();
                String json = gson.toJson(movie);
                HttpClient.getPage(LINK, method, json, httpResponse);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);
        init();
    }

}
