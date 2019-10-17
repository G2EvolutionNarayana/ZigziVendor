package g2evolution.Boutique;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Window;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;

import wail.splacher.com.splasher.lib.SplasherActivity;
import wail.splacher.com.splasher.models.SplasherConfig;
import wail.splacher.com.splasher.utils.Const;

public class SplashActivity extends SplasherActivity {



  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/

    @Override
    public void initSplasher(SplasherConfig splasherConfig) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        splasherConfig.setReveal_start(Const.START_TOP_LEFT) // anitmation type ..
                //---------------
                .setAnimationDuration(5000) // Reveal animation duration ..
                //---------------
              //  .setLogo(R.drawable.splash_logo) // logo src..
                .setLogo_animation(Techniques.BounceIn) // logo animation ..
                .setAnimationLogoDuration(2000) // logo animation duration ..
                .setLogoWidth(500) // logo width ..
                //---------------
                .setTitle("Boutique") // title ..
                .setTitleColor(Color.parseColor("#ffffff")) // title color ..
                .setTitleAnimation(Techniques.Bounce) // title animation ( from Android View Animations ) ..
                .setTitleSize(26) // title text size ..
                //---------------
                .setSubtitle("Best fashion collection for you") // subtitle
                .setSubtitleColor(Color.parseColor("#ffffff")) // subtitle color
                .setSubtitleAnimation(Techniques.FadeIn) // subtitle animation (from Android View Animations) ..
                .setSubtitleSize(19) // subtitle text size ..
                //---------------
                .setSubtitleTypeFace(Typeface.createFromAsset(getAssets(),"diana.otf")) // subtitle font type ..
                .setTitleTypeFace(Typeface.createFromAsset(getAssets(),"stc.otf")); // title font type ..
    }

    @Override
    public void onSplasherFinished() {

        Intent intent=new Intent(SplashActivity.this, Login_Activity.class);
        startActivity(intent);
        finish();

    }
}
