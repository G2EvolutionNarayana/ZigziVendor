package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import g2evolution.Boutique.R;
import g2evolution.Boutique.entit.Entity_Sizes;


public class Adapter_Sizes extends RecyclerView.Adapter<Adapter_Sizes.FilterViewHolder>{

    private Context mCtx;
    //we are storing all the products in a list
    private List<Entity_Sizes> courses_offered_list;
    private Adapter_Sizes.OnItemClickcourses mCallback1;
    String qty;
    String sub_category_id;


    Integer introwposition = null;

    //getting the context and product list with constructor
    public Adapter_Sizes(Context mCtx, List<Entity_Sizes> courses_offered_list, Adapter_Sizes.OnItemClickcourses mCallback1) {
        this.mCtx = mCtx;
        this.courses_offered_list = courses_offered_list;
        this.mCallback1 = mCallback1;
    }

    public interface OnItemClickcourses {
        void OnItemClickcourses(int pos, String qty, String sub_category_id, int status);
    }
    @Override
    public Adapter_Sizes.FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_sizes, parent, false);
        return new Adapter_Sizes.FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_Sizes.FilterViewHolder holder, final int position) {

        final Entity_Sizes follow = courses_offered_list.get(position);

        holder.texttitle.setText(follow.getProductName());

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
        holder.texttitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strid = follow.getId();
                String strname = follow.getProductName();
                introwposition = position;
              /*  Intent intent = new Intent(mCtx, Activity_BranchesDetails.class);
                mCtx.startActivity(intent);*/
                notifyDataSetChanged();

                if (mCallback1!=null){

                    mCallback1.OnItemClickcourses(position,strid, strname,1);

                }


            }
        });

        if (introwposition == null){

        }else if(introwposition==position){

            holder.texttitle.setTextColor(Color.parseColor("#FFFFFF"));
            holder.linearlayout1.setBackgroundResource(R.drawable.layout_bgfill);
        }else{
            holder.texttitle.setTextColor(Color.parseColor("#000000"));
            holder.linearlayout1.setBackgroundResource(R.drawable.layout_bggrey);
        }
    }

    @Override
    public int getItemCount() {
        return courses_offered_list.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {

        TextView texttitle;
        LinearLayout linearlayout1;

        public FilterViewHolder(View itemView) {
            super(itemView);
            texttitle=itemView.findViewById(R.id.texttitle);
            linearlayout1=itemView.findViewById(R.id.linearlayout1);

        }
    }
}
