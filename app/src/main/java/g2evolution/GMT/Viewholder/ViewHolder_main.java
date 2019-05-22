package g2evolution.GMT.Viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import g2evolution.GMT.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_main extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView textnavi;



    public ViewHolder_main(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);


        textnavi = (TextView)itemView.findViewById(R.id.textnavi);



    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
