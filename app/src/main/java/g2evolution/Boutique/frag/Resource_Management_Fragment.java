package g2evolution.Boutique.frag;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import g2evolution.Boutique.Adapter.Adapter_resourse;
import g2evolution.Boutique.Fragment.Fragment_Offer_Products;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entity_Resourse;


public class Resource_Management_Fragment extends Fragment implements Adapter_resourse.OnItemClickcropNames {




    private static Context context = null;
    public  static RecyclerView dashboard_reccyler;
    public  static
    Adapter_resourse.OnItemClickcropNames mCallbackcropnames;
    public  static ArrayList<Entity_Resourse> openings_entity=new ArrayList<Entity_Resourse>();
    public  static Adapter_resourse openings_adapter;

    String[] TitleName=new String[]{"Silver",
            "Gold",
            "Platinum"};
    String[] Titlecount=new String[]{"0/20",
            "0/50",
            "0/100"};
    String[] Titledesc=new String[]{"In this plan you can download 20 Resume",
            "In this plan you can download 50 Resume",
            "In this plan you can download 100 Resume"};

    String[] TitlePrice=new String[]{"100",
            "300",
            "700"};

    public  static JSONParser jsonParser = new JSONParser();
    public  static   String message;
    public  static   String status;
    public  static   String response;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_license, container, false);

        dashboard_reccyler=(RecyclerView)rootview.findViewById(R.id.dashboard_reccyler);
        dashboard_reccyler.setHasFixedSize(true);
        dashboard_reccyler.setLayoutManager(new LinearLayoutManager(getActivity()));
       // GridLayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(),2);
      //  dashboard_reccyler.setLayoutManager(mLayoutManager1);


        setUpReccyler();

        return rootview;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void OnItemClickcropNames(int pos, String qty, int status) {

    }




    private void setUpReccyler() {
        openings_entity = new ArrayList<Entity_Resourse>();

        for (int i = 0; i < TitleName.length; i++) {
            Entity_Resourse feedInfo = new Entity_Resourse();
            // feedInfo.setImages(productImage[i]);
            feedInfo.setTitlename(TitleName[i]);
            feedInfo.setTextcount(Titlecount[i]);
            feedInfo.setTextdesc(Titledesc[i]);
            feedInfo.setTextprice(TitlePrice[i]);

            openings_entity.add(feedInfo);
        }
        openings_adapter = new Adapter_resourse(getActivity(), openings_entity, mCallbackcropnames);
        dashboard_reccyler.setAdapter(openings_adapter);
    }



}
