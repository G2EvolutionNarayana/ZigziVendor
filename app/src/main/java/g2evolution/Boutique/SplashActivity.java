package g2evolution.Boutique;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.daimajia.androidanimations.library.Techniques;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import g2evolution.Boutique.Utility.ConnectionDetector;
import wail.splacher.com.splasher.lib.SplasherActivity;
import wail.splacher.com.splasher.models.SplasherConfig;
import wail.splacher.com.splasher.utils.Const;

public class SplashActivity extends SplasherActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    private void getFcmToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("testing", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("FCM_TOKEN", "" + token);
                        prefeditor.commit();
                        Log.e("testing", token);
//                        Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public void initSplasher(SplasherConfig splasherConfig) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        splasherConfig.setReveal_start(Const.START_TOP_LEFT) // anitmation type ..
                //---------------
                .setAnimationDuration(1000) // Reveal animation duration ..
                //---------------
                //  .setLogo(R.drawable.splash_logo) // logo src..
                .setLogo_animation(Techniques.BounceIn) // logo animation ..
                .setAnimationLogoDuration(2000) // logo animation duration ..
                .setLogoWidth(500) // logo width ..
                //---------------
                .setTitle("Zigzi") // title ..
                .setTitleColor(Color.parseColor("#ffffff")) // title color ..
                .setTitleAnimation(Techniques.Bounce) // title animation ( from Android View Animations ) ..
                .setTitleSize(26) // title text size ..
                //---------------
                .setSubtitle("Best fashion collection for you") // subtitle
                .setSubtitleColor(Color.parseColor("#ffffff")) // subtitle color
                .setSubtitleAnimation(Techniques.FadeIn) // subtitle animation (from Android View Animations) ..
                .setSubtitleSize(19) // subtitle text size ..
                //---------------
                .setSubtitleTypeFace(Typeface.createFromAsset(getAssets(), "diana.otf")) // subtitle font type ..
                .setTitleTypeFace(Typeface.createFromAsset(getAssets(), "stc.otf")); // title font type ..
    }

    @Override
    public void onSplasherFinished() {

        ConnectionDetector cd = new ConnectionDetector(SplashActivity.this);
        if (cd.isConnectingToInternet()) {
            getFcmToken();

            // new Loader().execute();
            threadcalling();

        } else {

            Toast.makeText(SplashActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }
    }

    private void threadcalling() {

        // StartSmartAnimation.startAnimation(logotxt.findViewById(R.id.logotxt), AnimationType.ZoomIn, 1000, 0, true, 100);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {


                    SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
                    String viewuseremail = prefuserdata.getString("UserId", "");

                    Log.e("testing", "viewuseremail = " + viewuseremail);

                    if (viewuseremail.equals("") || viewuseremail.equals("null") || viewuseremail.equals(null) || viewuseremail.equals("0")) {

                        Intent intent = new Intent(SplashActivity.this, Login_Activity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        Intent intent2 = new Intent(SplashActivity.this, Home_Activity.class);
                        startActivity(intent2);
                        finish();

                    }

                }
            }
        };
        timerThread.start();

    }
}
