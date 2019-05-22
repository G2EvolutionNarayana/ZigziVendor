package g2evolution.GMT.Viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import g2evolution.GMT.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_orderdetails extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView orderdate,ordername,orderprodetails,orderpriceamount,quantity_ordertext,ordertotalamount;

    public ImageView orderimage,imgdelete;

    public ViewHolder_orderdetails(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        orderimage = (ImageView)itemView.findViewById(R.id.orderimage);
      //  imgdelete = (ImageView)itemView.findViewById(R.id.imgdelete);

        orderdate = (TextView)itemView.findViewById(R.id.orderdate);
        ordername = (TextView)itemView.findViewById(R.id.ordername);
        orderprodetails = (TextView)itemView.findViewById(R.id.orderprodetails);
        orderpriceamount = (TextView)itemView.findViewById(R.id.orderpriceamount);
        quantity_ordertext = (TextView)itemView.findViewById(R.id.quantity_ordertext);
        ordertotalamount = (TextView)itemView.findViewById(R.id.ordertotalamount);


    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
