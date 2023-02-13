package tdtu.lab05.musicapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

public class Info extends AppCompatActivity {
    TextView getName, getDuration, getLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        getSupportActionBar().setTitle("Song info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getName = findViewById(R.id.getName);
        getDuration = findViewById(R.id.getDuration);
        getLocation = findViewById(R.id.getLocation);
        Intent i = getIntent();
        String songName = i.getStringExtra("name");
        String duration = i.getStringExtra("duration");
        String location = i.getStringExtra("location");
        getName.setText(songName);
        getDuration.setText(duration);
        getLocation.setText(location);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
