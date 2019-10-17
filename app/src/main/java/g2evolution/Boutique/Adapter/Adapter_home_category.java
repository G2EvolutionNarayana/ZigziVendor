package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import g2evolution.Boutique.Activity.Product_List;
import g2evolution.Boutique.FeederInfo.FeederInfo_home_category;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Viewholder.ViewHolder_home_category;


public class Adapter_home_category extends RecyclerView.Adapter<ViewHolder_home_category> {


    private ArrayList<FeederInfo_home_category> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    String categoryName, postid,subcatid;



    public Adapter_home_category(Context context, ArrayList<FeederInfo_home_category> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;

    }

    public void setData( ArrayList<FeederInfo_home_category> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();

    }


    @Override
    public ViewHolder_home_category onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_category, parent,false);
        ViewHolder_home_category rcv = new ViewHolder_home_category(layoutView);
        return rcv;

    }

    @Override
    public void onBindViewHolder(final ViewHolder_home_category holder, int position) {
        final FeederInfo_home_category feederInfo = mListFeeds.get(position);

        String feedDesc = null;

        holder.electronicname.setText(feederInfo.getCategoryname());

        subcatid=feederInfo.getSubcateid();
        postid = feederInfo.getId();



/*
        if (feederInfo.getElectronicimage() == null || feederInfo.getElectronicimage().equals("0")||feederInfo.getElectronicimage().equals("")||feederInfo.getElectronicimage().equals("null")){
            holder.electronicimage.setImageResource(R.drawable.car);
        }else {
            Glide.with(mContext)
                    .load(Uri.parse("https://www.google.co.in/search?q=grocery&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiNoPTRndXaAhUHvY8KHTC4AmYQ_AUICygC&biw=1600&bih=745#imgrc=Tr-xJ1rUTKzo4M:"))
                    // .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .error(R.drawable.car)
                    .into(holder.electronicimage);

        }*/

   //     holder.electronicimage.setImageResource(R.drawable.grocery_new);

        Glide.with(mContext)
                .load(Uri.parse(feederInfo.getCategoryimage()))
                // .diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                .error(R.drawable.car)
                .into(holder.electronicimage);

      /*  if (feederInfo.getElectronicimage() == null || feederInfo.getElectronicimage().equals("0")||feederInfo.getElectronicimage().equals("")||feederInfo.getElectronicimage().equals("null")){
            holder.electronicimage.setImageResource(R.drawable.car);
        }else {

        }
*/

  holder.linear.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

       postid = feederInfo.getId();
       categoryName =  feederInfo.getCategoryname();
        subcatid=feederInfo.getSubcateid();

        SharedPreferences prefuserdata = mContext.getSharedPreferences("ProDetails", 0);
        SharedPreferences.Editor prefeditor = prefuserdata.edit();

        prefeditor.putString("category", "" + postid);
        prefeditor.putString("categoryname", "" + categoryName);
        prefeditor.putString("subcategoryname", "" + subcatid);
        prefeditor.commit();


     /*   SharedPreferences prefuserdata = mContext.getSharedPreferences("category", 0);
        SharedPreferences.Editor prefeditor = prefuserdata.edit();
        prefeditor.putString("category", "" + postid);
        prefeditor.putString("categoryname", "" + categoryName);

        prefeditor.commit();*/

        Log.e("testing","subcatid===adapter _home"+subcatid);
        Log.e("testing","postid===adapter _home"+postid);


        Intent intent = new Intent(mContext, Product_List.class);
        mContext.startActivity(intent);


    }
});


     mPreviousPosition = position;


    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }

}