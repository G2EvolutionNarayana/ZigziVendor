package g2evolution.GMT.Viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import g2evolution.GMT.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_grocery extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView groname,grodetail1,electronicdetail2,groprice;

    public ImageView groimage;

    public ViewHolder_grocery(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        groimage = (ImageView)itemView.findViewById(R.id.groimage);
        groname = (TextView)itemView.findViewById(R.id.groname);
        grodetail1 = (TextView)itemView.findViewById(R.id.grodetail1);
      //  electronicdetail2 = (TextView)itemView.findViewById(R.id.electronicdetail2);
        groprice = (TextView)itemView.findViewById(R.id.groprice);



    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
