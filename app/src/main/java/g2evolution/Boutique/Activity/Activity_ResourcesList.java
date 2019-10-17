package g2evolution.Boutique.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import g2evolution.Boutique.Adapter.Adapter_resourselist;
import g2evolution.Boutique.R;
import g2evolution.Boutique.entit.Entity_ResourseList;

public class Activity_ResourcesList extends AppCompatActivity implements Adapter_resourselist.OnItemClickcropNames {



    public  static RecyclerView dashboard_reccyler;
    public  static
    Adapter_resourselist.OnItemClickcropNames mCallbackcropnames;
    public  static ArrayList<Entity_ResourseList> openings_entity=new ArrayList<Entity_ResourseList>();
    public  static Adapter_resourselist openings_adapter;

    String[] TextDesignation=new String[]{"Tailor",
            "Supplier",
            "Business Planer"};
    String[] TextEducation=new String[]{"Graduation",
            "Graduation",
            "Post Graduation"};
    String[] TextExpectedSalary=new String[]{"20000-30000",
            "15000-20000",
            "35000-40000"};

    String[] TextDesc=new String[]{"Good Knowledge in Designing and stiching",
            "Experience in marketing and Fast Supplying",
            "Have Good Knowledge on Designing, Marketing Plan and Speak well in English and Hindi Languages"};

    String[] TextDaysAgo=new String[]{"0 Days Ago",
            "1 Day Ago",
            "5 Days Ago"};

    LinearLayout linearfilter, linearsort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resourceslist);

        dashboard_reccyler=(RecyclerView)findViewById(R.id.dashboard_reccyler);
        dashboard_reccyler.setHasFixedSize(true);
        dashboard_reccyler.setLayoutManager(new LinearLayoutManager(Activity_ResourcesList.this));
        // GridLayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(),2);
        //  dashboard_reccyler.setLayoutManager(mLayoutManager1);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        linearfilter = (LinearLayout) findViewById(R.id.linearfilter);
        linearsort = (LinearLayout) findViewById(R.id.linearsort);
        linearfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filtering();

            }
        });
        linearsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sorting();
            }
        });
        setUpReccyler();


    }

    private void filtering() {

        final List<String> points = new ArrayList<>();

        final Dialog logindialog = new Dialog(Activity_ResourcesList.this);
        logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater1 = (LayoutInflater) Activity_ResourcesList.this.getSystemService(Activity_ResourcesList.this.LAYOUT_INFLATER_SERVICE);
        View convertView1 = (View) inflater1.inflate(R.layout.dialog_resourcefilter, null);

        //  StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

        logindialog.setContentView(convertView1);
        //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
        // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
        logindialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
        lp1.copyFrom(logindialog.getWindow().getAttributes());
        lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp1.gravity = Gravity.CENTER;
        logindialog.getWindow().setAttributes(lp1);



        TextView text_submit = (TextView) convertView1.findViewById(R.id.text_submit);
        text_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logindialog.dismiss();
            }
        });

        logindialog.show();

    }

    private void sorting() {
        final Dialog logindialog = new Dialog(Activity_ResourcesList.this);
        logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater1 = (LayoutInflater) Activity_ResourcesList.this.getSystemService(Activity_ResourcesList.this.LAYOUT_INFLATER_SERVICE);
        View convertView1 = (View) inflater1.inflate(R.layout.dialog_resourcesort, null);

        //  StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

        logindialog.setContentView(convertView1);
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

    @Override
    public void OnItemClickcropNames(int pos, String qty, int status) {

    }




    private void setUpReccyler() {
        openings_entity = new ArrayList<Entity_ResourseList>();

        for (int i = 0; i < TextDesignation.length; i++) {
            Entity_ResourseList feedInfo = new Entity_ResourseList();
            // feedInfo.setImages(productImage[i]);
            feedInfo.setTextdesignation(TextDesignation[i]);
            feedInfo.setTexteducation(TextEducation[i]);
            feedInfo.setTextexpectedsalary(TextExpectedSalary[i]);
            feedInfo.setTextdesc(TextDesc[i]);
            feedInfo.setTextdaysago(TextDaysAgo[i]);

            openings_entity.add(feedInfo);
        }
        openings_adapter = new Adapter_resourselist(Activity_ResourcesList.this, openings_entity, mCallbackcropnames);
        dashboard_reccyler.setAdapter(openings_adapter);
    }


}
