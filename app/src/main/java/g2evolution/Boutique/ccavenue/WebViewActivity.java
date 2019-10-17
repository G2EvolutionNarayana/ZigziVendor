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
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import g2evolution.Boutique.ccavenue.utility.AvenuesParams;
import g2evolution.Boutique.ccavenue.utility.Constants;
import g2evolution.Boutique.ccavenue.utility.LoadingDialog;
import g2evolution.Boutique.ccavenue.utility.RSAUtility;
import g2evolution.Boutique.ccavenue.utility.ServiceUtility;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.MainActivity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class WebViewActivity extends AppCompatActivity {


    Intent mainIntent;
    String encVal;
    String vResponse;
    JSONParser jsonParser = new JSONParser();
    String status, message;
    String addressid;
    String paymentmode;

    String products;
    String strtax,strfinaltotal;

    String _userId, _userName, _userEmail, _usermob;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String strtransactionid, strtransactionstatus, strpaymenttype, strtransdate;

    String postData,strmobileno,strFullAddress;

    String strsubtotal,strshipping,grandtotal;

    String strbuttonstatus, qty, _pid,Productid;
    String strsubtotal_booknow, strshipping_booknow, strfinaltotal_booknow, products_booknow;

    JSONArray responcearray2;
    JSONArray responcearray3;

    Dialog logindialog123;

    String order_id,adminorderId;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_webview);

        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        _userId = prefuserdata.getString("UserId", "");
        _userName = prefuserdata.getString("Username", "");
        _userEmail = prefuserdata.getString("Usermail", "");
        _usermob = prefuserdata.getString("Usermob", "");

        SharedPreferences prefuserdata4 = getSharedPreferences("addressid", 0);
        addressid = prefuserdata4.getString("addressid", "");
        strmobileno = prefuserdata4.getString("strmobileno", "");
        strFullAddress = prefuserdata4.getString("strFullAddress", "");

        SharedPreferences prefuserdata12 = getSharedPreferences("paymentamount", 0);
        grandtotal = prefuserdata12.getString("grandtotal", "");
      //  strsubtotal = prefuserdata12.getString("strsubtotal", "");
     //   strshipping = prefuserdata12.getString("strshipping", "");


        SharedPreferences prefuserdata3 = this.getSharedPreferences("ProDetails", 0);
        Productid = prefuserdata3.getString("Proid", "");


        SharedPreferences prefuserdata123 = getSharedPreferences("regcart", 0);

        strbuttonstatus = prefuserdata123.getString("bookstatus", "");
        qty = prefuserdata123.getString("qty", "");
        _pid = prefuserdata123.getString("productid", "" + _pid);


        if (strbuttonstatus.equals("addtocart")) {

            Log.e("testing", "strbuttonstatus==" + strbuttonstatus);

            new Add_to_Cart().execute();

        } else if (strbuttonstatus.equals("booknow")) {

            Log.e("testing", "strbuttonstatus==" + strbuttonstatus);

            new BOOK_Now().execute();

        }


       // new Loader2().execute();

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

                        if (strbuttonstatus.equals("addtocart")){

                            paymentmode = "Online";

                            new Add_to_Cart_Online().execute();

                        }else if (strbuttonstatus.equals("booknow")){

                            paymentmode = "Online";

                            new Book_Now_Online().execute();


                        }else {


                        }


                    }else{

                        Toast.makeText(WebViewActivity.this, strtransactionstatus, Toast.LENGTH_SHORT).show();
                    }

                    /*//Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
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

            /*    if (postData==null||postData.length()==0||postData.equals("null")||postData.equals("0")){


                    Log.e("testing","postData====null--=="+postData);

                }else {

                    postData = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), "UTF-8") + "&" + AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID), "UTF-8") + "&" + AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ORDER_ID), "UTF-8") + "&" + AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL), "UTF-8") + "&" + AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.CANCEL_URL), "UTF-8") + "&" + AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(encVal, "UTF-8");

                    Log.e("testing","postData  ------------ = "+postData);
                    webview.postUrl(Constants.TRANS_URL, postData.getBytes());

                }*/

              String  postData = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), "UTF-8") + "&" + AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID), "UTF-8") + "&" + AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ORDER_ID), "UTF-8") + "&" + AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL), "UTF-8") + "&" + AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.CANCEL_URL), "UTF-8") + "&" + AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(encVal, "UTF-8");

                Log.e("testing","postData  ------------ = "+postData);
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




    class Add_to_Cart_Online extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(WebViewActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(WebViewActivity.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(EndUrl.ConfirmOrder_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

/*

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

*/

                    SharedPreferences prefuserdata = getSharedPreferences("ordersuccess", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("order_id", "" + order_id);

                    prefeditor.commit();

                    logindialog123 = new Dialog(WebViewActivity.this);
                    logindialog123.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(WebViewActivity.this.LAYOUT_INFLATER_SERVICE);
                    View convertView = (View) inflater.inflate(R.layout.order_success, null);

                /* StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);*/

                    logindialog123.setContentView(convertView);
                    logindialog123.setCancelable(false);
                    //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                    // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                    logindialog123.setCanceledOnTouchOutside(false);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(logindialog123.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.CENTER;
                    logindialog123.getWindow().setAttributes(lp);

                    TextView order_success=(TextView)convertView.findViewById(R.id.orderid);
                    order_success.setText(adminorderId);
                    Button addsubmit=(Button)convertView.findViewById(R.id.addsubmit);

                    addsubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logindialog123.dismiss();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                    });

                    logindialog123.show();



                } else if (status.equals("error")) {

                    Toast.makeText(WebViewActivity.this, "no data found", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(WebViewActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson() {

        JSONObject jobj3 = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {
            JSONObject jobj = new JSONObject();
            JSONObject jobj2 = new JSONObject();
           // JSONObject jobj4 = new JSONObject();

            jobj.put("status", "Pending");
            jobj.put("payment_mode", paymentmode);
            jobj.put("total_amount", strfinaltotal);
            jobj.put("delivery_addressId", addressid);
           // jobj.put("FullAddress", strFullAddress);
            jobj.put("transaction_id", strtransactionid);



            jobj2.put("customer_id", _userId);
            jobj2.put("customer_email", _userEmail);
            jobj2.put("customer_name", _userName);
            jobj2.put("customer_mobileno", _usermob);
       //     jobj4.put("FullAddress", strFullAddress);


            jobj3.put("FullAddress", strFullAddress);
            jobj3.put("strsubtotal",strsubtotal);
            jobj3.put("strshipping", strshipping);

            jobj3.put("order", jobj);
            jobj3.put("customer", jobj2);
            jobj3.put("items", responcearray2);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.e("testing","jobj3 = "+jobj3);


        return jobj3;

    }



    public JSONObject postJsonObject(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;

        try {

            json = new JSONObject(result);
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));
            status = json.getString("status");
            message = json.getString("message");

            String  JSON = json.getString("JSON");

       String  data = json.getString("data");
            JSONArray jsonarray =new JSONArray(data);


            for (int i = 0; i < jsonarray.length(); i++) {

                JSONObject jsonObject=jsonarray.getJSONObject(i);

                adminorderId =jsonObject.optString("adminorderId");


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }






    class Book_Now_Online extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(WebViewActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(WebViewActivity.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject123(EndUrl.ConfirmOrder_URL, makingJson123());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

/*

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


*/
                    SharedPreferences prefuserdata = getSharedPreferences("ordersuccess", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("order_id", "" + order_id);

                    prefeditor.commit();

                    logindialog123 = new Dialog(WebViewActivity.this);
                    logindialog123.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(WebViewActivity.this.LAYOUT_INFLATER_SERVICE);
                    View convertView = (View) inflater.inflate(R.layout.order_success, null);

                /* StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);*/

                    logindialog123.setContentView(convertView);
                    logindialog123.setCancelable(false);
                    //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                    // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                    logindialog123.setCanceledOnTouchOutside(false);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(logindialog123.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.CENTER;
                    logindialog123.getWindow().setAttributes(lp);

                    TextView order_success=(TextView)convertView.findViewById(R.id.orderid);
                    order_success.setText(adminorderId);
                    Button addsubmit=(Button)convertView.findViewById(R.id.addsubmit);

                    addsubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logindialog123.dismiss();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                    });

                    logindialog123.show();


                } else if (status.equals("error")) {

                    Toast.makeText(WebViewActivity.this, "no data found", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(WebViewActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson123() {

        JSONObject jobj3 = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {
            JSONObject jobj = new JSONObject();
            JSONObject jobj2 = new JSONObject();
            // JSONObject jobj4 = new JSONObject();

            jobj.put("status", "Pending");
            jobj.put("payment_mode", paymentmode);
            jobj.put("total_amount", strsubtotal_booknow);
            jobj.put("delivery_addressId", addressid);
            // jobj.put("FullAddress", strFullAddress);
            jobj.put("transaction_id", strtransactionid);



            jobj2.put("customer_id", _userId);
            jobj2.put("customer_email", _userEmail);
            jobj2.put("customer_name", _userName);

            jobj2.put("customer_mobileno", _usermob);
            //     jobj4.put("FullAddress", strFullAddress);


            jobj3.put("FullAddress", strFullAddress);
            jobj3.put("strsubtotal",strfinaltotal_booknow);
            jobj3.put("strshipping", strshipping_booknow);

            jobj3.put("order", jobj);
            jobj3.put("customer", jobj2);
            jobj3.put("items", responcearray3);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.e("testing","jobj3 = "+jobj3);


        return jobj3;

    }



    public JSONObject postJsonObject123(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString123(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;

        try {

            json = new JSONObject(result);
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));
            status = json.getString("status");
            message = json.getString("message");
            // data = json.getString("data");
            String  JSON = json.getString("JSON");

            String   data = json.getString("data");

            JSONArray jsonarray =new JSONArray(data);


            for (int i = 0; i < jsonarray.length(); i++) {

                JSONObject jsonObject=jsonarray.getJSONObject(i);

                adminorderId =jsonObject.optString("adminorderId");


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private String convertInputStreamToString123(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


   /* class Loader2 extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(WebViewActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(WebViewActivity.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject2(EndUrl.GetuserCartDetails_URL, makingJson2());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {
                   // textprice.setText(strfinaltotal);

                }else{

                }



            } else {
                Toast.makeText(WebViewActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson2() {


        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.GetuserCartDetails_Id, _userId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("testing","json object "+jobj);
        return jobj;

    }



    public JSONObject postJsonObject2(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString2(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;

        try {

            json = new JSONObject(result);
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));
            status = json.getString("status");
            message = json.getString("message");
            // data = json.getString("data");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);


            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();


                strsubtotal = post.getString("total");
                strshipping = post.getString("shipppingAmount");
                strtax = post.getString("tax");
                strfinaltotal = post.getString("subTotal");
                products = post.getString("products");


                responcearray2 = new JSONArray(products);

                Log.e("testing1", "responcearray value=" + responcearray2);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private String convertInputStreamToString2(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }



*/
    //===============================get cart user details--------------------------------------//

    class Add_to_Cart extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(WebViewActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(WebViewActivity.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject6(EndUrl.GetuserCartDetails_URL, makingJson6());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);
                if (status.equals("success")) {
/*
                    final String strrs = getResources().getString(R.string.Rs);
                    amt.setText(strrs +" "+strfinaltotal);
                    total.setText(strrs +" "+strsubtotal);

                    if (strshipping==null||strshipping.length()==0||strshipping.equals("")){


                        shippingamount.setText(strrs +" "+" N.A");

                    }else {

                        shippingamount.setText(strrs +" "+strshipping);
                    }

                    */
                }else{


                }

            } else {
                Toast.makeText(WebViewActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson6() {


        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.GetuserCartDetails_Id, _userId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("testing","json object "+jobj);
        return jobj;

    }



    public JSONObject postJsonObject6(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString3(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;

        try {

            json = new JSONObject(result);
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));

            status = json.getString("status");
            message = json.getString("message");
            // data = json.getString("data");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);


            for (int i = 0; i < responcearray.length(); i++) {


                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();


                strsubtotal = post.getString("total");
                strshipping = post.getString("shipppingAmount");
                //     strtax = post.getString("tax");
                strfinaltotal = post.getString("subTotal");
                products = post.getString("products");


                responcearray2 = new JSONArray(products);

                Log.e("testing", "responcearray value====products=====" + responcearray2);
                Log.e("testing", "products value====products=====" + products);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private String convertInputStreamToString3(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    private class BOOK_Now extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(WebViewActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(WebViewActivity.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject3(EndUrl.BOOK_NOW_URL, makingJson3());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){

/*

                    if (strsubtotal_booknow.length()==0||strsubtotal_booknow==null||strsubtotal_booknow.equals("")){
                        final String strrs = getResources().getString(R.string.Rs);
                        amt.setText(strrs+" "+strsubtotal_booknow);

                        total.setText(strrs +" "+strfinaltotal_booknow);

                        if (strshipping_booknow==null||strshipping_booknow.length()==0||strshipping_booknow.equals("")){


                            shippingamount.setText(strrs +" "+"N.A");
                        }else {

                            shippingamount.setText(strrs +" "+strshipping_booknow);
                        }
                    }else {
                        final String strrs = getResources().getString(R.string.Rs);
                        amt.setText(strrs+" "+strsubtotal_booknow);
                        shippingamount.setText(strrs +" "+strshipping_booknow);
                        total.setText(strrs +" "+strfinaltotal_booknow);
                    }

*/


                }else {
                    Toast.makeText(WebViewActivity.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {

                Toast.makeText(WebViewActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson3() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            // object.put(EndUrl.Addtocart_userId,UserId);
            details.put(EndUrl.BOOK_NOW_productId,Productid);
            details.put(EndUrl.BOOK_NOW_quantity,qty);



            //if you want to modify some value just do like this.

            // details.put(EndUrl.Addtocart_outside_otp,object);
            Log.d("json",details.toString());
            //   Log.d("json",object.toString());
            Log.e("testing","json====book_now"+details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

    }

    public JSONObject postJsonObject3(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));
            Log.e("testing","result in post message========="+json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);


            for (int i = 0; i < responcearray.length(); i++) {


                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();


                strsubtotal_booknow = post.getString("subTotal");
                strshipping_booknow = post.getString("shipppingAmount");
                //  strtax_booknow = post.getString("tax");
                strfinaltotal_booknow = post.getString("total");
                products_booknow = post.getString("products");


                responcearray3 = new JSONArray(products_booknow);

                Log.e("testing1", "responcearray value====products=====" + responcearray3);


            }


       //     Log.e("testing","testing==afterDiscount="+afterDiscount);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }


}