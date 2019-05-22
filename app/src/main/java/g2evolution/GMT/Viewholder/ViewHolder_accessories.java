package g2evolution.GMT.Viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import g2evolution.GMT.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_accessories extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView accname,accdetail1,electronicdetail2,accprice;

    public ImageView accimage;

    public ViewHolder_accessories(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        accimage = (ImageView)itemView.findViewById(R.id.accimage);
        accname = (TextView)itemView.findViewById(R.id.accname);
        accdetail1 = (TextView)itemView.findViewById(R.id.accdetail1);
      //  electronicdetail2 = (TextView)itemView.findViewById(R.id.electronicdetail2);
        accprice = (TextView)itemView.findViewById(R.id.accprice);



    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
