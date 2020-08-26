package g2evolution.Boutique.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import g2evolution.Boutique.Adapter.PersonAdapter;
import g2evolution.Boutique.FeederInfo.Person;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.Utility.Utils;
import g2evolution.Boutique.ccavenue.InitialScreenActivity;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.MainActivity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.ccavenue.WebViewActivity;
import g2evolution.Boutique.ccavenue.utility.AvenuesParams;
import g2evolution.Boutique.ccavenue.utility.ServiceUtility;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


public class Activity_PaymentMode extends AppCompatActivity {


    TextView amt,total,textcouponprice, textcreditprice, shippingamount,pay;
    // For Radio buttons
    RadioGroup radioselGroup;
    int pos;
    int pos1;
    String spstring;
    String PAmount, Productid, UserId,_userId;

    String strshippingid = "";
    String strshippingamount = "";

    String status, message;
    String strsubtotal, strshipping, strfinaltotal;
    String paymentmode;
    JSONArray responcearray2;
    JSONArray responcearray3;
    String products;
    String addressid, Username, Usermail;

    String strsubtotal_booknow, strshipping_booknow, strfinaltotal_booknow, products_booknow;

    String order_id,adminorderId, adminorderamount,Usermob,strFullAddress;

    String stractualprice, strdiscountprice, strfinalprice;


    ArrayList<HashMap<String, String>> arraylist;
    JSONParser jsonParser = new JSONParser();
    private PersonAdapter adapter;
    private List<Person> feedItemList = new ArrayList<Person>();
private String straccessCode, strmerchantId, strcurrency, strorderId, strrsaKeyUrl, strredirectUrl, strcancelUrl;
    String grandtotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode);

        SharedPreferences prefuserdata5 = getSharedPreferences("PriceDeatils", 0);
        stractualprice = prefuserdata5.getString("price", "");
        strdiscountprice = prefuserdata5.getString("discount_price", "");
        strfinalprice = prefuserdata5.getString("total_price", "");

        SharedPreferences prefuserdata = getSharedPreferences("addressid", 0);
        addressid=prefuserdata.getString("addressid","");

        SharedPreferences prefuserdata1 = getSharedPreferences("regId", 0);
        _userId = prefuserdata1.getString("UserId", "");
