package g2evolution.Boutique.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import g2evolution.Boutique.Adapter.Adapter_RecyclerViewDataCategory;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entity_CategorySimilarDataModel;
import g2evolution.Boutique.entit.Entoty_CategorySingleItemModel;

public class Activity_SubSactegory extends AppCompatActivity implements Adapter_RecyclerViewDataCategory.OnItemClick{

    RecyclerView my_recycler_view;
    ArrayList<Entity_CategorySimilarDataModel> allSampleData;
    private Adapter_RecyclerViewDataCategory.OnItemClick mCallback;
    Adapter_RecyclerViewDataCategory adapter;

    String category_id;

    JSONParser jsonParser = new JSONParser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_sactegory);

        SharedPreferences prefuserdata = getSharedPreferences("categoryid", 0);
        category_id = prefuserdata.getString("categoryid","0");

        Log.e("testing","category_id = "+category_id);
        mCallback=this;

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        allSampleData = new ArrayList<Entity_CategorySimilarDataModel>();
        my_recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setVisibility(View.VISIBLE);

        new Getproducts().execute();

    }

    @Override
    public void onClickedItem(int pos, String category, int status) {

    }

    private class Getproducts extends AsyncTask<Void, Void, Void> {

        String strresponse;

        String strcode, strtype, strmessage;

        String status;

        String headers;
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Activity_SubSactegory.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            userpramas.add(new BasicNameValuePair(EndUrl.GetSubcatList_id, category_id));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.GetSubCategoryList_URL, "GET", userpramas);



            Log.e("testing","json sub category = "+json);



            if (json != null) {

                try {

                    status = json.getString("status");
                    strresponse = json.getString("response");
                    JSONObject jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");
                    if (status.equals("success")) {
                        status = json.getString("status");
                        strresponse = json.getString("response");



                        //JSONObject jsonArray1 = new JSONObject(json.toString());
                        //  Result = response.getString("status");
                        JSONArray posts = json.optJSONArray("data");
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
                                headers = post.getString("name");

                                Log.e("testing", "name is 11= " + post.getString("name"));


                                String Title = post.getString("name");

                                Entity_CategorySimilarDataModel dm = new Entity_CategorySimilarDataModel();


                                dm.setHeaderTitle(post.getString("name"));
                                // dm.setHeaderid(post.getString("categoryId"));

                                JSONArray posts2 = post.optJSONArray("child_category");
                                ArrayList<Entoty_CategorySingleItemModel> singleItem = new ArrayList<Entoty_CategorySingleItemModel>();
                                if (posts2 == null) {
                                    JSONArray posts3 = post.optJSONArray("products");
                                    if (posts3 == null) {

                                    }else {
                                        for (int i1 = 0; i1 < posts3.length(); i1++) {
                                            JSONObject post3 = posts3.optJSONObject(i1);

                                            String finalimg = null;

                                            finalimg = post3.getString("image");


                                            singleItem.add(new Entoty_CategorySingleItemModel(post3.getString("id"), post3.getString("name"), post3.getString("name"), post3.getString("name"), "2", "3", "1", finalimg, "1", "childcategory"));


                                        }
                                    }
                                } else{
                                    for (int i1 = 0; i1 < posts2.length(); i1++) {
                                        JSONObject post2 = posts2.optJSONObject(i1);


                               /*
                                String Title2 = post2.getString("title");
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

                                        singleItem.add(new Entoty_CategorySingleItemModel(post2.getString("id"), post2.getString("name"), post2.getString("name"), post2.getString("name"), "2", "3", "1", finalimg, "1","childcategory"));


                                    }
                                }
                                dm.setAllItemsInSection(singleItem);

                                allSampleData.add(dm);

                            }

                        }

                    }
                } catch (final JSONException e) {
                    Activity_SubSactegory.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText( Activity_SubSactegory.this,
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Activity_SubSactegory.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText( Activity_SubSactegory.this,
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            adapter = new Adapter_RecyclerViewDataCategory( Activity_SubSactegory.this, allSampleData, mCallback);

            my_recycler_view.setLayoutManager(new LinearLayoutManager( Activity_SubSactegory.this, LinearLayoutManager.VERTICAL, false));

            my_recycler_view.setAdapter(adapter);
        }

    }

}
