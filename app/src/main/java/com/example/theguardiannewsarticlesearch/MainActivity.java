package com.example.theguardiannewsarticlesearch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.ContentValues;

public class MainActivity extends AppCompatActivity implements ArticlesAdapter.OnArticleClickListener {
    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView articlesRecyclerView;
    private ArticlesAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        articlesRecyclerView = findViewById(R.id.articlesRecyclerView);

        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString();
            if (!query.isEmpty()) {
                searchArticles(query);
                adapter.setIsFavoritesList(false);
            } else {
                loadFavorites();
                adapter.setIsFavoritesList(true);
            }
        });

        loadFavorites();
    }

    private void loadFavorites() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Article> favoriteArticles = new ArrayList<>();
        Cursor cursor = db.query("favorites", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String webTitle = cursor.getString(cursor.getColumnIndex("webTitle"));
            @SuppressLint("Range") String webUrl = cursor.getString(cursor.getColumnIndex("webURL"));
            @SuppressLint("Range") String sectionName = cursor.getString(cursor.getColumnIndex("sectionName"));

            Article article = new Article(webTitle, webUrl, sectionName);
            favoriteArticles.add(article);
        }
        cursor.close();
        adapter = new ArticlesAdapter(favoriteArticles, this, true);
        articlesRecyclerView.setAdapter(adapter);
    }

    private void searchArticles(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://content.guardianapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GuardianApiService service = retrofit.create(GuardianApiService.class);
        Call<ApiResponse> call = service.searchArticles(query, "4f732a4a-b27e-4ac7-9350-e9d0b11dd949");

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResponse().getResults() != null && !response.body().getResponse().getResults().isEmpty()) {
                        adapter.setArticles(response.body().getResponse().getResults());
                    } else {
                        Toast.makeText(MainActivity.this, "No articles found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "API Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onArticleClick(String url) {
        openArticleUrl(url);
    }

    @Override
    public void onFavoriteClick(Article article) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("webTitle", article.getWebTitle());
        values.put("webURL", article.getWebUrl());
        values.put("sectionName", article.getSectionName());

        long newRowId = db.insert("favorites", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error adding to favorites", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRemoveFavorite(Article article) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = db.delete("favorites", "webURL = ?", new String[]{article.getWebUrl()});
        if (deletedRows > 0) {
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            loadFavorites(); // Reload favorites to update the UI
        } else {
            Toast.makeText(this, "Error removing from favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void openArticleUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}