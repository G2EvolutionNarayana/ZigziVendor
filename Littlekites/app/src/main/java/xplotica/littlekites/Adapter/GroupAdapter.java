package xplotica.littlekites.Adapter;

/**
 * Created by G2evolution on 3/27/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import xplotica.littlekites.Activity.Activity_Groupchat;
import xplotica.littlekites.FeederInfo.GroupEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.GroupHolder;


public class GroupAdapter extends RecyclerView.Adapter<GroupHolder> {
    //contains the list of buyers
    private ArrayList<GroupEntity> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;

    String rowid, cusid;
    String clicking;


    public GroupAdapter(Context context, ArrayList<GroupEntity> feedList){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener){
        feedItemListner = listener;
    }

    public void setData( ArrayList<GroupEntity> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {





        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        GroupHolder rcv = new GroupHolder(layoutView);
        return rcv;
    }

    public  long getDifferenceDays( Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onBindViewHolder(final GroupHolder holder, int position) {
        final GroupEntity feederInfo = mListFeeds.get(position);




        holder.feederName.setText(feederInfo.getHomegroupname());
        holder.linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Activity_Groupchat.class);

                SharedPreferences prefuserdata1 = mContext.getSharedPreferences("group", 0);
                SharedPreferences.Editor prefeditor1 = prefuserdata1.edit();



                prefeditor1.putString("groupid", "" + feederInfo.getHomegroupid());
                prefeditor1.putString("homename", "" + feederInfo.getHomegroupname());

                prefeditor1.commit();
                mContext.startActivity(intent);

            }
        });



        mPreviousPosition = position;






    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


    public interface FeederItemListener {
        public void onFeedClicked(int position, int resid);
    }
}

