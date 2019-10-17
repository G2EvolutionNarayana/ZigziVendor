package xplotica.littlekites.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.FeederInfo.Fragment_sentitem_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.Sentitem_ViewHolder;

public class Fragment_sentitem_Adapter extends RecyclerView.Adapter<Sentitem_ViewHolder> {

    private ArrayList<Fragment_sentitem_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;


    public Fragment_sentitem_Adapter(Context context, ArrayList<Fragment_sentitem_feederInfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<Fragment_sentitem_feederInfo> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public Sentitem_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sentitem_list,parent, false);
        Sentitem_ViewHolder rcv = new Sentitem_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final Sentitem_ViewHolder holder, int position) {
        final Fragment_sentitem_feederInfo feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        holder.topic.setText(feederInfo.get_topic());
        holder.rollno.setText(feederInfo.get_rollno());
        holder.name.setText(feederInfo.get_name());
        holder.section.setText(feederInfo.get_section());
        holder.message.setText(feederInfo.get_message());

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