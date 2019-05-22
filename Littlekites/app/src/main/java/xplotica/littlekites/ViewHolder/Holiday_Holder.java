package xplotica.littlekites.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xplotica.littlekites.R;

/**
 * Created by G2evolution on 4/30/2017.
 */

public class Holiday_Holder extends RecyclerView.ViewHolder {
    public ImageView feederThumbnail;
    public TextView feederMobileno;
    public TextView feederName;
    public TextView feederDate;
    public LinearLayout linearlayout1;



    public Holiday_Holder(View itemView) {
        super(itemView);

        feederName = (TextView)itemView.findViewById(R.id.homename);
        feederDate = (TextView)itemView.findViewById(R.id.textdate);
        feederMobileno = (TextView)itemView.findViewById(R.id.homemobileno);
        linearlayout1 = (LinearLayout)itemView.findViewById(R.id.ListItem);




    }
}
