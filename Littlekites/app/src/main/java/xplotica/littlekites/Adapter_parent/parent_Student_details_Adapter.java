package xplotica.littlekites.Adapter_parent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.Activity_parent.Activity_Parent_Home;
import xplotica.littlekites.FeederInfo_parent.parent_Student_details_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder_parent.parent_Student_details_ViewHolder;


public class parent_Student_details_Adapter extends RecyclerView.Adapter<parent_Student_details_ViewHolder> {



    private ArrayList<parent_Student_details_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;

    String Pid,name,company,price,photo;
    String rating;


    String School_id,Student_id,School_name,ClassId,SectionId,mobile,logo,filepath;



    public parent_Student_details_Adapter(Context context, ArrayList<parent_Student_details_feederInfo> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;


    }

    public void setData( ArrayList<parent_Student_details_feederInfo> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public parent_Student_details_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_parent_student_details, null);
        parent_Student_details_ViewHolder rcv = new parent_Student_details_ViewHolder(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final parent_Student_details_ViewHolder holder, int position) {
        final parent_Student_details_feederInfo feederInfo = mListFeeds.get(position);


        String feedDesc = null;


        holder.student.setText(feederInfo.get_student());
        holder.school.setText(feederInfo.get_school());
        holder.rollno.setText( feederInfo.get_rollno());
        //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));



        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* SharedPreferences prefuserdata = mContext.getSharedPreferences("registerUser", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();

                prefeditor.putString("schoolid", "" + School_id);
                prefeditor.putString("studentid", "" + Student_id);
                prefeditor.putString("schoolname", "" + School_name);
                prefeditor.putString("classid", "" + ClassId);
                prefeditor.putString("sectionid", "" + SectionId);
                prefeditor.putString("mobile", "" + mobile);
                prefeditor.putString("logo", "" + logo);
                prefeditor.putString("filepath", "" + filepath);
                prefeditor.putString("type", "" + "2");


                prefeditor.commit();
*/
                Intent intent = new Intent(mContext, Activity_Parent_Home.class);
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

