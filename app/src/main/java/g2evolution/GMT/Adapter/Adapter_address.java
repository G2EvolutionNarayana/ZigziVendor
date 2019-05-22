package g2evolution.GMT.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import g2evolution.GMT.Activity.Activity_PaymentMode;
import g2evolution.GMT.Activity.Activity_address;
import g2evolution.GMT.FeederInfo.FeederInfo_address;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;
import g2evolution.GMT.Viewholder.ViewHolder_address;

/**
 * Created by G2evolution on 2/27/2018.
 */

public class Adapter_address extends RecyclerView.Adapter<ViewHolder_address> {

    private ArrayList<FeederInfo_address> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;

    private OnItemClick mCallback;

    String addressid;


    String strmobileno,strFullAddress;

    public Adapter_address(Context context, ArrayList<FeederInfo_address> feedList, OnItemClick listener){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        this.mCallback = listener;

    }

    public interface OnItemClick {
        void onClickedItem(int pos, int qty, int status);
    }
    public void setData( ArrayList<FeederInfo_address> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_address onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, null);
        ViewHolder_address rcv = new ViewHolder_address(layoutView);
        return rcv;

    }


    @Override
    public void onBindViewHolder(final ViewHolder_address holder, final  int position) {
        final FeederInfo_address feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        Log.e("testing","address = "+feederInfo.getTextaddress());


        holder.textname.setText(feederInfo.getTextname());
        holder.textaddress.setText(feederInfo.getTextaddress());
        holder.textlandmark.setText(feederInfo.getTextlandmark());
        holder.textlandmark1.setText(feederInfo.getLandMark1());
        if (feederInfo.getTextmobileno() == null || feederInfo.getTextmobileno().length() == 0 || feederInfo.getTextmobileno().equals("0")) {


        }else {

            holder.textmobileno.setText(feederInfo.getTextmobileno());
        }

        holder.textpincode.setText(feederInfo.getTextpincode());
        holder.impnotice.setText(feederInfo.getImpnotice());


        holder.radiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addressid = feederInfo.getId();
                strmobileno = feederInfo.getTextmobileno();
                strFullAddress = feederInfo.getLandMark1()+feederInfo.getTextlandmark()+feederInfo.getTextaddress();

             //   holder.radiobutton.setChecked(false);
                SharedPreferences prefuserdata = mContext.getSharedPreferences("addressid", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();
                prefeditor.putString("addressid", "" + addressid);
                prefeditor.putString("strmobileno", "" + strmobileno);
                prefeditor.putString("strFullAddress", "" + strFullAddress);

                prefeditor.commit();

                Intent i = new Intent(mContext,Activity_PaymentMode.class);
                mContext.startActivity(i);
            }
        });


        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addressid = feederInfo.getId();

                if (mCallback!=null){
                    mCallback.onClickedItem(position,Integer.parseInt(addressid), 1);
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