package xplotica.littlekites.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.FeederInfo.History_attendance_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.History_attendance_ViewHolder;

public class History_attendance_Adapter extends RecyclerView.Adapter<History_attendance_ViewHolder> {

    private ArrayList<History_attendance_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;


    public History_attendance_Adapter(Context context, ArrayList<History_attendance_feederInfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<History_attendance_feederInfo> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public History_attendance_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_attendance_list,parent, false);
        History_attendance_ViewHolder rcv = new History_attendance_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final History_attendance_ViewHolder holder, int position) {
        final History_attendance_feederInfo feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        holder.student_id.setText(feederInfo.get_studentid());
        holder.student_name.setText(feederInfo.get_studentname());

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