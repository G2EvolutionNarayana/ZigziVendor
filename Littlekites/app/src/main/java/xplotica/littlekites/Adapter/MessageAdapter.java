package xplotica.littlekites.Adapter;

/**
 * Created by G2evolution on 4/30/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import xplotica.littlekites.Activity.Activity_User_Chat;
import xplotica.littlekites.FeederInfo.MessageEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.Message_Holder;


/**
 * Created by brajabasi on 07-03-2016.
 */
public class MessageAdapter extends RecyclerView.Adapter<Message_Holder> {
    //contains the list of buyers
    private ArrayList<MessageEntity> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;

    String rowid, cusid;
    String clicking;


    public MessageAdapter(Context context, ArrayList<MessageEntity> feedList){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener){
        feedItemListner = listener;
    }

    public void setData( ArrayList<MessageEntity> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public Message_Holder onCreateViewHolder(ViewGroup parent, int viewType) {





        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        Message_Holder rcv = new Message_Holder(layoutView);
        return rcv;
    }

    public  long getDifferenceDays( Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onBindViewHolder(final Message_Holder holder, int position) {
        final MessageEntity feederInfo = mListFeeds.get(position);




        holder.feederName.setText(feederInfo.getFirstname());
        holder.feederMobileno.setText(feederInfo.getRollno());

        holder.linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(mContext, Activity_User_Chat.class);

                    SharedPreferences prefuserdata1 = mContext.getSharedPreferences("message", 0);
                    SharedPreferences.Editor prefeditor1 = prefuserdata1.edit();



                    prefeditor1.putString("studentid", "" + feederInfo.getStudentid());

                    prefeditor1.commit();
                    mContext.startActivity(intent);



            }
        });

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

