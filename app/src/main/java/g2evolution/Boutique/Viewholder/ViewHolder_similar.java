package g2evolution.Boutique.Viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import g2evolution.Boutique.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_similar extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView simname,phodetail1,phodetail2,phoprice;

    public ImageView phoimage;

    public ViewHolder_similar(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        phoimage = (ImageView)itemView.findViewById(R.id.phoimage);
     //   phoname = (TextView)itemView.findViewById(R.id.phoname);
        phodetail1 = (TextView)itemView.findViewById(R.id.phodetail1);
      //  electronicdetail2 = (TextView)itemView.findViewById(R.id.electronicdetail2);
        phoprice = (TextView)itemView.findViewById(R.id.phoprice);



    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
