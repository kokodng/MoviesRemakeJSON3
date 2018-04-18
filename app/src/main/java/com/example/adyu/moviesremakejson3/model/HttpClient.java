package com.example.adyu.moviesremakejson3.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.adyu.moviesremakejson3.pojo.Movie;
import com.example.adyu.moviesremakejson3.presenter.OnHttpResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import static com.example.adyu.moviesremakejson3.app.AppValues.HTTP_DELETE;
import static com.example.adyu.moviesremakejson3.app.AppValues.HTTP_PUT;
import static com.example.adyu.moviesremakejson3.app.AppValues.LINK;

public class HttpClient {
    private static String downloadPage(String link, String method, String json) {
        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = "";
        try {
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            if (!method.equals("GET")) {
                connection.setDoOutput(true);
            }
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();
            if (!method.equals("GET")) {
                OutputStreamWriter outStream = new OutputStreamWriter(connection.getOutputStream());
                outStream.write(json);
                outStream.flush();
                outStream.close();
            }
            stream = connection.getInputStream();
            if (stream != null) {
                result = readStream(stream);
            }
        } catch (ProtocolException e) {
            Log.v("xyzyx", "Stream");
        } catch (IOException e) {
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    private static String readStream(InputStream stream) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[1024];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1)) {
            buffer.append(rawBuffer, 0, readSize);
        }
        return buffer.toString();
    }

    public static void getPage(String link, String method, String json, final OnHttpResponse response) {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return downloadPage(params[0], params[1], params[2]);
            }

            @Override
            protected void onPostExecute(String s) {
                response.setResult(s);
            }
        };
        task.execute(link, method, json);
    }
    public static void deleteJson(final Context context, final int id) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> taskDelete = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
//                deleteJson("https://json-server-kokozv.c9users.io:8080/movies_log" + id, "DELETE", context);
                deleteJson(LINK + "/" + id,  context);
                return null;
            }
        };
        taskDelete.execute();
    }
    private static void deleteJson(String link, Context context) {
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HTTP_DELETE);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            conn.getInputStream();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void putJson(final Movie movie, final int id) {

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> taskPut = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                putJson(LINK + "/" + id, movie);
                return null;
            }
        };
        taskPut.execute();
    }

    private static void putJson(String link, Movie movie) {
        Gson gson = new Gson();
        String json = gson.toJson(movie);
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HTTP_PUT);
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
