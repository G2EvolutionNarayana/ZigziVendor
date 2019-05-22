package g2evolution.GMT.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.HashMap;

import g2evolution.GMT.Adapter.Adapter_electronic;
import g2evolution.GMT.FeederInfo.FeederInfo_electronic;
import g2evolution.GMT.R;


/**
 * Created by G2e Android on 23-01-2018.
 */

public class Fragment_electronic extends Fragment {



    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_electronic> allItems1 = new ArrayList<FeederInfo_electronic>();
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_electronic> mListFeederInfo;
    Adapter_electronic mAdapterFeeds;
    RecyclerView rView;


    GridLayoutManager lLayout;

    String []Name =new String[]{"Moto","Moto","Moto"};
    String []details =new String[]{"(Black,32 GB),(3GB RAM)","(Black,32 GB),(3GB RAM)","(Black,32 GB),(3GB RAM)"};
    String []details1 =new String[]{"(3GB RAM)","(3GB RAM)","(3GB RAM)"};
    String []price =new String[]{"50.00","50.00","50.00"};
    Integer[]Image={R.drawable.mob, R.drawable.mob, R.drawable.mob};



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_eletronic, container, false);



        mFeedRecyler = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //setUpRecycler();
        // context = this;
        lLayout = new GridLayoutManager(getActivity(),2);
        rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
         rView.setLayoutManager(lLayout);
         mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);

        setUpRecycler();


        return rootView;
    }

    private void setUpRecycler() {


        mListFeederInfo = new ArrayList<FeederInfo_electronic>();
        for (int i = 0; i < Image .length; i++) {
            FeederInfo_electronic feedInfo = new FeederInfo_electronic();

            feedInfo.setElectronicname(String.valueOf(Image[i]));


            feedInfo.setElectronicname(Name[i]);
            feedInfo.setElectronicdetail1(details[i]);
          //  feedInfo.setElectronicdetail2(details1[i]);
            feedInfo.setElectronicprice(price[i]);


            mListFeederInfo.add(feedInfo);
        }
        mAdapterFeeds = new Adapter_electronic(getActivity(), mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);
    }



}
