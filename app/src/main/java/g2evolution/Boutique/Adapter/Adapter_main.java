package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import g2evolution.Boutique.FeederInfo.FeederInfo_main;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Viewholder.ViewHolder_main;


public class Adapter_main extends RecyclerView.Adapter<ViewHolder_main> {

    private ArrayList<FeederInfo_main> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    String catid,category;

    String qty;


    private Adapter_main.OnItemClick mCallback;

    public Adapter_main(Context context, ArrayList<FeederInfo_main> feedList, Adapter_main.OnItemClick listener){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        this.mCallback = listener;

    }
    public interface OnItemClick {
        void onClickedItem(int pos, int qty, int status);
    }
    public void setData( ArrayList<FeederInfo_main> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_main onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigationrecycler, null);
        ViewHolder_main rcv = new ViewHolder_main(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final ViewHolder_main holder, final int position) {
        final FeederInfo_main feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        holder.textnavi.setText(feederInfo.getTextnavi());

        catid = feederInfo.getId();

   holder.textnavi.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {


           category = feederInfo.getTextnavi();
           catid = feederInfo.getId();

           qty = catid;

           SharedPreferences prefuserdata = mContext.getSharedPreferences("category", 0);
           SharedPreferences.Editor prefeditor = prefuserdata.edit();
           prefeditor.putString("category", "" + category);
           prefeditor.putString("catid", "" + catid);

           Log.e("testing","category  = " + category);
           Log.e("testing","catid  = " + catid);
           prefeditor.commit();


           if (mCallback!=null){
               mCallback.onClickedItem(position,Integer.parseInt(qty), 1);

           }
/*
           Intent i = new Intent(mContext, Activity_categories.class);
           mContext.startActivity(i);*/


       }
   });










        //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));


        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}