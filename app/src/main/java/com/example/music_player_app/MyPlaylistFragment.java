package com.example.music_player_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyPlaylistFragment extends Fragment {

    private RecyclerView rvCollection;  // RecyclerView untuk menampilkan playlist
    private MyPlaylistProfileCollection adapter;  // Adapter untuk RecyclerView
    private List<MyPlaylistProfile> playlistData = new ArrayList<>();  // Menyimpan data playlist

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);  // Pastikan menggunakan profile.xml

        // Menghubungkan RecyclerView dengan layout
        rvCollection = view.findViewById(R.id.myRecyclerView);
        rvCollection.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Inisialisasi adapter dan set ke RecyclerView
        adapter = new MyPlaylistProfileCollection(getActivity(), playlistData);
        rvCollection.setAdapter(adapter);  // Menghubungkan adapter ke RecyclerView

        // Ambil data playlist dari API
        new FetchPlaylistDataTask().execute();  // Menjalankan AsyncTask

        return view;
    }

    // Method untuk memperbarui RecyclerView dengan data yang baru
    private void updateRecyclerView(List<MyPlaylistProfile> playlist) {
        playlistData.clear();  // Hapus data lama
        playlistData.addAll(playlist);  // Tambahkan data baru
        adapter.notifyDataSetChanged();  // Beritahu adapter untuk memperbarui UI
    }

    // Kelas AsyncTask untuk mengambil data playlist dari API
    private class FetchPlaylistDataTask extends AsyncTask<Void, Void, String> {

        private static final String API_URL = "http://192.168.1.42/PAM/restAPI.php"; // Ganti dengan URL API Anda

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Membuat koneksi ke API
                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(10000);  // 10 detik timeout
                urlConnection.setReadTimeout(10000);     // 10 detik timeout

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                return stringBuilder.toString();
            } catch (Exception e) {
                Log.e("FetchPlaylistDataTask", "Error fetching data", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                // Proses data JSON di sini
                parsePlaylistData(result);
            } else {
                Log.e("FetchPlaylistDataTask", "Failed to fetch data.");
            }
        }

        // Parse data JSON dan update RecyclerView
        private void parsePlaylistData(String jsonData) {
            try {
                JSONArray jsonArray = new JSONArray(jsonData);
                List<MyPlaylistProfile> playlist = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject playlistObject = jsonArray.getJSONObject(i);
                    String title = playlistObject.getString("title");
                    String date = playlistObject.getString("tanggal");
                    String imgUrl = playlistObject.getString("img_url");
                    playlist.add(new MyPlaylistProfile(title, date, imgUrl));
                }

                // Panggil method untuk mengupdate RecyclerView
                updateRecyclerView(playlist);
            } catch (Exception e) {
                Log.e("FetchPlaylistDataTask", "Error parsing JSON", e);
            }
        }
    }
}