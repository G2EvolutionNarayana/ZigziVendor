package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

import g2evolution.Boutique.Activity.Activity_productdetails;
import g2evolution.Boutique.FeederInfo.FeederInfo_electronic;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Viewholder.ViewHolder_electronic;



public class Adapter_electronic extends RecyclerView.Adapter<ViewHolder_electronic> {

    private ArrayList<FeederInfo_electronic> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    String categoryName, postid;

    String _descvalue;

    public Adapter_electronic(Context context, ArrayList<FeederInfo_electronic> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;


    }

    public void setData( ArrayList<FeederInfo_electronic> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_electronic onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.eletronic_products, null);
        ViewHolder_electronic rcv = new ViewHolder_electronic(layoutView);
        return rcv;

    }


    @Override
    public void onBindViewHolder(final ViewHolder_electronic holder, int position) {
        final FeederInfo_electronic feederInfo = mListFeeds.get(position);

        String feedDesc = null;

        _descvalue=feederInfo.getDiscountvalue();

        holder.electronicname.setText(feederInfo.getElectronicname());
        holder.electronicdetail1.setText(feederInfo.getElectronicdetail1());
      //  holder.electronicdetail2.setText(feederInfo.getElectronicdetail2());
     //   holder.pdsubprice.setText(feederInfo.getElectronicprice());

        if (feederInfo.getDiscountvalue() == null || feederInfo.getDiscountvalue().length() == 0 || feederInfo.getDiscountvalue().equals("NA")){
            final String strrs = mContext.getResources().getString(R.string.Rs);
            holder.pdsubprice.setText(strrs+" "+feederInfo.getAfterdiscount());
        }else{
            holder.pdprice.setVisibility(View.VISIBLE);
            final String strrs = mContext.getResources().getString(R.string.Rs);
            holder.pdsubprice.setText(strrs+" "+feederInfo.getAfterdiscount());


            holder.pdprice.setText(strrs+" "+feederInfo.getElectronicprice());
            holder.pdprice.setPaintFlags(holder.pdprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

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

holder.electronicimage.setOnClickListener(new View.OnClickListener() {
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
        mContext.startActivity(intent);


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