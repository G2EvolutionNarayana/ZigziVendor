package g2evolution.Boutique.Activity;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import g2evolution.Boutique.Adapter.CustomAdapter;
import g2evolution.Boutique.Adapter.DBHelper;
import g2evolution.Boutique.Adapter.Student;
import g2evolution.Boutique.Adapter.StudentRepo;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.Info_MyCart;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;


public class Activity_search extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();
    private ArrayList<Info_MyCart> mListFeederInfo;

    //------------------searchable view in actionbar---------------------
    private MenuItem searchMenuItem;
    MenuItemCompat menuitem = null;
    SearchView searchView;
    RelativeLayout relativemain;
    ListView listview;
    ArrayAdapter<String> adapter;
    String strsearchtext;
    private CustomAdapter customAdapter;
    ListView listView;
    Cursor cursor;
    StudentRepo studentRepo ;
    private static DBHelper dbHelper;
    String searchstatus;
    EditText editsearch;
    MenuItem  menusearch;
     ImageView emptyimage;

     String strcategoryname, strsubcategoryname, strchildname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView) findViewById(R.id.listView1);

        SharedPreferences prefuserdata23 = getSharedPreferences("searchparam", 0);
        strcategoryname = prefuserdata23.getString("category_id", "");
        strsubcategoryname = prefuserdata23.getString("sub_category_id", "");
        strchildname = prefuserdata23.getString("child_category_id", "");


        if (strchildname == null || strchildname.trim().length() == 0){
            strchildname = "";
        }else if (strchildname.trim().equals("All")){
            strchildname = "";
        }else{

        }

        ConnectionDetector cd = new ConnectionDetector(Activity_search.this);
        if (cd.isConnectingToInternet()) {
            new searchfunction().execute();
        } else {
            Toast.makeText(Activity_search.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
        }

        EditText editsearch = (EditText) findViewById(R.id.editsearch);

        emptyimage=(ImageView)findViewById(R.id.emptyimage);
        emptyimage.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        editsearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (searchstatus == null || searchstatus.length() == 0){


                    strsearchtext = s.toString();

                }else if (searchstatus.equals("success")) {
                    // Log.d(TAG, "onQueryTextSubmit ");
                    Log.e("testing","s = "+s);
                    cursor=studentRepo.getStudentListByKeyword(s.toString());

                    if (cursor==null){

                        listView.setVisibility(View.GONE);
                        emptyimage.setVisibility(View.VISIBLE);
                        //    Toast.makeText(Activity_search.this,"No records found!",Toast.LENGTH_LONG).show();


                    }else{

                        listView.setVisibility(View.VISIBLE);
                        emptyimage.setVisibility(View.GONE);
                        cursor=studentRepo.getStudentListByKeyword(s.toString());

                        customAdapter.swapCursor(cursor);
                        // Toast.makeText(MainActivity.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
                    }


                }else{





                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                if (searchstatus == null || searchstatus.length() == 0){


                }else if (searchstatus.equals("success")) {

                    if (cursor==null ||cursor.equals("null")||cursor.equals("0")){

                        Log.e("testing","cursor===null="+cursor);

                      //  Toast.makeText(Activity_search.this, "Please Check the Spelling or try a Different Spelling", Toast.LENGTH_SHORT).show();
                        listView.setVisibility(View.GONE);
                        emptyimage.setVisibility(View.VISIBLE);

                    }else {
                        listView.setVisibility(View.VISIBLE);
                        emptyimage.setVisibility(View.GONE);
                        Log.e("testing","cursor===="+cursor);
                        cursor=studentRepo.getStudentListByKeyword(s.toString());
                        if (cursor!=null){
                            customAdapter.swapCursor(cursor);
                        }

                    }

                }else{

                }




            }

            public void afterTextChanged(Editable s) {

            }
        });
    }

    class searchfunction extends AsyncTask<String, String,  Map<String,String>> {

        String responce;
        String spintype;
        String arrayresponce;
        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        public  Map<String,String> doInBackground(String... args) {
            // Create an array
            //   search_array = new ArrayList<String>();
            mListFeederInfo = new ArrayList<Info_MyCart>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            //Log.e("testing", "user_id value=" + id);
            Map<String,String> productRateMap = new LinkedHashMap<String, String>();

            userpramas.add(new BasicNameValuePair(EndUrl.Search_categoryname, strcategoryname));
            userpramas.add(new BasicNameValuePair(EndUrl.Search_subcategoryname, strsubcategoryname));
            userpramas.add(new BasicNameValuePair(EndUrl.Search_child, strchildname));

            Log.e("testing", "json userpramas = " + userpramas);
            JSONObject json = jsonParser.makeHttpRequest(EndUrl.Search_Products_URL, "GET", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);


            }else {
                Log.e("testing", "jon2222222222222");
                try {
                    searchstatus = json.getString("status");
                    arrayresponce = json.getString("data");
                    Log.e("testing", "adapter value=" + arrayresponce);

                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray value=" + responcearray);

                    if (arrayresponce == null) {
                        Log.e("testing", "jon11111111111111111");

                        //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
                    } else {

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            return productRateMap;
        }

        @Override
        protected void onPostExecute( Map<String,String> responce) {
            super.onPostExecute(responce);

            Log.e("testing", "response is = " + responce.size());

            if (searchstatus == null || searchstatus.length() == 0){


            }else if (searchstatus.equals("success")) {

                //---------for deleting table database--------------------
                dbHelper = new DBHelper(Activity_search.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete(Student.TABLE, null,null);

                studentRepo = new StudentRepo(Activity_search.this);
                cursor=studentRepo.getStudentList();
                customAdapter = new CustomAdapter(Activity_search.this,  cursor, 0);
                listView = (ListView) findViewById(R.id.listView1);
                listView.setAdapter(customAdapter);


                try {

                    Log.e("testing", "adapter value=" + arrayresponce);

                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray value=" + responcearray);


                    if (arrayresponce == null) {
                        Log.e("testing", "jon11111111111111111");

                        //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();

                            Student student= new Student();

                          //  student.student_ID=post.optInt("productId");
                            student.email=post.optString("productId");
                            student.name=post.optString("title");
                            student.age=post.optInt("stockQuantity");
                            studentRepo.insert(student);


                        /*    item.set_cartid( post.optString("product_id"));
                            item.set_title( post.optString("product_name"));
                            mListFeederInfo.add(item);*/
                            //  search_array.add(post.optString("product_name"));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

/*

                adapter = new ArrayAdapter<String>(Activity_Home.this, android.R.layout.simple_list_item_1, search_array);
                listview = (ListView) findViewById(R.id.listView1);
                listview.setAdapter(adapter);
*/


                String s = "";
                cursor=studentRepo.getStudentListByKeyword(s.toString());
                customAdapter.swapCursor(cursor);


            } else if (searchstatus.equals("fail")){

                //  Log.e("testing1", "json response == " + response);
                emptyimage.setVisibility(View.VISIBLE);

            }





        }

    }

}








