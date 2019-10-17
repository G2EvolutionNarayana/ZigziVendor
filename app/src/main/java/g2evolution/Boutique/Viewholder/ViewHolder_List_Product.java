package g2evolution.Boutique.Viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import g2evolution.Boutique.R;

public class ViewHolder_List_Product extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView electronicname,electronicdetail1,electronicdetail2,pdsubprice, pdprice,textdiscount,discount_price,actual_price;

    public ImageView electronicimage;

    public RelativeLayout line;
    public ViewHolder_List_Product(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        electronicimage = (ImageView)itemView.findViewById(R.id.electronicimage);
        electronicname = (TextView)itemView.findViewById(R.id.electronicname);
        electronicdetail1 = (TextView)itemView.findViewById(R.id.electronicdetail1);
        //  electronicdetail2 = (TextView)itemView.findViewById(R.id.electronicdetail2);
        pdsubprice = (TextView)itemView.findViewById(R.id.pdsubprice);
        pdprice = (TextView)itemView.findViewById(R.id.pdprice);
        textdiscount = (TextView) itemView.findViewById(R.id.textdiscount);
        line = (RelativeLayout) itemView.findViewById(R.id.line1);
        actual_price = (TextView)itemView.findViewById(R.id.actual_price);



        discount_price = (TextView)itemView.findViewById(R.id.discount_price);






    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
