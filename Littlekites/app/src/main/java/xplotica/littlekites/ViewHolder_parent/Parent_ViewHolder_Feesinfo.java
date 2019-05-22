package xplotica.littlekites.ViewHolder_parent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import xplotica.littlekites.R;

/**
 * Created by G2evolution on 11/3/2017.
 */

public class Parent_ViewHolder_Feesinfo  extends RecyclerView.ViewHolder implements View.OnClickListener{

    public LinearLayout linearlayout1;
    public TextView textname;
    public TextView textamount;
    public TextView textstatus;
    public TextView textdate;




    public Parent_ViewHolder_Feesinfo(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);
        linearlayout1 = (LinearLayout)itemView.findViewById(R.id.linearlayout1);
        textname = (TextView)itemView.findViewById(R.id.textname);
        textamount = (TextView)itemView.findViewById(R.id.textamount);
        textstatus = (TextView)itemView.findViewById(R.id.textstatus);
        textdate = (TextView)itemView.findViewById(R.id.textdate);

    }

    @Override
    public void onClick(View view) {


        //  Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();


    }
}
