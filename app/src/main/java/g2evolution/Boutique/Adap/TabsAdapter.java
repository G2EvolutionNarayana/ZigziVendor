package g2evolution.Boutique.Adap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import g2evolution.Boutique.frag.EmploylistFragment;
import g2evolution.Boutique.frag.MembersshipFragment;
import g2evolution.Boutique.frag.Resource_Management_Fragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                EmploylistFragment employlistFragment = new EmploylistFragment();
                return employlistFragment;
            case 1:
                Resource_Management_Fragment membershipFragment = new Resource_Management_Fragment();
                return membershipFragment;

            default:
                return null;
        }
    }

}
