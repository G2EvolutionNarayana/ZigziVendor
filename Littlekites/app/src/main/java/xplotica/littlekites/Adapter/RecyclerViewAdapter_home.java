package xplotica.littlekites.Adapter;

/**
 * Created by Sujata on 07-11-2016.
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.Activity.Activity_Dairy;
import xplotica.littlekites.Activity.Activity_GroupMessage;
import xplotica.littlekites.Activity.Activity_Home_work;
import xplotica.littlekites.Activity.Activity_Leave;
import xplotica.littlekites.Activity.Activity_MessageChat;
import xplotica.littlekites.Activity.Activity_Timetable;
import xplotica.littlekites.Activity.Activity_attendance;
import xplotica.littlekites.Activity.Activity_history;
import xplotica.littlekites.Activity_parent.Activity_Parent_Gallery;
import xplotica.littlekites.FeederInfo.ItemObject_home;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.RecyclerViewHolder_home;


public class RecyclerViewAdapter_home extends RecyclerView.Adapter<RecyclerViewHolder_home> {

    private ArrayList<ItemObject_home> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;


    public RecyclerViewAdapter_home(Context context, ArrayList<ItemObject_home> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<ItemObject_home> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder_home onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_layout,parent, false);
        RecyclerViewHolder_home rcv = new RecyclerViewHolder_home(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder_home holder, int position) {
        final ItemObject_home feederInfo = mListFeeds.get(position);


        String feedDesc = null;


        holder.input_name.setText(feederInfo.getName());
        holder.image_input.setImageResource(feederInfo.getPhoto());
        holder.linear_layout.setBackgroundColor(feederInfo.getBackcolor());


        if (feederInfo.getName().equals("Attendance")){

            // (holder.linear_layout).setBackgroundColor(Color.parseColor("#19AB5A"));
        }
        else if (feederInfo.getName().equals("Diary"))
        {
            //(holder.linear_layout).setBackgroundColor(Color.parseColor("#F39C12"));
        }

        else if (feederInfo.getName().equals("Leave"))
        {
            //(holder.linear_layout).setBackgroundColor(Color.parseColor("#1976D2"));
        }
        else if (feederInfo.getName().equals("Time Table"))
        {
           // (holder.linear_layout).setBackgroundColor(Color.parseColor("#00ACC1"));
        }

        else if (feederInfo.getName().equals("Message"))
        {
            //(holder.linear_layout).setBackgroundColor(Color.parseColor("#EF5350"));
        }

        else if (feederInfo.getName().equals("Gallery"))
        {
            //(holder.linear_layout).setBackgroundColor(Color.parseColor("#8E44AD"));
        }

        else if (feederInfo.getName().equals("History"))
        {
           // (holder.linear_layout).setBackgroundColor(Color.parseColor("#34495E"));
        }



        mPreviousPosition = position;

        holder.image_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (feederInfo.getName().equals("Attendance")){


                    //   Log.e("testing", "attendance");
                    Intent intent=new Intent(mContext, Activity_attendance.class);
                    mContext.startActivity(intent);

                }
                else if (feederInfo.getName().equals("Diary"))
                {
                    // Log.e("testing", "attendance");
                    Intent intent=new Intent(mContext, Activity_Dairy.class);
                    mContext.startActivity(intent);
                }

                else if (feederInfo.getName().equals("Leave"))
                {
                    //Log.e("testing", "attendance");
                    Intent intent=new Intent(mContext, Activity_Leave.class);
                    mContext.startActivity(intent);
                }

                else if (feederInfo.getName().equals("Time Table"))
                {
                    Intent intent=new Intent(mContext, Activity_Timetable.class);
                    mContext.startActivity(intent);
                } else if (feederInfo.getName().equals("Message"))
                {
                    Intent intent=new Intent(mContext, Activity_MessageChat.class);
                    mContext.startActivity(intent);
                } else if (feederInfo.getName().equals("Group Message"))
                {
                    Intent intent=new Intent(mContext, Activity_GroupMessage.class);
                    mContext.startActivity(intent);
                } else if (feederInfo.getName().equals("Gallery"))
                {
                    Intent intent=new Intent(mContext, Activity_Parent_Gallery.class);
                    mContext.startActivity(intent);
                }

                else if (feederInfo.getName().equals("History"))
                {
                    Intent intent=new Intent(mContext, Activity_history.class);
                    mContext.startActivity(intent);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


    public interface FeederItemListener {
        public void onFeedClicked(int position, int resid);
    }

}
