package xplotica.littlekites.Activity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.joooonho.SelectableRoundedImageView;

import xplotica.littlekites.R;


public class Activity_Profile extends AppCompatActivity {

    String name, empId, photo, finalphoto, gender, presentaddress, email, bloodGroup, Mobile;
    SelectableRoundedImageView imgthumbnail;
    TextView textname, textempid, textemailid, textmobileno, textaddress;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        SharedPreferences prefuserdata= getSharedPreferences("myprofile",0);
        name=prefuserdata.getString("name","2");
        empId=prefuserdata.getString("empId","");
        photo=prefuserdata.getString("photo","");
        finalphoto=prefuserdata.getString("finalphoto","");
        gender=prefuserdata.getString("gender","");
        presentaddress=prefuserdata.getString("presentaddress","");
        email=prefuserdata.getString("email","");
        bloodGroup=prefuserdata.getString("bloodGroup","");
        Mobile=prefuserdata.getString("Mobile","");

        imgthumbnail = (SelectableRoundedImageView)findViewById(R.id.fedderthumbnail);
        textname = (TextView) findViewById(R.id.textname);
        textempid = (TextView) findViewById(R.id.textempid);
        textemailid = (TextView) findViewById(R.id.textemailid);
        textmobileno = (TextView) findViewById(R.id.textmobileno);
        textaddress = (TextView) findViewById(R.id.textaddress);


        if (photo == null || photo.equals("")||photo.equals(null)||photo.equals("null")){
            imgthumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgthumbnail.setOval(true);
            imgthumbnail.setImageResource(R.drawable.profiledefault);
        }else{
            imgthumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgthumbnail.setOval(true);

            Log.e("testing","Image in Dynamic="+finalphoto);
            Glide.with(Activity_Profile.this)
                    .load(Uri.parse(finalphoto))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imgthumbnail);
        }

        textname.setText(name);
        textempid.setText(empId);
        textemailid.setText(email);
        textmobileno.setText(Mobile);
        textaddress.setText(presentaddress);

        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
