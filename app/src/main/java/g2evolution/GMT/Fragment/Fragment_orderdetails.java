package g2evolution.GMT.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import g2evolution.GMT.Adapter.Adapter_orderdetails;
import g2evolution.GMT.FeederInfo.FeederInfo_orderdetails;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.JSONParser;


/**
 * Created by G2e Android on 23-01-2018.
 */

public class Fragment_orderdetails extends Fragment {


    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_orderdetails> allItems1 = new ArrayList<FeederInfo_orderdetails>();
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_orderdetails> mListFeederInfo;
    Adapter_orderdetails mAdapterFeeds;
    RecyclerView rView;

    String []Name =new String[]{"Maggi","Maggi","Maggi"};
    String []Date =new String[]{"01-02-2018","01-02-2018","01-02-2018"};
    String []details =new String[]{"(Kachi Ghani),1 ltr Bottle","(Kachi Ghani),1 ltr Bottle","(Kachi Ghani),1 ltr Bottle"};
    String []price =new String[]{"50.00","50.00","50.00"};
    String []quantity =new String[]{"1","2","1"};
    String []amount =new String[]{"50.00","50.00","50.00"};
    Integer[]Image={R.drawable.maggi, R.drawable.maggi, R.drawable.maggi};

    String status;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_orderdetails, container, false);



        mFeedRecyler = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //setUpRecycler();
        // context = this;
        //lLayout = new GridLayoutManager(getActivity(),2);
        rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        // rView.setLayoutManager(lLayout);
        // mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);

     //   setUpRecycler();


        new Loader().execute();

        return rootView;

    }

    private void setUpRecycler() {



        mListFeederInfo = new ArrayList<FeederInfo_orderdetails>();
        for (int i = 0; i < Image .length; i++) {
            FeederInfo_orderdetails feedInfo = new FeederInfo_orderdetails();

            feedInfo.setOrderimage(String.valueOf(Image[i]));

            feedInfo.setOrderdate(Date[i]);
            feedInfo.setOrdername(Name[i]);
            feedInfo.setOrderprodetails(details[i]);
            feedInfo.setOrderpriceamount(price[i]);
            feedInfo.setQuantity_ordertext(quantity[i]);
            feedInfo.setOrdertotalamount(amount[i]);


            mListFeederInfo.add(feedInfo);
        }

       /* mAdapterFeeds = new Adapter_orderdetails(getActivity(), mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);*/

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
            allItems1 = new ArrayList<FeederInfo_orderdetails>();
            return postJsonObject("", makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                 /*   mAdapterFeeds = new Adapter_orderdetails(getActivity(), allItems1);
                    mFeedRecyler.setAdapter(mAdapterFeeds);
*/
                } else if (status.equals("error")) {

                    allItems1.clear();

                   /* mAdapterFeeds = new Adapter_orderdetails(getActivity(), allItems1);
                    mFeedRecyler.setAdapter(mAdapterFeeds);*/

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
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put("", "1");


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
            // message = json.getString("message");
            // data = json.getString("data");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();


                FeederInfo_orderdetails item = new FeederInfo_orderdetails();

                // item.s(post.optString("id"));
               /* item.setOrganisationname(post.optString("organisationName"));
                item.setVisittime(post.optString("visitTime"));
                item.setKeyperson(post.optString("keyPerson"));
                item.setStatus(post.optString("statusName"));
                item.setAssignedby(post.optString("assignedBy"));
*/

                allItems1.add(item);

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
}





