package g2evolution.Boutique.ccavenue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.ccavenue.utility.AvenuesParams;
import g2evolution.Boutique.ccavenue.utility.ServiceUtility;
import g2evolution.Boutique.R;


public class InitialScreenActivity extends AppCompatActivity {


	TextView amt = null;
	Button pay = null;
	String grandtotal;
	String amount;
	public static final String TAG = "PayUMoneySDK Sample";

	Context context;


	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	private String straccessCode, strmerchantId, strcurrency, strorderId, strrsaKeyUrl, strredirectUrl, strcancelUrl;

	String strsubtotal,strshipping;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initial_screen);


		SharedPreferences prefuserdata = getSharedPreferences("paymentamount", 0);
		grandtotal = prefuserdata.getString("grandtotal", "");
		strsubtotal = prefuserdata.getString("strsubtotal", "");
		strshipping = prefuserdata.getString("strshipping", "");


		Log.e("testing","grandtotal====="+grandtotal);

		//----------------------------------For Action bar title and back button operation---------------------------------------------

		ImageView Back = (ImageView) findViewById(R.id.backpressed);
		Back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		amt = (TextView) findViewById(R.id.amount);
		pay = (Button) findViewById(R.id.confirmbooking);

		amt.setText(grandtotal);
		//amt.setText("1");

		straccessCode = EndUrl.straccessCode;
		strmerchantId = EndUrl.strmerchantId;
		strcurrency = EndUrl.strcurrency;

		strrsaKeyUrl = EndUrl.strrsaKeyUrl;
		strredirectUrl = EndUrl.strredirectUrl;
		strcancelUrl = EndUrl.strcancelUrl;

/*

		public static final String CCAvenue_Redirect = "http://www.grocshop.in/ccavenue/ccavResponseHandler.php";
		public static final String CCAvenue_Cancel = "http://www.grocshop.in/ccavenue/ccavResponseHandler.php";
		public static final String CCAvenue_RSA = "http://www.grocshop.in/ccavenue/GetRSA.php";
		Access code:  AVQB78FE28CH17BQHC
		merchant id:   176524*/

	}

	public void onClick(View view) {
		grandtotal = amt.getText().toString();
		//Mandatory parameters. Other parameters can be added if required.
		String vAccessCode = ServiceUtility.chkNull(straccessCode).toString().trim();
		String vMerchantId = ServiceUtility.chkNull(strmerchantId).toString().trim();
		String vCurrency = ServiceUtility.chkNull(strcurrency).toString().trim();
		String vAmount = ServiceUtility.chkNull(grandtotal).toString().trim();
		if(!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")){
			Intent intent = new Intent(this,WebViewActivity.class);
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



