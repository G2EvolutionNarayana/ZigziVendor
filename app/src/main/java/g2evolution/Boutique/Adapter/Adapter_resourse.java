package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import g2evolution.Boutique.Activity.Activity_ResourcesList;
import g2evolution.Boutique.R;
import g2evolution.Boutique.entit.Entity_Resourse;

public class Adapter_resourse extends RecyclerView.Adapter<Adapter_resourse.Title_List_ViewHolder>{

    private List<Entity_Resourse> Entity_Resourse;
    private Adapter_resourse.OnItemClickcropNames mCallbackcropnames;
    String qty,category_id,title_name;
    private Context mCtx;
    //getting the context and product list with constructor
    public Adapter_resourse(Context mCtx, List<Entity_Resourse> Entity_Resourse, Adapter_resourse.OnItemClickcropNames mCallbackcropnames) {
        this.mCtx = mCtx;
        this.Entity_Resourse = Entity_Resourse;
        this.mCallbackcropnames = mCallbackcropnames;


    }
    public interface OnItemClickcropNames {
        void OnItemClickcropNames(int pos, String qty, int status);
    }

    @NonNull
    @Override
    public Adapter_resourse.Title_List_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_resourse, parent, false);
        return new Adapter_resourse.Title_List_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_resourse.Title_List_ViewHolder holder, final int position) {

        final Entity_Resourse follow = Entity_Resourse.get(position);

      /*  if(follow.getTitlename()==null || follow.getTitlename().equals("null") || follow.getTitlename().length()==0)
        {
            holder.title_name.setText("");
        }
        else {
            String upperString1 = follow.getTitlename().substring(0,1).toUpperCase() + follow.getTitlename().substring(1);
            holder.title_name.setText(upperString1);
        }*/

      String strs =  mCtx.getResources().getString(R.string.Rs);

      holder.title_name.setText(follow.getTitlename());
      holder.title_count.setText(follow.getTextcount());
      holder.textprice.setText(strs+follow.getTextprice());
      holder.textdesc.setText(follow.getTextdesc());

        holder.cardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qty=follow.getId();
                category_id=follow.getCategory_id();
                title_name=follow.getTitlename();

                if (mCallbackcropnames != null) {
                    mCallbackcropnames.OnItemClickcropNames(position, qty, 1);
                    Log.e("testing", "qtyadapter" + qty);

                }

                SharedPreferences prefuserdata =mCtx.getSharedPreferences("ProductIDDetails", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();
                prefeditor.putString("ProductId", "" + qty);
                prefeditor.putString("Strcategory_id", "" + category_id);
                prefeditor.putString("title_name", "" + title_name);
                prefeditor.commit();

                Intent intent=new Intent(mCtx, Activity_ResourcesList.class);
                mCtx.startActivity(intent);


                Log.e("testing","subcatid===child========"+qty);
            }
        });



    }

    @Override
    public int getItemCount()
    {
        return Entity_Resourse.size();
    }

    public class Title_List_ViewHolder extends RecyclerView.ViewHolder {

        TextView title_name, title_count, textprice, textdesc;

        CardView cardview1;




        public Title_List_ViewHolder(View itemView) {
            super(itemView);
            title_name = itemView.findViewById(R.id.title_name);
            title_count = itemView.findViewById(R.id.title_count);
            textprice = itemView.findViewById(R.id.textprice);
            textdesc = itemView.findViewById(R.id.textdesc);
            cardview1 = itemView.findViewById(R.id.cardview1);


        }
    }

}
