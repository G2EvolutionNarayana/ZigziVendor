package xplotica.littlekites.Adapter_parent;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import xplotica.littlekites.FeederInfo_parent.parent_fee_history;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder_parent.parent_fee_history_ViewHolder;

/**
 * Created by G2evolution on 10/21/2017.
 */

public class parent_fee_history_adapter  extends RecyclerView.Adapter<parent_fee_history_ViewHolder> {

    private ArrayList<parent_fee_history> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private parent_fee_history_adapter.FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;

    String strdate,strtime;

    public parent_fee_history_adapter(Context context, ArrayList<parent_fee_history> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(parent_fee_history_adapter.FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<parent_fee_history> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public parent_fee_history_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_feehistory_item,parent, false);
        parent_fee_history_ViewHolder rcv = new parent_fee_history_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final parent_fee_history_ViewHolder holder, int position) {
        final parent_fee_history feederInfo = mListFeeds.get(position);


      /*  String dtStart = feederInfo.getDate();
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
*/

      /*
        String s = feederInfo.getTime();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = inputFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputFormat1 =
                new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(outputFormat1.format(d));
        Log.e("testing", "Time ========= " + outputFormat1.format(d));
        strdate = outputFormat1.format(d);

        SimpleDateFormat outputFormat =
                new SimpleDateFormat("hh:mm a");
        System.out.println(outputFormat.format(d));
        Log.e("testing", "Time ========= " + outputFormat.format(d));
        strtime = outputFormat.format(d);


        String feedDesc = null;*/



        holder.amount.setText("RS: "+feederInfo.getAmount());
        holder.time.setText(feederInfo.getTime());

        holder.desc.setText(feederInfo.getDescription());
        holder.transid.setText(feederInfo.getTransid());
        holder.transstatus.setText(feederInfo.getTransstatus());
        holder.paymenttype.setText(feederInfo.getPaymenttype());






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
