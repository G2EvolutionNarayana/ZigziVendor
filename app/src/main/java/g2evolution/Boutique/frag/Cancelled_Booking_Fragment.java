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
import g2evolution.Boutique.Adap.Cancelled_Orders_Adapter;
import g2evolution.Boutique.R;
import g2evolution.Boutique.entit.Cancelled_Orders_List;


public class Cancelled_Booking_Fragment extends Fragment implements Cancelled_Orders_Adapter.OnItemlevelsinside {




    Cancelled_Orders_Adapter.OnItemlevelsinside mCallback2;
    private ArrayList<Cancelled_Orders_List> booking_lists = new ArrayList<Cancelled_Orders_List>();
    Cancelled_Orders_Adapter cancelled_orders_adapter;


    Integer[]Image= new Integer[]{R.drawable.splash_logo,   R.drawable.splash_logo,  R.drawable.splash_logo,R.drawable.splash_logo};
    String[]Name= new String[]{"Henry",   "Jhon",  "Zakir","Kumar"};
    String[]Order_Number= new String[]{"OD1154",   "OD1157",  "OD1158","OD115476"};
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
        View rootview= inflater.inflate(R.layout.fragment_butcancelled, container, false);

        ButterKnife.bind(this, rootview);

        mCallback2=this;


        new_booking_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        new_booking_recycler.setLayoutManager(mLayoutManager);

        setUpReccyler2();

        return rootview;

    }



    private void setUpReccyler2() {
        booking_lists =new ArrayList<Cancelled_Orders_List>();

        for(int i=0;i<Name.length;i++){
            Cancelled_Orders_List feedInfo = new Cancelled_Orders_List();
            feedInfo.setName(Name[i]);
            feedInfo.setOrder_number(Order_Number[i]);
            feedInfo.setOrder_date(Order_Date[i]);
            feedInfo.setDelivery_date(Delivery_Date[i]);
            feedInfo.setImage(Image[i]);

            booking_lists.add(feedInfo);
        }
        cancelled_orders_adapter = new Cancelled_Orders_Adapter(getActivity(),booking_lists, mCallback2);
        new_booking_recycler.setAdapter(cancelled_orders_adapter);
    }


    @Override
    public void OnItemlevelsinside(int pos, String qty, int status) {

    }
}
