package xplotica.littlekites.Adapter_parent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.Activity_parent.Activity_Parent_Attendance_Calender;
import xplotica.littlekites.Activity_parent.Activity_Parent_Chat;
import xplotica.littlekites.Activity_parent.Activity_Parent_Dairy_Details;
import xplotica.littlekites.Activity_parent.Activity_Parent_Events;
import xplotica.littlekites.Activity_parent.Activity_Parent_Gallery;
import xplotica.littlekites.Activity_parent.Activity_Parent_Holiday;
import xplotica.littlekites.Activity_parent.Activity_Parent_Notice;
import xplotica.littlekites.Activity_parent.Activity_Parent_School_Details;
import xplotica.littlekites.Activity_parent.Activity_parent_Fees;
import xplotica.littlekites.Activity_parent.Activity_parent_Home_work;
import xplotica.littlekites.FeederInfo.ItemObject_home;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder_parent.parent_RecyclerViewHolder_home;

/**
 * Created by Santanu on 14-04-2017.
 */

public class RecyclerViewAdapter_Parent_home extends RecyclerView.Adapter<parent_RecyclerViewHolder_home> {



    private ArrayList<ItemObject_home> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;


    public RecyclerViewAdapter_Parent_home(Context context, ArrayList<ItemObject_home> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;

    }

    public void setData( ArrayList<ItemObject_home> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }

    @Override
    public parent_RecyclerViewHolder_home onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_parent_layout, null);
        parent_RecyclerViewHolder_home rcv = new parent_RecyclerViewHolder_home(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final parent_RecyclerViewHolder_home holder, int position) {
        final ItemObject_home feederInfo = mListFeeds.get(position);


        String feedDesc = null;


        holder.input_name.setText(feederInfo.getName());

        holder.image_input.setImageResource(feederInfo.getPhoto());

        //holder.linear_layout.setBackgroundColor(feederInfo.getBackcolor());

        //   holder.linear_layout.setBackgroundColor(Color.parseColor("#E0426E"));

        if (feederInfo.getName().equals("Attendance")){

          //  (holder.linear_layout).setBackgroundColor(Color.parseColor("#19AB5A"));

        }else if (feederInfo.getName().equals("Diary")){
           // (holder.linear_layout).setBackgroundColor(Color.parseColor("#F39C12"));
        }else if (feederInfo.getName().equals("Fees")){
           // (holder.linear_layout).setBackgroundColor(Color.parseColor("#1976D2"));
        }else if (feederInfo.getName().equals("Message")){
           // (holder.linear_layout).setBackgroundColor(Color.parseColor("#EF5350"));
        }else if (feederInfo.getName().equals("Gallery")){
           // (holder.linear_layout).setBackgroundColor(Color.parseColor("#8E44AD"));
        }else if (feederInfo.getName().equals("Calender")){
           // (holder.linear_layout).setBackgroundColor(Color.parseColor("#34495E"));
        }else if (feederInfo.getName().equals("School Details")){

           // (holder.linear_layout).setBackgroundColor(Color.parseColor("#95A5A6"));

        }else if (feederInfo.getName().equals("Events")){

           // (holder.linear_layout).setBackgroundColor(Color.parseColor("#F1C40F"));
        }else if (feederInfo.getName().equals("Notice")){

           // (holder.linear_layout).setBackgroundColor(Color.parseColor("#17A697"));

        }



/*
        Glide.with(mContext)
                .load(Uri.parse(feederInfo.get_photo()))
                        // .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.img)
                .placeholder(R.drawable.img)
                        //  .skipMemoryCache(true)
                .into(holder.photo);
        Log.e("testing","phot in adapter ="+feederInfo.get_photo());
*/


        holder.image_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (feederInfo.getName().equals("Attendance")){

                    Intent intent = new Intent(mContext, Activity_Parent_Attendance_Calender.class);
                    mContext.startActivity(intent);

                }else if (feederInfo.getName().equals("Diary")){
                    Intent intent = new Intent(mContext, Activity_Parent_Dairy_Details.class);
                    mContext.startActivity(intent);
                }else if (feederInfo.getName().equals("Fees")){
                    Intent intent = new Intent(mContext, Activity_parent_Fees.class);
                    mContext.startActivity(intent);
                }else if (feederInfo.getName().equals("Message")){
                    Intent intent = new Intent(mContext, Activity_Parent_Chat.class);
                    mContext.startActivity(intent);
                }else if (feederInfo.getName().equals("Gallery")){
                    Intent intent = new Intent(mContext, Activity_Parent_Gallery.class);
                    mContext.startActivity(intent);
                }else if (feederInfo.getName().equals("Calender")){
                    Intent intent = new Intent(mContext, Activity_Parent_Holiday.class);
                    mContext.startActivity(intent);
                }else if (feederInfo.getName().equals("School Details")){

                    Intent intent = new Intent(mContext, Activity_Parent_School_Details.class);
                    mContext.startActivity(intent);

                }else if (feederInfo.getName().equals("Events")){

                    Intent intent =new Intent(mContext, Activity_Parent_Events.class);
                    mContext.startActivity(intent);
                }else if (feederInfo.getName().equals("Notice")){

                    Intent intent =new Intent(mContext, Activity_Parent_Notice.class);
                    mContext.startActivity(intent);
                }
            }
        });
        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }



}
