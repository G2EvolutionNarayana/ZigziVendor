package g2evolution.GMT;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import g2evolution.GMT.Activity.Activity_cart;
import g2evolution.GMT.Fragment.Fragment_AccountInformation;
import g2evolution.GMT.Utility.ConnectionDetector;

public class Testing_Menu extends AppCompatActivity {

    private MenuItem menuItemcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        menuItemcart = menu.findItem(R.id.cartitem);


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.cartitem:

                //    menuItemcart = menu.findItem(R.id.cartitem);


                return true;


            default:

                return super.onOptionsItemSelected(item);
        }
    }
}