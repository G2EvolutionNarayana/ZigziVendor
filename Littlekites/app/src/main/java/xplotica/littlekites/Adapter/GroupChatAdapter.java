package xplotica.littlekites.Adapter;

/**
 * Created by G2evolution on 3/27/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import xplotica.littlekites.FeederInfo.GroupChatEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.GroupChatHolder;


public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatHolder> {
    //contains the list of buyers
    private ArrayList<GroupChatEntity> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;

    String rowid, cusid;
    String clicking;
    long daysAgo;
    String strtime;

    String schoolid,type,loginid;

    public GroupChatAdapter(Context context, ArrayList<GroupChatEntity> feedList){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        mCollapsedStatus = new SparseBooleanArray();



        SharedPreferences prefuserdata = mContext.getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        loginid = prefuserdata.getString("staffid", "");
        type = prefuserdata.getString("type", "");

        Log.e("testing","scoolid = "+schoolid);
        Log.e("testing","loginid = "+loginid);
        Log.e("testing","type = "+type);


    }

    public void SetListener(FeederItemListener listener){
        feedItemListner = listener;
    }

    public void setData( ArrayList<GroupChatEntity> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public GroupChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {





        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_user2_item, parent, false);
        GroupChatHolder rcv = new GroupChatHolder(layoutView);
        return rcv;
    }

    public  long getDifferenceDays( Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onBindViewHolder(final GroupChatHolder holder, int position) {
        final GroupChatEntity feederInfo = mListFeeds.get(position);

        holder.imagechat.setVisibility(View.GONE);
        holder.imagechat2.setVisibility(View.GONE);


        holder.feederDesc.setText(feederInfo.getHomegroupmessage());
        String dtStart = feederInfo.getHomedate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dtStart);
            daysAgo = getDifferenceDays(date);

            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String s = feederInfo.getHomedate();
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


        // holder.feederDate.setText(feederInfo.getHomedate());

        if (feederInfo.getHomeloginid().equals(loginid)){
            holder.incoming_layout_bubble.setVisibility(View.GONE);
            holder.outgoing_layout_bubble.setVisibility(View.VISIBLE);

            if (feederInfo.getHomegroupmessage().length() == 0){

             /*   if (feederInfo.getImage().equals("e437c655cc66aa4effeae0166fd417b7.png")){
                    holder.imagechat2.setImageResource(R.drawable.splash);
                    holder.feederDesc2.setText("");
                }*/
                holder.imagechat2.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(Uri.parse(feederInfo.getHomegroupimage()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(holder.imagechat2);
                holder.feederDesc2.setText("");
                holder.feederDate2.setText(daysAgo + " days ago");
                holder.feedertime2.setText(strtime);
            }else{
                holder.imagechat2.setVisibility(View.GONE);
                holder.feederDesc2.setText(feederInfo.getHomegroupmessage());
                holder.feederDate2.setText(daysAgo + " days ago");
                holder.feedertime2.setText(strtime);
            }


           /* holder.feederDesc2.setText(feederInfo.getHomegroupmessage());
            holder.feederDate2.setText(daysAgo + " days ago");
            holder.feedertime2.setText(strtime);*/
        }else{
            holder.outgoing_layout_bubble.setVisibility(View.GONE);
            holder.incoming_layout_bubble.setVisibility(View.VISIBLE);

            if (feederInfo.getHomegroupmessage().length() == 0){
                holder.imagechat.setVisibility(View.VISIBLE);
                /*if (feederInfo.getImage().equals("e437c655cc66aa4effeae0166fd417b7.png")){

                    holder.imagechat2.setImageResource(R.drawable.splash);
                    holder.feederDesc.setText("");
                }*/
                Glide.with(mContext)
                        .load(Uri.parse(feederInfo.getHomegroupimage()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(holder.imagechat);
                holder.feederDesc.setText("");
                holder.feederDate.setText(daysAgo + " days ago , " + feederInfo.getHomesendername());
                holder.feedertime.setText(strtime);
            }else{
                holder.imagechat.setVisibility(View.GONE);
                holder.feederDesc.setText(feederInfo.getHomegroupmessage());
                holder.feederDate.setText(daysAgo + " days ago , " + feederInfo.getHomesendername());
                holder.feedertime.setText(strtime);
            }

          /*  holder.feederDesc.setText(feederInfo.getHomegroupmessage());
            holder.feederDate.setText(daysAgo + " days ago , " + feederInfo.getHomesendername());
            holder.feedertime.setText(strtime);*/
        }



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

