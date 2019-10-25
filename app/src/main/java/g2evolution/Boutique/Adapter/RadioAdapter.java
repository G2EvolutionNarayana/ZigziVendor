package g2evolution.Boutique.Adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import g2evolution.Boutique.R;


public abstract class RadioAdapter<T> extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {
    public int mSelectedItem = -1;
    public List<T> mItems;
    private Context mContext;

    public RadioAdapter(Context context, List<T> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.mRadio.setChecked(i == mSelectedItem);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_item, null);
        // LayoutInflater inflater = LayoutInflater.from(mContext);
        // final View view = inflater.inflate(R.layout.view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public RadioButton mRadio;
        public TextView mText;
        public TextView mprice;
        public TextView mpid;
        String radioitem;

        public ViewHolder(final View inflate) {
            super(inflate);
            mText = (TextView) inflate.findViewById(R.id.text);
            mprice = (TextView) inflate.findViewById(R.id.price);
            mpid = (TextView) inflate.findViewById(R.id.textViewpid);
            mRadio = (RadioButton) inflate.findViewById(R.id.radio);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();


                    radioitem=mText.getText().toString();


                    SharedPreferences prefuserdata = mContext.getSharedPreferences("productadapter", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();

                    prefeditor.putString("shippingid", "" + mpid.getText().toString());
                    prefeditor.putString("packname", "" + mText.getText().toString());
                    prefeditor.putString("price", "" + mprice.getText().toString());
                    prefeditor.commit();
                    Log.e("testing","pricepack = "+mText.getText().toString());
                    Log.e("testing","pice = "+mprice.getText().toString());

                    Log.e("radioitem -mahi -------", "radiobutton" + radioitem);

                    notifyItemRangeChanged(0, mItems.size());
                }
            };
            itemView.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);
        }
    }

}