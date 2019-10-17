package xplotica.littlekites.Adapter_parent;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.FeederInfo_parent.parent_Notification_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder_parent.parent_Notification_ViewHolder;

public class parent_Notification_Adapter extends RecyclerView.Adapter<parent_Notification_ViewHolder> {

    private ArrayList<parent_Notification_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;


    public parent_Notification_Adapter(Context context, ArrayList<parent_Notification_feederInfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<parent_Notification_feederInfo> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public parent_Notification_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_parent_notification, null);
        parent_Notification_ViewHolder rcv = new parent_Notification_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final parent_Notification_ViewHolder holder, int position) {
        final parent_Notification_feederInfo feederInfo = mListFeeds.get(position);


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