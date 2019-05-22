package xplotica.littlekites.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xplotica.littlekites.R;

/**
 * Created by G2evolution on 7/4/2017.
 */

public class Tue_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {




    public TextView textperiod;
    public TextView texttime;
    public TextView textsubject;
    public TextView textclass;
    public TextView textsection;


    public Tue_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        textperiod = (TextView)itemView.findViewById(R.id.textperiod);
        texttime = (TextView)itemView.findViewById(R.id.texttime);
        textsubject = (TextView)itemView.findViewById(R.id.textsubject);
        textclass = (TextView)itemView.findViewById(R.id.textclass);
        textsection = (TextView)itemView.findViewById(R.id.textsection);

    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
