package xplotica.littlekites.Adapter_parent;

/**
 * Created by Sujata on 07-11-2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xplotica.littlekites.FeederInfo_parent.parent_ItemObject_home;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder_parent.parent_RecyclerViewHolder_home;


public class parent_RecyclerViewAdapter_home extends RecyclerView.Adapter<parent_RecyclerViewHolder_home> {

    private List<parent_ItemObject_home> itemList;
    private Context context;

    public parent_RecyclerViewAdapter_home(Context context, List<parent_ItemObject_home> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public parent_RecyclerViewHolder_home onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_home_layout, null);
        parent_RecyclerViewHolder_home rcv = new parent_RecyclerViewHolder_home(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final parent_RecyclerViewHolder_home holder, final int position) {

        final parent_ItemObject_home productitem = itemList.get(position);

        holder.input_name.setText(itemList.get(position).getName());
        holder.image_input.setImageResource(itemList.get(position).getPhoto());
        holder.image_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent intent=new Intent(context, Activity_Myorders.class);
               // context.startActivity(intent);
            }
        });
        holder.linear_layout.setBackgroundColor(Color.parseColor(itemList.get(position).getBackcolor()));



    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
