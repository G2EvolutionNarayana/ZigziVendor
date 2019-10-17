package g2evolution.Boutique.Viewholder;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import g2evolution.Boutique.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_Child_subcategory_list extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView subcatname;
    public CardView card_view;

    public ViewHolder_Child_subcategory_list(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

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
