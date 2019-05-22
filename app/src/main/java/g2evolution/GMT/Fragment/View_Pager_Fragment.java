package g2evolution.GMT.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import g2evolution.GMT.R;


public class View_Pager_Fragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    Integer strviewpagerposition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_view__pager, container, false);

        strviewpagerposition = Integer.valueOf(0);
        viewPager = (ViewPager)rootview.findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)rootview.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        return  rootview;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        // adapter.addFragment(new Home_Fragment(), "Dashboard");
        adapter.addFragment(new UpComing_Fragment(),      "Upcoming               ");
        adapter.addFragment(new Delivered_Fragment(),      "Delivered             ");
        adapter.addFragment(new Cancelled_Fragment(),      "Cancelled             ");




        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(strviewpagerposition);
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
