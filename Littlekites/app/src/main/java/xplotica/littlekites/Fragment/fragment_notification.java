package xplotica.littlekites.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import xplotica.littlekites.Adapter.Notification_Adapter;
import xplotica.littlekites.FeederInfo.Notification_feederInfo;
import xplotica.littlekites.R;

/**
 * Created by Sujata on 17-03-2017.
 */
public class fragment_notification extends Fragment {

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<Notification_feederInfo> allItems1 = new ArrayList<Notification_feederInfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<Notification_feederInfo> mListFeederInfo;
    private Notification_feederInfo adapter;
    Notification_Adapter mAdapterFeeds;
    RecyclerView rView;
    Context context;


    String[]Name = new String[]{"Class Absent","Class Absent","Class Absent","Class Absent","Class Absent","Class Absent"};

    Integer[] images={R.drawable.notification, R.drawable.notification, R.drawable.notification, R.drawable.notification, R.drawable.notification, R.drawable.notification};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        mFeedRecyler = (RecyclerView)rootView.findViewById(R.id.feedrecycler);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager((getActivity())));

        //setUpRecycler();
        // context = this;
        //lLayout = new GridLayoutManager(context,2);
        rView = (RecyclerView)rootView.findViewById(R.id.feedrecycler);
        rView.setHasFixedSize(true);
        // rView.setLayoutManager(lLayout);
        // mFeedRecyler.setLayoutManager(lLayout);
        //mFeedRecyler.setHasFixedSize(true);

        setUpRecycler();

        return rootView;
    }

    private void setUpRecycler(){

        mListFeederInfo =new ArrayList<Notification_feederInfo>();
        for(int i=0;i<Name.length;i++){
            Notification_feederInfo notification_feederInfo = new Notification_feederInfo();
            notification_feederInfo.setName(Name[i]);
            notification_feederInfo.setPhoto(images[i]);

            mListFeederInfo.add(notification_feederInfo);
        }
        mAdapterFeeds= new Notification_Adapter(getActivity(),mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);


    }
}
