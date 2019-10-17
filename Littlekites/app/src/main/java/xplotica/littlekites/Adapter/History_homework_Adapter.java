package xplotica.littlekites.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.FeederInfo.History_homework_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.History_homework_ViewHolder;


public class History_homework_Adapter extends RecyclerView.Adapter<History_homework_ViewHolder> {

    private ArrayList<History_homework_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;


    public History_homework_Adapter(Context context, ArrayList<History_homework_feederInfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<History_homework_feederInfo> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public History_homework_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_homework_list,parent, false);
        History_homework_ViewHolder rcv = new History_homework_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final History_homework_ViewHolder holder, int position) {
        final History_homework_feederInfo feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        holder.topic.setText(feederInfo.get_topic());
        holder.topic_details.setText(feederInfo.get_topic_details());

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