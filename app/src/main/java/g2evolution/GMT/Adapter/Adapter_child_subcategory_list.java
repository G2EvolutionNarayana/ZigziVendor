package g2evolution.GMT.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import g2evolution.GMT.FeederInfo.FeederInfo_child_subcategory_list;
import g2evolution.GMT.R;
import g2evolution.GMT.Viewholder.ViewHolder_Child_subcategory_list;


public class Adapter_child_subcategory_list extends RecyclerView.Adapter<ViewHolder_Child_subcategory_list> {

    private ArrayList<FeederInfo_child_subcategory_list> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;

    private Adapter_child_subcategory_list.OnItemClick1 mCallback;

    String subcatid;
    Integer introwposition = null;

    public Adapter_child_subcategory_list(Context context, ArrayList<FeederInfo_child_subcategory_list> feedList,Adapter_child_subcategory_list.OnItemClick1 listener){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        this.mCallback = listener;
    }
    public interface OnItemClick1 {
        void onClickedItem1(int pos, int qty, int status);
    }

    public void setData( ArrayList<FeederInfo_child_subcategory_list> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();

    }


    @Override
    public ViewHolder_Child_subcategory_list onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_subcategory_lists, null);
        ViewHolder_Child_subcategory_list rcv = new ViewHolder_Child_subcategory_list(layoutView);
        return rcv;

    }


    @Override
    public void onBindViewHolder(final ViewHolder_Child_subcategory_list holder, final  int position) {
        final FeederInfo_child_subcategory_list feederInfo = mListFeeds.get(position);

        String feedDesc = null;

        subcatid=feederInfo.getId();

        holder.subcatname.setText(feederInfo.getName());


        holder.subcatname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                introwposition = position;
                subcatid=feederInfo.getId();


                if (mCallback!=null){
                    mCallback.onClickedItem1(position,Integer.parseInt(subcatid), 1);
                }

                Log.e("testing","subcatid===child========"+subcatid);
                notifyDataSetChanged();

            }
        });

        if (introwposition == null){



        }else if(introwposition==position){
            holder.card_view.setCardBackgroundColor(Color.parseColor("#4527A0"));
            holder.subcatname.setTextColor(Color.parseColor("#ffffff"));

        }else{
            holder.card_view.setCardBackgroundColor(Color.parseColor("#ffffff"));
            holder.subcatname.setTextColor(Color.parseColor("#4527A0"));
        }
        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}