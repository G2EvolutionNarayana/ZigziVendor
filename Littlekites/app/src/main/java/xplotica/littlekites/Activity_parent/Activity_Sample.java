package xplotica.littlekites.Activity_parent;

/**
 * Created by G2evolution on 4/28/2017.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.roomorama.caldroid.CaldroidFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import xplotica.littlekites.R;

public class Activity_Sample extends AppCompatActivity {
    private CaldroidFragment caldroidFragment;
    ArrayList<Date> disabledDates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        caldroidFragment = new CaldroidFragment();

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt( CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY    );
        caldroidFragment.setArguments(args);

        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        final Button customizeButton = (Button) findViewById(R.id.customize_button);
        // Customize the calendar
        customizeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Set disabled dates
                try {
                    disabledDates = new ArrayList<Date>();

                    disabledDates.add(getDate("2017-04-28"));
                    disabledDates.add(getDate("2017-04-22"));
                    disabledDates.add(getDate("2017-04-23"));

                    // Customize
                    caldroidFragment.setDisableDates(disabledDates);
                    // Refresh
                    caldroidFragment.refreshView();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        // month start at 1. Need to minus 1 to get javaMonth
        calendar.set(year, month - 1, day);

        return calendar.getTime();
    }

    private Date getDate(String strDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(strDate);

        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        calendar.setTime(date);

        return calendar.getTime();
    }
}