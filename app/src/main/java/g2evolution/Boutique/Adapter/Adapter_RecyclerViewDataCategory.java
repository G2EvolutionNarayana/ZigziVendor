package g2evolution.Boutique.Adapter;

/**
 * Created by G2evolution on 5/20/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import g2evolution.Boutique.R;
import g2evolution.Boutique.entit.Entity_CategorySimilarDataModel;


public class Adapter_RecyclerViewDataCategory extends RecyclerView.Adapter<Adapter_RecyclerViewDataCategory.ItemRowHolder> {

    private ArrayList<Entity_CategorySimilarDataModel> dataList;
    private Context mContext;
    String catid,category,subcategoryname;


    private Adapter_RecyclerViewDataCategory.OnItemClick mCallback;
    public Adapter_RecyclerViewDataCategory(Context context, ArrayList<Entity_CategorySimilarDataModel> dataList, Adapter_RecyclerViewDataCategory.OnItemClick listener) {
        this.dataList = dataList;
        this.mContext = context;
        this.mCallback = listener;
    }
    public interface OnItemClick {
        void onClickedItem(int pos, String category, int status);
    }
    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_datacategory, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, final int i) {

        final String sectionid = dataList.get(i).getHeaderid();
        final String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

        Adapter_SectionListDatacategory itemListDataAdapter = new Adapter_SectionListDatacategory(mContext, singleSectionItems);

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

        protected Button btnMore;



        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
           // this.btnMore= (Button) view.findViewById(R.id.btnMore);


        }

    }

}