package g2evolution.GMT.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;


import g2evolution.GMT.FeederInfo.Brands_List;
import g2evolution.GMT.R;

public class Brands_Adapter extends RecyclerView.Adapter<Brands_Adapter.BrandsViewHolder> {




    private Context mCtx;

    //we are storing all the products in a list
    private List<Brands_List> brands_lists;
    private Brands_Adapter.OnItemClickbrands mCallbackbrnds;
    String qty,BrandName;


    //getting the context and product list with constructor
    public Brands_Adapter(Context mCtx, List<Brands_List> brands_lists, Brands_Adapter.OnItemClickbrands mCallbackbrnds) {
        this.mCtx = mCtx;
        this.brands_lists = brands_lists;
        this.mCallbackbrnds = mCallbackbrnds;


    }

    public interface OnItemClickbrands {
        void onClickedItembrands (int pos, String qty, String BrandName,int status);
    }
    @Override
    public Brands_Adapter.BrandsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.brands_custom_layout, parent, false);
        return new Brands_Adapter.BrandsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Brands_Adapter.BrandsViewHolder holder, final int position) {
        final Brands_List follow = brands_lists.get(position);
        final int pos = position;
        holder.brand_name.setText(follow.getBrandName());
        holder.brand_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (mCallbackbrnds != null) {
                    qty = follow.getBrandId();
                    qty = follow.getBrandName();
                    mCallbackbrnds.onClickedItembrands(position, qty,BrandName, 1);
                }
            }
        });


        holder.brand_checkbox.setTag(brands_lists.get(position));

        holder.brand_checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Brands_List model = (Brands_List) cb.getTag();

                model.setSelected(cb.isChecked());
                brands_lists.get(pos).setSelected(cb.isChecked());

                if(cb.isChecked())
                {
                    holder.brand_checkbox.setSelected(true);
                }
                {
                    holder.brand_checkbox.setSelected(false);
                }
                Log.e("testing","testing in adapter on checkbox position = "+brands_lists);


            }
        });

    }

    @Override
    public int getItemCount() {
        return brands_lists.size();
    }

    public class BrandsViewHolder  extends  RecyclerView.ViewHolder{
        TextView brand_name;
        CheckBox brand_checkbox;
        public BrandsViewHolder(View itemView) {
            super(itemView);
            brand_name=itemView.findViewById(R.id.brand_name_text);
            brand_checkbox=itemView.findViewById(R.id.brand_check_box);

        }
    }
}
