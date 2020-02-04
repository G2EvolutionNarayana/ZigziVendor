package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import g2evolution.Boutique.Activity.Activity_orderdetails;
import g2evolution.Boutique.FeederInfo.FeederInfo_orderhistory;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Viewholder.ViewHolder_orderhistory;


public class Adapter_orderhistory extends RecyclerView.Adapter<ViewHolder_orderhistory> {

    private ArrayList<FeederInfo_orderhistory> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    private OnItemClick mCallback;
    int currentNos;

    String orderid,ORDERID;

    String qty;

    String UserId,orderitemid;

    public Adapter_orderhistory(Context context, ArrayList<FeederInfo_orderhistory> feedList, OnItemClick listener){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        this.mCallback = listener;

        SharedPreferences prefuserdata1 = mContext.getSharedPreferences("regId", 0);
        UserId = prefuserdata1.getString("UserId", "");





    }
    public interface OnItemClick {
        void onClickedItem(int pos, String qty, int status);
    }
    public void setData( ArrayList<FeederInfo_orderhistory> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_orderhistory onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history, parent,false);
        ViewHolder_orderhistory rcv = new ViewHolder_orderhistory(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final ViewHolder_orderhistory holder, final int position) {
        final FeederInfo_orderhistory feederInfo = mListFeeds.get(position);


        final String strrs = mContext.getResources().getString(R.string.Rs);
        String feedDesc = null;

        if (feederInfo.getDeliverystatus().equals("In_transit")){
            holder.deliverystatus.setText("Pending");
        }else if (feederInfo.getDeliverystatus().equals("Canceled")){
            holder.deliverystatus.setText("Cancelled");
            holder.imgdelete.setVisibility(View.GONE);
        }else if (feederInfo.getDeliverystatus().equals("Delivered")){
            holder.deliverystatus.setText("Delivered");
        }else{
            holder.deliverystatus.setText("Pending");
        }

        holder.orderdate.setText(feederInfo.getOrderdate());
        holder.orderid.setText(feederInfo.getOrderid());
        holder.totalprice.setText(strrs+feederInfo.getTotalprice());
        // holder.shippingaddress.setText(feederInfo.getShippingadress());
        holder.paymentmode.setText(feederInfo.getPaymentmode());

        ORDERID =  feederInfo.getOrderID();


        Log.e("testing","orderid  = " + ORDERID);

        holder.linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderid =  feederInfo.getOrderid();
                ORDERID =  feederInfo.getOrderID();

                SharedPreferences prefuserdata = mContext.getSharedPreferences("OrderDetails", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();
                prefeditor.putString("orderid", "" + ORDERID);
                Log.e("testing","orderid  = " + ORDERID);
                prefeditor.commit();


                Intent i = new Intent(mContext, Activity_orderdetails.class);
                mContext.startActivity(i);


            }
        });




        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (feederInfo.getDeliverystatus().equals("Canceled")){


                }else{
                     /*   orderitemid = feederInfo.getOrderid();
                Log.e("testing","test2 = "+orderitemid);
                new DeleteItem().execute();*/

                    qty = feederInfo.getOrderID();
                    Log.e("testing","qty====adapter"+qty);
                    Log.e("testing","qty====adapter"+qty);
                    if (mCallback!=null){
                        mCallback.onClickedItem(position,feederInfo.getOrderid(), 1);
                    }
                }

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