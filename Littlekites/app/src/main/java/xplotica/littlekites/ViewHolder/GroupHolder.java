package xplotica.littlekites.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xplotica.littlekites.R;


/**
 * Created by G2evolution on 3/27/2017.
 */
public class GroupHolder extends RecyclerView.ViewHolder {
    public ImageView feederThumbnail;
    public TextView feederName;
    public LinearLayout linearlayout1;



    public GroupHolder(View itemView) {
        super(itemView);

        feederName = (TextView)itemView.findViewById(R.id.groupname);
        linearlayout1 = (LinearLayout)itemView.findViewById(R.id.ListItem);




    }
}

