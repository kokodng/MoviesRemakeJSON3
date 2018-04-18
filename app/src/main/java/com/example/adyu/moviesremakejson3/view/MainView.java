package com.example.adyu.moviesremakejson3.view;
//https://antonioleiva.com/mvp-android/
//https://medium.com/@cervonefrancesco/model-view-presenter-android-guidelines-94970b430ddf
//json-server  --watch movies_log.json --port 8080
//c9 kokozv c9kokoloko11

/*  API JSON
 *      http://localhost:3000/movie
 *      get: http://localhost:3000/movie                   -> json array de cuentas bancarias
 *      get: http://localhost:3000/movie/id                -> json objeto cuenta bancaria
 *      post: http://localhost:3000/movie + body en json   -> json objeto cuenta bancaria
 *      put: http://localhost:3000/movie/id + body en json -> json objeto cuenta bancaria
 *      delete: http://localhost:3000/movie/id             -> objeto vacio {} []
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.adyu.moviesremakejson3.presenter.OnHttpResponse;
import com.example.adyu.moviesremakejson3.R;
import com.example.adyu.moviesremakejson3.adapter.RecyclerAdapter;
import com.example.adyu.moviesremakejson3.model.HttpClient;
import com.example.adyu.moviesremakejson3.pojo.Movie;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.adyu.moviesremakejson3.app.AppValues.HTTP_GET;
import static com.example.adyu.moviesremakejson3.app.AppValues.LINK;
import static com.example.adyu.moviesremakejson3.app.AppValues.REGISTER_PARAMETER;

public class MainView extends AppCompatActivity {

    private ArrayList<Movie> movieList = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private static final int REGISTER = 1;
    private int flag;

    private void init() {
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        RecyclerView recyclerMovies = findViewById(R.id.rvMovies);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.v("xyzyx", "nuevo");
                Intent intent = new Intent(MainView.this, MovieRegisterView.class);
                startActivityForResult(intent, REGISTER);
            }
        });
        recyclerAdapter = new RecyclerAdapter(this, movieList);
        recyclerMovies.setAdapter(recyclerAdapter);
        recyclerMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        OnHttpResponse httpclient = new OnHttpResponse() {
            @Override
            public void setResult(String s) {
                Gson gson = new Gson();
                Movie[] data = gson.fromJson(s, Movie[].class);
                Collections.addAll(movieList, data);
                recyclerAdapter.notifyDataSetChanged();
            }
        };
        HttpClient.getPage(LINK, HTTP_GET, null, httpclient);
//        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("xyzyx", "ON ACTIVITY RESULT ANTES DE REGISTER");
        if (requestCode == REGISTER) {
//            Log.v("xyzyx", "ON ACTIVITY RESULT DESPUÉS DE REGISTER");
            Movie movie = data.getParcelableExtra(REGISTER_PARAMETER);
            flag = getIntent().getIntExtra("action", 0);
            if (flag == 1) {
                Log.v("xyzyx", "HACEMOS PUT");
//                movieList.set(movie.getId(), movie);
                HttpClient.putJson(movie, movie.getId());
            }
            Log.v("xyzyx", "HACEMOS POST");
            movieList.add(movie);
//             check flag
//             new movie if POST replace movie if PUT
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}