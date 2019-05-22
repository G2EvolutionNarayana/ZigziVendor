package xplotica.littlekites.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xplotica.littlekites.FeederInfo.Fragment_inbox_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.ViewHolder.Inbox_ViewHolder;

public class Fragment_inbox_Adapter extends RecyclerView.Adapter<Inbox_ViewHolder> {


    Dialog dialog1;
    EditText reply;
    String Message,Result,_reply;


    private ArrayList<Fragment_inbox_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    // private VolleySingleton mVolleySingleton;
    // private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;
    private Context mContext;
    private FeederItemListener feedItemListner;
    private final SparseBooleanArray mCollapsedStatus;


    public Fragment_inbox_Adapter(Context context, ArrayList<Fragment_inbox_feederInfo> feedList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListFeeds = feedList;
        mCollapsedStatus = new SparseBooleanArray();

    }

    public void SetListener(FeederItemListener listener) {
        feedItemListner = listener;
    }

    public void setData(ArrayList<Fragment_inbox_feederInfo> feedList) {
        mListFeeds = feedList;
        notifyDataSetChanged();
    }

    @Override
    public Inbox_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_inbox_list,parent, false);
        Inbox_ViewHolder rcv = new Inbox_ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final Inbox_ViewHolder holder, int position) {
        final Fragment_inbox_feederInfo feederInfo = mListFeeds.get(position);


        dialog1 = new Dialog(mContext);


        String feedDesc = null;

        holder.topic.setText(feederInfo.get_topic());
        holder.rollno.setText(feederInfo.get_rollno());
        holder.name.setText(feederInfo.get_name());
        holder.section.setText(feederInfo.get_section());
        holder.message.setText(feederInfo.get_message());

        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext, "Floating Action button", Toast.LENGTH_SHORT).show();
                dialog1.setContentView(R.layout.customdialogbox);
                dialog1.setTitle("Reply");

                reply = (EditText) dialog1.findViewById(R.id.description);


                // set the custom dialog components - text, image and button
                //TextView text = (TextView) dialog.findViewById(R.id.text);
                //text.setText("Android custom dialog example!");
                //ImageView image = (ImageView) dialog.findViewById(R.id.image);
                //image.setImageResource(R.drawable.user);

                Button Sendreply = (Button) dialog1.findViewById(R.id.send_reply);

                // if button is clicked, close the custom dialog
                Sendreply.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {


                        if (reply.getText()==null) {
                            reply.setError("Enter Message");
                            // registerUser();
                        }  else {

                            Toast.makeText(mContext, "All Fields are required", Toast.LENGTH_LONG).show();

                        }

                    }
                });


                dialog1.show();
            }
        });

        mPreviousPosition = position;

    }


    private void registerUser() {




        _reply = reply.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, "",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("testing", "json response111=="+response);

                        try {
                            // {"status":"Yes","id":"56","username":"shiva","response":"Success"}

                            //{"status":"Yes","id":"44","username":"","response":"Success"}
                            //JSONArray jsonArray = new JSONArray(response);

                            //for(int i=0;i<jsonArray.length();i++){
                            //JSONObject jresponse = 	jsonArray.getJSONObject(i);


                            //{"status":"Yes","id":"56","username":"shiva","email":"shiva@gmail.com","mobile":"1133557799","response":"Success"}


                            JSONObject jsonArray1 = new JSONObject(response);
                            Result = jsonArray1.getString("status");
                            Message = jsonArray1.getString("response");
                          /*  Username = jsonArray1.getString("username");
                            ID = jsonArray1.getString("id");
                            Email = jsonArray1.getString("email");
                            Mobile = jsonArray1.getString("mobile");*/

                            //Username = jsonArray1.getString("username");
                            Log.e("testing","Result == "+Result);
                            Log.e("testing","Message == "+Message);




                            //}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("testing", "json response222=="+response);

                        if (Result.equals("Yes")) {
                            dialog1.dismiss();
                           /*
                            resultdialog = "Yes";

                            linearlayouttitle.setVisibility(View.VISIBLE);
                            linearlayoutreview.setVisibility(View.VISIBLE);
                            linearlayoutrating.setVisibility(View.VISIBLE);
                            linearlayoutbutton.setVisibility(View.VISIBLE);
                            linearlayoutlogin.setVisibility(View.GONE);

                            SharedPreferences prefuserdata = getSharedPreferences("registerUser", 0);
                            SharedPreferences.Editor prefeditor = prefuserdata.edit();


                            prefeditor.putString("name", ""+ Username);
                            prefeditor.putString("id", ""+ ID);
                            //prefeditor.putString("phone", ""+ _phoneText.getText().toString());
                            prefeditor.putString("email", ""+ Email);
                            prefeditor.putString("phone", ""+ Mobile);


                            prefeditor.commit();
*/


                            //Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_LONG).show();
                            //Log.e("testing", "Message111==" + Message);
                            //openProfile();
                        }
                        else
                        {
                            dialog1.dismiss();
                            Log.e("testing", "json response == " + response);
                            Toast.makeText(mContext,Message, Toast.LENGTH_LONG).show();
                            //resultdialog = "No";

                           /* Intent intent = new Intent(context,LoginActivity.class);

                            ((LoginActivity) context).finish();
                            //intent.putExtra(KEY_PHONENO, phoneno);
                            mContext.startActivity(intent);
*/

                        }
                       /* if(response.trim().equals("success")){
                            openProfile();
                            Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_LONG).show();
                        }else{
                            Log.e("testing", "json response == " + response);
                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
                            //intent.putExtra(KEY_PHONENO, phoneno);
                            startActivity(intent);

                        }*/
                    }
                },



                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("testing","error response == "+error);
                        // Toast.makeText(context,"No network connection",Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
/*
                map.put(KEY_KEYVAL,"0");
                map.put(KEY_USERNAME,name);
                map.put(KEY_PASSWORD,pwd);*/

                Log.e("tesing","map value"+map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


    public interface FeederItemListener {
        public void onFeedClicked(int position, int resid);
    }
}