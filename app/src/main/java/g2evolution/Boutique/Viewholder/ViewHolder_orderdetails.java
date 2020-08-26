package g2evolution.Boutique.Viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import g2evolution.Boutique.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_orderdetails extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView orderdate,ordername,orderprodetails,pdsubprice,pdprice,quantity_ordertext,ordertotalamount;

    public ImageView orderimage,imgdelete;

    public ViewHolder_orderdetails(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        orderimage = (ImageView)itemView.findViewById(R.id.orderimage);
      //  imgdelete = (ImageView)itemView.findViewById(R.id.imgdelete);

//        orderdate = (TextView)itemView.findViewById(R.id.orderdate);
        ordername = (TextView)itemView.findViewById(R.id.ordername);
        orderprodetails = (TextView)itemView.findViewById(R.id.orderprodetails);
//        pdsubprice = (TextView)itemView.findViewById(R.id.pdsubprice);
//        pdprice = (TextView)itemView.findViewById(R.id.pdprice);
        quantity_ordertext = (TextView)itemView.findViewById(R.id.quantity_ordertext);
        ordertotalamount = (TextView)itemView.findViewById(R.id.ordertotalamount);


    }

    @Override
    public void onClick(View view) {
        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
