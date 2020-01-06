package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import g2evolution.Boutique.R;
import g2evolution.Boutique.entit.Entity_CategorySelect;

public class Adapter_CategorySelect extends RecyclerView.Adapter<Adapter_CategorySelect.ViewHolder> {


    public ArrayList<Entity_CategorySelect> item_list;


    public Adapter_CategorySelect(ArrayList<Entity_CategorySelect> arrayList) {

        item_list = arrayList;
    }
    private void updateProductOrder(Entity_CategorySelect pOrder,String count){

        pOrder.setItemcount(count);
    }
    @Override
    public Adapter_CategorySelect.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoryselect, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter_CategorySelect.ViewHolder holder, int position) {

        final int pos = position;

        holder.item_name.setText(item_list.get(position).getItemName());

        holder.chkSelected.setChecked(item_list.get(position).isSelected());

        holder.chkSelected.setTag(item_list.get(position));

        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Entity_CategorySelect Entity_CategorySelect = (Entity_CategorySelect) cb.getTag();

                Entity_CategorySelect.setSelected(cb.isChecked());
                item_list.get(pos).setSelected(cb.isChecked());
                Log.e("testing","testing in adapter on checkbox position = "+item_list);


            }
        });
        holder.editcount.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                Entity_CategorySelect pOrder = item_list.get(position);

                String strcout = String.valueOf(cs);
                updateProductOrder(pOrder, strcout);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_name;
        public EditText editcount;
        public CheckBox chkSelected;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            item_name = (TextView) itemLayoutView.findViewById(R.id.txt_Name);
            editcount = (EditText) itemLayoutView.findViewById(R.id.editcount);
            chkSelected = (CheckBox) itemLayoutView.findViewById(R.id.chk_selected);

        }
    }
}
