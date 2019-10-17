package g2evolution.Boutique.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import g2evolution.Boutique.R;


public class Activity_Aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView name1 = (TextView) findViewById(R.id.name1);
        TextView name2 = (TextView) findViewById(R.id.name2);

      /*  name1.setText("GMT is a daily needs booking application, main function of the application will be providing user a platform to do a booking for the house hold items and add a subscription for the same. Through the application user will do the booking and he will add money in the Wallet through the online payment. The wallet money addition option will be available from the Admin panel. Main goal is make the process easy to use.");

        name2.setText(" GMT is a daily needs booking application, main function of the application will be providing user a platform to do a booking for the house hold items and add a subscription for the same.\n" +
                "1. Through the application user will do the booking and he will add money in the Wallet through the online payment.\n" +
                "2. The wallet money addition option will be available from the Admin panel. When the admin will add the Money will be added to the Wallet of the specific user.\n" +
                "3. For the daily bookings minimum user should add 100 rupees to the Wallet and booking cut off time will be 10:00 PM.\n" +
                "4. The user can subscribe the selected items. (Take reference of Daily ninza)");

*/

    }
}
