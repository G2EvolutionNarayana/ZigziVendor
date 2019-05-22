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

import xplotica.littlekites.FeederInfo_parent.parent_sentitem_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder_parent.parent_sentitem_ViewHolder;

/**
 * Created by santa on 3/29/2017.
 */
public class parent_Sentitem_Adapter extends RecyclerView.Adapter<parent_sentitem_ViewHolder> {

    private ArrayList<parent_sentitem_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;

    public parent_Sentitem_Adapter(Context context, ArrayList<parent_sentitem_feederInfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public long getDifferenceDays(Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public void SetListener(FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<parent_sentitem_feederInfo> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public parent_sentitem_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_parent_sentitem_list, parent,false);
        parent_sentitem_ViewHolder rcv = new parent_sentitem_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final parent_sentitem_ViewHolder holder, int position) {
        final parent_sentitem_feederInfo feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        holder.Message.setText(feederInfo.getMessage());

        holder.Time.setText(feederInfo.getTime());

        long daysAgo = getDifferenceDays(feederInfo.getDate());

        holder.Date.setText(daysAgo + " days ago");


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

