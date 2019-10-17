package g2evolution.Boutique.frag;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import g2evolution.Boutique.R;

public class Profile_Fragment extends Fragment implements View.OnClickListener{



    @BindView(R.id.vendor_name_edit_text)
    EditText vendor_name_edit_text;
    @BindView(R.id.owner_name_edit_text)
    EditText owner_name_edit_text;
    @BindView(R.id.established_edit_text)
    EditText established_edit_text;
    @BindView(R.id.registration_edit_text)
    EditText registration_edit_text;
    @BindView(R.id.employees_edit_text)
    EditText employees_edit_text;
    @BindView(R.id.mobile_edit_text)
    EditText mobile_edit_text;
    @BindView(R.id.email_edit_text)
    EditText email_edit_text;

    @BindView(R.id.update_button)
    Button update_button;
    @BindView(R.id.submit_button)
    Button submit_button;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, rootview);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "arial.ttf");


        ((TextView)rootview. findViewById(R.id.profile_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.basic_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.vendor_name_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.vendor_name_edit_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.owner_name_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.owner_name_edit_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.established_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.registration_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.registration_edit_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.employees_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.contact_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.mobile_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.mobile_edit_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.email_text)).setTypeface(typeface);
        ((TextView)rootview. findViewById(R.id.email_edit_text)).setTypeface(typeface);
        ((Button)rootview. findViewById(R.id.update_button)).setTypeface(typeface);
        ((Button)rootview. findViewById(R.id.submit_button)).setTypeface(typeface);


        vendor_name_edit_text.setEnabled(false);
        owner_name_edit_text.setEnabled(false);
        established_edit_text.setEnabled(false);
        registration_edit_text.setEnabled(false);
        employees_edit_text.setEnabled(false);
        mobile_edit_text.setEnabled(false);
        email_edit_text.setEnabled(false);

        listeners();
        return rootview;
    }

    private void listeners() {

        update_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.update_button:

                vendor_name_edit_text.setEnabled(true);
                owner_name_edit_text.setEnabled(true);
                established_edit_text.setEnabled(true);
                registration_edit_text.setEnabled(true);
                employees_edit_text.setEnabled(true);
                submit_button.setVisibility(v.VISIBLE);
                update_button.setVisibility(View.GONE);
                break;

            case R.id.submit_button:

                update_button.setVisibility(View.VISIBLE);
                submit_button.setVisibility(View.GONE);
                break;


            default:

        }

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
