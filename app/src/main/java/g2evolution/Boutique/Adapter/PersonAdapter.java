package g2evolution.Boutique.Adapter;


import android.content.Context;
import android.util.Log;

import java.util.List;

import g2evolution.Boutique.FeederInfo.Person;


public class PersonAdapter extends RadioAdapter<Person> {
    public PersonAdapter(Context context, List<Person> items) {
        super(context, items);
    }

    @Override
    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);


        //viewHolder.mText.setText(mItems.get(i).mName);


        viewHolder.mpid.setText(mItems.get(i).getMpid());
        viewHolder.mText.setText(mItems.get(i).getmName());
        viewHolder.mprice.setText(mItems.get(i).getMprice());

      /*  if (mItems.get(i).getMdiscount().equals("0")||mItems.get(i).getMdiscount() == null){
            viewHolder.mdiscount.setVisibility(View.GONE);
        }else {
            viewHolder.mdiscount.setText("-" + Html.fromHtml(mItems.get(i).getMdiscount()) + "%");
        }*/

        Log.e("tesing", "name in adapter " + (mItems.get(i).getmName()));
    }
}