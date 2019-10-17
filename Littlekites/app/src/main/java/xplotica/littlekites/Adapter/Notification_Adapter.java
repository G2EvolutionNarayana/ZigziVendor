package xplotica.littlekites.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.FeederInfo.Notification_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.Notification_ViewHolder;


public class Notification_Adapter extends RecyclerView.Adapter<Notification_ViewHolder> {

    private ArrayList<Notification_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;


    public Notification_Adapter(Context context, ArrayList<Notification_feederInfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<Notification_feederInfo> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public Notification_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification, null);
        Notification_ViewHolder rcv = new Notification_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final Notification_ViewHolder holder, int position) {
        final Notification_feederInfo feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        holder.Details.setText(feederInfo.getName());


        holder.icon.setImageResource(feederInfo.getPhoto());


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