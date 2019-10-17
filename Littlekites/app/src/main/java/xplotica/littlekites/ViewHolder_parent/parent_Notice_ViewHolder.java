package xplotica.littlekites.ViewHolder_parent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xplotica.littlekites.R;


public class parent_Notice_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView texttime;
    public TextView textdesc;

    public parent_Notice_ViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        texttime = (TextView) itemView.findViewById(R.id.texttime);
        textdesc = (TextView) itemView.findViewById(R.id.textdesc);


    }

    @Override
    public void onClick (View view){
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

      /*  Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");*/
    }


}
