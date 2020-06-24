package com.example.udemylearntimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final int MAX_VALUE = 60;

    MediaPlayer player;
    SeekBar timerLeftBar;
    Button button;
    TextView timerValueView;
    Integer timerVal = 30;
    CountDownTimer timer;
    SharedPreferences preferences;

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
            int newVal = (int) (millisUntilFinished / 1000);
            timerLeftBar.setProgress(newVal);
            viewTimerVal(newVal);
        }

        @Override
        public void onFinish() {
            if (preferences.getBoolean("enable_sound", true)) {
                player.start();
            }

            stopTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        button = findViewById(R.id.startBtn);
        timerValueView = findViewById(R.id.timerValue);
        player = MediaPlayer.create(getApplicationContext(), R.raw.gong);

        timerLeftBar = findViewById(R.id.leftTimerBar);
        timerLeftBar.setMax(MAX_VALUE);
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

    @SuppressLint("SetTextI18n")
    private void viewTimerVal(Integer val) {
        int minutes = val / 60;
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

    @SuppressLint("SetTextI18n")
    public void startTimer() {
        timer.start();
        button.setText("Stop");
        timerLeftBar.setEnabled(false);
    }

    @SuppressLint("SetTextI18n")
    public void stopTimer() {
        timer.cancel();
        button.setText("Start");
        timerLeftBar.setEnabled(true);
        timer = null;
        viewTimerVal(timerVal);
        timerLeftBar.setProgress(timerVal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent selectedIntent;

        if (id == R.id.action_settings) {
            selectedIntent = new Intent(this, SettingsActivity.class);
            startActivity(selectedIntent);
            return true;

        } else if (id == R.id.action_about) {
            selectedIntent = new Intent(this, AboutActivity.class);
            startActivity(selectedIntent);
            return true;
        }

        return true;
    }
}