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

import g2evolution.Boutique.Adapter.Adapter_photo;
import g2evolution.Boutique.FeederInfo.FeederInfo_photo;
import g2evolution.Boutique.R;


/**
 * Created by G2e Android on 23-01-2018.
 */

public class Fragment_photo extends Fragment {



    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_photo> allItems1 = new ArrayList<FeederInfo_photo>();
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_photo> mListFeederInfo;
    Adapter_photo mAdapterFeeds;
    RecyclerView rView;


    GridLayoutManager lLayout;

    String []Name =new String[]{"Moto","Moto","Moto"};
    String []details =new String[]{"(Black,32 GB),(3GB RAM)","(Black,32 GB),(3GB RAM)","(Black,32 GB),(3GB RAM)"};
    String []details1 =new String[]{"(3GB RAM)","(3GB RAM)","(3GB RAM)"};
    String []price =new String[]{"50.00","50.00","50.00"};
    Integer[]Image={R.drawable.acc, R.drawable.acc, R.drawable.acc};



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);



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


        mListFeederInfo = new ArrayList<FeederInfo_photo>();
        for (int i = 0; i < Image .length; i++) {
            FeederInfo_photo feedInfo = new FeederInfo_photo();

            feedInfo.setPhoimage(String.valueOf(Image[i]));


            feedInfo.setPhoname(Name[i]);
            feedInfo.setPhodetail1(details[i]);
          //  feedInfo.setElectronicdetail2(details1[i]);
            feedInfo.setPhoprice(price[i]);


            mListFeederInfo.add(feedInfo);
        }
        mAdapterFeeds = new Adapter_photo(getActivity(), mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);
    }



}
