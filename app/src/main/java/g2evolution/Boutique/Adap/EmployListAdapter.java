package g2evolution.Boutique.Adap;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import g2evolution.Boutique.R;
import g2evolution.Boutique.Retrofit.EmployListDataModel.Datum;

public class EmployListAdapter extends RecyclerView.Adapter<EmployListAdapter.EmployListAdapterChild> {
    Context context;
    List<Datum> data;

    public EmployListAdapter(FragmentActivity activity, List<Datum> data) {
        this.context = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public EmployListAdapterChild onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_employlist, parent, false);
        return new EmployListAdapterChild(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployListAdapterChild holder, int position) {

        Datum datum = data.get(position);
        if (datum.getGender() != null) {
            holder.xTvSex.setText(datum.getGender());
        }else {
            holder.xTvSex.setVisibility(View.GONE);
        }
        if (datum.getName() != null) {
            holder.cust_name_text1.setText(datum.getName());
        }
        if (datum.getTotalExp() != null) {
            holder.xTvExperience.setText(datum.getTotalExp() + " Years");
        }

        if (datum.getCreatedAt() != null) {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = originalFormat.parse(datum.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDate = targetFormat.format(date);
            Log.e("testing",formattedDate);
            holder.xTvPosted.setText((formattedDate));
        }

        if (datum.getResumes().size() > 0) {
            if (datum.getResumes().size() == 1) {
                holder.xTvCutting.setText(datum.getResumes().get(0).getServices());

            } else {
                holder.xTvCutting.setText(datum.getResumes().get(0).getServices() + " +" + datum.getResumes().size());

            }
        }else {
            holder.xTvCutting.setVisibility(View.GONE);
        }


        holder.xTvCutting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getResumes().size()>1){
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.servicelist_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    RecyclerView mRecyServiceList = (RecyclerView) dialog.findViewById(R.id.xRecyServiceList);
                    ImageView mIvCancel = (ImageView) dialog.findViewById(R.id.xIvCancel);
                    mIvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    mRecyServiceList.setHasFixedSize(true);
                    mRecyServiceList.setLayoutManager(new LinearLayoutManager(context));
                    mRecyServiceList.setAdapter(new ServicelistAdapter(context,data.get(position).getResumes()));
                    dialog.show();
                }


            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class EmployListAdapterChild extends RecyclerView.ViewHolder {
        TextView xTvCutting, cust_name_text1, xTvSex, xTvExperience, xTvPosted;

        public EmployListAdapterChild(@NonNull View itemView) {
            super(itemView);
            xTvCutting = itemView.findViewById(R.id.xTvCutting);
            cust_name_text1 = itemView.findViewById(R.id.cust_name_text1);
            xTvSex = itemView.findViewById(R.id.xTvSex);
            xTvExperience = itemView.findViewById(R.id.xTvExperience);
            xTvPosted = itemView.findViewById(R.id.xTvPosted);
        }
    }
}
