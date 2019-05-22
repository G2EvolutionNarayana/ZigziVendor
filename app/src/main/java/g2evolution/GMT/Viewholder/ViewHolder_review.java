package g2evolution.GMT.Viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import g2evolution.GMT.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_review extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView textrating,texttitle,textdesc,textdetails,textname;



    public ViewHolder_review(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);


        textrating = (TextView)itemView.findViewById(R.id.textrating);
        texttitle = (TextView)itemView.findViewById(R.id.texttitle);
        textdesc = (TextView)itemView.findViewById(R.id.textdesc);
        textdetails = (TextView)itemView.findViewById(R.id.textdetails);
        textname = (TextView)itemView.findViewById(R.id.textname);



    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
