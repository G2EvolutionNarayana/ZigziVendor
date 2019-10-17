package xplotica.littlekites.Adapter_parent;

/**
 * Created by G2evolution on 3/27/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;
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

import xplotica.littlekites.FeederInfo_parent.Parent_ChatEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder_parent.Parent_ChatHolder;


public class Parent_ChatAdapter extends RecyclerView.Adapter<Parent_ChatHolder> {
    //contains the list of buyers
    private ArrayList<Parent_ChatEntity> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;
    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile,classTeacherId, classTeacherName;
    String rowid, cusid;
    String clicking;
    long daysAgo;
    String strtime;
    public Parent_ChatAdapter(Context context, ArrayList<Parent_ChatEntity> feedList){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        mCollapsedStatus = new SparseBooleanArray();

        SharedPreferences prefuserdata=mContext.getSharedPreferences("registerUser",0);
        type=prefuserdata.getString("type","2");
        School_id=prefuserdata.getString("schoolid","");
        School_name=prefuserdata.getString("schoolname","");
        Student_id=prefuserdata.getString("studentid","");
        Classid=prefuserdata.getString("classid","");
        Sectionid=prefuserdata.getString("sectionid","");
        mobile=prefuserdata.getString("mobile","");
        classTeacherId=prefuserdata.getString("classTeacherId","");
        classTeacherName=prefuserdata.getString("classTeacherName","");

    }

    public void SetListener(FeederItemListener listener){
        feedItemListner = listener;
    }

    public void setData( ArrayList<Parent_ChatEntity> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public Parent_ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {





        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_user1_item_parent, parent, false);
        Parent_ChatHolder rcv = new Parent_ChatHolder(layoutView);
        return rcv;
    }

    public  long getDifferenceDays( Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onBindViewHolder(final Parent_ChatHolder holder, int position) {
        final Parent_ChatEntity feederInfo = mListFeeds.get(position);





        String dtStart = feederInfo.getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dtStart);
            daysAgo = getDifferenceDays(date);
            holder.feederDate.setText(daysAgo + " days ago");
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
        // holder.feederDate.setText(feederInfo.getDate());

        if (feederInfo.getSenderid().equals(Student_id) && feederInfo.getLogintype().equals("Student")){
            holder.incoming_layout_bubble.setVisibility(View.GONE);
            holder.outgoing_layout_bubble.setVisibility(View.VISIBLE);
            holder.feederDesc2.setText(feederInfo.getMessage());
            holder.feederDate2.setText(daysAgo + " days ago");
            holder.feedertime2.setText(strtime);
        }else{
            holder.outgoing_layout_bubble.setVisibility(View.GONE);
            holder.incoming_layout_bubble.setVisibility(View.VISIBLE);
            holder.feederDesc.setText(feederInfo.getMessage());
            holder.feederDate.setText(daysAgo + " days ago");
            holder.feedertime.setText(strtime);
        }

       /* if (feederInfo.getSenderid().equals(strloginid) && feederInfo.getUsertype().equals(strlogintype)){
            holder.incoming_layout_bubble.setBackgroundResource(R.drawable.balloon_outgoing_normal);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.incoming_layout_bubble.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            holder.incoming_layout_bubble.setLayoutParams(params);
        }else{
            holder.incoming_layout_bubble.setBackgroundResource(R.drawable.balloon_incoming_normal);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.incoming_layout_bubble.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            holder.incoming_layout_bubble.setLayoutParams(params);
        }*/



        //holder.incoming_layout_bubble.righ



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
