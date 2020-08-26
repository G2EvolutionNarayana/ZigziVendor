package g2evolution.Boutique.frag;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import g2evolution.Boutique.Adap.EmployListAdapter;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Retrofit.Api;
import g2evolution.Boutique.Retrofit.ApiClient;
import g2evolution.Boutique.Retrofit.EmployListDataModel.Datum;
import g2evolution.Boutique.Retrofit.EmployListDataModel.Example;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmploylistFragment extends Fragment {
    public View mRootView;
    RecyclerView xRecyview;
    List<Datum> arrayList;
    EmployListAdapter employListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView=inflater.inflate(R.layout.fragment_employlist, container, false);
        mInitWidgets();
        EmployiList();
        return mRootView;
    }

    private void mInitWidgets() {
        xRecyview=mRootView.findViewById(R.id.xRecyview);
        xRecyview.setLayoutManager(new LinearLayoutManager(getActivity()));
        xRecyview.setHasFixedSize(true);

    }

    private void EmployiList() {


        final ProgressDialog pProgressDialog;
        pProgressDialog = new ProgressDialog(getActivity());
        pProgressDialog.setMessage("Please Wait ...");
        pProgressDialog.setIndeterminate(false);
        pProgressDialog.setCancelable(false);
        pProgressDialog.show();

        //call retrofit
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<Example> login = api.EmployList();

        login.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                pProgressDialog.dismiss();

                if (response.body().getType() == null || response.body().getType().length() == 0){

                }else if (response.body().getType().equals("success")) {
                    if (response.body().getType() != null ){
                        arrayList=new ArrayList<>();
                        arrayList = response.body().getData();
                        employListAdapter = new EmployListAdapter(getActivity(), response.body().getData());
                        xRecyview.setAdapter(employListAdapter);
                    }
                } else  {
                    Log.e("testing","error");
                    Toast.makeText(getActivity(), response.body().getType(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                pProgressDialog.dismiss();

            }
        });
    }


}