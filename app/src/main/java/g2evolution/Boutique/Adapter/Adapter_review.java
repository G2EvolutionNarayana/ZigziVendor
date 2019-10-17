package g2evolution.Boutique.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import g2evolution.Boutique.FeederInfo.FeederInfo_review;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Viewholder.ViewHolder_review;


public class Adapter_review extends RecyclerView.Adapter<ViewHolder_review> {

    private ArrayList<FeederInfo_review> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    String qty;
    Date date;
    String subcatgoryid;

    public Adapter_review(Context context, ArrayList<FeederInfo_review> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;


    }

    public void setData( ArrayList<FeederInfo_review> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_review onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewdetails, null);
        ViewHolder_review rcv = new ViewHolder_review(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final ViewHolder_review holder, int position) {
        final FeederInfo_review feederInfo = mListFeeds.get(position);


        int daysAgo = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
        try {
            date = format.parse(feederInfo.getTexton());


            System.out.println(date);
        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }


        String feedDesc = null;



        holder.textrating.setText(feederInfo.getTextrating());
        holder.texttitle.setText(feederInfo.getTexttitle());
        holder.textdesc.setText(feederInfo.getTextdesc());


        String dayOfTheWeek = (String) DateFormat.format("E", date); // Thursday
        String day          = (String) DateFormat.format("dd",   date); // 20
        String monthString  = (String) DateFormat.format("MMM",  date); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
        String year         = (String) DateFormat.format("yyyy", date); // 2013

        Log.e("testing","dayoftheWeek"+dayOfTheWeek);
        Log.e("testing","dayoftheWeek"+day);
        Log.e("testing","dayoftheWeek"+monthString);
        Log.e("testing","dayoftheWeek"+monthNumber);
        Log.e("testing","dayoftheWeek"+year);

        holder.textname.setText(feederInfo.getName()+" ");

        holder.textdetails.setText(day+" "+monthString+", "+year);



        //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));


        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}