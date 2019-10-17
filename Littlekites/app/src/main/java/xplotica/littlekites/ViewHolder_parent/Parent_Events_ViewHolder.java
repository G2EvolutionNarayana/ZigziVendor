package xplotica.littlekites.ViewHolder_parent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xplotica.littlekites.R;


/**
 * Created by Santanu on 03-04-2017.
 */

public class Parent_Events_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView EventName;
    public TextView Time;
    public TextView Message;
    public TextView StartDate;
    public TextView EndDate;


    public Parent_Events_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        EventName = (TextView) itemView.findViewById(R.id.topicname);
        Time = (TextView) itemView.findViewById(R.id.time);
        Message = (TextView) itemView.findViewById(R.id.message);
        StartDate = (TextView) itemView.findViewById(R.id.startdate);
        EndDate = (TextView) itemView.findViewById(R.id.enddate);



    }

    @Override
    public void onClick (View view){
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }


}