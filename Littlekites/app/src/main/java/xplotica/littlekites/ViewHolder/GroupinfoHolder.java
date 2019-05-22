package xplotica.littlekites.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xplotica.littlekites.R;


/**
 * Created by G2evolution on 3/27/2017.
 */
public class GroupinfoHolder extends RecyclerView.ViewHolder {

    public TextView homename;

    public ImageView imagechat2;




    public GroupinfoHolder(View itemView) {
        super(itemView);

        homename = (TextView)itemView.findViewById(R.id.homename);





    }
}

