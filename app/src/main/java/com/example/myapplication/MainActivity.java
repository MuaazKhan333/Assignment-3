package com.example.dataviewerapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dataviewerapp.database.DBHelper;
import com.example.dataviewerapp.models.Post;
import com.example.dataviewerapp.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    RecyclerView rv;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        if (prefs.getBoolean("isDark", false)) setTheme(androidx.appcompat.R.style.Theme_AppCompat_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        RetrofitClient.getInterface().getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    for (Post p : response.body()) db.insertPost(p);
                    rv.setAdapter(new PostAdapter(response.body(), MainActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                rv.setAdapter(new PostAdapter(db.getAllPosts(), MainActivity.this));
                Toast.makeText(MainActivity.this, "Offline Mode", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Toggle Theme");
        menu.add(0, 2, 0, "Logout");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            prefs.edit().putBoolean("isDark", !prefs.getBoolean("isDark", false)).apply();
            recreate();
        } else {
            prefs.edit().putBoolean("isLoggedIn", false).apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return true;
    }
}