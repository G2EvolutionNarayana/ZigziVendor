package xplotica.littlekites.ViewHolder_parent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xplotica.littlekites.R;


/**
 * Created by santa on 3/29/2017.
 */
public class parent_sentitem_ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView Date;
    public TextView Time;
    public TextView Message;


    public parent_sentitem_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        Date = (TextView) itemView.findViewById(R.id.date);
        Time = (TextView) itemView.findViewById(R.id.time);
        Message = (TextView) itemView.findViewById(R.id.message);



    }


    @Override
    public void onClick (View view){
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}

