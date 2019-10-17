package g2evolution.Boutique.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashMap;

import g2evolution.Boutique.Adapter.Upcoming_Adapter;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_orderhistory;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;

public class UpComing_Fragment extends Fragment implements   Upcoming_Adapter.OnItemClick {

    RecyclerView up_coming_recycler;

    ImageView no_data;

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_orderhistory> allItems1 = new ArrayList<FeederInfo_orderhistory>();
    private Upcoming_Adapter.OnItemClick mCallback;
    Upcoming_Adapter mAdapterFeeds;
    RecyclerView rView;

    String UserId;

    String status,message,order_date,ordersInfo;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_up_coming, container, false);


        SharedPreferences prefuserdata1 = getActivity().getSharedPreferences("regId", 0);
        UserId = prefuserdata1.getString("UserId", "");

        up_coming_recycler = (RecyclerView)rootview. findViewById(R.id.up_coming_recycler);
        no_data = (ImageView)rootview. findViewById(R.id.no_data);

        up_coming_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        up_coming_recycler.setLayoutManager(mLayoutManager1);


        mCallback=this;

        new Loader().execute();

        return  rootview;
    }


    @Override
    public void onClickedItem(int pos, String qty, int status) {

    }



    class Loader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(getActivity());
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            allItems1 = new ArrayList<FeederInfo_orderhistory>();
            return postJsonObject(EndUrl.OrderHistory_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    up_coming_recycler.setVisibility(View.VISIBLE);
                    no_data.setVisibility(View.GONE);
                    mAdapterFeeds = new Upcoming_Adapter(getActivity(), allItems1, mCallback);
                    up_coming_recycler.setAdapter(mAdapterFeeds);

                } else if (status.equals("fail")) {

                    allItems1.clear();

                    up_coming_recycler.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                    mAdapterFeeds = new Upcoming_Adapter(getActivity(), allItems1, mCallback);
                    up_coming_recycler.setAdapter(mAdapterFeeds);

                    Toast.makeText(getActivity(), "no data found", Toast.LENGTH_LONG).show();

                }

            } else {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson() {


        JSONObject jobj = new JSONObject();

        try {

            jobj.put(EndUrl.OrderHistory_customer_id,UserId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("testing","json object "+jobj);
        return jobj;

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
            // data = json.getString("data");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray123 value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();


                order_date = post.getString("order_date");
                ordersInfo = post.getString("ordersInfo");


                JSONArray responcearray2 = new JSONArray(ordersInfo);
                Log.e("testing", "responcearray123 value=" + responcearray2);

                for (int i2 = 0; i2 < responcearray2.length(); i2++) {
                    JSONObject post2 = responcearray2.getJSONObject(i2);

                    String strstatus = post2.optString("status");

                    if (strstatus == null || strstatus.trim().length() == 0 || strstatus.trim().equals("null")){

                    }else if (strstatus.equals("Pending")){
                        FeederInfo_orderhistory item = new FeederInfo_orderhistory();

                        // item.s(post.optString("id"));
                        item.setDeliverystatus(post2.optString("status"));
                        item.setOrderdate(post2.optString("order_date"));
                        item.setOrderid(post2.optString("order_id"));
                        item.setOrderID(post2.optString("orderid"));
                        item.setTotalprice(post2.optString("total_amount"));
                        item.setShippingadress(post2.optString("shipping_address"));
                        item.setPaymentmode(post2.optString("payment_mode"));

                        allItems1.add(item);
                    }else{

                    }




                }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
