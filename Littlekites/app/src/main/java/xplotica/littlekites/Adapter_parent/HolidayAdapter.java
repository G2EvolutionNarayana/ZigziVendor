package xplotica.littlekites.Adapter_parent;

/**
 * Created by G2evolution on 4/30/2017.
 */


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

import xplotica.littlekites.FeederInfo_parent.HolidayEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.Holiday_Holder;


/**
 * Created by brajabasi on 07-03-2016.
 */
public class HolidayAdapter extends RecyclerView.Adapter<Holiday_Holder> {
    //contains the list of buyers
    private ArrayList<HolidayEntity> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;

    String rowid, cusid;
    String clicking;


    public HolidayAdapter(Context context, ArrayList<HolidayEntity> feedList){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener){
        feedItemListner = listener;
    }

    public void setData( ArrayList<HolidayEntity> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public Holiday_Holder onCreateViewHolder(ViewGroup parent, int viewType) {





        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holiday_item, parent, false);
        Holiday_Holder rcv = new Holiday_Holder(layoutView);
        return rcv;
    }

    public  long getDifferenceDays( Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onBindViewHolder(final Holiday_Holder holder, int position) {
        final HolidayEntity feederInfo = mListFeeds.get(position);




        holder.feederName.setText(feederInfo.getHolidaytitle());
        holder.feederDate.setText(feederInfo.getHolidaydate());
        holder.feederMobileno.setText(feederInfo.getHolidaydesc());



        mPreviousPosition = position;

       /* holder.feederThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Activity_HomeDetails.class);
                mContext.startActivity(intent);

            }
        });*/




    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


    public interface FeederItemListener {
        public void onFeedClicked(int position, int resid);
    }
}

