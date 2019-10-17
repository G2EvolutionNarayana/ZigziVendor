package xplotica.littlekites.Activity_parent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import xplotica.littlekites.CCAvenue.WebViewActivity;
import xplotica.littlekites.CCAvenue.utility.AvenuesParams;
import xplotica.littlekites.CCAvenue.utility.ServiceUtility;
import xplotica.littlekites.R;

public class Activity_Parent_FeesIntal extends AppCompatActivity {


    TextView amt = null;
    Button pay = null;

    public static final String TAG = "PayUMoneySDK Sample";

    Context context;


    private String straccessCode, strmerchantId, strcurrency, stramt,stramount, strorderId, strrsaKeyUrl, strredirectUrl, strcancelUrl;


    TextView textfinalamount;
    String instalmentid, instalmentamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_fees_intal);
        SharedPreferences prefuserdata= getSharedPreferences("parentinstal",0);
        instalmentid=prefuserdata.getString("instalmentid","");
        instalmentamount=prefuserdata.getString("instalmentamount","");
        //----------------------------------For Action bar title and back button operation---------------------------------------------
        TextView texttitle = (TextView) findViewById(R.id.textview_title1);
        texttitle.setText("Final Payment");
        ImageView Back = (ImageView) findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onBackPressed();


            }
        });

        textfinalamount = (TextView) findViewById(R.id.textfinalamount);
        amt = (TextView) findViewById(R.id.amount);
        pay = (Button) findViewById(R.id.pay);

        amt.setText(instalmentamount);


        straccessCode = "AVVF74EK26BB74FVBB";
        strmerchantId = "154076";
        strcurrency = "INR";

        strrsaKeyUrl = "http://rmglobaltrust.in/littlekites/ccavenue/ccavResponseHandler.php";
        strredirectUrl = "http://rmglobaltrust.in/littlekites/ccavenue/ccavResponseHandler.php";
        strcancelUrl = "http://rmglobaltrust.in/littlekites/ccavenue/GetRSA.php";

        stramt = amt.getText().toString();

        if (!stramt.equals("")) {
            double amount = Double.parseDouble(stramt);
            double res = (amount / 100.0f) * 2;
            // Toast.makeText(getApplicationContext(), "" + res, Toast.LENGTH_SHORT).show();
            Double stramount1 = Double.parseDouble(stramt);
            Double  sum = stramount1 + res;
            stramount = String.valueOf(sum);
            textfinalamount.setText("Final Amount: Rs "+ stramount);
        }else{
            stramount = "";
            textfinalamount.setText("Final Amount: ");
            // Toast.makeText(getApplicationContext(), "Amount cannot be empty", Toast.LENGTH_SHORT).show();

        }




    }


    public void onClick(View view) {




        //Mandatory parameters. Other parameters can be added if required.
        String vAccessCode = ServiceUtility.chkNull(straccessCode).toString().trim();
        String vMerchantId = ServiceUtility.chkNull(strmerchantId).toString().trim();
        String vCurrency = ServiceUtility.chkNull(strcurrency).toString().trim();
        String vAmount = ServiceUtility.chkNull(stramount).toString().trim();
        if(!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")){
            Intent intent = new Intent(this,WebViewActivity.class);
            intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(straccessCode).toString().trim());
            intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(strmerchantId).toString().trim());
            intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(strorderId).toString().trim());
            intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(strcurrency).toString().trim());
            intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(stramount).toString().trim());

            intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(strrsaKeyUrl).toString().trim());
            intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(strredirectUrl).toString().trim());
            intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(strcancelUrl).toString().trim());

            SharedPreferences prefuserdata = getSharedPreferences("paymentdetails", 0);
            SharedPreferences.Editor prefeditor = prefuserdata.edit();

            prefeditor.putString("stramount", "" + stramount);

            prefeditor.commit();


            startActivity(intent);
        }else{
            showToast("All parameters are mandatory.");
        }
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



}



