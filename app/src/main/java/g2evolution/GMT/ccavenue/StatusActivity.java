package g2evolution.GMT.ccavenue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import g2evolution.GMT.R;
import g2evolution.GMT.Utility.JSONParser;


public class StatusActivity extends AppCompatActivity {
	String type,School_id,School_name,Student_id,Classid,Sectionid,mobile;
	String instalmentid, stramount;
	String strtransactionid, strtransactionstatus, strtransdate;
	JSONParser jsonParser = new JSONParser();
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_status);


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


		Intent mainIntent = getIntent();
		strtransactionid = mainIntent.getStringExtra("strtransactionid");
		strtransactionstatus = mainIntent.getStringExtra("strtransactionstatus");
		strtransdate = mainIntent.getStringExtra("strtransdate");

		Log.e("testing","strtransactionid = "+strtransactionid);
		Log.e("testing","strtransactionstatus = "+strtransactionstatus);
		Log.e("testing","strtransdate = "+strtransdate);


		TextView tv4 = (TextView) findViewById(R.id.textView1);
		tv4.setText(mainIntent.getStringExtra("transStatus"));

		if (strtransactionstatus.equals("Success")){
			//new TopTrend().execute();

		}else{
			Toast.makeText(StatusActivity.this, strtransactionstatus, Toast.LENGTH_SHORT).show();
		}

	}
	
	public void showToast(String msg) {
		Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
	}


	/*class TopTrend extends AsyncTask<String, String, String>
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
			pDialog = new ProgressDialog(StatusActivity.this);
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
				//Toast.makeText(StatusActivity.this, ""+arrayresponce, Toast.LENGTH_SHORT).show();


			}

		}
	}*/


} 