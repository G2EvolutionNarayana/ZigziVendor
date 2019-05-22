package g2evolution.GMT.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import g2evolution.GMT.Activity.Activity_productdetails;
import g2evolution.GMT.FeederInfo.FeederInfo_Product_List;
import g2evolution.GMT.R;
import g2evolution.GMT.Viewholder.ViewHolder_Product_List;


public class Adapter_Product_List extends RecyclerView.Adapter<ViewHolder_Product_List> {

    private ArrayList<FeederInfo_Product_List> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    String categoryName, postid;
    String _descvalue,stockQuantity;


    public Adapter_Product_List(Context context, ArrayList<FeederInfo_Product_List> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;

    }

    public void setData( ArrayList<FeederInfo_Product_List> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_Product_List onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, null);
        ViewHolder_Product_List rcv = new ViewHolder_Product_List(layoutView);
        return rcv;

    }


    @Override
    public void onBindViewHolder(final ViewHolder_Product_List holder, int position) {
        final FeederInfo_Product_List feederInfo = mListFeeds.get(position);

        String feedDesc = null;

        _descvalue = feederInfo.getDiscountvalue();

        stockQuantity = feederInfo.getStockQuantity().trim();


            int foo = Integer.parseInt(stockQuantity);

            if (foo > 0) {


                holder.textView1.setVisibility(View.GONE);
                if (feederInfo.getElectronicimage() == null || feederInfo.getElectronicimage().equals("0") || feederInfo.getElectronicimage().equals("") || feederInfo.getElectronicimage().equals("null")) {
                    holder.electronicimage.setImageResource(R.drawable.car);

                } else {

                    Glide.with(mContext)
                            .load(Uri.parse(feederInfo.getElectronicimage()))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .error(R.drawable.car)
                            .into(holder.electronicimage);


                }

                holder.line.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        postid = feederInfo.getId();
                        categoryName = feederInfo.getCategoryname();


                        SharedPreferences prefuserdata = mContext.getSharedPreferences("ProDetails", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("Proid", "" + postid);
                        prefeditor.putString("categoryName", "" + categoryName);


                        prefeditor.commit();


                        Intent intent = new Intent(mContext, Activity_productdetails.class);
                        mContext.startActivity(intent);


                    }
                });

            } else {


                holder.line.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(mContext, "Product Is Not Available", Toast.LENGTH_SHORT).show();

                    }
                });

                //  holder.electronicimage.setImageResource(R.drawable.outofstock);

                Glide.with(mContext)
                        .load(Uri.parse(feederInfo.getElectronicimage()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.car)
                        .into(holder.electronicimage);


                holder.textView1.setVisibility(View.VISIBLE);
                holder.textView1.setText("Out Of Stock");
                holder.textView1.setTextColor(Color.WHITE);
                //     holder.textView1.setTe(Color.WHITE);

          /*  holder.electronicimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Product Is Not Available", Toast.LENGTH_SHORT).show();
                }
            });*/

            }
            holder.electronicname.setText(feederInfo.getElectronicname());
            holder.electronicdetail1.setText(feederInfo.getElectronicdetail1());
            //  holder.electronicdetail2.setText(feederInfo.getElectronicdetail2());
            //   holder.pdsubprice.setText(feederInfo.getElectronicprice());

            if (feederInfo.getDiscountvalue() == null || feederInfo.getDiscountvalue().length() == 0 || feederInfo.getDiscountvalue().equals("NA")) {
                final String strrs = mContext.getResources().getString(R.string.Rs);
                holder.pdsubprice.setText(strrs + " " + feederInfo.getAfterdiscount());
            } else {
                holder.pdprice.setVisibility(View.VISIBLE);
                final String strrs = mContext.getResources().getString(R.string.Rs);
                holder.pdsubprice.setText(strrs + " " + feederInfo.getAfterdiscount());


                holder.pdprice.setText(strrs + " " + feederInfo.getElectronicprice());
                holder.pdprice.setPaintFlags(holder.pdprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }

            if (_descvalue == null || _descvalue.length() == 0 || _descvalue.equals("NA")) {

                holder.textdiscount.setVisibility(View.INVISIBLE);


            } else {

                holder.textdiscount.setVisibility(View.VISIBLE);
                holder.textdiscount.setText((_descvalue + "%"));
            }

            postid = feederInfo.getId();

            //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));


            mPreviousPosition = position;


    }
    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}