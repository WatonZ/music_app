package tdtu.lab05.musicapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Lyrics extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyrics);
        getSupportActionBar().setTitle("Lyrics");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
