package xplotica.littlekites.Fragment_parent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xplotica.littlekites.Adapter_parent.parent_RecyclerViewAdapter_home;
import xplotica.littlekites.FeederInfo_parent.parent_ItemObject_home;
import xplotica.littlekites.R;

/**
 * Created by Sujata on 15-03-2017.
 */
public class fragment_parent_home extends Fragment {

    private GridLayoutManager lLayout;
    Context context;
    RecyclerView rView;
    parent_RecyclerViewAdapter_home rcAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent_home, container, false);

        List<parent_ItemObject_home> rowListItem = getAllItemList();


        lLayout = new GridLayoutManager(context,3);
        rView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        // Back =(ImageView)rootView.findViewById(R.id.Back);

        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        rcAdapter = new parent_RecyclerViewAdapter_home(context, rowListItem);
        rView.setAdapter(rcAdapter);


        return rootView;

    }

    private List<parent_ItemObject_home> getAllItemList() {

        List<parent_ItemObject_home> allItems = new ArrayList<parent_ItemObject_home>();


        allItems.add(new parent_ItemObject_home("Dairy", R.drawable.notification, "#FFFFFF"));
        allItems.add(new parent_ItemObject_home("Home Work", R.drawable.notification, "#FFFFFF"));
        allItems.add(new parent_ItemObject_home("Attendance", R.drawable.notification, "#FFFFFF"));
        allItems.add(new parent_ItemObject_home("Message", R.drawable.notification, "#FFFFFF"));
        allItems.add(new parent_ItemObject_home("Gallery", R.drawable.notification, "#FFFFFF"));

        return allItems;
    }

}
