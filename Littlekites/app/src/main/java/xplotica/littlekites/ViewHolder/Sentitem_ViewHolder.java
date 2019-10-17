package xplotica.littlekites.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xplotica.littlekites.R;


/**
 * Created by Sujata on 30-03-2017.
 */
public class Sentitem_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

public TextView topic;
public TextView rollno;
public TextView date;
public TextView name;
public TextView section;
public TextView message;


public Sentitem_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        topic = (TextView) itemView.findViewById(R.id.topic);
        rollno = (TextView) itemView.findViewById(R.id.rollno);
        date = (TextView) itemView.findViewById(R.id.date);
        name = (TextView) itemView.findViewById(R.id.name);
        section = (TextView) itemView.findViewById(R.id.section);
        message = (TextView) itemView.findViewById(R.id.message);
        }

@Override
public void onClick (View view){
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
        }

        }
