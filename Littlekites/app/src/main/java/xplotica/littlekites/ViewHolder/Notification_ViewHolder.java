package xplotica.littlekites.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xplotica.littlekites.R;


/**
 * Created by santa on 3/1/2017.
 */
public class Notification_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


   public ImageView icon;
   public TextView Details;

    public Notification_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        Details = (TextView) itemView.findViewById(R.id.details);


    }

    @Override
    public void onClick (View view){
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }


}
