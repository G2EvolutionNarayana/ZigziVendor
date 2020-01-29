package g2evolution.Boutique.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entity_BookingDeliveryHistory;

/**
 * Created by Android4 on 2/1/2019.
 */

public class Adapter_BookingDeliveryHistory extends RecyclerView.Adapter<Adapter_BookingDeliveryHistory.NotificationViewHolder> {



    JSONParser jsonParser = new JSONParser();
    //this context we will use to inflate the layout
    private Context mCtx;
    private OnItemClick mCallback;

    //we are storing all the products in a list
    private List<Entity_BookingDeliveryHistory> clearList;

    Integer selectposition;


    String strnotification, strnotificationtype, strnotificationname;
    //getting the context and product list with constructor
    public Adapter_BookingDeliveryHistory(Context mCtx, List<Entity_BookingDeliveryHistory> clearList, OnItemClick listener) {
        this.mCtx = mCtx;
        this.clearList = clearList;
        this.mCallback = listener;




    }



    public interface OnItemClick {
        void onClickedItem(int pos, String qty, int status);
    }


    private ProgressDialog pDialog;

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_bookingdeliveryhistory, parent, false);




        return new Adapter_BookingDeliveryHistory.NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationViewHolder holder, final int position) {
        //getting the product of the specified position
        final Entity_BookingDeliveryHistory follow = clearList.get(position);

        holder.textorderid.setText(follow.getOrderid());
        holder.textdate.setText(follow.getDate());
        holder.textfrom.setText("From: "+follow.getFromlocation());
        holder.txtto.setText("To: "+follow.getTolocation());
        holder.textdeliverymode.setText(follow.getDeliverymode());
        holder.textpickupdate.setText("Pick up time: "+follow.getPickupdate());







    }


    @Override
    public int getItemCount() {
        return clearList.size();
    }


    class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView textorderid, textdate, textfrom, txtto, textdeliverymode, textpickupdate;
        LinearLayout linearlayout1;

        public NotificationViewHolder(View itemView) {
            super(itemView);

            linearlayout1 = itemView.findViewById(R.id.linearlayout1);
            textorderid = itemView.findViewById(R.id.textorderid);
            textdate = itemView.findViewById(R.id.textdate);
            textfrom = itemView.findViewById(R.id.textfrom);
            txtto = itemView.findViewById(R.id.txtto);
            textdeliverymode = itemView.findViewById(R.id.textdeliverymode);
            textpickupdate = itemView.findViewById(R.id.textpickupdate);
        }
    }
}
