package xplotica.littlekites.ViewHolder_parent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import xplotica.littlekites.R;

/**
 * Created by G2evolution on 10/21/2017.
 */

public class parent_fee_history_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView time;
    public TextView desc;
    public  TextView amount;
    public  TextView transid;
    public  TextView transstatus;
    public  TextView paymenttype;
    public LinearLayout lineardesc;

    // public LinearLayout linear_layout;


    public parent_fee_history_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);
        amount=(TextView)itemView.findViewById(R.id.amount);
        time = (TextView)itemView.findViewById(R.id.time);
        desc = (TextView)itemView.findViewById(R.id.desc);
        transid = (TextView)itemView.findViewById(R.id.transid);
        transstatus = (TextView)itemView.findViewById(R.id.transstatus);
        paymenttype = (TextView)itemView.findViewById(R.id.paymenttype);
        lineardesc = (LinearLayout)itemView.findViewById(R.id.lineardesc);


    }

    @Override
    public void onClick(View view) {


        //  Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();


    }
}
