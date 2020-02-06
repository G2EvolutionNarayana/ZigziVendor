package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.io.Serializable;
import java.util.List;

import g2evolution.Boutique.Activity.Activity_productdetails;
import g2evolution.Boutique.R;
import g2evolution.Boutique.entit.Entity_SearchProducts;

public class Adapter_SearchProducts extends RecyclerView.Adapter<Adapter_SearchProducts.FilterViewHolder>{

    private Context mCtx;
    //we are storing all the products in a list
    private List<Entity_SearchProducts> courses_offered_list;
    private Adapter_SearchProducts.OnItemClickcourses mCallback1;
    String qty;
    String sub_category_id;


    String categoryName, postid;


    //getting the context and product list with constructor
    public Adapter_SearchProducts(Context mCtx, List<Entity_SearchProducts> courses_offered_list, Adapter_SearchProducts.OnItemClickcourses mCallback1) {
        this.mCtx = mCtx;
        this.courses_offered_list = courses_offered_list;
        this.mCallback1 = mCallback1;
    }

    public interface OnItemClickcourses {
        void OnItemClickcourses(int pos);
    }
    @Override
    public Adapter_SearchProducts.FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_searchproducts, parent, false);
        return new Adapter_SearchProducts.FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_SearchProducts.FilterViewHolder holder, final int position) {

        final Entity_SearchProducts follow = courses_offered_list.get(position);

        final String strrs = mCtx.getResources().getString(R.string.Rs);

        holder.textname.setText(follow.getCategoryname());


        holder.linearlayoutmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postid = follow.getId();
                categoryName =  follow.getCategoryname();


                SharedPreferences prefuserdata = mCtx.getSharedPreferences("ProDetails", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();
                prefeditor.putString("Proid", "" + postid);
                prefeditor.putString("categoryName", "" + categoryName);

                Log.e("testing","Proid  = " + postid);
                Log.e("testing","productid in adapter = "+postid);
                prefeditor.commit();

              /*  SharedPreferences prefuserdata2 = mContext.getSharedPreferences("regId", 0);
                SharedPreferences.Editor prefeditor2 = prefuserdata2.edit();
                prefeditor2.putString("UserId", "" + "2");

                prefeditor2.commit();*/



                Intent intent = new Intent(mCtx, Activity_productdetails.class);
                Bundle extras = new Bundle();
                extras.putSerializable("HashMap", (Serializable) follow.getMapparameters());
                intent.putExtras(extras);
                mCtx.startActivity(intent);

            }
        });
       /* if (follow.getProductImage() == null || follow.getProductImage().length() == 0){
            Glide.with(mCtx)
                    .load(Uri.parse(String.valueOf(R.drawable.logo)))
                    .error(R.drawable.logo)
                    .into(holder.product_image);

        }else{

            Glide.with(mCtx)
                    .load(Uri.parse(follow.getProductImage()))
                    .error(R.drawable.logo)
                    .into(holder.product_image);
        }*/
      /*  holder.texttitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, Activity_BranchesDetails.class);
                SharedPreferences prefuserdata = mCtx.getSharedPreferences("branchlist", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();
                prefeditor.putString("branchlistid", "" + follow.getId());
                prefeditor.commit();

                mCtx.startActivity(intent);

            }
        });*/


    }

    @Override
    public int getItemCount() {
        return courses_offered_list.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {

        TextView textname;

        LinearLayout linearlayoutmain;

        public FilterViewHolder(View itemView) {
            super(itemView);
            textname = itemView.findViewById(R.id.textname);
            linearlayoutmain = itemView.findViewById(R.id.linearlayoutmain);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCallback1.OnItemClickcourses(getAdapterPosition());
                }
            });*/

        }
    }
}
