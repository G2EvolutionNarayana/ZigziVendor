package xplotica.littlekites;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import xplotica.littlekites.Activity.Activity_home;
import xplotica.littlekites.Activity_parent.Activity_Parent_Home;

public class Activity_Spalsh extends AppCompatActivity {

    Context context;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity__spalsh);

        SharedPreferences prefuserdata = this.getSharedPreferences("registerUser", 0);
        type = prefuserdata.getString("type", "");

        if(isWorkingInternetPersent()){
            splash();
        }
        else{
            showAlertDialog(Activity_Spalsh.this, "Internet Connection",
                    "You don't have internet connection", false);
        }
        context = this;
       // splash();
    }

    public void splash() {
        Thread timerTread = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {


                    SharedPreferences prefuserdata = getSharedPreferences("loginresponse", 0);
                    String viewuseremail = prefuserdata.getString("loginresponse", "");


                  //  if (viewuseremail == null || viewuseremail.equals("")viewuseremail.equals("") || viewuseremail.equals("null") || viewuseremail.equals(null) || viewuseremail.equals("0")) {
                    if (viewuseremail.equals("yes")){

                        SharedPreferences prefuserdata2 = getSharedPreferences("registerUser", 0);
                        type = prefuserdata2.getString("type", "");


                        if(type.equals("1"))
                        {
                            Intent intent2 = new Intent(Activity_Spalsh.this,Activity_home.class);
                            startActivity(intent2);
                            finish();

                        }else if (type.equals("2")){
                            Intent intent2 = new Intent(Activity_Spalsh.this,Activity_Parent_Home.class);
                            startActivity(intent2);
                            finish();

                        }else{
                            Intent intent = new Intent(Activity_Spalsh.this, Activity_Details.class);
                            startActivity(intent);
                            finish();
                        }



                    } else {


                            Intent intent2 = new Intent(Activity_Spalsh.this,Login.class);
                            startActivity(intent2);
                            finish();



                    }


                }
            }
        };
        timerTread.start();
    }

    public boolean isWorkingInternetPersent() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        // alertDialog.setIcon((status) ? R.mipmap.ic_launcher : R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
                System.exit(0);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
