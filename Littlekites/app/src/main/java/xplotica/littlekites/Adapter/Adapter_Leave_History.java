package xplotica.littlekites.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import xplotica.littlekites.FeederInfo.FeederInfo_leave_history;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.ViewHolder_Leave_history;

/**
 * Created by G2evolution on 10/21/2017.
 */

public class Adapter_Leave_History  extends RecyclerView.Adapter<ViewHolder_Leave_history> {

    private ArrayList<FeederInfo_leave_history> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private Adapter_Leave_History.FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;



    public Adapter_Leave_History(Context context, ArrayList<FeederInfo_leave_history> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(Adapter_Leave_History.FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<FeederInfo_leave_history> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder_Leave_history onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_history_list,parent, false);
        ViewHolder_Leave_history rcv = new ViewHolder_Leave_history(layoutView);
        return rcv;
    }

    public long getDifferenceDays(Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


    @Override
    public void onBindViewHolder(final ViewHolder_Leave_history holder, int position) {
        final FeederInfo_leave_history feederInfo = mListFeeds.get(position);



        String dtStart = feederInfo.getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dtStart);
            long daysAgo = getDifferenceDays(date);
            if (daysAgo == 0){
                holder.time.setText("Today");
            }else{
                holder.time.setText(daysAgo + " days ago");
            }

            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        String feedDesc = null;


        holder.Date.setText(feederInfo.getFromdate()+" - "+feederInfo.getTodate());

        holder.details.setText("Description: "+feederInfo.getDescription());
        holder.reason.setVisibility(View.GONE);


        if (feederInfo.getAccept() == null || feederInfo.getAccept().equals("")){


        }
        else if (feederInfo.getAccept().equals("Approved")){
            holder.accept.setText("Approved");
            holder.accept.setTextColor(Color.parseColor("#4CAF50"));
            holder.reason.setVisibility(View.GONE);

        } else if (feederInfo.getAccept().equals("Pending")){
            holder.accept.setText("Pending");
            holder.accept.setTextColor(Color.parseColor("#424242"));
            holder.reason.setVisibility(View.GONE);

        }
        else if (feederInfo.getAccept().equals("Rejected")) {
            holder.accept.setText("Rejected");
            holder.reason.setVisibility(View.VISIBLE);

            holder.reason.setText("Reason: "+feederInfo.getReason());
        }



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
