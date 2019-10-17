package xplotica.littlekites.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xplotica.littlekites.R;

/**
 * Created by Sujata on 30-03-2017.
 */
public class History_attendance_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

public TextView student_id;
public TextView student_name;


public History_attendance_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        student_id = (TextView) itemView.findViewById(R.id.student_id);
        student_name = (TextView) itemView.findViewById(R.id.student_name);
        }

@Override
public void onClick (View view){
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
        }

        }
