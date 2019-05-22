package g2evolution.GMT.Adapter;

/**
 * Created by G2evolution on 5/20/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;

import g2evolution.GMT.FeederInfo.SingleItemModel_SUBCATEGORY;
import g2evolution.GMT.Fragment.Fragment_Offer_Products;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.JSONParser;


public class SectionListDataAdapter_subcategory extends RecyclerView.Adapter<SectionListDataAdapter_subcategory.SingleItemRowHolder> {

    private ArrayList<SingleItemModel_SUBCATEGORY> itemsList;
    private Context mContext;

    JSONObject json;
    JSONParser jsonParser = new JSONParser();
    ArrayList<String> images = new ArrayList<String>();


    String categoryName,postid,subcategoryname;

    public SectionListDataAdapter_subcategory(Context context, ArrayList<SingleItemModel_SUBCATEGORY> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card_subcategory, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, int i) {

        final SingleItemModel_SUBCATEGORY singleItem = itemsList.get(i);


        holder.tvTitle.setText(singleItem.getName());

       // holder.tvTitle.setTextColor(Color.parseColor("#FFFFFF"));

      // holder.itemImage.setBackgroundResource(R.drawable.check3g);

        Log.e("testing","Name in adapter ===== "+singleItem.getName());
        Log.e("testing","Image in adapter ====== "+singleItem.getUrl());
        Log.e("testing","Image in adapter ====== "+singleItem.getDescription());



holder.tvTitle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

       // holder.tvTitle.setTextColor(Color.parseColor("#000000"));
       // holder.itemImage.setBackgroundResource(R.drawable.check3b);

        postid=singleItem.getUrl();
        categoryName=singleItem.getName();
        SharedPreferences prefuserdata = mContext.getSharedPreferences("category", 0);
        SharedPreferences.Editor prefeditor = prefuserdata.edit();
        prefeditor.putString("Proid", "" + postid);
        prefeditor.putString("categoryName", "" + categoryName);

        prefeditor.commit();


        Intent i = new Intent(mContext, Fragment_Offer_Products.class);
        mContext.startActivity(i);

    }
});

       /* holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postid =  singleItem.getName();
                categoryName =  singleItem.getDescription();

                Log.e("testing","testing image = ");

                  SharedPreferences prefuserdata = mContext.getSharedPreferences("ProDetails", 0);
                  SharedPreferences.Editor prefeditor = prefuserdata.edit();
                  prefeditor.putString("Proid", "" + postid);
                  prefeditor.putString("categoryName", "" + categoryName);
                 prefeditor.putString("subcategoryname", "" + subcategoryname);
                  Log.e("testing","Proid  = " + postid);

                  Log.e("testing","productid in adapter = "+postid);
                  prefeditor.commit();

                Intent i = new Intent(mContext, Fragment_Offer_Products.class);
                mContext.startActivity(i);
                // new userdata().execute();

            }
        });

*/

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;

        protected ImageView itemImage;




        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);


        }

    }





}