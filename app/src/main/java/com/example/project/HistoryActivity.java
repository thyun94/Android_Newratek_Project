package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButtonToggleGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {

    private static final String LABEL_JSON = "result";
    private static final String LABEL_USER_USERID = "user.userID";
    private static final String LABEL_USERNAME = "userName";
    private static final String LABEL_USERADDRESS = "userAddress";
    private static final String LABEL_PRICEID = "priceID";
    private static final String LABEL_ENERGYUSED = "energyUsed";
    private static final String LABEL_PRICEAMOUNT = "priceAmount";
    private static final String LABEL_PRICEDAY = "priceDay";
    private static final String LABEL_PRICEMONTH = "priceMonth";
    private static final String LABEL_PRICEYEAR = "priceYear";
    private static final String LABEL_PRICE_USERID = "price.userID";

    ArrayList<HashMap<String, String>> resultArraylist;
    String jsonString;

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private ArrayList<RecyclerItem> itemList_all;

    private ArrayList<RecyclerItem> itemList_energy;

    private ArrayList<RecyclerItem> itemList_price;

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

        resultArraylist = new ArrayList<>();

        getData task = new getData();
        task.execute("http://13.125.233.133/dbdata.php");



    }

    @SuppressWarnings("deprecation")
    private class getData extends AsyncTask<String, Void, String> {

        ProgressDialog progDialog;
        String errorString = "error";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progDialog = progDialog.show(HistoryActivity.this, "Please wait", null,true,true);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            result = result.replace("<pre>", "");
            result = result.replace("<br>", "");

            //check the data read from cloud
            progDialog.dismiss();
            Log.d("historyActivity", "response : " + result);

            jsonString = result;


            saveResult();
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
                Log.d("historyActivity", "response code : " + responseStatusCode);

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
                Log.d("historyActivity", "InsertData: error ", e);
                errorString = e.toString();
            }

            return null;
        }
    }


    //save data into hashmap and arraylist
    private void saveResult() {
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
            Log.d("historyActivity", "showResult = ", e);
        }
    }

    private void updateData() {

        //get today's date
        GetDate gdate = new GetDate();

        // Set date to 07-25-2023 for testing purposes
        gdate.setDay("25");
        gdate.setMonth("07");
        gdate.setYear("2023");

        itemList_all = new ArrayList<>();
        itemList_energy = new ArrayList<>();
        itemList_price = new ArrayList<>();

        for (int i = 0; i < resultArraylist.size(); i++) {
            if (resultArraylist.get(i).get(LABEL_PRICEYEAR).equals(gdate.getYear()) &&
                    resultArraylist.get(i).get(LABEL_PRICEMONTH).equals(gdate.getMonth())) {

                itemList_all.add(new RecyclerItem(R.drawable.icon_elec_circle_blue,
                        "Energy Used \n" + resultArraylist.get(i).get(LABEL_PRICEMONTH) + "/" + resultArraylist.get(i).get(LABEL_PRICEDAY),
                        "+ " + resultArraylist.get(i).get(LABEL_ENERGYUSED) + " kWh"));
                itemList_energy.add(new RecyclerItem(R.drawable.icon_elec_circle_blue,
                        "Energy Used \n" + resultArraylist.get(i).get(LABEL_PRICEMONTH) + "/" + resultArraylist.get(i).get(LABEL_PRICEDAY),
                        "+ " + resultArraylist.get(i).get(LABEL_ENERGYUSED) + " kWh"));

                itemList_all.add(new RecyclerItem(R.drawable.icon_dollarsign_circle_yellow,
                        "Price \n" + resultArraylist.get(i).get(LABEL_PRICEMONTH) + "/" + resultArraylist.get(i).get(LABEL_PRICEDAY),
                        "- $ " + resultArraylist.get(i).get(LABEL_PRICEAMOUNT)));
                itemList_price.add(new RecyclerItem(R.drawable.icon_dollarsign_circle_yellow,
                        "Price \n" + resultArraylist.get(i).get(LABEL_PRICEMONTH) + "/" + resultArraylist.get(i).get(LABEL_PRICEDAY),
                        "- $ " + resultArraylist.get(i).get(LABEL_PRICEAMOUNT)));
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerAdapter = new RecyclerAdapter();

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        Collections.reverse(itemList_all);
        Collections.reverse(itemList_energy);
        Collections.reverse(itemList_price);

        mRecyclerAdapter.setItemList(itemList_all);

        //set fixed max height when scrolling
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int a = (displaymetrics.heightPixels * 60) / 100;
        mRecyclerView.getLayoutParams().height = a;



        //create button for toggling between graphs
        MaterialButtonToggleGroup materialButtonToggleGroup = findViewById(R.id.toggleGroup);
        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked) {
                    if (checkedId == R.id.btn_all) {
                        mRecyclerAdapter.setItemList(itemList_all);
                    }
                    else if (checkedId == R.id.btn_energy_used) {
                        mRecyclerAdapter.setItemList(itemList_energy);
                    }
                    else if (checkedId == R.id.btn_price) {
                        mRecyclerAdapter.setItemList(itemList_price);
                    }
                }
            }
        });

    }



}