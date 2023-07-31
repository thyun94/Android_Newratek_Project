package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        View homeIcon = findViewById(R.id.homeIcon);
        homeIcon.setOnClickListener(view -> {
            Intent mainActivityIntent = new Intent(
                    getApplicationContext(), MainActivity.class
            );
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(mainActivityIntent);
        });

        View historyIcon = findViewById(R.id.historyIcon);
        historyIcon.setOnClickListener(view -> {
            Intent historyActivityIntent = new Intent(
                    getApplicationContext(), HistoryActivity.class
            );
            historyActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(historyActivityIntent);
        });

        View paymentIcon = findViewById(R.id.paymentIcon);
        paymentIcon.setOnClickListener(view -> {
            Intent paymentActivityIntent = new Intent(
                    getApplicationContext(), PaymentActivity.class
            );
            paymentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(paymentActivityIntent);
        });

        View settingsIcon = findViewById(R.id.settingsIcon);
        settingsIcon.setOnClickListener(view -> {
            Intent settingsActivityIntent = new Intent(
                    getApplicationContext(), SettingsActivity.class
            );
            settingsActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(settingsActivityIntent);
        });
    }
}