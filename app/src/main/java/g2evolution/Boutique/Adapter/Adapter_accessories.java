package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import g2evolution.Boutique.FeederInfo.FeederInfo_accessories;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Viewholder.ViewHolder_accessories;


public class Adapter_accessories extends RecyclerView.Adapter<ViewHolder_accessories> {

    private ArrayList<FeederInfo_accessories> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    String qty;

    String subcatgoryid;

    public Adapter_accessories(Context context, ArrayList<FeederInfo_accessories> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;


    }

    public void setData( ArrayList<FeederInfo_accessories> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_accessories onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.accessories, null);
        ViewHolder_accessories rcv = new ViewHolder_accessories(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final ViewHolder_accessories holder, int position) {
        final FeederInfo_accessories feederInfo = mListFeeds.get(position);


        String feedDesc = null;



        holder.accname.setText(feederInfo.getAccname());
        holder.accdetail1.setText(feederInfo.getAccdetail1());
      //  holder.electronicdetail2.setText(feederInfo.getElectronicdetail2());
        holder.accprice.setText(feederInfo.getAccprice());


        Glide.with(mContext)
                .load("http://"+ Uri.parse(String.valueOf(feederInfo.getAccimage())))
                // .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.acc)
                .placeholder(R.drawable.acc)
                //  .skipMemoryCache(true)
                .into(holder.accimage);







        //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));


        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}