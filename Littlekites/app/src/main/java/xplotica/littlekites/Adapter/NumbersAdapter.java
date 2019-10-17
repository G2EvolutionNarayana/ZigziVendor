package xplotica.littlekites.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xplotica.littlekites.FeederInfo.Number1;
import xplotica.littlekites.R;

/**
 * Created by Sujata on 29-03-2017.
 */
public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.ViewHolder> {

    ArrayList<Number1> numbers;

    public NumbersAdapter(List<Number1> numbers) {
        this.numbers = new ArrayList<>(numbers);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bindData(numbers.get(position));

        //in some cases, it will prevent unwanted situations
        holder.checkbox.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        holder.checkbox.setChecked(numbers.get(position).isSelected());

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                numbers.get(holder.getAdapterPosition()).setSelected(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView ONEs;
        private TextView textONEs;
        private CheckBox checkbox;

        public ViewHolder(View v) {
            super(v);
            ONEs = (TextView) v.findViewById(R.id.ordreid);
            textONEs = (TextView) v.findViewById(R.id.name);
            checkbox = (CheckBox) v.findViewById(R.id.checkbox);
        }

        public void bindData(Number1 number) {
            ONEs.setText(number.getTextrolnno());
            textONEs.setText(number.getTextONEs());
        }
    }
}
