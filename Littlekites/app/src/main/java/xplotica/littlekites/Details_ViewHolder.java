package xplotica.littlekites;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by G2evolution on 5/10/2017.
 */

public class Details_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView student;
    public TextView school;
    public TextView rollno;
    public LinearLayout linearLayout;

    public Details_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        student = (TextView) itemView.findViewById(R.id.StudentName);
        school = (TextView) itemView.findViewById(R.id.SchoolName);
        rollno = (TextView) itemView.findViewById(R.id.rollno);
        linearLayout=(LinearLayout)itemView.findViewById(R.id.linearlayout);


    }

    @Override
    public void onClick (View view){
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }


}
