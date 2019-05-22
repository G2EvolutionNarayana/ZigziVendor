package xplotica.littlekites.ViewHolder_parent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xplotica.littlekites.R;

/**
 * Created by santa on 3/21/2017.
 */
public class ParentGallery_ViewHolderFeed extends RecyclerView.ViewHolder implements View.OnClickListener {



    public ImageView thumnail;
    public TextView textcount;
    public TextView textname;




    public ParentGallery_ViewHolderFeed(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        thumnail = (ImageView)itemView.findViewById(R.id.thumbnail);
        textcount = (TextView)itemView.findViewById(R.id.textcount);
        textname = (TextView)itemView.findViewById(R.id.textname);


    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        Integer valueinteger = getPosition();
        String positionvalue = String.valueOf(valueinteger);
        positionvalue.equals("number");
    }

}
