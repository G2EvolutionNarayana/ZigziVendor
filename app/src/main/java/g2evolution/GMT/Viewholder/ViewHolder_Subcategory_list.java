package g2evolution.GMT.Viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import g2evolution.GMT.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_Subcategory_list extends RecyclerView.ViewHolder implements View.OnClickListener {


    public ImageView electronicimage;
    public TextView subcatname;
    public CardView card_view;

    public ViewHolder_Subcategory_list(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        electronicimage = (ImageView)itemView.findViewById(R.id.subcatimage);
        subcatname = (TextView) itemView.findViewById(R.id.subcatname);
        card_view = (CardView) itemView.findViewById(R.id.card_view);



    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
