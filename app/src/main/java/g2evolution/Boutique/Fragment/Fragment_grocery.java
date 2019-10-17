package g2evolution.Boutique.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.HashMap;

import g2evolution.Boutique.Adapter.Adapter_grocery;
import g2evolution.Boutique.FeederInfo.FeederInfo_grocery;
import g2evolution.Boutique.R;


/**
 * Created by G2e Android on 23-01-2018.
 */

public class Fragment_grocery extends Fragment {



    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_grocery> allItems1 = new ArrayList<FeederInfo_grocery>();
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_grocery> mListFeederInfo;
    Adapter_grocery mAdapterFeeds;
    RecyclerView rView;


    GridLayoutManager lLayout;

    String []Name =new String[]{"Moto","Moto","Moto"};
    String []details =new String[]{"(Black,32 GB),(3GB RAM)","(Black,32 GB),(3GB RAM)","(Black,32 GB),(3GB RAM)"};
    String []details1 =new String[]{"(3GB RAM)","(3GB RAM)","(3GB RAM)"};
    String []price =new String[]{"50.00","50.00","50.00"};
    Integer[]Image={R.drawable.acc, R.drawable.acc, R.drawable.acc};



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_grocery, container, false);



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


        mListFeederInfo = new ArrayList<FeederInfo_grocery>();
        for (int i = 0; i < Image .length; i++) {
            FeederInfo_grocery feedInfo = new FeederInfo_grocery();

            feedInfo.setGroimage(String.valueOf(Image[i]));


            feedInfo.setGroname(Name[i]);
            feedInfo.setGrodetail1(details[i]);
          //  feedInfo.setElectronicdetail2(details1[i]);
            feedInfo.setGroprice(price[i]);


            mListFeederInfo.add(feedInfo);
        }
        mAdapterFeeds = new Adapter_grocery(getActivity(), mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);
    }



}
