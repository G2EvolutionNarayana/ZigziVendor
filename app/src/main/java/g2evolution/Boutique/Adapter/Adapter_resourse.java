package g2evolution.Boutique.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import g2evolution.Boutique.Activity.Activity_ResourcesList;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Retrofit.ResourceList.Datum;
import g2evolution.Boutique.entit.Entity_Resourse;

public class Adapter_resourse extends RecyclerView.Adapter<Adapter_resourse.Title_List_ViewHolder>{

    private List<Datum> Datum;
    private Adapter_resourse.OnItemClickcropNames mCallbackcropnames;
    String qty,category_id,title_name;
    private Context mCtx;
    //getting the context and product list with constructor
    public Adapter_resourse(Context mCtx, List<Datum> Datum, Adapter_resourse.OnItemClickcropNames mCallbackcropnames) {
        this.mCtx = mCtx;
        this.Datum = Datum;
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

        final Datum follow = Datum.get(position);

      /*  if(follow.getTitlename()==null || follow.getTitlename().equals("null") || follow.getTitlename().length()==0)
        {
            holder.title_name.setText("");
        }
        else {
            String upperString1 = follow.getTitlename().substring(0,1).toUpperCase() + follow.getTitlename().substring(1);
            holder.title_name.setText(upperString1);
        }*/

      String strs =  mCtx.getResources().getString(R.string.Rs);

      holder.title_name.setText(follow.getName());
      holder.title_count.setText(follow.getAllocatedResumes()+"/"+follow.getCvCount());
      holder.textprice.setText(strs+follow.getPrice());
      holder.textdesc.setText(follow.getDescription());

        holder.cardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qty= String.valueOf(follow.getResourcePackagesId());

            /*    if (mCallbackcropnames != null) {
                    mCallbackcropnames.OnItemClickcropNames(position, qty, 1);
                    Log.e("testing", "qtyadapter" + qty);

                }*/

            if (follow.getSubscribed().trim().equals("yes")){
                SharedPreferences prefuserdata =mCtx.getSharedPreferences("ProductIDDetails", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();
                prefeditor.putString("ProductId", "" + qty);
                prefeditor.putString("Productsubscriptionid", "" + follow.getSubscriptionId());
                prefeditor.commit();

                Intent intent=new Intent(mCtx, Activity_ResourcesList.class);
                mCtx.startActivity(intent);
            }else{
                Toast.makeText(mCtx, "Please Buy this plan", Toast.LENGTH_SHORT).show();
            }




                Log.e("testing","subcatid===child========"+qty);
            }
        });


        holder.butbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog logindialog = new Dialog(mCtx);
                logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater1 = (LayoutInflater) mCtx.getSystemService(mCtx.LAYOUT_INFLATER_SERVICE);
                View convertView1 = (View) inflater1.inflate(R.layout.dialog_buy, null);

                //  StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

                logindialog.setContentView(convertView1);
                TextView textprice = (TextView) convertView1.findViewById(R.id.textprice);
                TextView textpaynow = (TextView) convertView1.findViewById(R.id.textpaynow);
                TextView textcustoms = (TextView) convertView1.findViewById(R.id.textcustoms);
                SpannableString content = new SpannableString("Custom Select");
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                textcustoms.setText(content);

                textprice.setText(follow.getPrice());
                textpaynow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qty = String.valueOf(follow.getResourcePackagesId());
                        if (mCallbackcropnames!=null){
                            mCallbackcropnames.OnItemClickcropNames(position,qty,  1);
                        }
                    }
                });
                textcustoms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qty = String.valueOf(follow.getResourcePackagesId());
                        if (mCallbackcropnames!=null){
                            mCallbackcropnames.OnItemClickcropNames(position,qty,  2);
                            logindialog.dismiss();
                        }
                    }
                });
                //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                logindialog.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
                lp1.copyFrom(logindialog.getWindow().getAttributes());
                lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp1.gravity = Gravity.CENTER;
                logindialog.getWindow().setAttributes(lp1);



                // lowtohigh = (TextView) convertView1.findViewById(R.id.lowtohigh);


                logindialog.show();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return Datum.size();
    }

    public class Title_List_ViewHolder extends RecyclerView.ViewHolder {

        TextView title_name, title_count, textprice, textdesc, butbuy;

        CardView cardview1;




        public Title_List_ViewHolder(View itemView) {
            super(itemView);
            title_name = itemView.findViewById(R.id.title_name);
            title_count = itemView.findViewById(R.id.title_count);
            textprice = itemView.findViewById(R.id.textprice);
            textdesc = itemView.findViewById(R.id.textdesc);
            butbuy = itemView.findViewById(R.id.butbuy);
            cardview1 = itemView.findViewById(R.id.cardview1);


        }
    }

}
