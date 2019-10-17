package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

import g2evolution.Boutique.FeederInfo.FeederInfo_photo;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Viewholder.ViewHolder_photo;


public class Adapter_photo extends RecyclerView.Adapter<ViewHolder_photo> {

    private ArrayList<FeederInfo_photo> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    String qty;

    String subcatgoryid;

    public Adapter_photo(Context context, ArrayList<FeederInfo_photo> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;


    }

    public void setData( ArrayList<FeederInfo_photo> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_photo onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo, null);
        ViewHolder_photo rcv = new ViewHolder_photo(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final ViewHolder_photo holder, int position) {
        final FeederInfo_photo feederInfo = mListFeeds.get(position);


        String feedDesc = null;



        holder.phoname.setText(feederInfo.getPhoname());
        holder.phodetail1.setText(feederInfo.getPhodetail1());
      //  holder.electronicdetail2.setText(feederInfo.getElectronicdetail2());
        holder.phoprice.setText(feederInfo.getPhoprice());


        Glide.with(mContext)
                .load("http://"+ Uri.parse(String.valueOf(feederInfo.getPhoimage())))
                // .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.photo)
                .placeholder(R.drawable.photo)
                //  .skipMemoryCache(true)
                .into(holder.phoimage);







        //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));


        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}