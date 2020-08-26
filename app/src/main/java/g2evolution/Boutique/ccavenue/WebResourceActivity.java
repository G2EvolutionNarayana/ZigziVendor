package g2evolution.Boutique.ccavenue;

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

import androidx.appcompat.app.AppCompatActivity;

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

import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.Home_Activity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.ccavenue.utility.AvenuesParams;
import g2evolution.Boutique.ccavenue.utility.Constants;
import g2evolution.Boutique.ccavenue.utility.LoadingDialog;
import g2evolution.Boutique.ccavenue.utility.RSAUtility;
import g2evolution.Boutique.ccavenue.utility.ServiceUtility;

public class WebResourceActivity extends AppCompatActivity {
    Intent mainIntent;
    String encVal;
    String vResponse;
    JSONParser jsonParser = new JSONParser();
    String status, message;
    String addressid;
    String paymentmode;

    String products;
    String strtax, strfinaltotal;

    String _userId, _userName, _userEmail, _usermob;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String strtransactionid, strtransactionstatus, strpaymenttype, strtransdate;

    String postData, strmobileno, strFullAddress, strCity, strState, strZipcode, strLandMark;

    String strsubtotal, strshipping, grandtotal;

    String strbuttonstatus, qty, _pid, Productid;
    String strsubtotal_booknow, strshipping_booknow, strfinaltotal_booknow, products_booknow;

    JSONArray responcearray2;
    JSONArray responcearray3;

    Dialog logindialog123;

    String order_id, adminorderId;
    String strorderId;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_resource);

        Integer randomNum = ServiceUtility.randInt(0, 9999999);
        strorderId = randomNum.toString();

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("BuyResource"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        _userId = prefuserdata.getString("UserId", "");
        _userName = prefuserdata.getString("Username", "");
        _userEmail = prefuserdata.getString("Usermail", "");
        _usermob = prefuserdata.getString("Usermob", "");
        get_RSA_key(strorderId);

    }

    public void get_RSA_key(final String od) {
        LoadingDialog.showLoadingDialog(WebResourceActivity.this, "Loading...");
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
                                new WebResourceActivity.RenderView().execute();   // Calling async task to get display content
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
                WebResourceActivity.this).create();

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

    private class RenderView extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            LoadingDialog.showLoadingDialog(WebResourceActivity.this, "Loading...");

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if (!ServiceUtility.chkNull(vResponse).equals("")
                    && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR") == -1) {
                StringBuffer vEncVal = new StringBuffer("");
                try {
                    vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, jsonObject.getString(EndUrl.UploadResource_price)));
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

                        Log.e("testing", "Online");

                        new LoadUploadResource().execute();


                    } else {

                        Toast.makeText(WebResourceActivity.this, strtransactionstatus, Toast.LENGTH_SHORT).show();
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
                    LoadingDialog.showLoadingDialog(WebResourceActivity.this, "Loading...");
                    Log.e("testing", "test 3=== ");
                }
            });


            try {


                String postData = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(EndUrl.straccessCode, "UTF-8") + "&" + AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(EndUrl.strmerchantId, "UTF-8") + "&" + AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(strorderId, "UTF-8") + "&" + AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(EndUrl.strredirectUrl, "UTF-8") + "&" + AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(EndUrl.strcancelUrl, "UTF-8") + "&" + AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(encVal, "UTF-8");

                Log.e("testing", "postData  ------------ = " + postData);
                webview.postUrl(Constants.TRANS_URL, postData.getBytes());


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    public class LoadUploadResource extends AsyncTask<String, String, String> {
        String responce;
        String message;
        String headers;
        String childs;
        String Result;

        String status;
        String strresponse;
        String strdata;
        String strcode, strtype, strmessage;

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(getApplicationContext());
//            pDialog.setMessage("Loading");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();

        }

        protected String doInBackground(String... args) {
            Integer result = 0;
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            Log.e("testing", "jsonParser startedkljhk");
            try {
                userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_resource_packages_id, jsonObject.getString(EndUrl.UploadResource_resource_packages_id)));
                userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_user_id, _userId));
                userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_price, jsonObject.getString(EndUrl.UploadResource_price)));
                userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_transaction_id, jsonObject.getString(EndUrl.UploadResource_transaction_id)));
                userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_payment_status, jsonObject.getString(EndUrl.UploadResource_payment_status)));
                userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_description, jsonObject.getString(EndUrl.UploadResource_description)));
                userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_category_id, jsonObject.getString(EndUrl.UploadResource_category_id)));
                userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_cv_count, jsonObject.getString(EndUrl.UploadResource_cv_count)));
                userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_order_number, strorderId));
                Log.e("testing", userpramas.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            //  Log.e("testing", "feader_reg_id" + id);
            JSONObject json = jsonParser.makeHttpRequest(EndUrl.UploadResource_URL, "POST", userpramas);

            Log.e("testing", "userpramas = " + userpramas);
            Log.e("testing", "jsonParser = " + json);


            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing", "jon2222222222222");
                // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {

                    status = json.getString("status");
                    strresponse = json.getString("response");
                    JSONObject jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");
                    if (status.equals("success")) {
                        Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return responce;
            }


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            pDialog.dismiss();
            if (status == null || status.length() == 0) {

            } else if (status.equals("success")) {

                if (strresponse == null || strresponse.length() == 0) {

                } else if (strtype.equals("save_success")) {
                    Toast.makeText(getApplicationContext(), strmessage, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), strmessage, Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(getApplicationContext(), strmessage, Toast.LENGTH_SHORT).show();
            }


        }


    }
}




