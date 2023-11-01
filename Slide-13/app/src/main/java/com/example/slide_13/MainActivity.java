package com.example.slide_13;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer player;
    private List<Integer> songList;
    private int currentSongIndex = 0;

    public static final String CHANNEL_ID = "Music Player";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the list of songs
        songList = new ArrayList<>();
        songList.add(R.raw.brave_climber);
        songList.add(R.raw.brave_climber_x11);

        player = MediaPlayer.create(this, songList.get(currentSongIndex));
    }

    public void play(View v) {
        if (player != null && !player.isPlaying()) {
            player.start();
        }
    }

    public void pause(View v) {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    public void stop(View v) {
        stopPlayer();
    }

    public void next(View v) {
        playNextSong();
    }

    public void previous(View v) {
        playPreviousSong();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    private void playNextSong() {
        currentSongIndex++;
        if (currentSongIndex >= songList.size()) {
            currentSongIndex = 0; // Loop back to the first song
        }
        playCurrentSong();
    }

    private void playPreviousSong() {
        currentSongIndex--;
        if (currentSongIndex < 0) {
            currentSongIndex = songList.size() - 1; // Wrap to the last song
        }
        playCurrentSong();
    }

    private void playCurrentSong() {
        stopPlayer();
        player = MediaPlayer.create(this, songList.get(currentSongIndex));
        player.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}
