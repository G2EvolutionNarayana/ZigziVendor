package g2evolution.Boutique.frag;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import g2evolution.Boutique.R;


public class MembersshipFragment extends Fragment {

    private View mRootview;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootview=inflater.inflate(R.layout.fragment_membersship, container, false);
        mInitWidgest();

        return mRootview;
    }

    private void mInitWidgest() {

    }
}