package xplotica.littlekites.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.FeederInfo.Mon_Info;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.Mon_ViewHolder;

/**
 * Created by G2evolution on 7/4/2017.
 */

public class Mon_Adapter extends RecyclerView.Adapter<Mon_ViewHolder> {



    private ArrayList<Mon_Info> mListFeeds;
    private LayoutInflater Mon_Info;
    private int mPreviousPosition = 0;
    private Context mContext;
    int pos;
    String qty,rowid, radioid;
    String Pid,name,company,price,photo;
    String rating;



    public Mon_Adapter(Context context, ArrayList<Mon_Info> feedList){

        // mInflater = LayoutInflater.from(context);



        this.mListFeeds = feedList;
        this.mContext = context;


    }


    public void setData( ArrayList<Mon_Info> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public Mon_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_mon, null);
        Mon_ViewHolder rcv = new Mon_ViewHolder(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final Mon_ViewHolder holder, final int i) {
        final Mon_Info feederInfo = mListFeeds.get(i);


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