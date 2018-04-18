package com.example.adyu.moviesremakejson3.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adyu.moviesremakejson3.model.HttpClient;
import com.example.adyu.moviesremakejson3.pojo.Movie;
import com.example.adyu.moviesremakejson3.R;
import com.example.adyu.moviesremakejson3.view.MovieRegisterView;

import java.util.ArrayList;

import static com.example.adyu.moviesremakejson3.app.AppValues.REGISTER_PARAMETER;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MoviesViewHolder> {

    private ArrayList<Movie> movieArrayList;
    private Context context;

    public RecyclerAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
        this.context = context;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        MoviesViewHolder movieHolder = new MoviesViewHolder(itemView);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Movie newMovie = movieArrayList.get(position);
        holder.bindMovies(newMovie, position, context);
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDirector, tvYear, tvDuration;
        private CardView cvMovie;


        public MoviesViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDirector = itemView.findViewById(R.id.tvDirector);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            cvMovie = itemView.findViewById(R.id.cardView);
        }

        public void bindMovies(final Movie movie, final int position, final Context context) {
            tvTitle.setText(movie.getTitle());
            tvDirector.setText(movie.getDirector());
            tvYear.setText(movie.getYear() + "");
            tvDuration.setText(movie.getDuration() + "");


            //delete on longclick
            cvMovie.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    deleteCard(movie.getId());
                    return false;
                }
            });
            cvMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int action = 1;
                    int id = movie.getId();
                    Log.v("xyzyx", "AQUI LE ESTOY PASANDO EL OBJETO");
//                    Log.v("xyzyx", String.valueOf(movie.getId()));
                    Intent intent = new Intent(context, MovieRegisterView.class);
                    intent.putExtra(REGISTER_PARAMETER, movie);
//                    intent.putExtra("position", position);
                    intent.putExtra("id", id);
//                    intent.putExtra("array", movieArrayList);
                    intent.putExtra("action", action);
                    context.startActivity(intent);
                }
            });

        }

        private void deleteCard(final int id) {
            Button btnConfirm, btnCancel;
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            final AlertDialog deleteAlert;
            alert.setView(inflater.inflate((R.layout.alert), null));
            alert.setTitle("ALERT");
            deleteAlert = alert.create();
            deleteAlert.show();
            btnConfirm = deleteAlert.findViewById(R.id.btnConfirm);
            btnCancel = deleteAlert.findViewById(R.id.btnCancel);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieArrayList.remove(id);
                    notifyDataSetChanged();
                    HttpClient.deleteJson(context, id);
                    deleteAlert.cancel();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteAlert.cancel();
                }
            });
        }
    }
}