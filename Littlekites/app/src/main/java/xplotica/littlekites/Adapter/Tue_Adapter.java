package xplotica.littlekites.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.FeederInfo.Tue_Info;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.Tue_ViewHolder;

/**
 * Created by G2evolution on 7/4/2017.
 */

public class Tue_Adapter extends RecyclerView.Adapter<Tue_ViewHolder> {



    private ArrayList<Tue_Info> mListFeeds;
    private LayoutInflater Tue_Info;
    private int mPreviousPosition = 0;
    private Context mContext;
    int pos;
    String qty,rowid, radioid;
    String Pid,name,company,price,photo;
    String rating;



    public Tue_Adapter(Context context, ArrayList<Tue_Info> feedList){

        // mInflater = LayoutInflater.from(context);



        this.mListFeeds = feedList;
        this.mContext = context;


    }


    public void setData( ArrayList<Tue_Info> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public Tue_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_tue, null);
        Tue_ViewHolder rcv = new Tue_ViewHolder(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final Tue_ViewHolder holder, final int i) {
        final Tue_Info feederInfo = mListFeeds.get(i);


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