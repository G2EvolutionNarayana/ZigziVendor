package g2evolution.Boutique.Activity;

import android.content.SharedPreferences;
import android.net.Uri;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import g2evolution.Boutique.Fragment.UpComing_Fragment;
import g2evolution.Boutique.Fragment.View_Pager_Fragment;
import g2evolution.Boutique.R;

public class My_Bookings_Activity extends AppCompatActivity implements UpComing_Fragment.OnFragmentInteractionListener {

    ImageView back;

    String UserId,Username,Usermail,Usermob;


    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__bookings);
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        UserId = prefuserdata.getString("UserId", "");
        Username = prefuserdata.getString("Username", "");
        Usermail = prefuserdata.getString("Usermail", "");
        Usermob = prefuserdata.getString("Usermob", "");

        Log.e("testing","Usermob===="+Usermob);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container1, new View_Pager_Fragment()).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
