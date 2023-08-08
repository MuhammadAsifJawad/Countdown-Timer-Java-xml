package com.example.countdowntimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView counterTextView;
    private TextView timerLabel;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private Button increaseButton;
    private Button decreaseButton;

    private CountDownTimer countDownTimer;
    private long initialTimeInMillis = 120000; // 2 minutes
    private long timeLeftInMillis = initialTimeInMillis;
    private boolean timerRunning = false;

    private Vibrator vibrator;

//        private boolean buttonStopStart=true;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // appbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Countdown Timer App");
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.image); // Replace "image" with the actual image resource ID
        counterTextView = findViewById(R.id.counterTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);
        increaseButton = findViewById(R.id.increaseButton);
        decreaseButton = findViewById(R.id.decreaseButton);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startTimer();

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseTimerDuration();
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseTimerDuration();
            }
        });
    }

    private void startTimer() {
        if (!timerRunning) {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateCountdownText();
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                    updateButtons();
                    vibrator.vibrate(1000); // Vibrate for 1 second
                }
            }.start();

            timerRunning = true;
            updateButtons();
        }
    }

    private void stopTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
            updateButtons();
        }
    }

    private void resetTimer() {
        countDownTimer.cancel();
        timeLeftInMillis = initialTimeInMillis;
        updateCountdownText();
        timerRunning = false;
        updateButtons();
    }

    private void increaseTimerDuration() {
        timeLeftInMillis += 5000; // Increase by 5 seconds (5000 milliseconds)
        updateCountdownText();
        updateButtons();
    }

    private void decreaseTimerDuration() {
        if (timeLeftInMillis > 5000) { // Ensure timer doesn't go below 5 seconds
            timeLeftInMillis -= 5000; // Decrease by 5 seconds (5000 milliseconds)
            updateCountdownText();
            updateButtons();
        }
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        counterTextView.setText(timeFormatted);
    }

    private void updateButtons() {
        if (timerRunning) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            resetButton.setEnabled(false);
            increaseButton.setEnabled(false);
            decreaseButton.setEnabled(false);
        } else {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            resetButton.setEnabled(true);
            increaseButton.setEnabled(true);
            decreaseButton.setEnabled(true);
        }
    }
}
