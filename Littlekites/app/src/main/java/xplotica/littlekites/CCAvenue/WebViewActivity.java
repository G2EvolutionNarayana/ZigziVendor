package xplotica.littlekites.CCAvenue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
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

import xplotica.littlekites.Activity_parent.Activity_Parent_Home;
import xplotica.littlekites.CCAvenue.utility.AvenuesParams;
import xplotica.littlekites.CCAvenue.utility.Constants;
import xplotica.littlekites.CCAvenue.utility.LoadingDialog;
import xplotica.littlekites.CCAvenue.utility.RSAUtility;
import xplotica.littlekites.CCAvenue.utility.ServiceUtility;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.Parent_feesinfo;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.JSONParser;

public class WebViewActivity extends AppCompatActivity {
    Intent mainIntent;
    String encVal;
    String vResponse;
    JSONParser jsonParser = new JSONParser();
    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile;
    String instalmentid, stramount;
    String strtransactionid, strtransactionstatus, strpaymenttype, strtransdate;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_webview);

        SharedPreferences prefuserdata= getSharedPreferences("registerUser",0);
        type=prefuserdata.getString("type","2");
        School_id=prefuserdata.getString("schoolid","");
        School_name=prefuserdata.getString("schoolname","");
        Student_id=prefuserdata.getString("studentid","");
        Classid=prefuserdata.getString("classid","");
        Sectionid=prefuserdata.getString("sectionid","");
        mobile=prefuserdata.getString("mobile","");


        SharedPreferences prefuserdata2= getSharedPreferences("parentinstal",0);
        instalmentid=prefuserdata2.getString("instalmentid","");

        SharedPreferences prefuserdata3= getSharedPreferences("paymentdetails",0);
        stramount=prefuserdata3.getString("stramount","");


        Log.e("testing","type = "+type);
        Log.e("testing","School_id = "+School_id);
        Log.e("testing","School_name = "+School_name);
        Log.e("testing","Student_id = "+Student_id);
        Log.e("testing","Classid = "+Classid);
        Log.e("testing","Sectionid = "+Sectionid);
        Log.e("testing","mobile = "+mobile);
        Log.e("testing","instalmentid = "+instalmentid);
        Log.e("testing","stramount = "+stramount);

        mainIntent = getIntent();

