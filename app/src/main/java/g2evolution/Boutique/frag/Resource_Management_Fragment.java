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
import g2evolution.Boutique.ccavenue.WebActivity2;
import g2evolution.Boutique.ccavenue.WebResourceActivity;
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
//            new LoadUploadResource().execute();


            JSONObject BuyResource=new JSONObject();
            try {
                BuyResource.put(EndUrl.UploadResource_resource_packages_id,strorderid);
                BuyResource.put(EndUrl.UploadResource_user_id,UserId);
                BuyResource.put(EndUrl.UploadResource_price,strorderprice);
                BuyResource.put(EndUrl.UploadResource_transaction_id,"1234");
                BuyResource.put(EndUrl.UploadResource_payment_status,"paid");
                BuyResource.put(EndUrl.UploadResource_description,strorderdesc);
                BuyResource.put(EndUrl.UploadResource_category_id,"");
                BuyResource.put(EndUrl.UploadResource_cv_count,"");


                Intent intent=new Intent(getActivity(), WebResourceActivity.class);
                intent.putExtra("BuyResource",BuyResource.toString() );
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

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



}
