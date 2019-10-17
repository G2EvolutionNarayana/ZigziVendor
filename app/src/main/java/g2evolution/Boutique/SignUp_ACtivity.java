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

public class SignUp_ACtivity extends AppCompatActivity implements View.OnClickListener {



    @BindView(R.id.user_name_edit_text)
    EditText user_name_edit_text;
    @BindView(R.id.phone_number_edit_text)
    EditText phone_number_edit_text;
    @BindView(R.id.password_edit_text)
    EditText password_edit_text;
    @BindView(R.id.confirm_password_edit_text)
    EditText confirm_password_edit_text;
    @BindView(R.id.sign_up_text)
    TextView sign_up_text;
    @BindView(R.id.member_text)
    TextView member_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);


        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "arial.ttf");

        ((TextView) findViewById(R.id.title_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.sub_title_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.sign_up_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.member_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.user_name_edit_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.phone_number_edit_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.password_edit_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.confirm_password_edit_text)).setTypeface(typeface);

        listeners();
    }

    private void listeners() {

        sign_up_text.setOnClickListener(this);
        member_text.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sign_up_text:

                Intent intent1=new Intent(SignUp_ACtivity.this,OTP_Activity.class);
                startActivity(intent1);
                break;


            case R.id.member_text:

                Intent intent2=new Intent(SignUp_ACtivity.this,Login_Activity.class);
                startActivity(intent2);
                break;




            default:


        }

    }
}
