package xplotica.littlekites.Fragment_parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import xplotica.littlekites.Activity_parent.Main2Activity;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.R;


/**
 * Created by Sujata on 22-03-2017.
 */
public class fragment_parent_message extends Fragment {


    EditText TopicName,WriteMessage;

    Button Submit;

    Spinner spinGender;

    String[] Gender = {"Select Type", "Parent","Admin"};

    ArrayAdapter<String> adaptersp1;

    String topicname,writemessage;

    String Result,Message;

    String userid;

    String strgndr;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent_message, container, false);


        spinGender= (Spinner)rootView.findViewById(R.id.spinsgender);

        adaptersp1 = new ArrayAdapter<String>(getActivity(),
                //android.R.layout.simple_spinner_item, spinnrelist2);

                R.layout.spinner_item_parent, Gender);
        spinGender.setAdapter(adaptersp1);

        TopicName =(EditText)rootView.findViewById(R.id.TopicName);
        WriteMessage =(EditText)rootView.findViewById(R.id.Writemessage);

        Submit =(Button)rootView.findViewById(R.id.submit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getActivity(),Main2Activity.class);
                startActivity(intent);
              //  savingData();
            }
        });

        return rootView;

    }


    private void savingData() {


        topicname = TopicName.getText().toString().trim();
        writemessage = WriteMessage.getText().toString().trim();
        strgndr = spinGender.getSelectedItem().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, End_Urls.STUDENT_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("testing", "json response111==" + response);

                        try {


                            JSONObject jsonArray1 = new JSONObject(response);
                            Result = jsonArray1.getString("status");
                            Message = jsonArray1.getString("response");

                            // USERROLE_ID = jsonArray1.getString("user_role");
                            // QUERY_ID = jsonArray1.getString("queryId");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("testing", "Result == " + Result);
                        Log.e("testing", "Message == " + Message);


                        Log.e("testing", "json response222==" + response);

                        if (Result.equals("yes")) {

                            Toast.makeText(getActivity(), Message, Toast.LENGTH_LONG).show();
                            Log.e("testing", "Message111==" + Message);

                            Intent intent = new Intent(getActivity(), Main2Activity.class);
                            startActivity(intent);
                            // openProfile();
                        } else {

                            Log.e("testing", "json response == " + response);
                            Toast.makeText(getActivity(), Message, Toast.LENGTH_LONG).show();


                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("testing", "error response == " + error);
                        Toast.makeText(getActivity(), "No network connection", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

              /*  map.put(End_Urls.USER_ROLE, topicname);
                map.put(End_Urls.QUERY_ID, userid);
                map.put(End_Urls.COMMENT, writemessage);
*/
                Log.e("testing", "USERROLE == " + topicname);
                Log.e("testing", "QUERYID == " + userid);

                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
