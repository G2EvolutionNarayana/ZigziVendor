package g2evolution.Boutique.Adap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import g2evolution.Boutique.R;
import g2evolution.Boutique.Retrofit.EmployListDataModel.Resume;

public class ServicelistAdapter extends RecyclerView.Adapter<ServicelistAdapter.ServicelistAdapterChild> {
    private Context context;
    private List<Resume> resumes;

    public ServicelistAdapter(Context context, List<Resume> resumes) {
        this.context = context;
        this.resumes = resumes;
    }

    @NonNull
    @Override
    public ServicelistAdapter.ServicelistAdapterChild onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_servicelist, parent, false);
        return new ServicelistAdapterChild(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicelistAdapter.ServicelistAdapterChild holder, int position) {
        holder.order_date_text.setText(resumes.get(position).getServices());

    }

    @Override
    public int getItemCount() {
        return resumes.size();
    }

    public class ServicelistAdapterChild extends RecyclerView.ViewHolder {
        TextView order_date_text;
        public ServicelistAdapterChild(@NonNull View itemView) {
            super(itemView);
            order_date_text=itemView.findViewById(R.id.order_date_text);
        }
    }

}
