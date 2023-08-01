package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        View homeIcon = findViewById(R.id.homeIcon);
        homeIcon.setOnClickListener(view -> {
            Intent mainActivityIntent = new Intent(
                    getApplicationContext(), MainActivity.class
            );
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(mainActivityIntent);
        });

        View paymentIcon = findViewById(R.id.paymentIcon);
        paymentIcon.setOnClickListener(view -> {
            Intent paymentActivityIntent = new Intent(
                    getApplicationContext(), PaymentActivity.class
            );
            paymentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(paymentActivityIntent);
        });

        View accountIcon = findViewById(R.id.accountIcon);
        accountIcon.setOnClickListener(view -> {
            Intent accountActivityIntent = new Intent(
                    getApplicationContext(), AccountActivity.class
            );
            accountActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(accountActivityIntent);
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