package xplotica.littlekites.Adapter;

/**
 * Created by G2evolution on 3/27/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import xplotica.littlekites.FeederInfo.GroupinfoEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.GroupinfoHolder;


public class GroupinfoAdapter extends RecyclerView.Adapter<GroupinfoHolder> {
    //contains the list of buyers
    private ArrayList<GroupinfoEntity> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;
    String strloginid, strloginname, strlogintype;
    String rowid, cusid;
    String clicking;
    long daysAgo;
    String strtime;
    public GroupinfoAdapter(Context context, ArrayList<GroupinfoEntity> feedList){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        mCollapsedStatus = new SparseBooleanArray();

        SharedPreferences prefuserdata = mContext.getSharedPreferences("registerUser", 0);
        strloginid = prefuserdata.getString("loginid", "");
        strloginname = prefuserdata.getString("username", "");
        strlogintype = prefuserdata.getString("usertype", "");

    }

    public void SetListener(FeederItemListener listener){
        feedItemListner = listener;
    }

    public void setData( ArrayList<GroupinfoEntity> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public GroupinfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {





        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.groupinfo_item, parent, false);
        GroupinfoHolder rcv = new GroupinfoHolder(layoutView);
        return rcv;
    }

    public  long getDifferenceDays( Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onBindViewHolder(final GroupinfoHolder holder, int position) {
        final GroupinfoEntity feederInfo = mListFeeds.get(position);






        holder.homename.setText(feederInfo.getUsername());

       /* holder.linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Activity_Singlechat.class);

                SharedPreferences prefuserdata1 = mContext.getSharedPreferences("single", 0);
                SharedPreferences.Editor prefeditor1 = prefuserdata1.edit();



                prefeditor1.putString("toid", "" + feederInfo.getHomeid());
                prefeditor1.putString("homename", "" + feederInfo.getHomename());
                prefeditor1.putString("usertype", "" + feederInfo.getHometype());

                prefeditor1.commit();
                mContext.startActivity(intent);

            }
        });*/


        mPreviousPosition = position;

       /* holder.feederThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Activity_HomeDetails.class);
                mContext.startActivity(intent);

            }
        });*/





    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


    public interface FeederItemListener {
        public void onFeedClicked(int position, int resid);
    }
}

