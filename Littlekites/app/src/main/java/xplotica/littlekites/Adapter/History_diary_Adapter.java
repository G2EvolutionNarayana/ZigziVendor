package xplotica.littlekites.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.FeederInfo.History_diary_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.History_diary_ViewHolder;

public class History_diary_Adapter extends RecyclerView.Adapter<History_diary_ViewHolder> {

    private ArrayList<History_diary_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;

    String dairyid;

    public History_diary_Adapter(Context context, ArrayList<History_diary_feederInfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<History_diary_feederInfo> feedList) {
        mListFeeds = feedList;
       // mListFeeds.addAll(feedList);
       mListFeeds.clear();
       notifyDataSetChanged();

    }

    public void clear() {
        int size = this.mListFeeds.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mListFeeds.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
            notifyItemRemoved(size);
        }
    }


    @Override
    public History_diary_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_diary_list,parent, false);
        History_diary_ViewHolder rcv = new History_diary_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final History_diary_ViewHolder holder, int position) {
        final History_diary_feederInfo feederInfo = mListFeeds.get(position);

        String feedDesc = null;

        //holder.topic.setText(feederInfo.get_date());
        holder.topic_details.setText(feederInfo.get_topic_details());
        //dairyid=feederInfo.getDairyid();

        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }

    public void delete(int position) { //removes the row
        mListFeeds.remove(position);
        notifyItemRemoved(position);
    }

    public interface FeederItemListener {
        public void onFeedClicked(int position, int resid);
    }
}