//        _userName = prefuserdata1.getString("Username", "");
//        _userEmail = prefuserdata1.getString("Usermail", "");
//        _usermob = prefuserdata1.getString("Usermob", "");

        straccessCode = EndUrl.straccessCode;
        strmerchantId = EndUrl.strmerchantId;
        strcurrency = EndUrl.strcurrency;

        strrsaKeyUrl = EndUrl.strrsaKeyUrl;
        strredirectUrl = EndUrl.strredirectUrl;
        strcancelUrl = EndUrl.strcancelUrl;

        amt = (TextView) findViewById(R.id.amount);
        shippingamount = (TextView) findViewById(R.id.dcharge);
        total = (TextView) findViewById(R.id.netamount);
        textcouponprice = (TextView) findViewById(R.id.textcouponprice);
        textcreditprice = (TextView) findViewById(R.id.textcreditprice);
        pay = (TextView) findViewById(R.id.pay);

        radioselGroup = (RadioGroup) findViewById(R.id.radioselGroup);
        spstring = "0";


        final String strrs = getResources().getString(R.string.Rs);
        if (stractualprice == null || stractualprice.trim().length() == 0 || stractualprice.trim().equals("null")){

        }else{
            total.setText(strrs +" "+stractualprice);
        }
        if (strdiscountprice == null || strdiscountprice.trim().length() == 0 || strdiscountprice.trim().equals("null")){

        }else{
            textcouponprice.setText(strrs +" "+strdiscountprice);
        }
        if (strfinalprice == null || strfinalprice.trim().length() == 0 || strfinalprice.trim().equals("null")){

        }else{
            amt.setText(strfinalprice);
        }
        radioselGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos = radioselGroup.indexOfChild(findViewById(checkedId));




                //Method 2 For Getting Index of RadioButton
                pos1 = radioselGroup.indexOfChild(findViewById(radioselGroup.getCheckedRadioButtonId()));

                /*Toast.makeText(getBaseContext(), "Method 2 ID = " + String.valueOf(pos1),
                        Toast.LENGTH_SHORT).show();*/

                switch (pos) {



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

                SharedPreferences prefuserdata4 = getSharedPreferences("productadapter", 0);
                strshippingid = prefuserdata4.getString("shippingid", "");
                strshippingamount = prefuserdata4.getString("price", "");

                Log.e("testing","String strshippingid = "+strshippingid);
                Log.e("testing","String strshippingamount = "+strshippingamount);

                if (spstring.equals("1")) {

                    grandtotal = amt.getText().toString();
                    //Mandatory parameters. Other parameters can be added if required.
                    String vAccessCode = ServiceUtility.chkNull(straccessCode).toString().trim();
                    String vMerchantId = ServiceUtility.chkNull(strmerchantId).toString().trim();
                    String vCurrency = ServiceUtility.chkNull(strcurrency).toString().trim();
                    String vAmount = ServiceUtility.chkNull(grandtotal).toString().trim();
                    if(!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")){
                        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                        intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(straccessCode).toString().trim());
                        intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(strmerchantId).toString().trim());
                        intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(strorderId).toString().trim());
                        intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(strcurrency).toString().trim());
                        intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(grandtotal).toString().trim());

                        intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(strredirectUrl).toString().trim());
                        intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(strcancelUrl).toString().trim());
                        intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(strrsaKeyUrl).toString().trim());

                        SharedPreferences prefuserdata = getSharedPreferences("paymentdetails", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("stramount", "" + grandtotal);
                        prefeditor.commit();
                        startActivity(intent);

                    }else{

                        showToast("All parameters are mandatory.");

                    }


                } else if (spstring.equals("2")) {

                    paymentmode = "COD";

                    new PlaceOrder().execute();


                } else {
                    Toast.makeText(getApplicationContext(), "Please Select Payment Mode", Toast.LENGTH_SHORT).show();

                }



            }
        });

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
    public void showToast(String msg) {
        Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //generating new order number for every transaction
        Integer randomNum = ServiceUtility.randInt(0, 9999999);
        strorderId = randomNum.toString();

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

            //  jobj4.put("FullAddress", strFullAddress);

        } catch (JSONException e) {
            e.printStackTrace();

        }
        Log.e("testing","jobj3 = ==="+jobj3);
        Log.e("testing","responcearray3===== = "+responcearray3);

        return jobj3;

    }


    class PlaceOrder extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {


        String status;
        String response;
        String strresponse;
        String strcode, strtype, strmessage;

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_PaymentMode.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            feedItemList =new ArrayList<Person>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.PlaceOrder_user_id,  _userId));
            userpramas.add(new BasicNameValuePair(EndUrl.PlaceOrder_delivery_address_id,  addressid));
            userpramas.add(new BasicNameValuePair(EndUrl.PlaceOrder_payment_mode_id,  "1"));
           // userpramas.add(new BasicNameValuePair(EndUrl.PlaceOrder_coupon_id,  coupon_id));
           // userpramas.add(new BasicNameValuePair(EndUrl.PlaceOrder_coupon_price, jsoncoupon));
           // userpramas.add(new BasicNameValuePair(EndUrl.PlaceOrder_shipping_method_id, strshippingid));
           // userpramas.add(new BasicNameValuePair(EndUrl.PlaceOrder_shipping_price,  strshippingamount));
           // userpramas.add(new BasicNameValuePair(EndUrl.PlaceOrder_credits_id,  ""));
           // userpramas.add(new BasicNameValuePair(EndUrl.PlaceOrder_credits_price,  jsoncredits));
           // userpramas.add(new BasicNameValuePair(EndUrl.PlaceOrder_gst,  jsongst));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.PlaceOrder_URL, "POST", userpramas);

            Log.e("testing", "userpramas result = " + userpramas);
            Log.e("testing", "json result = " + json);
            Log.e("testing", "userpramas UserId = " + UserId);
            Log.e("testing", "userpramas addressid = " + addressid);
            Log.e("testing", "userpramas 1 = " + 1);

            if (json == null) {

            } else {
                Log.e("testing", "jon2222222222222");
                try {
                    status = json.getString("status");
                    strresponse = json.getString("response");
                    JSONObject  jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            return response;


        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);

            pDialog.dismiss();
            if (status == null || status.trim().length() == 0 || status.equals("null")){

            }else if (status.equals("success")) {
                successdialog();
            }
            else {
            }



        }

    }
    private void successdialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(
                Activity_PaymentMode.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Success");
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage("Order Placed Successfully");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.tick);
        Utils.Cart_Count=0;

        // Setting OK Button
        alertDialog.setButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {

                        Intent intent =new Intent(Activity_PaymentMode.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }



}





