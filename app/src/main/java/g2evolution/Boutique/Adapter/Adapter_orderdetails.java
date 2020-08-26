package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import g2evolution.Boutique.FeederInfo.FeederInfo_orderdetails;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Viewholder.ViewHolder_orderdetails;


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

//        holder.orderdate.setText(feederInfo.getOrderdate());
        holder.ordername.setText(feederInfo.getOrdername());
        holder.orderprodetails.setText(feederInfo.getOrderprodetails());
//        if (feederInfo.getDiscountamount() == null ||feederInfo.getDiscountamount().trim().equals("null")||feederInfo.getDiscountamount().trim().length() == 0 || feederInfo.getDiscountamount().trim().equals("0")){
//        }else{
//
//            holder.pdprice.setText(strrs+" "+feederInfo.getOrderpriceamount());
//            holder.pdprice.setVisibility(View.VISIBLE);
//            holder.pdprice.setPaintFlags(holder.pdprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        }
        holder.quantity_ordertext.setText(feederInfo.getQuantity_ordertext());
        holder.ordertotalamount.setText(strrs+" "+feederInfo.getOrdertotalamount());

        if (feederInfo.getOrderimage() == null || feederInfo.getOrderimage().equals("0")||feederInfo.getOrderimage().equals("")||feederInfo.getOrderimage().equals("null")){
            holder.orderimage.setImageResource(R.drawable.car);
        }else {
            Log.e("testing","image in adapter = "+feederInfo.getOrderimage());
            Glide.with(mContext)
                    .load(Uri.parse(feederInfo.getOrderimage()))
                    // .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .error(R.drawable.car)
                    .into(holder.orderimage);

        }



        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}