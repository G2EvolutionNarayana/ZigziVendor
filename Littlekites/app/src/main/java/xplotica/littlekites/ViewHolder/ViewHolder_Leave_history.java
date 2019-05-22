package xplotica.littlekites.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xplotica.littlekites.R;

/**
 * Created by G2evolution on 10/21/2017.
 */

public class ViewHolder_Leave_history extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView Date;
    public TextView time;
    public TextView accept;
    public TextView details;
    public  TextView reason;

    public ViewHolder_Leave_history(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);
        Date = (TextView)itemView.findViewById(R.id.date);
        time = (TextView)itemView.findViewById(R.id.time);
        details = (TextView)itemView.findViewById(R.id.details);
        reason=(TextView)itemView.findViewById(R.id.reason);
        accept = (TextView)itemView.findViewById(R.id.acceptreject);
    }

    @Override
    public void onClick(View view) {


        //  Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();


    }
}
