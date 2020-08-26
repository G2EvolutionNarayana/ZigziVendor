package g2evolution.Boutique.Adapter;

/**
 * Created by G2evolution on 5/20/2017.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;

import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entity_descriptionchild;


public class Adapter_ProductdescriptionSelection extends RecyclerView.Adapter<Adapter_ProductdescriptionSelection.SingleItemRowHolder> {

    JSONObject json;
    JSONParser jsonParser = new JSONParser();
    ArrayList<String> images = new ArrayList<String>();
    String categoryName, postid, _descvalue, subcategoryname;
    private ArrayList<Entity_descriptionchild> itemsList;
    private Context mContext;

    public Adapter_ProductdescriptionSelection(Context context, ArrayList<Entity_descriptionchild> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_productvariationsselection, viewGroup, false);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        final Entity_descriptionchild singleItem = itemsList.get(i);

        holder.texttitle.setText("Product Details");
        holder.textvalue.setText(singleItem.getValue());
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView texttitle, textvalue;


        public SingleItemRowHolder(View view) {
            super(view);

            this.texttitle = (TextView) view.findViewById(R.id.texttitle);
            this.textvalue = (TextView) view.findViewById(R.id.textvalue);

/*

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();

                }
            });

*/

        }

    }


}