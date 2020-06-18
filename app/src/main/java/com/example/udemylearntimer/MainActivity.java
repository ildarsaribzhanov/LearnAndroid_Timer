package com.example.udemylearntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_START = 30;

    SeekBar timerLeftBar;
    Button button;
    TextView timerValueView;
    Integer timerVal = this.DEFAULT_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerLeftBar = findViewById(R.id.leftTimerBar);
        button = findViewById(R.id.startBtn);
        timerValueView = findViewById(R.id.timerValue);
        viewTimerVal();
    }

    private void viewTimerVal() {
        Integer minutes = Math.round(timerVal / 60);
        Integer seconds = timerVal - minutes * 60;
        String minView = String.format("%1$2s", minutes).replace(' ', '0');
        String secView = String.format("%1$2s", seconds).replace(' ', '0');
        timerValueView.setText(minView + ":" + secView);
    }
}