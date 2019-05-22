package g2evolution.GMT.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import g2evolution.GMT.FeederInfo.FeederInfo_orderdetails;
import g2evolution.GMT.R;
import g2evolution.GMT.Viewholder.ViewHolder_orderdetails;


public class Adapter_orderdetails extends RecyclerView.Adapter<ViewHolder_orderdetails> {

    private ArrayList<FeederInfo_orderdetails> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    private OnItemClick mCallback;
    String qty;

    String subcatgoryid;

    public Adapter_orderdetails(Context context, ArrayList<FeederInfo_orderdetails> feedList, OnItemClick listener){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        this.mCallback = listener;


    }
    public interface OnItemClick {
        void onClickedItem(int pos, String qty, int status);
    }
    public void setData( ArrayList<FeederInfo_orderdetails> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_orderdetails onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details, null);
        ViewHolder_orderdetails rcv = new ViewHolder_orderdetails(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final ViewHolder_orderdetails holder, final int position) {
        final FeederInfo_orderdetails feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        final String strrs = mContext.getResources().getString(R.string.Rs);

        holder.orderdate.setText(feederInfo.getOrderdate());
        holder.ordername.setText(feederInfo.getOrdername());
        holder.orderprodetails.setText(feederInfo.getOrderprodetails());
        holder.orderpriceamount.setText(strrs+" "+feederInfo.getOrderpriceamount());
        holder.quantity_ordertext.setText(feederInfo.getQuantity_ordertext());
        holder.ordertotalamount.setText(strrs+" "+feederInfo.getOrdertotalamount());

        if (feederInfo.getOrderimage() == null || feederInfo.getOrderimage().equals("0")||feederInfo.getOrderimage().equals("")||feederInfo.getOrderimage().equals("null")){
            holder.orderimage.setImageResource(R.drawable.car);
        }else {
            Glide.with(mContext)
                    .load(Uri.parse(feederInfo.getOrderimage()))
                    // .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .error(R.drawable.car)
                    .into(holder.orderimage);

        }



    /*    holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = feederInfo.getId();
                if (mCallback!=null){
                    mCallback.onClickedItem(position,feederInfo.getId(), 1);
                }
            }
        });
*/



        //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));


        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}