package g2evolution.GMT.Viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import g2evolution.GMT.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_orderhistory extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView orderdate,orderid,totalprice,shippingaddress,paymentmode, deliverystatus;
    public CardView card;
    public TextView imgdelete;
    public LinearLayout linearlayout1;



    public ViewHolder_orderhistory(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);


        deliverystatus = (TextView)itemView.findViewById(R.id.deliverystatus);
        orderdate = (TextView)itemView.findViewById(R.id.orderdate);
        orderid = (TextView)itemView.findViewById(R.id.orderid);
        totalprice = (TextView)itemView.findViewById(R.id.totalprice);
        shippingaddress = (TextView)itemView.findViewById(R.id.shppingaddress);
        paymentmode = (TextView)itemView.findViewById(R.id.paymentmode);
        card = (CardView)itemView.findViewById(R.id.card);
        linearlayout1 = (LinearLayout)itemView.findViewById(R.id.linearlayout1);

        imgdelete = (TextView) itemView.findViewById(R.id.imgdelete);



    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
