package g2evolution.Boutique.Adap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import g2evolution.Boutique.R;
import g2evolution.Boutique.View_Details_Activity;
import g2evolution.Boutique.entit.New_Booking_List;

/**
 * Created by Jana on 2/22/2019.
 */

public class New_Booking_Adapter extends RecyclerView.Adapter<New_Booking_Adapter.Title_List_ViewHolder>{

    private List<New_Booking_List> new_booking_lists;
    private New_Booking_Adapter.OnItemlevelsinside onItemlevels;
    String qty,parameters,extractformid;
    private Context mCtx;


    Integer introwposition = null,mPreviousPosition=0;
    //getting the context and product list with constructor
    public New_Booking_Adapter(Context mCtx, List<New_Booking_List> new_booking_lists, New_Booking_Adapter.OnItemlevelsinside onItemlevels) {
        this.mCtx = mCtx;
        this.new_booking_lists = new_booking_lists;
        this.onItemlevels = onItemlevels;


    }
    public interface OnItemlevelsinside {
        void OnItemlevelsinside(int pos, String qty, int status);
    }

    @NonNull
    @Override
    public New_Booking_Adapter.Title_List_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adpater_new_booking, parent, false);
        return new New_Booking_Adapter.Title_List_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull New_Booking_Adapter.Title_List_ViewHolder holder, final int position) {

        final New_Booking_List follow = new_booking_lists.get(position);


        if(follow.getName()==null || follow.getName().equals("null") || follow.getName().length()==0)
        {
            holder.cust_name_text1.setText("");
        }
        else {
            holder.cust_name_text1.setText(follow.getName());
        }


        if(follow.getOrder_number()==null || follow.getOrder_number().equals("null") || follow.getOrder_number().length()==0)
        {
            holder.order_no_text1.setText("");
        }
        else {
            holder.order_no_text1.setText(follow.getOrder_number());
        }

        if(follow.getOrder_date()==null || follow.getOrder_date().equals("null") || follow.getOrder_date().length()==0)
        {
            holder.order_date_text1.setText("");
        }
        else {
            holder.order_date_text1.setText(follow.getOrder_date());
        }

        if(follow.getDelivery_date()==null || follow.getDelivery_date().equals("null") || follow.getDelivery_date().length()==0)
        {
            holder.delivery_date_text1.setText("");
        }
        else {
            holder.delivery_date_text1.setText(follow.getDelivery_date());
        }



        if(follow.getImage()==null)
        {
            holder.booking_image.setImageResource(R.drawable.splash_logo);
        }
        else {
            holder.booking_image.setImageResource(follow.getImage());
        }

        holder.card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                introwposition = position;
                qty=follow.getId();

                Intent intent=new Intent(mCtx, View_Details_Activity.class);
                mCtx.startActivity(intent);

                if (onItemlevels!=null){
                    onItemlevels.OnItemlevelsinside(position, follow.getId(), 1 );



                }

                Log.e("testing","subcatid===child========"+qty);
               // notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return new_booking_lists.size();
    }

    public class Title_List_ViewHolder extends RecyclerView.ViewHolder {

        TextView view_details_text,cust_name_text1,order_no_text1,order_date_text1,delivery_date_text1;
        ImageView booking_image;
        CardView card_view1;




        public Title_List_ViewHolder(View itemView) {
            super(itemView);
            booking_image = itemView.findViewById(R.id.booking_image);
            view_details_text = itemView.findViewById(R.id.view_details_text);
            cust_name_text1 = itemView.findViewById(R.id.cust_name_text1);
            order_no_text1 = itemView.findViewById(R.id.order_no_text1);
            order_date_text1 = itemView.findViewById(R.id.order_date_text1);
            delivery_date_text1 = itemView.findViewById(R.id.delivery_date_text1);
            card_view1 = itemView.findViewById(R.id.card_view1);

            Typeface typeface = Typeface.createFromAsset(mCtx.getAssets(), "arial.ttf");

            ((TextView)itemView. findViewById(R.id.cust_name_text)).setTypeface(typeface);
            ((TextView)itemView. findViewById(R.id.cust_name_text1)).setTypeface(typeface);
            ((TextView)itemView. findViewById(R.id.order_no_text)).setTypeface(typeface);
            ((TextView)itemView. findViewById(R.id.order_no_text1)).setTypeface(typeface);
            ((TextView)itemView. findViewById(R.id.order_date_text)).setTypeface(typeface);
            ((TextView)itemView. findViewById(R.id.order_date_text1)).setTypeface(typeface);
            ((TextView)itemView. findViewById(R.id.delivery_date_text)).setTypeface(typeface);
            ((TextView)itemView. findViewById(R.id.delivery_date_text1)).setTypeface(typeface);
            ((TextView)itemView. findViewById(R.id.view_details_text)).setTypeface(typeface);






        }
    }

}
