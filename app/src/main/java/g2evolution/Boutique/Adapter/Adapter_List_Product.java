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

import g2evolution.Boutique.R;
import g2evolution.Boutique.Viewholder.ViewHolder_List_Product;
import g2evolution.Boutique.entit.FeederInfo_List_Product;
import g2evolution.Boutique.Activity.Activity_productdetails;

public class Adapter_List_Product extends RecyclerView.Adapter<ViewHolder_List_Product> {

    private ArrayList<FeederInfo_List_Product> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    String categoryName, postid;

    String _descvalue,stockQuantity;

    public Adapter_List_Product(Context context, ArrayList<FeederInfo_List_Product> feedList){
        mContext = context;
        mListFeeds=feedList;
    }


    public void setData( ArrayList<FeederInfo_List_Product> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_List_Product onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, null);
        ViewHolder_List_Product rcv = new ViewHolder_List_Product(layoutView);
        return rcv;

    }


    @Override
    public void onBindViewHolder(final ViewHolder_List_Product holder, int position) {
        final FeederInfo_List_Product feederInfo = mListFeeds.get(position);

        String feedDesc = null;

        Log.e("testing","adapetr====="+feederInfo.getElectronicname());
        _descvalue=feederInfo.getDiscountvalue();
        if (feederInfo.getElectronicimage() == null || feederInfo.getElectronicimage().equals("0")||feederInfo.getElectronicimage().equals("")||feederInfo.getElectronicimage().equals("null")){
            holder.electronicimage.setImageResource(R.drawable.car);
        }else {
            Glide.with(mContext)
                    .load(Uri.parse(feederInfo.getElectronicimage()))
                    .error(R.drawable.car)
                    .into(holder.electronicimage);

        }

        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                postid = feederInfo.getId();
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

        holder.electronicname.setText(feederInfo.getElectronicname());
        if (feederInfo.getDiscountvalue() == null || feederInfo.getDiscountvalue().length() == 0 || feederInfo.getDiscountvalue().equals("NA")){
            final String strrs = mContext.getResources().getString(R.string.Rs);
            holder.pdsubprice.setText(strrs+" "+feederInfo.getElectronicprice());
        }else{
            final String strrs = mContext.getResources().getString(R.string.Rs);
            holder.pdsubprice.setText(strrs+" "+feederInfo.getAfterdiscount());

        }

        Log.e("testing","_descvalue"+_descvalue);

        postid = feederInfo.getId();

        Log.e("testing","image in adapter = "+feederInfo.getElectronicimage());

        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}