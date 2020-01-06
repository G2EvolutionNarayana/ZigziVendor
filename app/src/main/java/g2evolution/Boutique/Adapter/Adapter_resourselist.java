package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import g2evolution.Boutique.Activity.Activity_ResourcesList;
import g2evolution.Boutique.R;
import g2evolution.Boutique.entit.Entity_ResourseList;

public class Adapter_resourselist extends RecyclerView.Adapter<Adapter_resourselist.Title_List_ViewHolder>{

    private List<g2evolution.Boutique.entit.Entity_ResourseList> Entity_ResourseList;
    private Adapter_resourselist.OnItemClickcropNames mCallbackcropnames;
    String qty,category_id,title_name;
    private Context mCtx;
    //getting the context and product list with constructor
    public Adapter_resourselist(Context mCtx, List<Entity_ResourseList> Entity_ResourseList, Adapter_resourselist.OnItemClickcropNames mCallbackcropnames) {
        this.mCtx = mCtx;
        this.Entity_ResourseList = Entity_ResourseList;
        this.mCallbackcropnames = mCallbackcropnames;


    }
    public interface OnItemClickcropNames {
        void OnItemClickcropNames(int pos, String qty, int status);
    }

    @NonNull
    @Override
    public Adapter_resourselist.Title_List_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_resourselist, parent, false);
        return new Adapter_resourselist.Title_List_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_resourselist.Title_List_ViewHolder holder, final int position) {

        final Entity_ResourseList follow = Entity_ResourseList.get(position);

      /*  if(follow.getTitlename()==null || follow.getTitlename().equals("null") || follow.getTitlename().length()==0)
        {
            holder.title_name.setText("");
        }
        else {
            String upperString1 = follow.getTitlename().substring(0,1).toUpperCase() + follow.getTitlename().substring(1);
            holder.title_name.setText(upperString1);
        }*/


        String strs =  mCtx.getResources().getString(R.string.Rs);

        holder.designation_text.setText(follow.getTextcategory());
        holder.education_text.setText(follow.getTexteducation());
        holder.expected_salary_text.setText(strs+follow.getTextexpectedsalary());
        holder.description_text.setText(follow.getTextdesc());
      //  holder.posted_days.setText(follow.getTextdaysago());

        Date date = null;
        Date date1 = null;
        Date date2 = null;

        String strstartdate = follow.getTextdaysago();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        SimpleDateFormat mdformat1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Log.e("testing","start on create = "+strstartdate);

        Calendar calendar = Calendar.getInstance();
        String strcurrenctdate = mdformat1.format(calendar.getTime());
        try {
            date = mdformat.parse(strstartdate);

            String str = mdformat1.format(date);
            date1 = mdformat1.parse(strcurrenctdate);
            date2 = mdformat1.parse(str);

            Log.e("testing","year on create = "+str);

//in milliseconds
            long diff = date1.getTime() - date2.getTime();

            long diffSeconds = diff / 1000 % 60;

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Log.e("testing","difference days = "+diffDays+"-"+diffHours+"-"+diffMinutes+"-"+diffSeconds);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

            if (diffDays < 0){

            }else if (diffDays == 0){


                holder.posted_days.setText(diffDays+"Days Ago ");
            }else{
                holder.posted_days.setText(diffDays+"Days Ago ");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.cardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qty=follow.getId();

                if (mCallbackcropnames != null) {
                    mCallbackcropnames.OnItemClickcropNames(position, qty, 1);
                    Log.e("testing", "qtyadapter" + qty);

                }

              /*  SharedPreferences prefuserdata =mCtx.getSharedPreferences("ProductIDDetails", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();
                prefeditor.putString("ProductId", "" + qty);
                prefeditor.putString("Strcategory_id", "" + category_id);
                prefeditor.putString("title_name", "" + title_name);
                prefeditor.commit();

                Intent intent=new Intent(mCtx, Activity_ResourcesList.class);
                mCtx.startActivity(intent);


                Log.e("testing","subcatid===child========"+qty);*/
            }
        });


        holder.textresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(follow.getTextresume()));
                mCtx.startActivity(browserIntent);
            }
        });
        holder.textphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strcall = follow.getTextmobileno();
                Uri number = Uri.parse("tel:"+strcall);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                mCtx.startActivity(callIntent);
            }
        });
        holder.textemailid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strmail = follow.getTextemailid();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", strmail, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                mCtx.startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return Entity_ResourseList.size();
    }

    public class Title_List_ViewHolder extends RecyclerView.ViewHolder {

        TextView designation_text, education_text, expected_salary_text, description_text, posted_days, textresume, textphone, textemailid;

        CardView cardview1;




        public Title_List_ViewHolder(View itemView) {
            super(itemView);
            designation_text = itemView.findViewById(R.id.designation_text);
            education_text = itemView.findViewById(R.id.education_text);
            expected_salary_text = itemView.findViewById(R.id.expected_salary_text);
            description_text = itemView.findViewById(R.id.description_text);
            posted_days = itemView.findViewById(R.id.posted_days);
            cardview1 = itemView.findViewById(R.id.cardview1);
            textresume = itemView.findViewById(R.id.textresume);
            textphone = itemView.findViewById(R.id.textphone);
            textemailid = itemView.findViewById(R.id.textemailid);


        }
    }

}
