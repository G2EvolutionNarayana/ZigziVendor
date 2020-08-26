package g2evolution.Boutique.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import g2evolution.Boutique.Adapter.Adapter_CategorySelect;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.Home_Activity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Retrofit.ResourceList.Datum;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.ccavenue.WebResourceActivity;
import g2evolution.Boutique.entit.Entity_CategorySelect;

public class Activity_CategorySelect extends AppCompatActivity {

    String [] strid = new String[]{"NSW", "SAU","IND","KAR","MRW"};
    String [] strtitle = new String[]{"TAS", "QUN","PAK","CHA","PSW"};

    JSONParser jsonParser = new JSONParser();

    String ProductId, Productallocatedaccount, productprice, productdesc;

    List<String> liste;
    List<String> liste2;

    public  static RecyclerView dashboard_reccyler;
    public  static ArrayList<Entity_CategorySelect> openings_entity=new ArrayList<Entity_CategorySelect>();
    public  static Adapter_CategorySelect openings_adapter;

    Button butsubmit;

    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);

        SharedPreferences prefuserdata1 = getSharedPreferences("regId", 0);
        UserId = prefuserdata1.getString("UserId", "");

        Log.e("testing","UserId = "+UserId);


        SharedPreferences prefuserdata3 = getSharedPreferences("ProductIDDetails", 0);
        ProductId = prefuserdata3.getString("ProductId", "");
        Productallocatedaccount = prefuserdata3.getString("Productallocatedaccount", "");
        productprice = prefuserdata3.getString("productprice", "");
        productdesc = prefuserdata3.getString("productdesc", "");

        Log.e("testing","Productallocatedaccount = "+Productallocatedaccount);

        ImageView back= (ImageView) findViewById(R.id.back);
        dashboard_reccyler = (RecyclerView) findViewById(R.id.recycler_view1);
        dashboard_reccyler.setLayoutManager(new LinearLayoutManager(Activity_CategorySelect.this));
        dashboard_reccyler.setHasFixedSize(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        butsubmit = (Button) findViewById(R.id.butsubmit);
        butsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

      //  setUpReccyler();
        new LoadCategoryData().execute();

    }

    private void submit() {
        liste = new ArrayList<String>();
        liste2 = new ArrayList<String>();
        StringBuilder stringBuilder = new StringBuilder();

        Integer intcount = 0;
        Integer intselectedcount = 0;
        for (Entity_CategorySelect model : openings_entity){
            if (model.isSelected()){
                liste.add(model.getItemid());
                liste2.add(model.getItemcount());

                intcount = Integer.valueOf(Productallocatedaccount);
              Integer  intselcount = Integer.valueOf(model.getItemcount());
              if (intselectedcount == 0){
                  intselectedcount = intselcount;
              }else{
                  intselectedcount = intselectedcount+intselcount;
              }



            }
        }

        // Toast.makeText(getActivity(), "["+stringBuilder.toString()+"]", Toast.LENGTH_LONG).show();
      //  Toast.makeText(Activity_CategorySelect.this, liste.toString(), Toast.LENGTH_LONG).show();



        Log.e("testing", "stringBuilder = "+stringBuilder);
        Log.e("testing","liste = "+liste);
        Log.e("testing","listecount = "+liste2);
        Log.e("testing","intcount = "+intcount);
        Log.e("testing","intselectedcount = "+intselectedcount);

        if (intcount>=intselectedcount){
            Log.e("testing", "allocated count = "+"true");

            if (liste.size() == 0){

            }else{
                JSONObject BuyResource=new JSONObject();
                try {
                    BuyResource.put(EndUrl.UploadResource_resource_packages_id,ProductId);
                    BuyResource.put(EndUrl.UploadResource_user_id,UserId);
                    BuyResource.put(EndUrl.UploadResource_price,productprice);
                    BuyResource.put(EndUrl.UploadResource_transaction_id,"1234");
                    BuyResource.put(EndUrl.UploadResource_payment_status,"paid");
                    BuyResource.put(EndUrl.UploadResource_description,productdesc);
                    BuyResource.put(EndUrl.UploadResource_category_id,"");
                    BuyResource.put(EndUrl.UploadResource_cv_count,"");

                    Intent intent=new Intent(getApplicationContext(), WebResourceActivity.class);
                    intent.putExtra("BuyResource",BuyResource.toString() );
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }else{
            Toast.makeText(Activity_CategorySelect.this, "You have only "+Productallocatedaccount+" to select", Toast.LENGTH_SHORT).show();
            Log.e("testing", "allocated count = "+"false");
        }

    }


    class LoadCategoryData extends AsyncTask<String, String, String> {
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
            pDialog = new ProgressDialog(Activity_CategorySelect.this);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String doInBackground(String... args) {

            openings_entity = new ArrayList<>();

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            userpramas.add(new BasicNameValuePair(EndUrl.CategoryList_CategoryId, ProductId));
            JSONObject json = jsonParser.makeHttpRequest(EndUrl.CategoryList_URL, "GET", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {

                try {
                    status = json.getString("status");

                    strresponse = json.getString("response");
                    JSONObject  jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");
                    if (status.equals("success")) {
                        status = json.getString("status");
                        strresponse = json.getString("response");
                        strdata = json.getString("data");


                        JSONArray responcearray = new JSONArray(strdata);
                        Log.e("testing", "responcearray value=" + responcearray);

                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);

                            openings_entity.add(new Entity_CategorySelect(post.getString("id"),post.getString("name"), "0",false));

                        }

                    } else {
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            pDialog.dismiss();
            Log.e("testing", "result is = " + responce);

            dashboard_reccyler.setHasFixedSize(true);
            dashboard_reccyler.setLayoutManager(new LinearLayoutManager(Activity_CategorySelect.this));
            openings_adapter = new Adapter_CategorySelect(openings_entity);
            dashboard_reccyler.setAdapter(openings_adapter);





        }

    }

}
