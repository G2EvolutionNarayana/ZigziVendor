package g2evolution.GMT.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import g2evolution.GMT.FeederInfo.Notification_list;
import g2evolution.GMT.R;

/**
 * Created by Jana on 2/22/2019.
 */

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.Title_List_ViewHolder>{

    private List<Notification_list> notification_lists;
    private Notification_Adapter.OnItemlevelsinside onItemlevels;
    String qty;
    private Context mCtx;
    //getting the context and product list with constructor
    public Notification_Adapter(Context mCtx, List<Notification_list> notification_lists, Notification_Adapter.OnItemlevelsinside onItemlevels) {
        this.mCtx = mCtx;
        this.notification_lists = notification_lists;
        this.onItemlevels = onItemlevels;


    }
    public interface OnItemlevelsinside {
        void OnItemlevelsinside(int pos, String qty, int status);
    }

    @NonNull
    @Override
    public Notification_Adapter.Title_List_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.notification_custom_layout, parent, false);
        return new Notification_Adapter.Title_List_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notification_Adapter.Title_List_ViewHolder holder, final int position) {

        final Notification_list follow = notification_lists.get(position);

        if(follow.getDate()==null || follow.getDate().equals("null") || follow.getDate().length()==0)
        {
            holder.date_text.setText("");
        }
        else {
            holder.date_text.setText(follow.getDate());
        }

        if(follow.getMessage()==null || follow.getMessage().equals("null") || follow.getMessage().length()==0)
        {
            holder.message_text.setText("");
        }
        else {
            holder.message_text.setText(follow.getMessage());
        }


        if(follow.getTitle()==null || follow.getTitle().equals("null") || follow.getTitle().length()==0)
        {
            holder.order_id_text.setText("");
        }
        else {
            holder.order_id_text.setText(follow.getTitle());
        }

        holder.message_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qty=follow.getId();

                if (onItemlevels != null) {
                    onItemlevels.OnItemlevelsinside(position, qty, 1);
                    Log.e("testing", "qtyadapter" + qty);

                }


                Log.e("testing","subcatid===child========"+qty);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return notification_lists.size();
    }

    public class Title_List_ViewHolder extends RecyclerView.ViewHolder {

        TextView date_text,message_text,order_id_text;


        public Title_List_ViewHolder(View itemView) {
            super(itemView);
            date_text = itemView.findViewById(R.id.date_text);
            message_text = itemView.findViewById(R.id.message_text);
            order_id_text = itemView.findViewById(R.id.order_id_text);



        }
    }

}
