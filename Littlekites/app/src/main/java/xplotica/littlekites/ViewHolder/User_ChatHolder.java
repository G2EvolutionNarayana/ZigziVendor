package xplotica.littlekites.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xplotica.littlekites.R;


/**
 * Created by G2evolution on 3/27/2017.
 */
public class User_ChatHolder extends RecyclerView.ViewHolder {
    public ImageView feederThumbnail;
    public TextView feederDesc;
    public TextView feederDesc2;
    public TextView feederDate;
    public TextView feedertime;
    public TextView feedertime2;
    public TextView feederDate2;
    public LinearLayout linearlayout1;
    public FrameLayout incoming_layout_bubble;
    public FrameLayout outgoing_layout_bubble;




    public User_ChatHolder(View itemView) {
        super(itemView);

        feederDesc = (TextView)itemView.findViewById(R.id.textview_message);
        feederDesc2 = (TextView)itemView.findViewById(R.id.textview_message2);
        feedertime = (TextView)itemView.findViewById(R.id.textview_time);
        feedertime2 = (TextView)itemView.findViewById(R.id.textview_time2);
        feederDate = (TextView)itemView.findViewById(R.id.textdate);
        feederDate2 = (TextView)itemView.findViewById(R.id.textdate2);
        incoming_layout_bubble = (FrameLayout)itemView.findViewById(R.id.incoming_layout_bubble);
        outgoing_layout_bubble = (FrameLayout)itemView.findViewById(R.id.ougoing_layout_bubble);




    }
}

