package g2evolution.Boutique;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class View_Details_Activity extends AppCompatActivity {


    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.booking_image)
    ImageView booking_image;
    @BindView(R.id.order_no_text1)
    TextView order_no_text1;
    @BindView(R.id.delivery_date_text1)
    TextView delivery_date_text1;

    @BindView(R.id.cust_name_text1)
    TextView cust_name_text1;
    @BindView(R.id.gender_text1)
    TextView gender_text1;
    @BindView(R.id.mobile_text1)
    TextView mobile_text1;

    @BindView(R.id.color_text1)
    TextView color_text1;
    @BindView(R.id.type_text1)
    TextView type_text1;
    @BindView(R.id.length_text)
    TextView length_text1;
    @BindView(R.id.expecting_text1)
    TextView expecting_text1;

    @BindView(R.id.total_price_text1)
    TextView total_price_text1;
    @BindView(R.id.advance_price_text1)
    TextView advance_price_text1;
    @BindView(R.id.balance_price_text1)
    TextView balance_price_text1;
    @BindView(R.id.payment_mode_text1)
    TextView payment_mode_text1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__details);
        ButterKnife.bind(this);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "arial.ttf");

        ((TextView)findViewById(R.id.details_text)).setTypeface(typeface);


        ((TextView)findViewById(R.id.order_no_text)).setTypeface(typeface);
        ((TextView)findViewById(R.id.order_no_text1)).setTypeface(typeface);
        ((TextView)findViewById(R.id.order_date_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.order_date_text1)).setTypeface(typeface);
        ((TextView)findViewById(R.id.delivery_date_text)).setTypeface(typeface);
        ((TextView)findViewById(R.id.delivery_date_text1)).setTypeface(typeface);

        ((TextView)findViewById(R.id.customer_details_text)).setTypeface(typeface);
        ((TextView)findViewById(R.id.cust_name_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.cust_name_text1)).setTypeface(typeface);
        ((TextView) findViewById(R.id.gender_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.gender_text1)).setTypeface(typeface);
        ((TextView) findViewById(R.id.mobile_text)).setTypeface(typeface);

        ((TextView) findViewById(R.id.product_details_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.fabric_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.color_text1)).setTypeface(typeface);
        ((TextView) findViewById(R.id.type_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.type_text1)).setTypeface(typeface);
        ((TextView) findViewById(R.id.length_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.expecting_type)).setTypeface(typeface);
        ((TextView) findViewById(R.id.expecting_text1)).setTypeface(typeface);
        ((TextView) findViewById(R.id.fare_text)).setTypeface(typeface);

        ((TextView) findViewById(R.id.total_price)).setTypeface(typeface);
        ((TextView) findViewById(R.id.total_price_text1)).setTypeface(typeface);
        ((TextView) findViewById(R.id.advance_price)).setTypeface(typeface);
        ((TextView) findViewById(R.id.advance_price_text1)).setTypeface(typeface);
        ((TextView) findViewById(R.id.balance_price)).setTypeface(typeface);
        ((TextView) findViewById(R.id.balance_price_text1)).setTypeface(typeface);
        ((TextView) findViewById(R.id.payment_mode_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.payment_mode_text1)).setTypeface(typeface);

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
