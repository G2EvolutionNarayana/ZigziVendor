package g2evolution.Boutique.frag;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import g2evolution.Boutique.Adap.TabsAdapter;
import g2evolution.Boutique.R;

public class EmployHireFragmet extends Fragment {
    private View mRootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootview = inflater.inflate(R.layout.fragment_employ_hire_fragmet, container, false);
        mInitWidgets();
        return mRootview;
    }

    private void mInitWidgets() {
        TabLayout tabLayout = mRootview.findViewById(R.id.TabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Candidates"));
        tabLayout.addTab(tabLayout.newTab().setText("Membership"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

         ViewPager viewPager = mRootview.findViewById(R.id.ViewPager);
        TabsAdapter tabsAdapter = new TabsAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}