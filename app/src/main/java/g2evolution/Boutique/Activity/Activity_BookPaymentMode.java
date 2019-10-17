package g2evolution.Boutique.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import g2evolution.Boutique.R;


public class Activity_BookPaymentMode extends AppCompatActivity {


    TextView subtotal,shipamount,taxamount,totalamount;
    Button pay;



    // For Radio buttons
    RadioGroup radioselGroup;
    int pos;
    int pos1;
    String spstring;

    String PAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_payment_mode);


        subtotal = (TextView) findViewById(R.id.subtotal);
        shipamount = (TextView) findViewById(R.id.shipamount);
        taxamount = (TextView) findViewById(R.id.taxamount);
        totalamount = (TextView) findViewById(R.id.totalamount);
        pay = (Button) findViewById(R.id.pay);

      //  subtotal.setText("");
        // For Radio Buttons
        radioselGroup = (RadioGroup) findViewById(R.id.radiocancel);
        spstring = "0";
        radioselGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos = radioselGroup.indexOfChild(findViewById(checkedId));


               /* Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/

                //Method 2 For Getting Index of RadioButton
                pos1 = radioselGroup.indexOfChild(findViewById(radioselGroup.getCheckedRadioButtonId()));

                /*Toast.makeText(getBaseContext(), "Method 2 ID = " + String.valueOf(pos1),
                        Toast.LENGTH_SHORT).show();*/


                switch (pos) {

                    /*case 0:


                        spstring = "1";


                        *//*Toast.makeText(getBaseContext(), "You have Clicked RadioButton 1",
                                Toast.LENGTH_SHORT).show();*//*
                        break;
                    case 1:


                        spstring = "2";


                        break;*/
                    case 0:


                        spstring = "1";

                        break;
                    case 1:


                        spstring = "2";

                        break;

                    default:


                        //The default selection is RadioButton 1
                        Toast.makeText(getApplicationContext(), "You have Clicked RadioButton 1",
                                Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        });
/*
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amt.getText().toString().equals("") || amt.getText().toString().equals("0") || amt.getText().toString().equals("null")) {
                    Toast.makeText(getApplicationContext(), "You Don't Have any Amount to Pay", Toast.LENGTH_SHORT).show();
                } else {

                    if (spstring.equals("1")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else if (spstring.equals("2")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }*/
/* else if (spstring.equals("3")) {
                        Intent intent = new Intent(getApplicationContext(), Activity_Payment_Address.class);
                        startActivity(intent);
                    } else if (spstring.equals("4")) {
                        Intent intent = new Intent(getApplicationContext(), Activity_Address.class);
                        startActivity(intent);
                    }*//*
 else {
                        Toast.makeText(getApplicationContext(), "Please Select Payment Mode", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });
*/

    }
}
