package xplotica.littlekites.Adapter_parent;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import xplotica.littlekites.FeederInfo_parent.Parent_Notice_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder_parent.parent_Notice_ViewHolder;


public class parent_Notice_Adapter extends RecyclerView.Adapter<parent_Notice_ViewHolder> {



    private ArrayList<Parent_Notice_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;

    String Pid,name,company,price,photo;
    String rating;

    long daysAgo;
    String School_id,Student_id,School_name,ClassId,SectionId,mobile,logo,filepath;



    public parent_Notice_Adapter(Context context, ArrayList<Parent_Notice_feederInfo> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;


    }

    public void setData( ArrayList<Parent_Notice_feederInfo> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public parent_Notice_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_notice_details, null);
        parent_Notice_ViewHolder rcv = new parent_Notice_ViewHolder(layoutView);
        return rcv;
    }

    public  long getDifferenceDays( Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onBindViewHolder(final parent_Notice_ViewHolder holder, int position) {
        final Parent_Notice_feederInfo feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        String dtStart = feederInfo.getNoticedate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dtStart);
            daysAgo = getDifferenceDays(date);
            holder.texttime.setText(daysAgo + " days ago");
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        holder.textdesc.setText(feederInfo.getNoticedescription());
        //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));



        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}

