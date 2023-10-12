package com.example.slide_13;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MediaActivity extends AppCompatActivity {
    TextView title;
    Button prev, play, next;
    MediaPlayer player = new MediaPlayer();
    String[] url = {
            "https://samplelib.com/lib/preview/mp3/sample-6s.mp3",
            "https://samplelib.com/lib/preview/mp3/sample-9s.mp3",
            "https://samplelib.com/lib/preview/mp3/sample-12s.mp3"
    };
    int current = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        prev = findViewById(R.id.prev);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);

        title = findViewById(R.id.title);
        title.setText("Song " + (current + 1));

        player.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
        );
        try {
            player.setDataSource(url[current]);
            player.prepare();
        } catch (IOException e) {
            throw new RuntimeException("Error occurred", e);
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()) {
                    player.pause();
                    play.setBackgroundResource(R.drawable.baseline_play_arrow_24);
                } else {
                    player.start();
                    play.setBackgroundResource(R.drawable.baseline_pause_24);
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current > 0) {
                    current--;
                } else {
                    current = url.length - 1;
                }
                changeSong();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < url.length - 1) {
                    current++;
                } else {
                    current = 0;
                }
                changeSong();
            }
        });
    }

    private void changeSong() {
        player.reset();
        try {
            player.setDataSource(url[current]);
            title.setText("Song " + (current + 1));
            player.prepare();
            player.start();
            play.setBackgroundResource(R.drawable.baseline_pause_24);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred", e);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (player.isPlaying()) {
            play.setBackgroundResource(R.drawable.baseline_pause_24);
        } else {
            play.setBackgroundResource(R.drawable.baseline_play_arrow_24);
        }
    }
}