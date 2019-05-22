package g2evolution.GMT.Viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import g2evolution.GMT.R;


/**
 * Created by G2evolution on 2/27/2018.
 */

public class ViewHolder_address extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView textname,textaddress,textlandmark,textmobileno,textpincode,impnotice;

    public ImageView imgdelete;
    public TextView radiobutton,textlandmark1;




    public ViewHolder_address(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);


        imgdelete = (ImageView)itemView.findViewById(R.id.imgdelete);

        radiobutton = (TextView)itemView.findViewById(R.id.radiobutton);

        textname = (TextView)itemView.findViewById(R.id.textname);
        textaddress = (TextView)itemView.findViewById(R.id.textaddress);
        impnotice = (TextView)itemView.findViewById(R.id.impnotice);
        textlandmark = (TextView)itemView.findViewById(R.id.textlandmark);
        textmobileno = (TextView)itemView.findViewById(R.id.textmobileno);
        textpincode = (TextView)itemView.findViewById(R.id.textpincode);
        textlandmark1 = (TextView)itemView.findViewById(R.id.textlandmark1);



    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
