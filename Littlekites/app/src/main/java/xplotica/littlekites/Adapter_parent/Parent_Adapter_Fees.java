package xplotica.littlekites.Adapter_parent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import xplotica.littlekites.Activity_parent.Activity_Parent_FeesIntal;
import xplotica.littlekites.Activity_parent.Activity_parent_Fees;
import xplotica.littlekites.CCAvenue.InitialScreenActivity;
import xplotica.littlekites.FeederInfo_parent.Parent_feesinfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder_parent.Parent_ViewHolder_Feesinfo;

/**
 * Created by G2evolution on 11/3/2017.
 */

public class Parent_Adapter_Fees  extends RecyclerView.Adapter<Parent_ViewHolder_Feesinfo> {

    private ArrayList<Parent_feesinfo> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private Parent_Adapter_Fees.FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;



    public Parent_Adapter_Fees(Context context, ArrayList<Parent_feesinfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(Parent_Adapter_Fees.FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<Parent_feesinfo> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public Parent_ViewHolder_Feesinfo onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_fee_info_item,parent, false);
        Parent_ViewHolder_Feesinfo rcv = new Parent_ViewHolder_Feesinfo(layoutView);
        return rcv;
    }

    public long getDifferenceDays(Date d1) {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


    @Override
    public void onBindViewHolder(final Parent_ViewHolder_Feesinfo holder, int position) {
        final Parent_feesinfo feederInfo = mListFeeds.get(position);






        holder.textname.setText(feederInfo.getName());
        holder.textamount.setText("RS: "+feederInfo.getAmount());
        holder.textstatus.setText(feederInfo.getStatus());
        holder.textdate.setText(feederInfo.getDate());

        holder.linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feederInfo.getStatus().equals("due")){
                    Intent intent = new Intent(mContext, Activity_Parent_FeesIntal.class);

                    SharedPreferences prefuserdata = mContext.getSharedPreferences("parentinstal", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();

                    prefeditor.putString("instalmentid", "" + feederInfo.getId());
                    prefeditor.putString("instalmentamount", "" + feederInfo.getAmount());

                    prefeditor.commit();

                    //  Intent intent = new Intent(mContext, InitialScreenActivity.class);
                    mContext.startActivity(intent);
                }else{
                    Toast.makeText(mContext, "No Amount for Pay", Toast.LENGTH_SHORT).show();
                }

            }
        });


        mPreviousPosition = position;


    }


    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


    public interface FeederItemListener {
        public void onFeedClicked(int position, int resid);
    }

}
