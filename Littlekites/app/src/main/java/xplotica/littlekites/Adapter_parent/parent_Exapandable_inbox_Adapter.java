package xplotica.littlekites.Adapter_parent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import xplotica.littlekites.FeederInfo_parent.ReadDetails_Parent_Inbox;
import xplotica.littlekites.FeederInfo_parent.ReadHeader;
import xplotica.littlekites.R;

/**
 * Created by Santanu on 10-04-2017.
 */

public class parent_Exapandable_inbox_Adapter extends BaseExpandableListAdapter {

    private Context mContext;

    // Context context;
    private ArrayList<ReadHeader> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ReadDetails_Parent_Inbox> _listDataChild;

    private customButtonListener customListner;

    public parent_Exapandable_inbox_Adapter(Context context, ArrayList<ReadHeader> listDataHeader,
                                            HashMap<String, ReadDetails_Parent_Inbox> listChildData) {
        this.mContext = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    public void setData(ArrayList<ReadHeader> listDataHeader,
                        HashMap<String, ReadDetails_Parent_Inbox> listChildData) {
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        notifyDataSetChanged();
    }

    public long getDifferenceDays(Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    @Override
    public int getGroupCount() {
        return _listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getHeaderName());
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ReadHeader feedHeader = (ReadHeader) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dashboardrow_parent_expandable_inbox, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.Textfeed);

        TextView lblListHeader3 = (TextView) convertView
                .findViewById(R.id.textViewDay);

       /* TextView lblListHeader4 = (TextView) convertView
                .findViewById(R.id.textViewTime);

        lblListHeader4.setText(feedHeader.getTime());

        Log.e("testing", "time is === " + feedHeader.getTime());
*/
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(feedHeader.getHeaderName());

        long daysAgo = getDifferenceDays(feedHeader.getDate());

        lblListHeader3.setText(daysAgo + " days ago");

/*

        lblListHeader2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Rrowid = ((ReadHeader) getGroup(groupPosition)).getRowid();
                ShowLogoutAlert("Are You Sure Want To Delete?");

                // Toast.makeText(mContext,"deleting data",Toast.LENGTH_LONG).show();


            }
        });
*/

        return convertView;

    }


    private void ShowLogoutAlert(String data) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Delete Feeds");
        alertDialog.setMessage(data);
        //  alertDialog.setBackgroundResource(R.color.dialog_color);
        // alertDialog.setIcon(R.drawable.exit);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                //new delete().execute();

                /*
                SharedPreferences prefuserdata = mContext.getSharedPreferences("userdata", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();

                prefeditor.putString("email", "0");
                prefeditor.putString("password", "0");
                prefeditor.putString("type", "0");
                prefeditor.putString("id", "0");

                prefeditor.clear();
                prefeditor.commit();
*/
                // Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
                //Intent i7 = new Intent(getActivity(), Login_Activity.class);

                // getActivity().startActivity(i7);

                //((Activity) getActivity()).finish();
                //((HomPage)getActivity()).finish();

                dialog.cancel();

            }

        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


   /* class delete extends AsyncTask<String, String, String> {
        String status;
        String message;
        String data;
        String id;


        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String doInBackground(String... args) {

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            Log.e("testing", "id=" + Rrowid);
            userpramas.add(new BasicNameValuePair("id", Rrowid));

            JSONObject json = jsonParser.makeHttpRequest("http://thememoirs.in/kaffeecup/feed_delete.php", "POST", userpramas);

            Log.e("testing", "json result = " + json);

            try {
                status = json.getString("status");
                message = json.getString("response");
                data = json.getString("data");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            pDialog.dismiss();
            if (status.equals("yes")) {


                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();


                mContext.startActivity(new Intent(mContext, Activity_Home.class));


            } else {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }


        }

    }

*/
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ReadDetails_Parent_Inbox childDetail = (ReadDetails_Parent_Inbox) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dashboardrowchild_parent_expandble_inbox, null);
        }
        TextView txtChildDesc = (TextView) convertView
                .findViewById(R.id.childdesc);

        txtChildDesc.setText(childDetail.getDescription());

     /*   TextView txtChildHeader = (TextView) convertView
                .findViewById(R.id.childheader);


        ImageView imagebut = (ImageView) convertView
                .findViewById(R.id.imageViewbut);

        txtChildHeader.setText(childDetail.getTitle());


        if (childDetail.getUpload().equals("") || childDetail.getUpload().contains("http://thememoirs.in/kaffeecup//feader//uploads//")) {
            imagebut.setVisibility(View.INVISIBLE);
        } else if (childDetail.getUpload().contains(".jpg") || childDetail.getUpload().contains(".png") || childDetail.getUpload().contains(".jpeg") || childDetail.getUpload().contains(".jpg") || childDetail.getUpload().contains(".gif")) {


            imagebut.setVisibility(View.VISIBLE);
            imagebut.setImageResource(R.drawable.photo3);

        } else {
            imagebut.setVisibility(View.VISIBLE);
            imagebut.setImageResource(R.drawable.galleryicon);
        }

        imagebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

;

                v.getContext().startActivity(myIntent);
            }
        });*/


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public interface customButtonListener {
        public void onButtonClickListner(int position);

        public void onButtonClickListner1(int position);

        public void onButtonClickListner2(int position);


    }
}