//get rsa key method
        get_RSA_key(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), mainIntent.getStringExtra(AvenuesParams.ORDER_ID));
    }



    private class RenderView extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if (!ServiceUtility.chkNull(vResponse).equals("")
                    && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR") == -1) {
                StringBuffer vEncVal = new StringBuffer("");
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, mainIntent.getStringExtra(AvenuesParams.AMOUNT)));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, mainIntent.getStringExtra(AvenuesParams.CURRENCY)));
                encVal = RSAUtility.encrypt(vEncVal.substring(0, vEncVal.length() - 1), vResponse);  //encrypt amount and currency
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.e("testing","result=== "+result);
            // Dismiss the progress dialog
            LoadingDialog.cancelLoading();

            @SuppressWarnings("unused")
            class MyJavaScriptInterface {
                @JavascriptInterface
                public void processHTML(String html) {
                    Log.e("testing","html ============= "+html);

                    Document doc  = Jsoup.parse(html);

                    Elements metaElems = doc.select("tr");

                    for (Element metaElem : metaElems) {
                        Elements col = metaElem.select("td");
                        String tag = col.get(0).text();
                        String val =  col.get(1).text();
                        if(tag.equals("order_id")){
                            Log.e("testing","order_id = "+val);
                        }else if(tag.equals("status_message")){
                            Log.e("testing","status_message = "+val);
                        }else if(tag.equals("tracking_id")){
                            Log.e("testing","tracking_id = "+val);
                            strtransactionid = val;
                        }else if(tag.equals("order_status")){
                            Log.e("testing","order_status = "+val);
                            strtransactionstatus = val;
                        }else if(tag.equals("payment_mode")){
                            Log.e("testing","payment_mode = "+val);
                            strpaymenttype = val;
                        }else if(tag.equals("trans_date")){
                            Log.e("testing","trans_date = "+val);
                            strtransdate = val;
                        }




                    }


                    // process the html source code to get final status of transaction
                    String status = null;
                    if (html.indexOf("Failure") != -1) {
                        status = "Transaction Declined!";
                        Log.e("testing","Transaction Declined! == ");
                    } else if (html.indexOf("Success") != -1) {

                        status = "Transaction Successful!";
                        Log.e("testing","Transaction Successful! == ");
                    } else if (html.indexOf("Aborted") != -1) {

                        Log.e("testing","Transaction Cancelled!== ");
                        status = "Transaction Cancelled!";

                        Log.e("testing","Transaction Cancelled!== ");
                    } else {
                        status = "Status Not Known!";
                        Log.e("testing","Status Not Known! ");
                    }



                    if (strtransactionstatus.equals("Success")){
                        new TopTrend().execute();

                    }else{
                        Toast.makeText(WebViewActivity.this, strtransactionstatus, Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                   /* Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                    intent.putExtra("transStatus", status);
                    intent.putExtra("strtransactionid", strtransactionid);
                    intent.putExtra("strtransactionstatus", strtransactionstatus);
                    intent.putExtra("strtransdate", strtransdate);
                    startActivity(intent);*/
                }
            }

            final WebView webview = (WebView) findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);
            Log.e("testing","test 1=== ");
            webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(webview, url);
                    LoadingDialog.cancelLoading();
                    Log.e("testing","test 2=== ");
                    if (url.indexOf("/ccavResponseHandler.php") != -1) {

                        Log.e("testing","test 4=== ");
                        webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");

                        Log.e("testing","test 5=== ");
                    }
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");
                    Log.e("testing","test 3=== ");
                }
            });


            try {
                String postData = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), "UTF-8") + "&" + AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID), "UTF-8") + "&" + AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ORDER_ID), "UTF-8") + "&" + AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL), "UTF-8") + "&" + AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.CANCEL_URL), "UTF-8") + "&" + AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(encVal, "UTF-8");

                Log.e("testing","postData   = "+postData);
                webview.postUrl(Constants.TRANS_URL, postData.getBytes());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    public void get_RSA_key(final String ac, final String od) {
        LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, mainIntent.getStringExtra(AvenuesParams.RSA_KEY_URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(WebViewActivity.this,response,Toast.LENGTH_LONG).show();
                        LoadingDialog.cancelLoading();

                        if (response != null && !response.equals("")) {
                            vResponse = response;     ///save retrived rsa key
                            if (vResponse.contains("!ERROR!")) {
                                show_alert(vResponse);
                            } else {
                                new RenderView().execute();   // Calling async task to get display content
                            }


                        }
                        else
                        {
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
                params.put(AvenuesParams.ACCESS_CODE, ac);
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
                WebViewActivity.this).create();

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

    class TopTrend extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {
        String responce;
        String message;
        String status;
        String arrayresponce;
        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(WebViewActivity.this);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(End_Urls.PARENTPAYMENT_schoolId, School_id));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENTPAYMENT_studentId, Student_id));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENTPAYMENT_installmentId, instalmentid));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENTPAYMENT_transationId, strtransactionid));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENTPAYMENT_transactionStatus, strtransactionstatus));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENTPAYMENT_transtactionDate, strtransdate));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENTPAYMENT_paymentType, "Installment"));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENTPAYMENT_amount, stramount));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.PARENTPAYMENT_URL, "POST", userpramas);

            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");



            } catch (JSONException e) {
                e.printStackTrace();
            }

            return responce;

        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            new CountDownTimer(700, 100) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    try {
                        pDialog.dismiss();
                        pDialog = null;
                    } catch (Exception e) {
                        //TODO: Fill in exception
                    }
                }
            }.start();
            Log.e("testing", "result is = " + responce);


            if (status.equals("success")){
                Intent intent = new Intent(getApplicationContext(), Activity_Parent_Home.class);
                startActivity(intent);
                finish();


            }else if (status.equals("no")){
                Toast.makeText(WebViewActivity.this, ""+arrayresponce, Toast.LENGTH_SHORT).show();


            }

        }
    }



}