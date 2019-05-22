package xplotica.littlekites.Adapter_parent;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import xplotica.littlekites.Activity_parent.parent_ObjectDrawerItem;
import xplotica.littlekites.R;


public class parent_DrawerItemCustomAdapter extends BaseAdapter {



    TextView nameTextView, notification, notification2;


    Context mContext;
    int mLayoutResourceId;
    parent_ObjectDrawerItem mData[] = null;

    public parent_DrawerItemCustomAdapter(Context context, int layoutResourceId, parent_ObjectDrawerItem[] data) {
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = data;

    }


    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        parent_ObjectDrawerItem objectDrawerItem = mData[position];

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            listItem = inflater.inflate(mLayoutResourceId, parent, false);
            // Now we can fill the layout with the right values
            ImageView iconImageView = (ImageView) listItem.findViewById(R.id.drawer_item_icon);
            nameTextView = (TextView) listItem.findViewById(R.id.drawer_item_name);
            notification = (TextView)listItem.findViewById(R.id.notificationid);
            notification2 = (TextView)listItem.findViewById(R.id.notificationid);

            iconImageView.setImageDrawable(listItem.getResources().getDrawable(objectDrawerItem.getIcon()));
            nameTextView.setText(objectDrawerItem.getName());



        }


        return listItem;
    }
}
