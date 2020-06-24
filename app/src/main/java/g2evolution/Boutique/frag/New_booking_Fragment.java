package g2evolution.Boutique.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import g2evolution.Boutique.Adap.New_Booking_Adapter;
import g2evolution.Boutique.R;
import g2evolution.Boutique.entit.New_Booking_List;


public class New_booking_Fragment extends Fragment implements New_Booking_Adapter.OnItemlevelsinside{



    New_Booking_Adapter.OnItemlevelsinside mCallback2;
    private ArrayList<New_Booking_List> booking_lists = new ArrayList<New_Booking_List>();
    New_Booking_Adapter new_booking_adapter;

    Integer[]Image= new Integer[]{R.drawable.design1,   R.drawable.design2,  R.drawable.design3,R.drawable.design4};
    String[]Name= new String[]{"Henry",   "Jhon",  "Zakir","Kumar"};
    String[]Order_Number= new String[]{" OD1154",   "OD1157",  "OD1158","OD115476"};
    String[]Order_Date= new String[]{"4/5/2019",   "8/5/2080",  "6/6/2019","9/6/2019"};
    String[] Delivery_Date= new String[]{"20/5/2019",   "25/5/2080",  "23/6/2019","20/6/2019"};


    @BindView(R.id.new_booking_recycler)
    RecyclerView new_booking_recycler;
    @BindView(R.id.no_image)
    ImageView no_image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_new_booking, container, false);
        ButterKnife.bind(this, rootview);

        mCallback2=this;


        new_booking_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        new_booking_recycler.setLayoutManager(mLayoutManager);

        setUpReccyler2();

        return  rootview;
    }



    private void setUpReccyler2() {
        booking_lists =new ArrayList<New_Booking_List>();

        for(int i=0;i<Name.length;i++){
            New_Booking_List feedInfo = new New_Booking_List();
            feedInfo.setName(Name[i]);
            feedInfo.setOrder_number(Order_Number[i]);
            feedInfo.setOrder_date(Order_Date[i]);
            feedInfo.setDelivery_date(Delivery_Date[i]);
            feedInfo.setImage(Image[i]);

            booking_lists.add(feedInfo);
        }
        new_booking_adapter = new New_Booking_Adapter(getActivity(),booking_lists, mCallback2);
        new_booking_recycler.setAdapter(new_booking_adapter);
    }
    @Override
    public void OnItemlevelsinside(int pos, String qty, int status) {

    }
}
