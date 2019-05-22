package xplotica.littlekites.Fragment_parent;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Adapter_parent.parent_Expandable_sentItem_Adapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.ReadDetails_Parent_SentItem;
import xplotica.littlekites.FeederInfo_parent.ReadHeader_Parent_SentItem;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by Santanu on 11-04-2017.
 */

public class fragment_parent_expandable_sentitem extends Fragment {


    parent_Expandable_sentItem_Adapter mListAdapter;
    ExpandableListView expListView;
    ArrayList<ReadHeader_Parent_SentItem> listDataHeader;
    HashMap<String, ReadDetails_Parent_SentItem> listDataChild;
    JSONParser jsonParser = new JSONParser();

    public static String name, id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent_expandable_sentitem, container, false);


        // preparing list data
        prepareListData();

        expListView = (ExpandableListView)rootView.findViewById(R.id.expandlistdashboard);

        mListAdapter = new parent_Expandable_sentItem_Adapter(getActivity(), listDataHeader, listDataChild);

        expListView.setAdapter(mListAdapter);


        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (cd.isConnectingToInternet()) {

            new userdata().execute(End_Urls.url);

        } else {


            Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }
        return rootView;


    }


    private void prepareListData() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        listDataHeader = new ArrayList<ReadHeader_Parent_SentItem>();
        listDataChild = new HashMap<String, ReadDetails_Parent_SentItem>();
        Date dt = null;
        try {
            dt = ft.parse("2016-08-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }





    }


    public class userdata extends AsyncTask<String, String, String> {
        String responce;
        String message;

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


            userpramas.add(new BasicNameValuePair(End_Urls.url_id, "1"));

            Log.e("testing", "feader_reg_id" + id);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.url, "POST", userpramas);

            Log.e("testing", "jsonParser" + json);


            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing", "jon2222222222222");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    JSONObject response = new JSONObject(json.toString());

                    Log.e("testing", "jsonParser2222" + json);

                    //JSONObject jsonArray1 = new JSONObject(json.toString());
                    // Result = response.getString("status");
                    JSONArray posts = response.optJSONArray("feed_details");
                    Log.e("testing", "jsonParser3333" + posts);



                    if (null == listDataHeader || null == listDataChild) {
                        listDataHeader = new ArrayList<ReadHeader_Parent_SentItem>();
                        listDataChild = new HashMap<String, ReadDetails_Parent_SentItem>();
                    } else {
                        listDataHeader.clear();
                        listDataChild.clear();
                    }

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
                            Log.e("testng", "" + post);


                            ReadHeader_Parent_SentItem item = new ReadHeader_Parent_SentItem();
                            String Rowid = post.getString("id");
                            String Title = post.getString("Title");
                            String date = post.getString("Date");
                            String desc = post.getString("Feed");
                            String time = post.getString("Time");
                            String uploads = post.getString("upload");
                            item.setHeaderName(Title);
                            item.setRowid(Rowid);
                            item.setDescription(desc);
                            item.setUpload(uploads);
                            //  item.setTime(time);

                            try {
                                item.setDate(dateFormat.parse(date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            ReadDetails_Parent_SentItem feedDetail = new ReadDetails_Parent_SentItem(Title, desc);
                            listDataHeader.add(item);
                            listDataChild.put(item.getHeaderName(), feedDetail);
                        }

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
            Log.e("testing", "result is === " + result);

            mListAdapter.setData(listDataHeader, listDataChild);

            // listviewmyorder.setAdapter(adapter);
            Log.e("testing", "adapter");
        }


    }
}



