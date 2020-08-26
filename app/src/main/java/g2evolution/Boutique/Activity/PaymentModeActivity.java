package g2evolution.Boutique.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import g2evolution.Boutique.Adapter.PersonAdapter;
import g2evolution.Boutique.FeederInfo.Person;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;

public class PaymentModeActivity extends AppCompatActivity {
    TextView amt,total,textcouponprice, textcreditprice, shippingamount;
    Button pay;
    // For Radio buttons
    RadioGroup radioselGroup;
    int pos;
    int pos1;
    String spstring;
    String PAmount, Productid, UserId;

    String strshippingid = "";
    String strshippingamount = "";

    String status, message;
    String strsubtotal, strshipping, strfinaltotal;
    JSONArray responcearray;
    String paymentmode;
    JSONArray responcearray2;
    JSONArray responcearray3;
    String products;
    String addressid, Username, Usermail;
    String strjsonsubtotal, jsongst, jsoncoupon, jsoncredits, jsongranttotal;
    String arrayresponce, strmobileno;

    String strbuttonstatus, coupon_id, qty, _pid, afterDiscount;

    String strsubtotal_booknow, strshipping_booknow, strfinaltotal_booknow, products_booknow;

    String order_id,adminorderId, adminorderamount,Usermob,strFullAddress;

    String stractualprice, strdiscountprice, strfinalprice;

    Dialog logindialog123;

    ArrayList<HashMap<String, String>> arraylist;
    JSONParser jsonParser = new JSONParser();
    private PersonAdapter adapter;
    private List<Person> feedItemList = new ArrayList<Person>();
//    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode2);
        stractualprice="0";
        strdiscountprice="0";
        strfinalprice="0";
    }
}