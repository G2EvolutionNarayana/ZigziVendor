package g2evolution.Boutique.Adap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import g2evolution.Boutique.R;
import g2evolution.Boutique.entit.Bottom_Navigation_List;

/**
 * Created by Jana on 2/22/2019.
 */

public class Bottom_Navigation_Adapter extends RecyclerView.Adapter<Bottom_Navigation_Adapter.Title_List_ViewHolder>{

    private List<Bottom_Navigation_List> bottom_navigation_lists;
    private Bottom_Navigation_Adapter.OnItemlevelsinside onItemlevels;
    String qty,parameters,extractformid;
    private Context mCtx;


    Integer introwposition = null,mPreviousPosition=0;
    //getting the context and product list with constructor
    public Bottom_Navigation_Adapter(Context mCtx, List<Bottom_Navigation_List> bottom_navigation_lists, Bottom_Navigation_Adapter.OnItemlevelsinside onItemlevels) {
        this.mCtx = mCtx;
        this.bottom_navigation_lists = bottom_navigation_lists;
        this.onItemlevels = onItemlevels;


    }
    public interface OnItemlevelsinside {
        void OnItemlevelsinside(int pos, String qty, int status);
    }

    @NonNull
    @Override
    public Bottom_Navigation_Adapter.Title_List_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_navigation_layout, parent, false);
        return new Bottom_Navigation_Adapter.Title_List_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Bottom_Navigation_Adapter.Title_List_ViewHolder holder, final int position) {

        final Bottom_Navigation_List follow = bottom_navigation_lists.get(position);

        if(position==0){

            holder.name_text.setTextColor(Color.parseColor("#3963d6"));

        }else{


            holder.name_text.setTextColor(Color.parseColor("#ffffff"));
        }


        if(follow.getName()==null || follow.getName().equals("null") || follow.getName().length()==0)
        {
            holder.name_text.setText("");
        }
        else {
            holder.name_text.setText(follow.getName());
        }

        if (introwposition == null){

        }else if(introwposition==position){
            holder.name_text.setTextColor(Color.parseColor("#3963d6"));


        }else{
            holder.name_text.setTextColor(Color.parseColor("#ffffff"));
        }
        mPreviousPosition = position;
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                introwposition = position;
                qty=follow.getId();


                if (onItemlevels!=null){
                    onItemlevels.OnItemlevelsinside(position, follow.getId(), 1 );
                }

                Log.e("testing","subcatid===child========"+qty);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return bottom_navigation_lists.size();
    }

    public class Title_List_ViewHolder extends RecyclerView.ViewHolder {

        TextView name_text;
        ImageView image_view;
        RelativeLayout main_layout;




        public Title_List_ViewHolder(View itemView) {
            super(itemView);
            image_view = itemView.findViewById(R.id.image_view);
            name_text = itemView.findViewById(R.id.name_text);
            main_layout = itemView.findViewById(R.id.main_layout);

            Typeface typeface = Typeface.createFromAsset(mCtx.getAssets(), "arial.ttf");

            ((TextView)itemView. findViewById(R.id.name_text)).setTypeface(typeface);






        }
    }

}
