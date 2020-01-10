package g2evolution.Boutique.Adapter;

/**
 * Created by G2evolution on 5/20/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;

import g2evolution.Boutique.Activity.Activity_ListProduct;
import g2evolution.Boutique.Activity.Activity_productdetails;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entoty_CategorySingleItemModel;


public class Adapter_SectionListDatacategory extends RecyclerView.Adapter<Adapter_SectionListDatacategory.SingleItemRowHolder> {

    private ArrayList<Entoty_CategorySingleItemModel> itemsList;
    private Context mContext;

    JSONObject json;
    JSONParser jsonParser = new JSONParser();
    ArrayList<String> images = new ArrayList<String>();


    String categoryName, postid,_descvalue,subcategoryname;

    public Adapter_SectionListDatacategory(Context context, ArrayList<Entoty_CategorySingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_subcategory, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        final Entoty_CategorySingleItemModel singleItem = itemsList.get(i);

        _descvalue=singleItem.getDiscvalue();
     // String subcatnme   =singleItem.getSubcatname();
      String StockQuantity   =singleItem.getStockQuantity();


      Log.e("testing","testing+StockQuantity==="+StockQuantity);

        int foo = Integer.parseInt(StockQuantity);

        if (foo>0){

            if (singleItem.getUrl() == null || singleItem.getUrl().equals("0")||singleItem.getUrl().equals("")||singleItem.getUrl().equals("null")){

                holder.itemImage.setImageResource(R.drawable.car);

            }else{

                Glide.with(mContext)
                        .load(Uri.parse(singleItem.getUrl()))
                        // .diskCacheStrategy(DiskCacheStrategy.NONE)
                        //.skipMemoryCache(true)
                        .error(R.drawable.car)
                        .into(holder.itemImage);


            }

            holder.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    postid =  singleItem.getId();
                    categoryName =  singleItem.getCategoryName();

                    Log.e("testing","testing image = "+singleItem.getId());

                    SharedPreferences prefuserdata = mContext.getSharedPreferences("ProDetails", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("Proid", "" + postid);
                    prefeditor.putString("categoryName", "" + categoryName);

                    prefeditor.commit();

             /*   SharedPreferences prefuserdata = mContext.getSharedPreferences("category", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();
                prefeditor.putString("Proid", "" + postid);
                prefeditor.putString("categoryName", "" + categoryName);
                prefeditor.putString("subcategoryname", "" + subcategoryname);
                Log.e("testing","Proid  = " + postid);

                Log.e("testing","productid in adapter = "+postid);
                prefeditor.commit();
*/

             if (singleItem.getStrType().equals("productcategory")){
                 Intent intent = new Intent(mContext, Activity_productdetails.class);
                 mContext.startActivity(intent);
             }else if (singleItem.getStrType().equals("childcategory")){
                 Intent i = new Intent(mContext, Activity_ListProduct.class);
                 mContext.startActivity(i);
             }else{

             }

                    // new userdata().execute();

                }
            });

            Log.e("testing1","foo====>>>>>>>>="+foo);



        }else {

            Log.e("testing1","foo=====<<<<<<"+foo);

            holder.itemImage.setImageResource(R.drawable.outofstock);

            holder.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(mContext, "Product Is Not Available", Toast.LENGTH_SHORT).show();
                }
            });
        }

if (singleItem.getName()==null||singleItem.getName().length()==0||singleItem.getName().equals("0")){

    holder.tvTitle.setText("No Brand Name");
}else {

    holder.tvTitle.setText(singleItem.getName());
}



        Log.e("testing","Name in adapter = "+singleItem.getName());
        Log.e("testing","Image in adapter = "+singleItem.getUrl());
       /* Glide.with(mContext)
                .load(singleItem.getUrl())
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
               // .centerCrop()
               // .error(R.drawable.car)
                .into(holder.itemImage);*/
      /*  Picasso.with(mContext)
                .load("http://androidappfirst.com/b2b/app/images/8f9b8cf90aefe22bf2071a0dea497325.jpg")
                .placeholder(R.drawable.car) //this is optional the image to display while the url image is downloading
                .error(R.drawable.car)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.itemImage);*/




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


/*

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();

                }
            });

*/

        }

    }





}