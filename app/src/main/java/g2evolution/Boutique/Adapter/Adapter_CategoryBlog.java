package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import g2evolution.Boutique.Activity.Activity_CategoryBlog;
import g2evolution.Boutique.Activity.Activity_SubSactegory;
import g2evolution.Boutique.R;
import g2evolution.Boutique.entit.Entity_CategoryBlog;

public class Adapter_CategoryBlog extends RecyclerView.Adapter<Adapter_CategoryBlog.FilterViewHolder>{

    private Context mCtx;
    //we are storing all the products in a list
    private List<Entity_CategoryBlog> filter_lists;
    private Adapter_CategoryBlog.OnItemClickcatlist mCallback1;
    String qty;



    //getting the context and product list with constructor
    public Adapter_CategoryBlog(Context mCtx, List<Entity_CategoryBlog> filter_lists) {
        this.mCtx = mCtx;
        this.filter_lists = filter_lists;
        this.mCallback1 = mCallback1;
    }

    public interface OnItemClickcatlist {
        void OnItemClickcatlist(int pos, String qty, int status);
    }
    @Override
    public Adapter_CategoryBlog.FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_categoryblog, parent, false);
        return new Adapter_CategoryBlog.FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_CategoryBlog.FilterViewHolder holder, final int position) {

        final Entity_CategoryBlog follow = filter_lists.get(position);
        holder.feedername.setText(follow.getTitle());

        if (follow.getImage() == null || follow.getImage().length() == 0) {
            Glide.with(mCtx)
                    .load(Uri.parse(String.valueOf(R.mipmap.ic_launcher)))
                    .error(R.mipmap.ic_launcher)
                    .into(holder.fedderthumbnail);

        } else {

            Glide.with(mCtx)
                    .load(Uri.parse(follow.getImage()))
                    .error(R.mipmap.ic_launcher)
                    .into(holder.fedderthumbnail);
        }


        holder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, Activity_CategoryBlog.class);

                SharedPreferences prefuserdata = mCtx.getSharedPreferences("categoryblogid", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();

                prefeditor.putString("categoryblogid", "" + follow.getId());


                prefeditor.commit();
                mCtx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return filter_lists.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {

        TextView feedername;
        ImageView fedderthumbnail;
        LinearLayout linearlayout;
        public FilterViewHolder(View itemView) {
            super(itemView);
            feedername=itemView.findViewById(R.id.feedername);
            fedderthumbnail=itemView.findViewById(R.id.fedderthumbnail);
            linearlayout=itemView.findViewById(R.id.linearlayout);
        }
    }
}
