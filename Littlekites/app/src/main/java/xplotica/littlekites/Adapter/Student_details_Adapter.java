package xplotica.littlekites.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xplotica.littlekites.FeederInfo.Student_details_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.Student_details_ViewHolder;


public class Student_details_Adapter extends RecyclerView.Adapter<Student_details_ViewHolder> {

    private ArrayList<Student_details_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;


    public Student_details_Adapter(Context context, ArrayList<Student_details_feederInfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<Student_details_feederInfo> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public Student_details_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_student_details, null);
        Student_details_ViewHolder rcv = new Student_details_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final Student_details_ViewHolder holder, int position) {
        final Student_details_feederInfo feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        holder.student.setText(feederInfo.get_student());
        holder.school.setText(feederInfo.get_school());
        holder.rollno.setText(feederInfo.get_rollno());





        mPreviousPosition = position;

    }


    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


    public interface FeederItemListener {
        public void onFeedClicked(int position, int resid);
    }
}