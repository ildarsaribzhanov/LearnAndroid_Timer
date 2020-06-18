package com.example.udemylearntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_VALUE = 600;

    SeekBar timerLeftBar;
    Button button;
    TextView timerValueView;
    Integer timerVal = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerLeftBar = findViewById(R.id.leftTimerBar);
        timerLeftBar.setMax(this.MAX_VALUE);
        timerLeftBar.setProgress(timerVal);

        button = findViewById(R.id.startBtn);
        timerValueView = findViewById(R.id.timerValue);
        viewTimerVal();

        timerLeftBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    timerVal = progress;
                    timerLeftBar.setProgress(timerVal);
                    viewTimerVal();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void viewTimerVal() {
        Integer minutes = Math.round(timerVal / 60);
        Integer seconds = timerVal - minutes * 60;
        String minView = String.format("%1$2s", minutes).replace(' ', '0');
        String secView = String.format("%1$2s", seconds).replace(' ', '0');
        timerValueView.setText(minView + ":" + secView);
    }
}