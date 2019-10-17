package g2evolution.Boutique.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import g2evolution.Boutique.R;

public class New_Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__login_);

        LinearLayout ll = (LinearLayout) findViewById(R.id.linearlayout);
        ll.setAlpha(0.2f);
    }
}
