package g2evolution.GMT.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import g2evolution.GMT.Adapter.RecyclerViewDataAdapter;
import g2evolution.GMT.EndUrl;
import g2evolution.GMT.FeederInfo.SectionDataModel;
import g2evolution.GMT.FeederInfo.SingleItemModel;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;
import g2evolution.GMT.Utility.JSONParser;

/**
 * Created by G2e Android on 31-01-2018.
 */

public class fragment_subcategory_details extends Fragment implements RecyclerViewDataAdapter.OnItemClick{

    private String TAG = fragment_subcategory_details.class.getSimpleName();

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    RecyclerView my_recycler_view;
    ArrayList<SectionDataModel> allSampleData;
    private RecyclerViewDataAdapter.OnItemClick mCallback;
    RecyclerViewDataAdapter adapter;

    String status;
    String headers;
    String pincode;
    String category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subcategory_details, container, false);

        SharedPreferences prefuserdata2 = getActivity().getSharedPreferences("pincode", 0);
        pincode = prefuserdata2.getString("pincode", "");

        SharedPreferences prefuserdata3 = getActivity().getSharedPreferences("category", 0);
        category = prefuserdata3.getString("category", "");


        Log.e("testing","category======"+category);

        mCallback = this;
        allSampleData = new ArrayList<SectionDataModel>();
        my_recycler_view = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setVisibility(View.VISIBLE);

        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (cd.isConnectingToInternet()) {

            if (pincode==null||pincode.length()== 0 ||pincode.equals("null")||pincode.equals("0")){
                Toast.makeText(getActivity(), "Select Pincode", Toast.LENGTH_SHORT).show();
            }else{

               // new Getproducts().execute();
               // new userdata().execute();
                new Loader().execute();
            }


          //  new userdata().execute();

        } else {

            Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

        return rootView;

    }

    @Override
    public void onClickedItem(int pos, String category, int status) {
        Log.e("DMen", "Pos:"+ pos + "category:"+category);
        Log.e("testing","status  = "+status);


        SharedPreferences prefuserdata = getActivity().getSharedPreferences("category", 0);
        SharedPreferences.Editor prefeditor = prefuserdata.edit();
        prefeditor.putString("category", "" + category);

        Log.e("testing","category  = " + category);

        prefeditor.commit();


      //  my_recycler_view.setVisibility(View.GONE);


       /* Fragment_Categories fragment3 = new Fragment_Categories();
        FragmentTransaction fragmentTransaction3 =
                getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction3.replace(R.id.fragment_container, fragment3);
        fragmentTransaction3.commit();*/


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
            allSampleData = new ArrayList<SectionDataModel>();
            return postJsonObject(EndUrl.GetCategoryProduct_URL, makingJson());

        }


        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            dialog.dismiss();


            Log.e("testing", "result in post execute11111==allSampleData=======" + allSampleData);

            adapter = new RecyclerViewDataAdapter(getActivity(), allSampleData, mCallback);

            my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            my_recycler_view.setAdapter(adapter);


        }


    }

    public JSONObject makingJson() {


        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.GetCategoryProduct_name,category);


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
       JSONObject jsonObj = new JSONObject(result);

        // Getting JSON Array node

        Log.e("testing","jsonObj = "+jsonObj);
        Log.e("testing","result = "+result);


        JSONObject response = new JSONObject(jsonObj.toString());

        Log.e("testing", "jsonParser2222" + response);

        //JSONObject jsonArray1 = new JSONObject(json.toString());
        //  Result = response.getString("status");
        JSONArray posts = response.optJSONArray("data");
        Log.e("testing", "jsonParser3333" + posts);


        if (posts == null) {
            Log.e("testing", "jon11111111111111111");

            //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();

        } else {

            Log.e("testing", "jon122222211");
            Log.e("testing", "jsonParser4444" + posts);

            for (int i = 0; i < posts.length(); i++) {

                Log.e("testing", "" + posts);

                Log.e("testing", "" + i);
                JSONObject post = posts.optJSONObject(i);
                // JSONArray posts2 = response.optJSONArray("categories");
                Log.e("testng", "" + post);
                headers = post.getString("subCatId");

                Log.e("testing", "name is 11= " + post.getString("categoryName"));


                String Title = post.getString("categoryName");

                SectionDataModel dm = new SectionDataModel();


                dm.setHeaderTitle(post.getString("subcatId"));
                dm.setHeaderid(post.getString("subcatname"));

                JSONArray posts2 = post.optJSONArray("childCategory");
                ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
                for (int i1 = 0; i1 < posts2.length(); i1++) {
                    JSONObject post2 = posts2.optJSONObject(i1);


                               /* String Title2 = post2.getString("title");
                                String Productid = post2.getString("productId");
                                String Parentid = post2.getString("subcatName");
                                String Typename = post2.getString("location");

                               */

                    String finalimg = null;

                    finalimg = post2.getString("image");
                              /*  JSONArray posts3 = post2.optJSONArray("multipleImages");
                                for (int i2 = 0; i2 < posts3.length(); i2++) {
                                    JSONObject post3 = posts3.optJSONObject(i2);

                                    finalimg = post3.getString("image");


                                    //find the group position inside the list
                                    //groupPosition = deptList.indexOf(headerInfo);
                                }*/

                    singleItem.add(new SingleItemModel(post2.getString("childcatId"),post2.getString("childCat_name"),post2.getString("title"),post2.getString("subTitle"),post2.getString("price"),post2.getString("discountValue"),post2.getString("afterDiscount"), finalimg,post2.getString("stockQuantity")));


                }

                dm.setAllItemsInSection(singleItem);

                allSampleData.add(dm);

            }

        }


    } catch (final JSONException e) {
        Log.e(TAG, "Json parsing error: " + e.getMessage());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),
                        "Json parsing error: " + e.getMessage(),
                        Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

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