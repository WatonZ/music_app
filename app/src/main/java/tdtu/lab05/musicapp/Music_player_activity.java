package tdtu.lab05.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Music_player_activity extends AppCompatActivity {
    Button play_button, next_button, prev_button, forward_button, rewind_button, show_lyrics, info;
    TextView name, start_time, stop_time;
    SeekBar time_seekbar;
    ImageView musicIcon;

    String sname;
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> mySong;
    Thread seekbar;

    //Set chức năng quay về danh sách bài hát khi đang ở giao diện phát nhạc
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        getSupportActionBar().setTitle("Now playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        play_button = findViewById(R.id.play_button);
        prev_button = findViewById(R.id.prev_button);
        next_button = findViewById(R.id.next_button);
        rewind_button = findViewById(R.id.rewind_button);
        forward_button = findViewById(R.id.forward_button);
        info = findViewById(R.id.info);
        show_lyrics = findViewById(R.id.lyrics);
        name = findViewById(R.id.name);
        musicIcon = findViewById(R.id.musicIcon);
        start_time = findViewById(R.id.start_time);
        stop_time = findViewById(R.id.stop_time);
        time_seekbar = findViewById(R.id.time_seekbar);

        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySong = (ArrayList) bundle.getParcelableArrayList("songs");
        position = bundle.getInt("position", 0);
        name.setSelected(true);
        Uri uri = Uri.parse(mySong.get(position).toString());
        sname = mySong.get(position).getName();
        name.setText(sname);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        seekbar = new Thread(){
            @Override
            public void run() {
                int totalTime = mediaPlayer.getDuration();
                int currentTime = 0;

                while(currentTime < totalTime){
                    try{
                        sleep(200);
                        currentTime = mediaPlayer.getCurrentPosition();
                        time_seekbar.setProgress(currentTime);
                    }
                    catch (InterruptedException | IllegalStateException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        time_seekbar.setMax(mediaPlayer.getDuration());
        seekbar.start();

        time_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String Ending_time = Showtime(mediaPlayer.getDuration());
        stop_time.setText(Ending_time);

        final Handler handler = new Handler();
        final int delay = 1000;
        //Set thời gian ở thời điểm bài hát đang phát
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = Showtime(mediaPlayer.getCurrentPosition());
                start_time.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    play_button.setBackgroundResource(R.drawable.ic_play_icon);
                    mediaPlayer.pause();
                } else {
                    play_button.setBackgroundResource(R.drawable.ic_pause_icon);
                    mediaPlayer.start();
                }
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position+1)%mySong.size());
                Uri uri1 = Uri.parse(mySong.get(position).toString());
                sname = mySong.get(position).getName();
                name.setText(sname);
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri1);
                mediaPlayer.start();
                play_button.setBackgroundResource(R.drawable.ic_pause_icon);
                String endTime = Showtime(mediaPlayer.getDuration());
                stop_time.setText(endTime);
                time_seekbar.setProgress(0);
                time_seekbar.setMax(mediaPlayer.getDuration());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        next_button.performClick();
                    }
                });
                Animation(musicIcon);
            }
        });
        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position-1)<0)?(mySong.size()-1):(position-1);
                Uri uri1 = Uri.parse(mySong.get(position).toString());
                sname = mySong.get(position).getName();
                name.setText(sname);
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri1);
                mediaPlayer.start();
                play_button.setBackgroundResource(R.drawable.ic_pause_icon);
                String endTime = Showtime(mediaPlayer.getDuration());
                stop_time.setText(endTime);
                time_seekbar.setProgress(0);
                time_seekbar.setMax(mediaPlayer.getDuration());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        next_button.performClick();
                    }
                });
                Animation(musicIcon);
            }
        });
        forward_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
                }
            }
        });

        rewind_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next_button.performClick();
            }
        });

        show_lyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Lyrics.class);
                startActivity(intent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String endTime = Showtime(mediaPlayer.getDuration());
                Intent intent = new Intent(getApplicationContext(),Info.class)
                        .putExtra("name", sname)
                        .putExtra("duration", endTime)
                        .putExtra("location", Environment.getExternalStorageDirectory().getAbsolutePath());
                startActivity(intent);
            }
        });
    }
    public void Animation(View v){
        ObjectAnimator animator = ObjectAnimator.ofFloat(musicIcon, "rotation", 0f, 360f);
        animator.setDuration(2000);
        AnimatorSet animatorset = new AnimatorSet();
        animatorset.playTogether(animator);
        animatorset.start();
    }

    public String Showtime(int duration){
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;

        time += min + ":";
        if (sec<10){
            time += "0";
        }
        time += sec;
        return time;
    }

}