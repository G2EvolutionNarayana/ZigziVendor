package xplotica.littlekites.Adapter_parent;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import xplotica.littlekites.FeederInfo_parent.parent_HomeWork_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder_parent.parent_HomeWork_ViewHolder;

/**
 * Created by santa on 3/30/2017.
 */
public class parent_Homework_Adapter extends RecyclerView.Adapter<parent_HomeWork_ViewHolder> {

    String strtime;

    private ArrayList<parent_HomeWork_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;


    public parent_Homework_Adapter(Context context, ArrayList<parent_HomeWork_feederInfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<parent_HomeWork_feederInfo> feedList) {
        mListFeeds = feedList;
        mListFeeds.clear();
        //mListFeeds.addAll(feedList);
        notifyDataSetChanged();
    }

    @Override
    public parent_HomeWork_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_homework_list, null);
        parent_HomeWork_ViewHolder rcv = new parent_HomeWork_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final parent_HomeWork_ViewHolder holder, int position) {
        final parent_HomeWork_feederInfo feederInfo = mListFeeds.get(position);


        String s = feederInfo.getDate();
        SimpleDateFormat inputFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d= null;
        try {
            d = inputFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputFormat=
                new SimpleDateFormat("hh:mm a");
        System.out.println(outputFormat.format(d));
        Log.e("testing", "Time ========= "+outputFormat.format(d));
        strtime = outputFormat.format(d);

        holder.texttime.setText(strtime);
        String feedDesc = null;

        holder.TopicName.setText(feederInfo.get_subject());
       // holder.Time.setText(feederInfo.get_time());
        holder.Message.setText(feederInfo.get_message());


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