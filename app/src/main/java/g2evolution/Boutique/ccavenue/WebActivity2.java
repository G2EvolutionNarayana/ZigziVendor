package g2evolution.Boutique.ccavenue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import g2evolution.Boutique.Activity.Activity_BookingDelivery2;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.Home_Activity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.ccavenue.utility.AvenuesParams;
import g2evolution.Boutique.ccavenue.utility.Constants;
import g2evolution.Boutique.ccavenue.utility.LoadingDialog;
import g2evolution.Boutique.ccavenue.utility.RSAUtility;
import g2evolution.Boutique.ccavenue.utility.ServiceUtility;

public class WebActivity2 extends AppCompatActivity {

    String encVal;
    String vResponse;
    JSONParser jsonParser = new JSONParser();

    String paymentmode;



    String _userId, _userName, _userEmail, _usermob;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String strtransactionid, strtransactionstatus, strpaymenttype, strtransdate;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("bookDelivery"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        _userId = prefuserdata.getString("UserId", "");
        _userName = prefuserdata.getString("Username", "");
        _userEmail = prefuserdata.getString("Usermail", "");
        _usermob = prefuserdata.getString("Usermob", "");

        try {
            get_RSA_key( jsonObject.getString(AvenuesParams.ORDER_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class RenderView extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            LoadingDialog.showLoadingDialog(WebActivity2.this, "Loading...");
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if (!ServiceUtility.chkNull(vResponse).equals("")
                    && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR") == -1) {
                StringBuffer vEncVal = new StringBuffer("");
                try {
                    vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, jsonObject.getString(EndUrl.StoreBookingDelivery_price)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, "1"));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, EndUrl.strcurrency));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_COUNTRY, "India"));

                encVal = RSAUtility.encrypt(vEncVal.substring(0, vEncVal.length() - 1), vResponse);  //encrypt amount and currency
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.e("testing", "result=== " + result);
            // Dismiss the progress dialog
            LoadingDialog.cancelLoading();

            @SuppressWarnings("unused")
            class MyJavaScriptInterface {
                @JavascriptInterface
                public void processHTML(String html) {
                    Log.e("testing", "html ============= " + html);

                    Document doc = Jsoup.parse(html);

                    Elements metaElems = doc.select("tr");

                    for (Element metaElem : metaElems) {
                        Elements col = metaElem.select("td");
                        String tag = col.get(0).text();
                        String val = col.get(1).text();

                        if (tag.equals("order_id")) {
                            Log.e("testing", "order_id = " + val);
                        } else if (tag.equals("status_message")) {
                            Log.e("testing", "status_message = " + val);
                        } else if (tag.equals("tracking_id")) {
                            Log.e("testing", "tracking_id = " + val);
                            strtransactionid = val;
                        } else if (tag.equals("order_status")) {
                            Log.e("testing", "order_status = " + val);
                            strtransactionstatus = val;
                        } else if (tag.equals("payment_mode")) {
                            Log.e("testing", "payment_mode = " + val);
                            strpaymenttype = val;
                        } else if (tag.equals("trans_date")) {
                            Log.e("testing", "trans_date = " + val);
                            strtransdate = val;
                        }
                    }


                    // process the html source code to get final status of transaction
                    String status = null;
                    if (html.indexOf("Failure") != -1) {
                        status = "Transaction Declined!";
                        Log.e("testing", "Transaction Declined! == ");
                    } else if (html.indexOf("Success") != -1) {

                        status = "Transaction Successful!";
                        Log.e("testing", "Transaction Successful! == ");
                    } else if (html.indexOf("Aborted") != -1) {

                        Log.e("testing", "Transaction Cancelled!== ");
                        status = "Transaction Cancelled!";

                        Log.e("testing", "Transaction Cancelled!== ");
                    } else {
                        status = "Status Not Known!";
                        Log.e("testing", "Status Not Known! ");
                    }


                    if (strtransactionstatus.equals("Success")) {


                        paymentmode = "Online";

                        new StoreBookingDelivery().execute();



                    } else {

                        Toast.makeText(WebActivity2.this, strtransactionstatus, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            final WebView webview = (WebView) findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);
            Log.e("testing", "test 1=== ");
            webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(webview, url);
                    LoadingDialog.cancelLoading();
                    Log.e("testing", "test 2=== ");
                    if (url.indexOf("/ccavResponseHandler.php") != -1) {

                        Log.e("testing", "test 4=== ");
                        webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");

                        Log.e("testing", "test 5=== ");
                    }
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    LoadingDialog.showLoadingDialog(WebActivity2.this, "Loading...");
                    Log.e("testing", "test 3=== ");
                }
            });


