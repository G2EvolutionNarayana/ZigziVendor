package g2evolution.Boutique.Adapter;

/**
 * Created by G2evolution on 5/20/2017.
 */

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import g2evolution.Boutique.FeederInfo.SectionDataModel;
import g2evolution.Boutique.R;


public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    String catid,category,subcategoryname;


    private RecyclerViewDataAdapter.OnItemClick mCallback;
    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList, RecyclerViewDataAdapter.OnItemClick listener) {
        this.dataList = dataList;
        this.mContext = context;
        this.mCallback = listener;
    }
    public interface OnItemClick {
        void onClickedItem(int pos, String category, int status);
    }
    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, final int i) {

        final String sectionid = dataList.get(i).getHeaderid();
        final String sectionName = dataList.get(i).getHeaderTitle();

        Log.e("testing","gethearderid = "+dataList.get(i).getHeaderid());
        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;

        protected RecyclerView recycler_view_list;

        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
           // this.btnMore= (Button) view.findViewById(R.id.btnMore);


        }

    }

}