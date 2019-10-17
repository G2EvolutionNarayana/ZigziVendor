package xplotica.littlekites.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xplotica.littlekites.R;

/**
 * Created by Sujata on 30-03-2017.
 */
public class History_diary_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

public TextView topic;
public TextView topic_details;

public History_diary_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        topic = (TextView) itemView.findViewById(R.id.topic);
        topic_details = (TextView) itemView.findViewById(R.id.topic_details);
        }

@Override
public void onClick (View view){
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
        }

        }
