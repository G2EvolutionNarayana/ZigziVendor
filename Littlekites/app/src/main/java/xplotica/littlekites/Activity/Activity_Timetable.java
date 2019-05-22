package xplotica.littlekites.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import xplotica.littlekites.Fragment.fragment_fri;
import xplotica.littlekites.Fragment.fragment_mon;
import xplotica.littlekites.Fragment.fragment_sat;
import xplotica.littlekites.Fragment.fragment_thu;
import xplotica.littlekites.Fragment.fragment_tue;
import xplotica.littlekites.Fragment.fragment_wed;
import xplotica.littlekites.R;

public class Activity_Timetable extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    ImageView back;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        context = this;

        viewPager = (ViewPager) findViewById(R.id.pager1);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout1);
        tabLayout.setupWithViewPager(viewPager);


        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               /* Intent intent = new Intent(getApplicationContext(),Activity_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new fragment_mon(), "Mon");
        adapter.addFragment(new fragment_tue(), "Tue");
        adapter.addFragment(new fragment_wed(), "Wed");
        adapter.addFragment(new fragment_thu(), "Thu");
        adapter.addFragment(new fragment_fri(), "Fri");
        adapter.addFragment(new fragment_sat(), "Sat");

        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override

        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }


}
