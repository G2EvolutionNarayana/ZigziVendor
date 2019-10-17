package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

import g2evolution.Boutique.FeederInfo.FeederInfo_grocery;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Viewholder.ViewHolder_grocery;


public class Adapter_grocery extends RecyclerView.Adapter<ViewHolder_grocery> {

    private ArrayList<FeederInfo_grocery> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    String qty;

    String subcatgoryid;

    public Adapter_grocery(Context context, ArrayList<FeederInfo_grocery> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;


    }

    public void setData( ArrayList<FeederInfo_grocery> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_grocery onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery, null);
        ViewHolder_grocery rcv = new ViewHolder_grocery(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final ViewHolder_grocery holder, int position) {
        final FeederInfo_grocery feederInfo = mListFeeds.get(position);


        String feedDesc = null;



        holder.groname.setText(feederInfo.getGroname());
        holder.grodetail1.setText(feederInfo.getGrodetail1());
      //  holder.electronicdetail2.setText(feederInfo.getElectronicdetail2());
        holder.groprice.setText(feederInfo.getGroprice());


        Glide.with(mContext)
                .load("http://"+ Uri.parse(String.valueOf(feederInfo.getGroimage())))
                // .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.grocery)
                .placeholder(R.drawable.grocery)
                //  .skipMemoryCache(true)
                .into(holder.groimage);







        //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));


        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}