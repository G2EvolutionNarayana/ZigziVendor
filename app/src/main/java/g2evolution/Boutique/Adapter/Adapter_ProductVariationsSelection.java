package g2evolution.Boutique.Adapter;

/**
 * Created by G2evolution on 5/20/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import g2evolution.Boutique.Activity.Activity_productdetails;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entity_weightchild;


public class Adapter_ProductVariationsSelection extends RecyclerView.Adapter<Adapter_ProductVariationsSelection.SingleItemRowHolder> {

    private ArrayList<Entity_weightchild> itemsList;
    private Context mContext;

    JSONObject json;
    JSONParser jsonParser = new JSONParser();
    ArrayList<String> images = new ArrayList<String>();

    private Adapter_ProductVariationsSelection.OnItemClickchildadapter mCallback;

    String categoryName, postid, _descvalue, subcategoryname;

    public Adapter_ProductVariationsSelection(Context context, ArrayList<Entity_weightchild> itemsList, Adapter_ProductVariationsSelection.OnItemClickchildadapter listener) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.mCallback = listener;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_productdescriptionselection, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    public interface OnItemClickchildadapter {
        void onClickedItemchildadapter(int pos, String qty, HashMap hashMap, int status);
    }


    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        final Entity_weightchild singleItem = itemsList.get(i);

        holder.texttitle.setText(singleItem.getName());

        if (singleItem.getIsselected() == null || singleItem.getIsselected().trim().length() == 0 || singleItem.getIsselected().trim().equals("null")) {

        } else if (singleItem.getIsselected().trim().equals("yes")) {

//            holder.texttitle.setTextColor(Color.parseColor("#FFFFFF"));

//            holder.linearlayout1.setBackgroundResource(R.drawable.layout_bgfill);
            holder.mIvTick.setImageResource(R.drawable.ic_red_tick);
        } else {

        }

        holder.mIvTick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                String strheadercode = singleItem.getHeadercode();
                String strvalue = singleItem.getValue();

                HashMap hashMap = (HashMap) singleItem.getMapparameters();

                hashMap.put(strheadercode, strvalue);

                Log.e("testing", "strheadercode = " + strheadercode);
                Log.e("testing", "strvalue = " + strvalue);
                mCallback.onClickedItemchildadapter(i, strheadercode + ", " + strvalue, hashMap, 1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView texttitle;
        protected RelativeLayout linearlayout1;
        protected ImageView mIvTick;


        public SingleItemRowHolder(View view) {
            super(view);

            this.texttitle = (TextView) view.findViewById(R.id.texttitle);
            this.linearlayout1 = (RelativeLayout) view.findViewById(R.id.linearlayout1);
            this.mIvTick = (ImageView) view.findViewById(R.id.xIvTick);


        }

    }


}