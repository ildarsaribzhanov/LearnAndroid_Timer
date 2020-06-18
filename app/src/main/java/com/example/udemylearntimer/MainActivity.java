package com.example.udemylearntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_START = 30;
    SeekBar timerLeft;
    Button button;
    TextView timerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerLeft = findViewById(R.id.leftTimerBar);
        button = findViewById(R.id.startBtn);
        timerValue = findViewById(R.id.timerValue);
    }
}