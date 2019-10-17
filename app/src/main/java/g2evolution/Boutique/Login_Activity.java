package g2evolution.Boutique;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {



    @BindView(R.id.user_name_edit_text)
    EditText user_name_edit_text;
    @BindView(R.id.password_edit_text)
    EditText password_edit_text;
    @BindView(R.id.login_text)
    TextView login_text;
    @BindView(R.id.sign_in_text)
    TextView sign_in_text;
    @BindView(R.id.forgot_password_text)
    TextView forgot_password_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_boutlogin);

        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "arial.ttf");

        ((TextView) findViewById(R.id.title_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.sub_title_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.sign_in_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.login_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.forgot_password_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.user_name_edit_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.password_edit_text)).setTypeface(typeface);

        listeners();
    }

    private void listeners() {

        sign_in_text.setOnClickListener(this);
        login_text.setOnClickListener(this);
        forgot_password_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sign_in_text:

                Intent intent1=new Intent(Login_Activity.this,SignUp_ACtivity.class);
                startActivity(intent1);
                break;


            case R.id.login_text:

                Intent intent2=new Intent(Login_Activity.this,Home_Activity.class);
                startActivity(intent2);
                break;


            case R.id.forgot_password_text:

                Intent intent3=new Intent(Login_Activity.this,Forgot_Password_Activity.class);
                startActivity(intent3);
                break;

                default:


        }

    }
}
