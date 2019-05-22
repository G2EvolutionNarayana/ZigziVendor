package g2evolution.GMT.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import g2evolution.GMT.FeederInfo.FeederInfo_Subcategory_list;
import g2evolution.GMT.R;
import g2evolution.GMT.Viewholder.ViewHolder_Subcategory_list;

public class Adapter_Subcategory_list extends RecyclerView.Adapter<ViewHolder_Subcategory_list> {

    private ArrayList<FeederInfo_Subcategory_list> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;

    private Adapter_Subcategory_list.OnItemClick mCallback;
    String subcatid;

    Integer introwposition;
    String subcategoryrowid;

    public Adapter_Subcategory_list(Context context, ArrayList<FeederInfo_Subcategory_list> feedList,Adapter_Subcategory_list.OnItemClick listener){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;

        this.mCallback = listener;

        SharedPreferences prefuserdata = mContext.getSharedPreferences("introwposition", 0);
        subcategoryrowid = prefuserdata.getString("introwposition", "");

        if (introwposition==null||introwposition.toString().trim().length()==0||introwposition.equals("")){

        }else {
            introwposition = Integer.parseInt(subcategoryrowid);
        }


    }

    public interface OnItemClick {
        void onClickedItem(int pos, int qty, int status);
    }
    public void setData( ArrayList<FeederInfo_Subcategory_list> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_Subcategory_list onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory_lists, null);
        ViewHolder_Subcategory_list rcv = new ViewHolder_Subcategory_list(layoutView);
        return rcv;

    }


    @Override
    public void onBindViewHolder(final ViewHolder_Subcategory_list holder,final int position) {
        final FeederInfo_Subcategory_list feederInfo = mListFeeds.get(position);

        String feedDesc = null;

        subcatid=feederInfo.getId();

        holder.subcatname.setText(feederInfo.getName());

        holder.subcatname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                introwposition = position;
                subcatid=feederInfo.getId();
                Log.e("testing","subcatid==="+subcatid);

                if (mCallback!=null){
                    mCallback.onClickedItem(position,Integer.parseInt(subcatid), 1);
                }
                notifyDataSetChanged();
            }
        });

        if (feederInfo.getImage() == null || feederInfo.getImage().equals("0")||feederInfo.getImage().equals("")||feederInfo.getImage().equals("null")){

            holder.electronicimage.setImageResource(R.drawable.car);

        }else {

            Glide.with(mContext)
                    .load(Uri.parse(feederInfo.getImage()))
                    // .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .error(R.drawable.car)
                    .into(holder.electronicimage);

        }

        Log.e("testing33","introwposition in adapter = "+introwposition);

        if (introwposition == null){


        }else if(introwposition==position){

            holder.card_view.setCardBackgroundColor(Color.parseColor("#ffffff"));

        }else{

            holder.card_view.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
        }

        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}