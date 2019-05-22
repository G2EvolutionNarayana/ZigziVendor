package xplotica.littlekites.Adapter_parent;

import android.content.Context;
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

import xplotica.littlekites.FeederInfo_parent.ReadDetails_Parent_SentItem;
import xplotica.littlekites.FeederInfo_parent.ReadHeader_Parent_SentItem;
import xplotica.littlekites.R;

/**
 * Created by Santanu on 11-04-2017.
 */

public class parent_Expandable_sentItem_Adapter extends BaseExpandableListAdapter {

    private Context mContext;


    // Context context;
    private ArrayList<ReadHeader_Parent_SentItem> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ReadDetails_Parent_SentItem> _listDataChild;

    private parent_Exapandable_inbox_Adapter.customButtonListener customListner;

    public parent_Expandable_sentItem_Adapter(Context context, ArrayList<ReadHeader_Parent_SentItem> listDataHeader,
                                              HashMap<String, ReadDetails_Parent_SentItem> listChildData) {
        this.mContext = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    public void setData(ArrayList<ReadHeader_Parent_SentItem> listDataHeader,
                        HashMap<String, ReadDetails_Parent_SentItem> listChildData) {
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        notifyDataSetChanged();
    }

    public long getDifferenceDays(Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


    public void setCustomButtonListner(parent_Exapandable_inbox_Adapter.customButtonListener listener) {
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
        ReadHeader_Parent_SentItem feedHeader = (ReadHeader_Parent_SentItem) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dashboardrow_parent_expandable_sentitem, null);
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




        return convertView;

    }


     @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ReadDetails_Parent_SentItem childDetail = (ReadDetails_Parent_SentItem) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dashboardrowchild_parent_sentitem, null);
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
