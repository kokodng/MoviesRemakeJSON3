/*package com.example.adyu.moviesremakejson3.old;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.adyu.moviesremakejson3.pojo.Movie;
import com.example.adyu.moviesremakejson3.view.MovieRegisterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpClienOld {

    public void connect(String link, String method, String json, Context context) {
        switch (method) {
            case "GET":
                verDBJson(link, method, context);
                break;
            case "POST":
                addJson(link, method, json, context);
                break;
            case "DELETE":
                recuJson(link, method, context);
                break;
            case "PUT":
                putJson(link, method, json, context);
                break;
        }
    }

    public void verDBJson(final String link, final String method, final Context context) {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, String> taskShowDBJson = new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... strings) {
                return getDBJson(link, method);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONArray jsonarray = null;
                ArrayList<Movie> array = new ArrayList<>();
                String json = s;
                try {
                    jsonarray = new JSONArray(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                array = jsonArraytoArrayList(jsonarray, array);
                Intent intent = new Intent(context, RecyclerActivity.class);
                intent.putParcelableArrayListExtra("Array", array);
                context.startActivity(intent);
            }
        };
        taskShowDBJson.execute();
    }

    private void addJson(final String link, final String method, final String json, Context context) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> taskAddJson = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                addJsonToDBJson(link, method, json);
                return null;
            }
        };
        taskAddJson.execute();
    }

    private void recuJson(final String link, final String method, final Context context) {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, String> taskRecu = new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... string) {
                return getDBJson(link, "GET");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String json = s;
                int action = 1;//1 = EDIT, 0 = NEW
                int id;
                try {
                    JSONObject jsonRecu = new JSONObject(json);
                    id = jsonRecu.getInt("id");
                    Movie newmovie = new Movie(jsonRecu.getString("title"),
                            jsonRecu.getString("director"),
                            jsonRecu.getInt("year"),
                            jsonRecu.getInt("duration"));
                    Intent intent = new Intent(context, MovieRegisterView.class);
                    intent.putExtra("Link", link);
                    intent.putExtra("Movie", newmovie);
                    intent.putExtra("Id", id);
                    intent.putExtra("Accion", action);
                    context.startActivity(intent);
                    ((RecyclerActivity) context).finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        taskRecu.execute();
    }

    private void putJson(final String link, final String method, final String json, final Context context) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> taskPut = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                addJsonToDBJson(link, method, json);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                verDBJson("http://10.0.2.2:3000/lista", "GET", context);
                ((MovieRegisterView) context).finish();
            }
        };
        taskPut.execute();
    }

    private String getDBJson(String link, String method) {
        String result = "";
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            InputStream is = conn.getInputStream();
            if (is != null) {
                result = readStream(is);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private String readStream(InputStream is) {
        String aux = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String data = "";
            while ((data = reader.readLine()) != null) {
                aux = aux + data;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aux;
    }

    private ArrayList<Movie> jsonArraytoArrayList(JSONArray jsonarray, ArrayList<Movie> array) {
        for (int i = 0; i < jsonarray.length(); i++) {
            try {
                JSONObject newJsonObject = jsonarray.getJSONObject(i);
                Movie newmovie = new Movie(newJsonObject.getString("title"),
                        newJsonObject.getString("director"),
                        newJsonObject.getInt("year"),
                        newJsonObject.getInt("duration"));
                array.add(newmovie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    private void addJsonToDBJson(String link, String method, String json) {
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            osw.write(json);
            osw.flush();
            osw.close();
            conn.getInputStream();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
*/