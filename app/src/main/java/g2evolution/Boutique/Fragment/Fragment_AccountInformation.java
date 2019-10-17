package g2evolution.Boutique.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import g2evolution.Boutique.R;


/**
 * Created by G2evolution on 1/17/2018.
 */

public class Fragment_AccountInformation extends AppCompatActivity {

    TextView textusername, textemailid, textmobileno;

    String shuesrid, shname, shemailid, shmobileno;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_accountinformation);

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences prefuserdata =  getSharedPreferences("regId", 0);
        shuesrid = prefuserdata.getString("UserId", "");
        shname = prefuserdata.getString("Username", "");
        shemailid = prefuserdata.getString("Usermail", "");
        shmobileno = prefuserdata.getString("Usermob", "");



        Log.e("testing","shuesrid = "+shuesrid);
        Log.e("testing","shname = "+shname);
        Log.e("testing","shemailid = "+shemailid);
        Log.e("testing","shmobileno = "+shmobileno);


        textusername = (TextView) findViewById(R.id.textusername);
        textemailid = (TextView) findViewById(R.id.textemailid);
        textmobileno = (TextView) findViewById(R.id.textmobileno);

        textusername.setText(shname);
        textemailid.setText(shemailid);
        textmobileno.setText(shmobileno);
    }




}
