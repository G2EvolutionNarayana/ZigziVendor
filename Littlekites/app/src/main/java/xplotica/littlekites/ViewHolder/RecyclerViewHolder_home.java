package xplotica.littlekites.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xplotica.littlekites.R;

/**
 * Created by Sujata on 02-12-2016.
 */
public class RecyclerViewHolder_home extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView image_input;
    public TextView input_name;
    public LinearLayout linear_layout;


    public RecyclerViewHolder_home(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);
        input_name = (TextView)itemView.findViewById(R.id.inputname);
        image_input = (ImageView)itemView.findViewById(R.id.imageinput);
        linear_layout = (LinearLayout)itemView.findViewById(R.id.linear);
    }

    @Override
    public void onClick(View view) {


      //  Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();


    }
}
