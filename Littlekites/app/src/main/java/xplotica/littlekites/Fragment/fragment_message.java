package xplotica.littlekites.Fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import xplotica.littlekites.R;

/**
 * Created by Sujata on 22-03-2017.
 */
public class fragment_message  extends Fragment {

    private Spinner spinner1,spinner2,spinner3;

    CardView cardview1, cardview2, cardview3, cardview4, cardview5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);

        spinner1=(Spinner)rootView.findViewById(R.id.spinner1);
        spinner2=(Spinner)rootView.findViewById(R.id.spinner2);
        spinner3= (Spinner)rootView.findViewById(R.id.spinner3);

        addItemsOnSpinner1();
        addItemsOnSpinner2();
        addItemsOnSpinner3();

        cardview1 = (CardView)rootView.findViewById(R.id.Cardview1);
        cardview2 = (CardView)rootView.findViewById(R.id.Cardview2);
        cardview3 = (CardView)rootView.findViewById(R.id.Cardview3);
        cardview4 = (CardView)rootView.findViewById(R.id.Cardview4);
        cardview5 = (CardView)rootView.findViewById(R.id.Cardview5);

        cardview2.setVisibility(View.GONE);
        cardview3.setVisibility(View.GONE);

        spinner1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        // Toast.makeText(context, "Spinner1: position=" + position + " id=" + id, Toast.LENGTH_SHORT).show();
                        switch (position) {

                            case 0:
                                cardview2.setVisibility(View.GONE);
                                cardview3.setVisibility(View.GONE);

                                //   Toast.makeText(context, "111111" , Toast.LENGTH_SHORT).show();


                                break;
                            case 1:
                                cardview2.setVisibility(View.VISIBLE);
                                cardview3.setVisibility(View.GONE);
                                //  Toast.makeText(context, "22222222", Toast.LENGTH_SHORT).show();


                                break;


                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        spinner2.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        // Toast.makeText(context, "Spinner1: position=" + position + " id=" + id, Toast.LENGTH_SHORT).show();
                        switch (position) {

                            case 0:
                                cardview3.setVisibility(View.GONE);
                                //   Toast.makeText(context, "111111" , Toast.LENGTH_SHORT).show();


                                break;
                            case 1:
                                cardview3.setVisibility(View.VISIBLE);
                                //  Toast.makeText(context, "22222222", Toast.LENGTH_SHORT).show();


                                break;


                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


        return rootView;

    }

    // add items into spinner1 dynamically
    public void addItemsOnSpinner1() {

        List<String> list = new ArrayList<String>();

        list.add("Appointment Mode");
       // list.add("Parent");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }


    // add items into spinner2 dynamically
    public void addItemsOnSpinner2() {

        List<String> list = new ArrayList<String>();

        list.add("All Parent");
        list.add("Individual Parent");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }


    // add items into spinner2 dynamically
    public void addItemsOnSpinner3() {

        List<String> list = new ArrayList<String>();

        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter);
    }

}
