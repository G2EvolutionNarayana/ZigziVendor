package g2evolution.Boutique.Viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import g2evolution.Boutique.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_electronic extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView electronicname,electronicdetail1,electronicdetail2,pdsubprice, pdprice,textdiscount;

    public ImageView electronicimage;

    public ViewHolder_electronic(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        electronicimage = (ImageView)itemView.findViewById(R.id.electronicimage);
        electronicname = (TextView)itemView.findViewById(R.id.electronicname);
        electronicdetail1 = (TextView)itemView.findViewById(R.id.electronicdetail1);
      //  electronicdetail2 = (TextView)itemView.findViewById(R.id.electronicdetail2);
        pdsubprice = (TextView)itemView.findViewById(R.id.pdsubprice);
        pdprice = (TextView)itemView.findViewById(R.id.pdprice);
        textdiscount = (TextView) itemView.findViewById(R.id.textdiscount);




    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
