package xplotica.littlekites.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xplotica.littlekites.R;


/**
 * Created by santa on 3/1/2017.
 */
public class Student_details_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



    public TextView student;
    public TextView school;
    public TextView rollno;

    public Student_details_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        student = (TextView) itemView.findViewById(R.id.student);
        school = (TextView) itemView.findViewById(R.id.school);
        rollno = (TextView) itemView.findViewById(R.id.rollno);


    }

    @Override
    public void onClick (View view){
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }


}
