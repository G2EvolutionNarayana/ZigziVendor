package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

import g2evolution.Boutique.Activity.Activity_productdetails;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Viewholder.ViewHolder_Whishlist_List;
import g2evolution.Boutique.entit.FeederInfo_Whishlist_List;


public class Adapter_Whishlist_List extends RecyclerView.Adapter<ViewHolder_Whishlist_List> {

    private ArrayList<FeederInfo_Whishlist_List> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    String categoryName, postid;
    private Adapter_Whishlist_List.OnItemClick mCallback;

    String _descvalue,stockQuantity;

    public Adapter_Whishlist_List(Context context, ArrayList<FeederInfo_Whishlist_List> feedList, Adapter_Whishlist_List.OnItemClick mCallback){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        this.mCallback = mCallback;

    }


    public void setData( ArrayList<FeederInfo_Whishlist_List> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }



    public interface OnItemClick {
        void onClickedItem(int pos, String qty, int status);
    }
    @Override
    public ViewHolder_Whishlist_List onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_whishlist_list, null);
        ViewHolder_Whishlist_List rcv = new ViewHolder_Whishlist_List(layoutView);
        return rcv;

    }


    @Override
    public void onBindViewHolder(final ViewHolder_Whishlist_List holder, final int position) {
        final FeederInfo_Whishlist_List feederInfo = mListFeeds.get(position);

        String feedDesc = null;

        Log.e("testing","adapetr====="+feederInfo.getElectronicname());
        _descvalue=feederInfo.getDiscountvalue();

        if (feederInfo.getDiscountvalue() == null || feederInfo.getDiscountvalue().trim().length() == 0 || feederInfo.getDiscountvalue().trim().equals("null")){
            holder.pdprice.setVisibility(View.GONE);
            holder.textdiscount.setVisibility(View.GONE);
            holder.discount_price.setVisibility(View.GONE);
        }else if (feederInfo.getDiscountvalue().trim().equals("0")){
            holder.pdprice.setVisibility(View.GONE);
            holder.textdiscount.setVisibility(View.GONE);
            holder.discount_price.setVisibility(View.GONE);
        }else{
            holder.pdprice.setText(feederInfo.getElectronicprice());
            holder. pdprice.setPaintFlags(holder.pdprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder. pdprice.setVisibility(View.VISIBLE);
            holder. textdiscount.setVisibility(View.VISIBLE);
            holder.textdiscount.setText((feederInfo.getDiscountvalue()+"%"));
            holder.discount_price.setVisibility(View.GONE);
        }


       /* stockQuantity=feederInfo.getStockQuantity().trim();
        int foo = Integer.parseInt(stockQuantity);

        if (foo>0){*/


            if (feederInfo.getElectronicimage() == null || feederInfo.getElectronicimage().equals("0")||feederInfo.getElectronicimage().equals("")||feederInfo.getElectronicimage().equals("null")){
                holder.electronicimage.setImageResource(R.drawable.car);
            }else {
                Glide.with(mContext)
                        .load(Uri.parse(feederInfo.getElectronicimage()))
                        // .diskCacheStrategy(DiskCacheStrategy.NONE)
                        //.skipMemoryCache(true)
                        .error(R.drawable.car)
                        .into(holder.electronicimage);

            }

            holder.line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    postid = feederInfo.getProductid();
                    categoryName =  feederInfo.getCategoryname();


                    SharedPreferences prefuserdata = mContext.getSharedPreferences("ProDetails", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("Proid", "" + postid);
                    prefeditor.putString("categoryName", "" + categoryName);

                    Log.e("testing","Proid  = " + postid);
                    Log.e("testing","productid in adapter = "+postid);
                    prefeditor.commit();


                    Intent intent = new Intent(mContext, Activity_productdetails.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable("HashMap", (Serializable) feederInfo.getMapparameters());
                    intent.putExtras(extras);
                    mContext.startActivity(intent);


                }
            });

            holder.imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                       String id = feederInfo.getId();
                        mCallback.onClickedItem(position, id, 1);

                    }
                }
            });
     /*   }else {

            Log.e("testing1","foo=====<<<<<<"+foo);

            holder.electronicimage.setImageResource(R.drawable.outofstock);

            holder.electronicimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Product Is Not Available", Toast.LENGTH_SHORT).show();
                }
            });

        }*/
        holder.electronicname.setText(feederInfo.getElectronicname());
        holder.electronicdetail1.setText(feederInfo.getElectronicdetail1());
        //  holder.electronicdetail2.setText(feederInfo.getElectronicdetail2());
        //   holder.pdsubprice.setText(feederInfo.getElectronicprice());

        if (feederInfo.getDiscountvalue() == null || feederInfo.getDiscountvalue().length() == 0 || feederInfo.getDiscountvalue().equals("NA")){
            final String strrs = mContext.getResources().getString(R.string.Rs);
            holder.pdsubprice.setText(strrs+" "+feederInfo.getAfterdiscount());
        }else{
            // holder.pdprice.setVisibility(View.VISIBLE);
            final String strrs = mContext.getResources().getString(R.string.Rs);
            holder.pdsubprice.setText(strrs+" "+feederInfo.getAfterdiscount());


            //  holder.pdprice.setText(strrs+" "+feederInfo.getElectronicprice());
            //  holder.pdprice.setPaintFlags(holder.pdprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        }

        Log.e("testing","_descvalue"+_descvalue);

        if (_descvalue == null || _descvalue.length() == 0 || _descvalue.equals("NA")){

            holder. textdiscount.setVisibility(View.INVISIBLE);


        }else{

            holder. textdiscount.setVisibility(View.VISIBLE);
            holder.textdiscount.setText((_descvalue+"%"));
        }

        postid = feederInfo.getId();

        Log.e("testing","image in adapter = "+feederInfo.getElectronicimage());






        //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));


        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}