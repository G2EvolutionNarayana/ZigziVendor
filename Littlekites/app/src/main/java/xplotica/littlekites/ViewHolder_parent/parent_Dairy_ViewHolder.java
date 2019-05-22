package xplotica.littlekites.ViewHolder_parent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xplotica.littlekites.R;


/**
 * Created by santa on 3/30/2017.
 */
public class parent_Dairy_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView TopicName;

    public TextView Message;
    public TextView texttime;


    public parent_Dairy_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        TopicName = (TextView) itemView.findViewById(R.id.topicname);

        Message = (TextView) itemView.findViewById(R.id.message);
        texttime = (TextView) itemView.findViewById(R.id.texttime);



    }

    @Override
    public void onClick (View view){
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }


}


