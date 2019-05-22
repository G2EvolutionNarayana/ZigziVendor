package xplotica.littlekites.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import xplotica.littlekites.Adapter.Student_details_Adapter;
import xplotica.littlekites.FeederInfo.Notification_feederInfo;
import xplotica.littlekites.FeederInfo.Student_details_feederInfo;
import xplotica.littlekites.R;

public class Activity_Student_details extends AppCompatActivity {

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<Student_details_feederInfo> allItems1 = new ArrayList<Student_details_feederInfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<Student_details_feederInfo> mListFeederInfo;
    private Notification_feederInfo adapter;
    Student_details_Adapter mAdapterFeeds;
    RecyclerView rView;
    Context context;


    String[]School = new String[]{"DAV School","DAV School","DAV School","DAV School","DAV School","DAV School"};
    String[]Student = new String[]{"Santanu","Santanu","Santanu","Santanu","Santanu","Santanu"};
    String[]Rollno = new String[]{"1","1","1","1","1","1"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        mFeedRecyler = (RecyclerView)findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager((this)));

        //setUpRecycler();
        // context = this;
        //lLayout = new GridLayoutManager(context,2);
        rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        // rView.setLayoutManager(lLayout);
        // mFeedRecyler.setLayoutManager(lLayout);
        //mFeedRecyler.setHasFixedSize(true);

        setUpRecycler();

    }

    private void setUpRecycler(){

        mListFeederInfo =new ArrayList<Student_details_feederInfo>();
        for(int i=0;i<School.length;i++){
            Student_details_feederInfo notification_feederInfo = new Student_details_feederInfo();
            notification_feederInfo.set_school(School[i]);
            notification_feederInfo.set_student(Student[i]);
            notification_feederInfo.set_rollno(Rollno[i]);


            mListFeederInfo.add(notification_feederInfo);
        }
        mAdapterFeeds= new Student_details_Adapter(this,mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);


    }
}
