package com.example.udemylearntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_VALUE = 600;

    MediaPlayer player;
    SeekBar timerLeftBar;
    Button button;
    TextView timerValueView;
    Integer timerVal = 30;
    CountDownTimer timer;

    public class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.d("MyTimer", String.valueOf(millisUntilFinished / 1000));
            timerVal = Math.round(millisUntilFinished / 1000);
            timerLeftBar.setProgress(timerVal);
            viewTimerVal(timerVal);
        }

        @Override
        public void onFinish() {
            Log.d("MyTimer", "Finished");
            player.start();
            stopTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.startBtn);
        timerValueView = findViewById(R.id.timerValue);
        player = MediaPlayer.create(getApplicationContext(), R.raw.gong);

        timerLeftBar = findViewById(R.id.leftTimerBar);
        timerLeftBar.setMax(this.MAX_VALUE);
        timerLeftBar.setProgress(timerVal);

        viewTimerVal(timerVal);

        timerLeftBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    timerVal = progress;
                    timerLeftBar.setProgress(timerVal);
                    viewTimerVal(timerVal);
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

    private void viewTimerVal(Integer val) {
        Integer minutes = Math.round(val / 60);
        Integer seconds = val - minutes * 60;
        String minView = String.format("%1$2s", minutes).replace(' ', '0');
        String secView = String.format("%1$2s", seconds).replace(' ', '0');
        timerValueView.setText(minView + ":" + secView);
    }

    public void toggleTimer(View view) {
        if (timer == null) {
            timer = new MyCountDownTimer(timerVal * 1000, 1000);
        }

        if (button.getText() == "Stop") {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        timer.start();
        button.setText("Stop");
        timerLeftBar.setEnabled(false);
    }

    public void stopTimer() {
        timer.cancel();
        button.setText("Start");
        timerLeftBar.setEnabled(true);
        timer = null;
    }
}