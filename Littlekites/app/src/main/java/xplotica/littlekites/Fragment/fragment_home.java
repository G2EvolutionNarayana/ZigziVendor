package xplotica.littlekites.Fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;

import xplotica.littlekites.Adapter.RecyclerViewAdapter_home;
import xplotica.littlekites.FeederInfo.ItemObject_home;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by Sujata on 15-03-2017.
 */
public class fragment_home extends Fragment {

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<ItemObject_home> allItems1 = new ArrayList<ItemObject_home>();

    private RecyclerView mFeedRecyler;
    private ArrayList<ItemObject_home> mListFeederInfo;
    private ItemObject_home adapter;
    RecyclerViewAdapter_home mAdapterFeeds;


    String schoolid,staffid,type,classid,sectionid, mobileno;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;

    // RecyclerView rView;

    String Result;
    private GridLayoutManager lLayout;
    SelectableRoundedImageView imgthumbnail;
    String staff_id, school_id, firstName, fcmToken, otp, middleName, lastName, empId, gender, dob, bloodGroup, presentaddress, state, city, pinCode, Mobile, email, maritalStatus, staffType, staffDesignation, joiningDate, subjectHandling, panNumber, expYear, previousEmployer, bankName, accountNumber, dateofLeaving, highSchoolPassYear, secondarySchoolPassYear, teacherTraining, teacherTrainingCertificate, otherCertificate, fatherName, fatherEmail, motherName, motherEmail, permanantPhone, permanantAddress, permanantState, permanantCity, permanantPincode, created_by, reg_date, status;
    private MenuItem menuItemcart;
    private ActionBarDrawerToggle mDrawerToggle;

    String photo;


    TextView textprofilename;
    String[]Name = new String[]{"Attendance","Diary","Home Work","Message","Gallery","History"};

    Integer[]Image={R.drawable.attendance, R.drawable.diary, R.drawable.homework, R.drawable.message, R.drawable.gallery, R.drawable.history};

    Integer[]Color= {R.color.blue, R.color.orange, R.color.purple, R.color.pink, R.color.brown, R.color.dark_green};


    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_home, container, false);


        lLayout = new GridLayoutManager(getActivity(),2);
        // rView = (RecyclerView) findViewById(R.id.recycler_view);
        // rView.setHasFixedSize(true);
        // rView.setLayoutManager(lLayout);
        mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);





        return rootView;

    }

    private void setUpRecycler() {
        mListFeederInfo =new ArrayList<ItemObject_home>();

        for(int i=0;i<Name.length;i++){
            ItemObject_home feedInfo = new ItemObject_home();
            feedInfo.setPhoto(Image[i]);
            feedInfo.setName(Name[i]);
            feedInfo.setBackcolor(Color[i]);
            mListFeederInfo.add(feedInfo);
        }

        mAdapterFeeds= new RecyclerViewAdapter_home(getActivity(),mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);

    }

}
