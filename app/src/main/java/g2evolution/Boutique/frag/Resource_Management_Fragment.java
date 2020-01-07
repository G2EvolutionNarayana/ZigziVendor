package g2evolution.Boutique.frag;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import g2evolution.Boutique.Activity.Activity_CategorySelect;
import g2evolution.Boutique.Adapter.Adapter_resourse;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.Fragment.Fragment_Offer_Products;
import g2evolution.Boutique.Home_Activity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Retrofit.Api;
import g2evolution.Boutique.Retrofit.ApiClient;
import g2evolution.Boutique.Retrofit.ResourceList.Datum;
import g2evolution.Boutique.Retrofit.ResourceList.ResourceListJson;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entity_Resourse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Resource_Management_Fragment extends Fragment implements Adapter_resourse.OnItemClickcropNames {




    private static Context context = null;
    public  static RecyclerView dashboard_reccyler;
    public  static Adapter_resourse.OnItemClickcropNames mCallbackcropnames;
    public  static List<Datum> openings_entity=new ArrayList<Datum>();
    public  static Adapter_resourse openings_adapter;

    String[] TitleName=new String[]{"Silver",
            "Gold",
            "Platinum"};
    String[] Titlecount=new String[]{"0/20",
            "0/50",
            "0/100"};
    String[] Titledesc=new String[]{"In this plan you can download 20 Resume",
            "In this plan you can download 50 Resume",
            "In this plan you can download 100 Resume"};

    String[] TitlePrice=new String[]{"100",
            "300",
            "700"};

    public  static JSONParser jsonParser = new JSONParser();
    public  static   String message;
    public  static   String status;
    public  static   String response;

    String strorderid, strorderprice, strorderdesc;

    String UserId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_license, container, false);

        SharedPreferences prefuserdata3 = getActivity().getSharedPreferences("regId", 0);
        UserId = prefuserdata3.getString("UserId", "");

        Log.e("testing","UserId = "+UserId);

        mCallbackcropnames = this;

        dashboard_reccyler=(RecyclerView)rootview.findViewById(R.id.dashboard_reccyler);
        dashboard_reccyler.setHasFixedSize(true);
        dashboard_reccyler.setLayoutManager(new LinearLayoutManager(getActivity()));
       // GridLayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(),2);
      //  dashboard_reccyler.setLayoutManager(mLayoutManager1);


        RetrofitResourcelist();
      //  setUpReccyler();

        return rootview;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void OnItemClickcropNames(int pos, String qty, int status) {

        Datum follow = openings_entity.get(pos);
        if (status == 1){

            strorderid = String.valueOf(follow.getResourcePackagesId());
            strorderprice = follow.getPrice();
            strorderdesc = follow.getDescription();
            new LoadUploadResource().execute();

        }else if (status == 2){
            SharedPreferences prefuserdata =getActivity().getSharedPreferences("ProductIDDetails", 0);
            SharedPreferences.Editor prefeditor = prefuserdata.edit();
            prefeditor.putString("ProductId", "" + follow.getResourcePackagesId());
            prefeditor.putString("Productallocatedaccount", "" + follow.getCvCount());
            prefeditor.putString("productprice", "" + follow.getPrice());
            prefeditor.putString("productdesc", "" + follow.getDescription());
            prefeditor.commit();
            Intent intent= new Intent(getActivity(), Activity_CategorySelect.class);
            startActivity(intent);

        }else{

        }

    }




  /*  private void setUpReccyler() {
        openings_entity = new ArrayList<Entity_Resourse>();

        for (int i = 0; i < TitleName.length; i++) {
            Entity_Resourse feedInfo = new Entity_Resourse();
            // feedInfo.setImages(productImage[i]);
            feedInfo.setTitlename(TitleName[i]);
            feedInfo.setTextcount(Titlecount[i]);
            feedInfo.setTextdesc(Titledesc[i]);
            feedInfo.setTextprice(TitlePrice[i]);

            openings_entity.add(feedInfo);
        }
        openings_adapter = new Adapter_resourse(getActivity(), openings_entity, mCallbackcropnames);
        dashboard_reccyler.setAdapter(openings_adapter);
    }*/

    private void RetrofitResourcelist() {


        final ProgressDialog pProgressDialog;
        pProgressDialog = new ProgressDialog(getActivity());
        pProgressDialog.setMessage("Please Wait ...");
        pProgressDialog.setIndeterminate(false);
        pProgressDialog.setCancelable(false);
        pProgressDialog.show();

        //call retrofit
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<ResourceListJson> login = api.ResourceListJson(UserId);

        login.enqueue(new Callback<ResourceListJson>() {
            @Override
            public void onResponse(Call<ResourceListJson> call, Response<ResourceListJson> response) {
                pProgressDialog.dismiss();
                Log.e("testing","status = "+response.body().getStatus());
                Log.e("testing","response = "+response.body().getResponse().getType());
                //  Log.e("testing","response = "+response.body().getData().getPageContent());

                if (response.body().getStatus() == null || response.body().getStatus().length() == 0){

                }else if (response.body().getStatus().equals("success")) {
                    if (response.body().getResponse() == null ){

                    }else if (response.body().getResponse().getType().equals("data_found")){
                        openings_entity = response.body().getData();
                        openings_adapter = new Adapter_resourse(getActivity(), openings_entity, mCallbackcropnames);
                        dashboard_reccyler.setAdapter(openings_adapter);

                    }else{
                        Toast.makeText(getActivity(), response.body().getResponse().getType(), Toast.LENGTH_SHORT).show();
                    }








                    //  Toast.makeText(Activity_Event_Details.this, message, Toast.LENGTH_SHORT).show();


                } else  {
                    Log.e("testing","error");
                    Toast.makeText(getActivity(), response.body().getResponse().getType(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<ResourceListJson> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                pProgressDialog.dismiss();

            }
        });





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
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            Integer result = 0;
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            Log.e("testing", "jsonParser startedkljhk");
            userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_resource_packages_id, strorderid));
            userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_user_id, UserId));
            userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_price, strorderprice));
            userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_order_number, "124"));
            userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_transaction_id, "12346"));
            userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_payment_status, "paid"));
            userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_description, strorderdesc));
            userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_category_id, ""));
            userpramas.add(new BasicNameValuePair(EndUrl.UploadResource_cv_count, ""));
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
                    JSONObject  jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");
                    if (status.equals("success")) {
                       /* status = json.getString("status");
                        strresponse = json.getString("response");
                        strdata = json.getString("data");
                        JSONObject  jsonobjectdata = new JSONObject(strdata);
                        String strmatchlist = jsonobjectdata.getString("matchList");
                        JSONObject  jsonobjectmatchlist = new JSONObject(strmatchlist);
                        String strmatches = jsonobjectmatchlist.getString("matches");

                        JSONArray responcearray = new JSONArray(strmatches);
                        Log.e("testing", "responcearray value=" + responcearray);

                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);

                            String strstatus = post.getString("status");


                        }*/

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
            pDialog.dismiss();
            if (status == null || status.length() == 0){

            }else if (status.equals("success")) {

                if (strresponse == null || strresponse.length() == 0){

                }else if (strtype.equals("save_success")){
                    Toast.makeText(getActivity(), strmessage, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), Home_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    Toast.makeText(getActivity(), strmessage, Toast.LENGTH_SHORT).show();
                }



            }else{
                Toast.makeText(getActivity(), strmessage, Toast.LENGTH_SHORT).show();
            }



        }


    }
}
