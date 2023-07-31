//package com.example.project;
//
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.HashMap;
//
//public class GetData extends AsyncTask<String, Void, String>  {
//    ProgressDialog progDialog;
//    String errorString = "error";
//
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//
//        progDialog.dismiss();
//        TextViewResult.setText(result);
//
//        if (result==null) {
//            TextViewResult.setText(errorString);
//        }
//        else {
//            jsonString = result;
//            showResult();
//        }
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
//        String serverURL = params[0];
//
//        try {
//            URL url = new URL(serverURL);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//            httpURLConnection.setReadTimeout(5000);
//            httpURLConnection.setConnectTimeout(5000);
//            httpURLConnection.connect();
//
//            int responseStatusCode = httpURLConnection.getResponseCode();
//            Log.d("mainActivity", "response code : " + responseStatusCode);
//
//            InputStream inputStream;
//            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
//                inputStream = httpURLConnection.getInputStream();
//            }
//            else {
//                inputStream = httpURLConnection.getErrorStream();
//            }
//
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            StringBuilder sb = new StringBuilder();
//            String line;
//
//            while((line = bufferedReader.readLine()) != null) {
//                sb.append(line);
//            }
//
//            bufferedReader.close();
//
//            return sb.toString().trim();
//
//        } catch (Exception e) {
//            Log.d("mainActivity", "InsertData: error ", e);
//            errorString = e.toString();
//        }
//
//        return null;
//    }
//
//    private void showResult() {
//        try {
//            JSONObject jsonObject = new JSONObject(jsonString);
//            JSONArray jsonArray = jsonObject.getJSONArray(LABEL_JSON);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject item = jsonArray.getJSONObject(i);
//
//                String user_userid = item.getString(LABEL_USER_USERID);
//                String username = item.getString(LABEL_USERNAME);
//                String useraddress = item.getString(LABEL_USERADDRESS);
//                String priceid = item.getString(LABEL_PRICEID);
//                String priceamount = item.getString(LABEL_PRICEAMOUNT);
//                String priceday = item.getString(LABEL_PRICEDAY);
//                String pricemonth = item.getString(LABEL_PRICEMONTH);
//                String priceyear = item.getString(LABEL_PRICEYEAR);
//                String price_userid = item.getString(LABEL_PRICE_USERID);
//
//                HashMap<String, String> hm = new HashMap<>();
//
//                hm.put(LABEL_USER_USERID, user_userid);
//                hm.put(LABEL_USERNAME, username);
//                hm.put(LABEL_USERADDRESS, useraddress);
//                hm.put(LABEL_PRICEID, priceid);
//                hm.put(LABEL_PRICEAMOUNT, priceamount);
//                hm.put(LABEL_PRICEDAY, priceday);
//                hm.put(LABEL_PRICEMONTH, pricemonth);
//                hm.put(LABEL_PRICEYEAR, priceyear);
//                hm.put(LABEL_PRICE_USERID, price_userid);
//
//                resultArraylist.add(hm);
//
//            }
//        } catch (JSONException e) {
//            Log.d("mainActivity", "showResult = ", e);
//        }
//    }
//}
