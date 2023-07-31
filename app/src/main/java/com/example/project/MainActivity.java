package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.button.MaterialButtonToggleGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String LABEL_JSON = "result";
    private static String LABEL_USER_USERID = "user.userID";
    private static String LABEL_USERNAME = "userName";
    private static String LABEL_USERADDRESS = "userAddress";
    private static String LABEL_PRICEID = "priceID";
    private static String LABEL_ENERGYUSED = "energyUsed";
    private static String LABEL_PRICEAMOUNT = "priceAmount";
    private static String LABEL_PRICEDAY = "priceDay";
    private static String LABEL_PRICEMONTH = "priceMonth";
    private static String LABEL_PRICEYEAR = "priceYear";
    private static String LABEL_PRICE_USERID = "price.userID";

    private TextView TextViewResult;
    ArrayList<HashMap<String, String>> resultArraylist;
    String jsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up click listeners for changing screens on the navigation bar
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


        TextViewResult = (TextView) findViewById(R.id.textview_result);

        resultArraylist = new ArrayList<>();

        getData task = new getData();
        task.execute("http://13.125.233.133/dbdata.php");

    }

    private class getData extends AsyncTask<String, Void, String> {

        ProgressDialog progDialog;
        String errorString = "error";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progDialog = progDialog.show(MainActivity.this, "Please wait", null,true,true);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            result = result.replace("<pre>", "");
            result = result.replace("<br>", "");

            progDialog.dismiss();
            //TextViewResult.setText(result);
            Log.d("mainActivity", "response : " + result);

            jsonString = result;
            showResult();

            updateData();
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("mainActivity", "response code : " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.d("mainActivity", "InsertData: error ", e);
                errorString = e.toString();
            }

            return null;
        }
    }

    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(LABEL_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String user_userid = item.getString(LABEL_USER_USERID);
                String username = item.getString(LABEL_USERNAME);
                String useraddress = item.getString(LABEL_USERADDRESS);
                String priceid = item.getString(LABEL_PRICEID);
                String energyused = item.getString(LABEL_ENERGYUSED);
                String priceamount = item.getString(LABEL_PRICEAMOUNT);
                String priceday = item.getString(LABEL_PRICEDAY);
                String pricemonth = item.getString(LABEL_PRICEMONTH);
                String priceyear = item.getString(LABEL_PRICEYEAR);
                String price_userid = item.getString(LABEL_PRICE_USERID);

                HashMap<String, String> hm = new HashMap<>();

                hm.put(LABEL_USER_USERID, user_userid);
                hm.put(LABEL_USERNAME, username);
                hm.put(LABEL_USERADDRESS, useraddress);
                hm.put(LABEL_PRICEID, priceid);
                hm.put(LABEL_ENERGYUSED, energyused);
                hm.put(LABEL_PRICEAMOUNT, priceamount);
                hm.put(LABEL_PRICEDAY, priceday);
                hm.put(LABEL_PRICEMONTH, pricemonth);
                hm.put(LABEL_PRICEYEAR, priceyear);
                hm.put(LABEL_PRICE_USERID, price_userid);

                resultArraylist.add(hm);

            }



        } catch (JSONException e) {
            Log.d("mainActivity", "showResult = ", e);
        }
    }

    private void updateData() {
        GetDate gdate = new GetDate();


        TextView myAddress = findViewById(R.id.my_address);
        myAddress.setText(resultArraylist.get(0).get(LABEL_USERADDRESS));



        int thisMonthEnergy=0;
        BigDecimal thisMonthPrice = new BigDecimal("0.0");

        int todayEnergyUsed = 0;
        BigDecimal todayPrice= new BigDecimal("0.0");

        LineChart chart = (LineChart) findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<Entry>();

        LineChart chart2 = (LineChart) findViewById(R.id.chart2);
        List<Entry> entries2 = new ArrayList<Entry>();

        for (int i = 0; i < resultArraylist.size(); i++) {
            if (resultArraylist.get(i).get(LABEL_PRICEYEAR).equals(gdate.getYear()) &&
                    resultArraylist.get(i).get(LABEL_PRICEMONTH).equals(gdate.getMonth())) {

                thisMonthPrice = thisMonthPrice.add(new BigDecimal(resultArraylist.get(i).get(LABEL_PRICEAMOUNT)));
                thisMonthEnergy=thisMonthEnergy+Integer.parseInt(resultArraylist.get(i).get(LABEL_ENERGYUSED));

                entries.add(new Entry(Float.parseFloat(resultArraylist.get(i).get(LABEL_PRICEDAY)),
                        Float.parseFloat(resultArraylist.get(i).get(LABEL_PRICEAMOUNT))));

                entries2.add(new Entry(Float.parseFloat(resultArraylist.get(i).get(LABEL_PRICEDAY)),
                        Float.parseFloat(resultArraylist.get(i).get(LABEL_ENERGYUSED))));

                if (resultArraylist.get(i).get(LABEL_PRICEDAY).equals(gdate.getDay())) {
                    todayPrice = todayPrice.add(new BigDecimal(resultArraylist.get(i).get(LABEL_PRICEAMOUNT)));
                    todayEnergyUsed = Integer.parseInt(resultArraylist.get(i).get(LABEL_ENERGYUSED));
                }
            }
        }

        TextView ThisMonthCurrentPrice = (TextView) findViewById(R.id.currentPriceValueText);
        ThisMonthCurrentPrice.setText("$ " + thisMonthPrice);

        TextView ThisMonthEnergyUsed = (TextView) findViewById(R.id.energyUsedValueText);
        ThisMonthEnergyUsed.setText(thisMonthEnergy + " kWh");

        TextView TodayPrice = (TextView) findViewById(R.id.today_current_price_text);
        TodayPrice.setText(todayEnergyUsed + " kWh         $ " + todayPrice);


        LineDataSet dataSet = new LineDataSet(entries, "price");
        dataSet.setDrawFilled(true);
        Drawable drawable1 = ContextCompat.getDrawable(this, R.drawable.fade_green);
        dataSet.setFillDrawable(drawable1);

        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineData data = new LineData(dataSet);


        chart.setData(data);
        chart.setDragEnabled(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setAxisMaximum(30f);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.animateXY(500,500);
        chart.getLegend().setEnabled(false);

        chart.invalidate();


        LineDataSet dataSet2 = new LineDataSet(entries2, "energy");
        dataSet2.setDrawFilled(true);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue);
        dataSet2.setFillDrawable(drawable);

        dataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet2.setDrawValues(false);
        dataSet2.setDrawCircles(false);

        LineData data2 = new LineData(dataSet2);

        chart2.setData(data2);
        chart2.setDragEnabled(false);
        chart2.getXAxis().setDrawAxisLine(false);
        chart2.getXAxis().setDrawGridLines(false);
        chart2.getAxisLeft().setDrawAxisLine(false);
        chart2.getAxisLeft().setDrawGridLines(false);
        chart2.getAxisRight().setEnabled(false);
        chart2.getXAxis().setAxisMaximum(30f);
        chart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart2.animateXY(1000,1000);
        chart2.getLegend().setEnabled(false);
        chart2.getDescription().setText("kWh / Day");
        chart2.getDescription().setPosition(120,585);



        chart2.invalidate();

        MaterialButtonToggleGroup materialButtonToggleGroup = findViewById(R.id.toggleGroup);

        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked) {
                    if (checkedId == R.id.btn_energy_used) {
                        chart2.setVisibility(View.VISIBLE);
                        chart.setVisibility(View.INVISIBLE);
                    }
                    else if (checkedId == R.id.btn_price) {
                        chart.setVisibility(View.VISIBLE);
                        chart2.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });






    }

}