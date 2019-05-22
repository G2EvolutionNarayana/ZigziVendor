package g2evolution.GMT.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;

import g2evolution.GMT.Adapter.RecyclerViewDataAdapter;
import g2evolution.GMT.FeederInfo.SectionDataModel;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;
import g2evolution.GMT.Utility.JSONParser;

/**
 * Created by G2e Android on 31-01-2018.
 */

public class home extends Fragment {



    JSONParser jsonParser = new JSONParser();
    RecyclerView my_recycler_view;
    ArrayList<SectionDataModel> allSampleData;



    private ArrayList<SectionDataModel> allItems1 = new ArrayList<SectionDataModel>();
    private RecyclerView mFeedRecyler;
    private ArrayList<SectionDataModel> mListFeederInfo;
    RecyclerViewDataAdapter mAdapterFeeds;
    RecyclerView rView;
    String status,message,data;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        allSampleData = new ArrayList<SectionDataModel>();
        mFeedRecyler = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mFeedRecyler.setHasFixedSize(true);

        mFeedRecyler = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFeedRecyler.setHasFixedSize(true);
        rView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setHasFixedSize(true);

        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (cd.isConnectingToInternet()) {


            // new LoadSpinnerdata().execute();

        //    new Loader().execute();




        } else {


            Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

        return rootView;

    }

/*

    class Loader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(getContext(), ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(getContext());
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            allItems1 = new ArrayList<SectionDataModel>();
            return postJsonObject(EndUrl.GetCategorywiseProducts_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    mAdapterFeeds = new RecyclerViewDataAdapter(getActivity(), allItems1);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                } else if (status.equals("error")) {

                    allItems1.clear();

                    mAdapterFeeds = new RecyclerViewDataAdapter(getActivity(), allItems1);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                    Toast.makeText(getContext(), "no data found", Toast.LENGTH_LONG).show();
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


                SectionDataModel item = new SectionDataModel();

                // item.s(post.optString("id"));
               // item.setOrganisationname(post.optString("organisationName"));
              //  item.setVisittime(post.optString("visitTime"));
              //  item.setKeyperson(post.optString("keyPerson"));
              //  item.setStatus(post.optString("statusName"));
              //  item.setAssignedby(post.optString("assignedBy"));


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
    }*/
}




