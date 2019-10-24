package g2evolution.Boutique.Viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import g2evolution.Boutique.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_cart extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView cartname,cartprodetails,pdsubprice,pdprice,cartquantity,carttotalamount, textsize;

    public ImageView cartimage,butdecrement,butincrement,delete;

    public ViewHolder_cart(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        cartimage = (ImageView)itemView.findViewById(R.id.cartimage);
        butdecrement = (ImageView)itemView.findViewById(R.id.butdecrement);
        butincrement = (ImageView)itemView.findViewById(R.id.butincrement);
        delete = (ImageView)itemView.findViewById(R.id.delete);


        cartname = (TextView)itemView.findViewById(R.id.cartname);
        cartprodetails = (TextView)itemView.findViewById(R.id.cartprodetails);
        pdprice = (TextView)itemView.findViewById(R.id.pdprice);
        pdsubprice = (TextView)itemView.findViewById(R.id.pdsubprice);
        cartquantity = (TextView)itemView.findViewById(R.id.cartquantity);
        carttotalamount = (TextView)itemView.findViewById(R.id.carttotalamount);
        textsize = (TextView)itemView.findViewById(R.id.textsize);


    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