            try {


                String postData = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(EndUrl.straccessCode, "UTF-8") + "&" + AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(EndUrl.strmerchantId, "UTF-8") + "&" + AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(jsonObject.getString(AvenuesParams.ORDER_ID), "UTF-8") + "&" + AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(EndUrl.strredirectUrl, "UTF-8") + "&" + AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(EndUrl.strcancelUrl, "UTF-8") + "&" + AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(encVal, "UTF-8");

                Log.e("testing", "postData  ------------ = " + postData);
                webview.postUrl(Constants.TRANS_URL, postData.getBytes());


            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void get_RSA_key(final String od) {
        LoadingDialog.showLoadingDialog(WebActivity2.this, "Loading...");
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "mainIntent.getStringExtra(AvenuesParams.RSA_KEY_URL)",

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://new-zigzi.g2evolution.com/ccavenue/PHP/GetRSA.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(WebViewActivity.this,response,Toast.LENGTH_LONG).show();
                        LoadingDialog.cancelLoading();

                        if (response != null && !response.equals("")) {
                            vResponse = response.toString();     ///save retrived rsa key


                            if (vResponse.contains("!ERROR!")) {
                                show_alert(vResponse);
                            } else {
                                new WebActivity2.RenderView().execute();   // Calling async task to get display content
                            }
                        } else {
                            show_alert("No response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LoadingDialog.cancelLoading();
                        //Toast.makeText(WebViewActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put(AvenuesParams.ACCESS_CODE, ac);
                params.put(AvenuesParams.ORDER_ID, od);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void show_alert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                WebActivity2.this).create();

        alertDialog.setTitle("Error!!!");
        if (msg.contains("\n"))
            msg = msg.replaceAll("\\\n", "");

        alertDialog.setMessage(msg);


        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });


        alertDialog.show();
    }

    class StoreBookingDelivery extends AsyncTask<String, String, String> {
        String responce;
        JSONArray responcearccay;
        String status;
        String strresponse;
        String strdata;
        ProgressDialog mProgress;
        String strcode, strtype, strmessage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(WebActivity2.this);
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            try {
                userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_from_latitude, jsonObject.getString(EndUrl.StoreBookingDelivery_from_latitude)));
                userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_from_longitude, jsonObject.getString(EndUrl.StoreBookingDelivery_from_longitude)));
                userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_to_latitude, jsonObject.getString(EndUrl.StoreBookingDelivery_to_latitude)));
                userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_to_longitude, jsonObject.getString(EndUrl.StoreBookingDelivery_to_longitude)));
                userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_delivery_module_id, jsonObject.getString(EndUrl.StoreBookingDelivery_delivery_module_id)));
                userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_from_address, jsonObject.getString(EndUrl.StoreBookingDelivery_from_address)));
                userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_to_address, jsonObject.getString(EndUrl.StoreBookingDelivery_to_address)));
                userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_user_id, jsonObject.getString(EndUrl.StoreBookingDelivery_user_id)));
                userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_price, jsonObject.getString(EndUrl.StoreBookingDelivery_price)));
                userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_pickup_time, jsonObject.getString(EndUrl.StoreBookingDelivery_pickup_time)));
                userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_distance, jsonObject.getString(EndUrl.StoreBookingDelivery_distance)));
                userpramas.add(new BasicNameValuePair(EndUrl.PlaceOrder_payment_mode_id, "2"));

            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.StoreBookingDelivery_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing", "jon2222222222222");

                try {

                    status = json.getString("status");
                    strresponse = json.getString("response");
                    JSONObject jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return responce;
            }
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            mProgress.dismiss();
            Log.e("testing", "status = " + status);
            Log.e("testing", "strresponse = " + strresponse);
            Log.e("testing", "strmessage = " + strmessage);

            if (status == null || status.length() == 0) {

            } else if (status.equals("success")) {
                Toast.makeText(WebActivity2.this, strmessage, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WebActivity2.this, Home_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            } else if (status.equals("failure")) {
                Toast.makeText(WebActivity2.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            } else {
                Toast.makeText(WebActivity2.this, strmessage, Toast.LENGTH_SHORT).show();
            }


        }

    }




}