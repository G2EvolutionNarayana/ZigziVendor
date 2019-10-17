package g2evolution.Boutique.Viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import g2evolution.Boutique.R;


/**
 * Created by G2e Android on 17-05-2017.
 */

public class ViewHolder_home_category extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView electronicname;

    public LinearLayout linear;
    public ImageView electronicimage;

    public ViewHolder_home_category(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        electronicimage = (ImageView)itemView.findViewById(R.id.electronicimage);
        electronicname = (TextView)itemView.findViewById(R.id.electronicname);
        linear = (LinearLayout)itemView.findViewById(R.id.linear);



    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
