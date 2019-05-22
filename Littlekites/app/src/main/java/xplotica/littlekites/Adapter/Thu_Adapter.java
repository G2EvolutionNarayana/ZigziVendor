package xplotica.littlekites.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.FeederInfo.Thu_Info;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.Thu_ViewHolder;

/**
 * Created by G2evolution on 7/4/2017.
 */

public class Thu_Adapter extends RecyclerView.Adapter<Thu_ViewHolder> {



    private ArrayList<Thu_Info> mListFeeds;
    private LayoutInflater Thu_Info;
    private int mPreviousPosition = 0;
    private Context mContext;
    int pos;
    String qty,rowid, radioid;
    String Pid,name,company,price,photo;
    String rating;



    public Thu_Adapter(Context context, ArrayList<Thu_Info> feedList){

        // mInflater = LayoutInflater.from(context);



        this.mListFeeds = feedList;
        this.mContext = context;


    }


    public void setData( ArrayList<Thu_Info> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public Thu_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_thu, null);
        Thu_ViewHolder rcv = new Thu_ViewHolder(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final Thu_ViewHolder holder, final int i) {
        final Thu_Info feederInfo = mListFeeds.get(i);


        String feedDesc = null;



        holder.textperiod.setText("Period "+feederInfo.getPeriod());
        holder.texttime.setText(feederInfo.getStarttime()+" - "+feederInfo.getEndtime());
        holder.textclass.setText("Class: "+feederInfo.getClassname());
        holder.textsection.setText("Section: "+feederInfo.getSection());
        holder.textsubject.setText("Subject: "+feederInfo.getSubject());





        mPreviousPosition = i;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}