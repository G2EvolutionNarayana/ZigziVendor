package g2evolution.GMT.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import g2evolution.GMT.FeederInfo.FeederInfo_review;
import g2evolution.GMT.ccavenue.InitialScreenActivity;
import g2evolution.GMT.EndUrl;
import g2evolution.GMT.MainActivity;
import g2evolution.GMT.R;

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
import java.util.HashMap;


public class Activity_PaymentMode extends AppCompatActivity {


    TextView amt,total,shippingamount;
    Button pay;
    // For Radio buttons
    RadioGroup radioselGroup;
    int pos;
    int pos1;
    String spstring;
    String PAmount, Productid, UserId;

    String status, message;
    String strsubtotal, strshipping, strfinaltotal;
    JSONArray responcearray;
    String paymentmode;
    JSONArray responcearray2;
    JSONArray responcearray3;
    String products;
    String addressid, Username, Usermail;

    String arrayresponce, strmobileno;

    String strbuttonstatus, qty, _pid, afterDiscount;

    String strsubtotal_booknow, strshipping_booknow, strfinaltotal_booknow, products_booknow;

    String adminorderId,Usermob,strFullAddress;

    Dialog logindialog123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode);


        SharedPreferences prefuserdata1 = this.getSharedPreferences("pay", 0);
        PAmount = prefuserdata1.getString("Apay", "");


        SharedPreferences prefuserdata3 = this.getSharedPreferences("ProDetails", 0);
        Productid = prefuserdata3.getString("Proid", "");


        SharedPreferences prefuserdata2 = getSharedPreferences("regId", 0);
        UserId = prefuserdata2.getString("UserId", "");
        Username = prefuserdata2.getString("Username", "");
        Usermail = prefuserdata2.getString("Usermail", "");
        Usermob = prefuserdata2.getString("Usermob", "");


        SharedPreferences prefuserdata = getSharedPreferences("regcart", 0);

        strbuttonstatus = prefuserdata.getString("bookstatus", "");
        qty = prefuserdata.getString("qty", "");
        _pid = prefuserdata.getString("productid", "" + _pid);


        Log.e("testing", "strbuttonstatus====" + strbuttonstatus);


        SharedPreferences prefuserdata4 = getSharedPreferences("addressid", 0);
        addressid = prefuserdata4.getString("addressid", "");
        strmobileno = prefuserdata4.getString("strmobileno", "");
        strFullAddress = prefuserdata4.getString("strFullAddress", "");

        Log.e("testing", "addressid = " + addressid);


        if (strbuttonstatus.equals("addtocart")) {

            Log.e("testing", "strbuttonstatus==" + strbuttonstatus);

            new Add_to_Cart().execute();

        } else if (strbuttonstatus.equals("booknow")) {

            Log.e("testing", "strbuttonstatus==" + strbuttonstatus);

            new BOOK_Now().execute();

        }

        amt = (TextView) findViewById(R.id.amount);
        shippingamount = (TextView) findViewById(R.id.dcharge);
        total = (TextView) findViewById(R.id.netamount);
        pay = (Button) findViewById(R.id.pay);

        // For Radio Buttons
        radioselGroup = (RadioGroup) findViewById(R.id.radiocancel);
        spstring = "0";


        radioselGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos = radioselGroup.indexOfChild(findViewById(checkedId));


               /* Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/

                //Method 2 For Getting Index of RadioButton
                pos1 = radioselGroup.indexOfChild(findViewById(radioselGroup.getCheckedRadioButtonId()));

                /*Toast.makeText(getBaseContext(), "Method 2 ID = " + String.valueOf(pos1),
                        Toast.LENGTH_SHORT).show();*/

                switch (pos) {


                    /*case 0:

                        spstring = "1";

                        *//*Toast.makeText(getBaseContext(), "You have Clicked RadioButton 1",
                                Toast.LENGTH_SHORT).show();*//*

                        break;

                    case 1:

                        spstring = "2";

                        break;
                        */


                    case 0:

                        spstring = "1";

                        break;

                    case 1:

                        spstring = "2";

                        break;

                    default:

                        //The default selection is RadioButton 1
                        Toast.makeText(getApplicationContext(), " You have Clicked RadioButton 1",
                                Toast.LENGTH_SHORT).show();

                        break;

                }

            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (strbuttonstatus.equals("addtocart")) {


                    if (amt.getText().toString().equals("") || amt.getText().toString().equals("0") || amt.getText().toString().equals("null")) {

                        Toast.makeText(getApplicationContext(), "You Don't Have any Amount to Pay", Toast.LENGTH_SHORT).show();

                    } else {

                        if (spstring.equals("1")) {

                            Intent intent = new Intent(getApplicationContext(), InitialScreenActivity.class);
                            //  Toast.makeText(Activity_PaymentMode.this, "Online Payment", Toast.LENGTH_SHORT).show();
                            SharedPreferences prefuserdata = getSharedPreferences("paymentamount", 0);
                            SharedPreferences.Editor prefeditor = prefuserdata.edit();
                            prefeditor.putString("grandtotal", "" + strfinaltotal);
                            prefeditor.putString("strsubtotal", "" + strsubtotal);
                            prefeditor.putString("strshipping", "" + strshipping);

                            prefeditor.commit();

                            startActivity(intent);

                        } else if (spstring.equals("2")) {

                            paymentmode = "COD";
                            // Toast.makeText(Activity_PaymentMode.this, "COD", Toast.LENGTH_SHORT).show();

                            new Add_to_cart_COD().execute();


                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select Payment Mode", Toast.LENGTH_SHORT).show();

                        }

                    }
                } else if (strbuttonstatus.equals("booknow")){

                    if (amt.getText().toString().equals("") || amt.getText().toString().equals("0") || amt.getText().toString().equals("null")) {
                        Toast.makeText(getApplicationContext(), "You Don't Have any Amount to Pay", Toast.LENGTH_SHORT).show();
                    } else {

                        if (spstring.equals("1")) {

                            Intent intent = new Intent(getApplicationContext(), InitialScreenActivity.class);
                            //  Toast.makeText(Activity_PaymentMode.this, "Online Payment", Toast.LENGTH_SHORT).show();
                            SharedPreferences prefuserdata = getSharedPreferences("paymentamount", 0);
                            SharedPreferences.Editor prefeditor = prefuserdata.edit();
                            // prefeditor.putString("grandtotal", "" + strfinaltotal);
                            prefeditor.putString("grandtotal", "" + strsubtotal_booknow);
                            prefeditor.putString("strsubtotal", "" + strsubtotal_booknow);
                            prefeditor.putString("strshipping", "" + strshipping_booknow);

                            prefeditor.commit();


                            startActivity(intent);

                        } else if (spstring.equals("2")) {

                            paymentmode = "COD";
                            // Toast.makeText(Activity_PaymentMode.this, "COD", Toast.LENGTH_SHORT).show();

                            new BOOKNOW_CONFIRMORDER().execute();


                        } else {

                            Toast.makeText(getApplicationContext(), "Please Select Payment Mode", Toast.LENGTH_SHORT).show();

                        }

                    }

                }

            }
        });

    }

    class Add_to_cart_COD extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_PaymentMode.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_PaymentMode.this);
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

                    SharedPreferences prefuserdata = getSharedPreferences("ordersuccess", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("order_id", "" + adminorderId);
                    prefeditor.commit();

                    logindialog123 = new Dialog(Activity_PaymentMode.this);
                    logindialog123.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Activity_PaymentMode.this.LAYOUT_INFLATER_SERVICE);
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

                }


            } else if (status.equals("error")) {

                Toast.makeText(Activity_PaymentMode.this, "no data found", Toast.LENGTH_LONG).show();
            }else {


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
        //    jobj.put("FullAddress", strFullAddress);

            jobj2.put("customer_id", UserId);
            jobj2.put("customer_email", Usermail);
            jobj2.put("customer_name", Username);
            jobj2.put("customer_mobileno", Usermob);

            jobj3.put("FullAddress", strFullAddress);
            jobj3.put("strsubtotal",strsubtotal);
            jobj3.put("strshipping", strshipping);
            jobj3.put("order", jobj);
            jobj3.put("customer", jobj2);
            jobj3.put("items", responcearray2);


            //jobj4.put("FullAddress", strFullAddress);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.e("testing","jobj3 = ==="+jobj3);
        Log.e("testing","responcearray2===== = "+responcearray2);


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
          String  JSON = json.getString("JSON");
            // data = json.getString("data");

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

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    class Add_to_Cart extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_PaymentMode.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_PaymentMode.this);
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

                    final String strrs = getResources().getString(R.string.Rs);
                    amt.setText(strrs +" "+strfinaltotal);
                    total.setText(strrs +" "+strsubtotal);

                    if (strshipping==null||strshipping.length()==0||strshipping.equals("")){


                        shippingamount.setText(strrs +" "+" N.A");

                    }else {

                        shippingamount.setText(strrs +" "+strshipping);
                    }
                }else{


                }

            } else {
                Toast.makeText(Activity_PaymentMode.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson2() {


        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.GetuserCartDetails_Id, UserId);


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

    private String convertInputStreamToString2(InputStream inputStream) throws IOException {
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
                dialog = new ProgressDialog(Activity_PaymentMode.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_PaymentMode.this);
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



                }else {
                    Toast.makeText(Activity_PaymentMode.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {

                Toast.makeText(Activity_PaymentMode.this, "Something went wrong", Toast.LENGTH_LONG).show();
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

                Log.e("testing", "responcearray value====products=====" + responcearray3);


            }


            Log.e("testing","testing==afterDiscount="+afterDiscount);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }


    class BOOKNOW_CONFIRMORDER extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_PaymentMode.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_PaymentMode.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject5(EndUrl.ConfirmOrder_URL, makingJson5());

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);


                dialog.dismiss();

                Log.e("testing111123", "result in post execute=========" + result);

                if (status.equals("success")) {

                    SharedPreferences prefuserdata = getSharedPreferences("ordersuccess", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("order_id", "" + adminorderId);

                    prefeditor.commit();



                    logindialog123 = new Dialog(Activity_PaymentMode.this);
                    logindialog123.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Activity_PaymentMode.this.LAYOUT_INFLATER_SERVICE);
                    View convertView = (View) inflater.inflate(R.layout.order_success, null);

                /* StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);*/

                    logindialog123.setContentView(convertView);
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

                    Toast.makeText(Activity_PaymentMode.this, "no data found", Toast.LENGTH_LONG).show();
                }


        }

    }

    public JSONObject makingJson5() {

        JSONObject jobj3 = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            JSONObject jobj = new JSONObject();
            JSONObject jobj2 = new JSONObject();
            //JSONObject jobj4 = new JSONObject();

            jobj.put("status", "Pending");
            jobj.put("payment_mode", paymentmode);
            jobj.put("total_amount", strsubtotal_booknow);
            jobj.put("delivery_addressId", addressid);

            jobj2.put("customer_id", UserId);
            jobj2.put("customer_email", Usermail);
            jobj2.put("customer_name", Username);
            jobj2.put("customer_mobileno", Usermob);


            jobj3.put("FullAddress", strFullAddress);

            jobj3.put("strsubtotal",strfinaltotal_booknow);
            jobj3.put("strshipping", strshipping_booknow);
            jobj3.put("order", jobj);
            jobj3.put("customer", jobj2);
            jobj3.put("items", responcearray3);


            Log.e("testing","status = ==="+status);
            Log.e("testing","paymentmode = ==="+paymentmode);
            Log.e("testing","strsubtotal_booknow = ==="+strsubtotal_booknow);
            Log.e("testing","addressid = ==="+addressid);
            Log.e("testing","UserId = ==="+UserId);
            Log.e("testing","Usermail = ==="+Usermail);
            Log.e("testing","Username = ==="+Username);
            Log.e("testing","Usermob = ==="+Usermob);
            Log.e("testing","strFullAddress = ==="+strFullAddress);
            Log.e("testing","strfinaltotal_booknow = ==="+strfinaltotal_booknow);
            Log.e("testing","strshipping_booknow = ==="+strshipping_booknow);

          //  jobj4.put("FullAddress", strFullAddress);

        } catch (JSONException e) {
            e.printStackTrace();

        }
        Log.e("testing","jobj3 = ==="+jobj3);
        Log.e("testing","responcearray3===== = "+responcearray3);

        return jobj3;

    }


    public JSONObject postJsonObject5(String url, JSONObject loginJobj){
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



}





