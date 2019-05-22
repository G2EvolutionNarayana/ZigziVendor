package xplotica.littlekites.Adapter_parent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import xplotica.littlekites.FeederInfo_parent.parent_Events_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder_parent.Parent_Events_ViewHolder;

/**
 * Created by Santanu on 03-04-2017.
 */

public class Parent_Events_Adapter extends RecyclerView.Adapter<Parent_Events_ViewHolder> {

    private ArrayList<parent_Events_feederInfo> mListFeeds;
    private LayoutInflater mInflater;

    private int mPreviousPosition = 0;
    private Context mContext;
    private parent_Homework_Adapter.FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;

      String id;

    public Parent_Events_Adapter(Context context, ArrayList<parent_Events_feederInfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(parent_Homework_Adapter.FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<parent_Events_feederInfo> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    public long getDifferenceDays(Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public Parent_Events_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_events_list, null);
        Parent_Events_ViewHolder rcv = new Parent_Events_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final Parent_Events_ViewHolder holder, int position) {
        final parent_Events_feederInfo feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        id=feederInfo.getId();
        holder.EventName.setText(feederInfo.get_eventnaem());
        holder.Message.setText(feederInfo.get_message());
        holder.StartDate.setText(feederInfo.get_strdate());
        holder.EndDate.setText(feederInfo.get_enddate());


        long daysAgo = getDifferenceDays(feederInfo.getDate());

        holder.Time.setText(daysAgo + " days ago");


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