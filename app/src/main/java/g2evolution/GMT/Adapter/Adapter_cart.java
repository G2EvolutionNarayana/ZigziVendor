package g2evolution.GMT.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import g2evolution.GMT.Activity.Activity_cart;
import g2evolution.GMT.EndUrl;
import g2evolution.GMT.FeederInfo.FeederInfo_cart;
import g2evolution.GMT.MainActivity;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;
import g2evolution.GMT.Viewholder.ViewHolder_cart;


public class Adapter_cart extends RecyclerView.Adapter<ViewHolder_cart> {

    private ArrayList<FeederInfo_cart> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;
    int currentNos;
    String qty;
    private OnItemClick mCallback;
    String rowid;
    String Productid,UserId;

    String status,message;

    String stockquantity;
    double num1,num2,mul;

    String userid,pid,strbuttonstatus,strquantity1;

    public Adapter_cart(Context context, ArrayList<FeederInfo_cart> feedList, OnItemClick listener){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;
        this.mCallback = listener;

        SharedPreferences prefuserdata1 = mContext.getSharedPreferences("regId", 0);
        UserId = prefuserdata1.getString("UserId", "");

        SharedPreferences prefuserdata =mContext.getSharedPreferences("regcart", 0);
        userid = prefuserdata.getString("userid","");
        pid = prefuserdata.getString("productid","");
        strbuttonstatus = prefuserdata.getString("addtocart","");
        strquantity1 = prefuserdata.getString("quantity","");




    }
    public interface OnItemClick {
        void onClickedItem(int pos, int qty, int status);
    }
    public void setData( ArrayList<FeederInfo_cart> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder_cart onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_cart, null);
        ViewHolder_cart rcv = new ViewHolder_cart(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final ViewHolder_cart holder, final int position) {
        final FeederInfo_cart feederInfo = mListFeeds.get(position);


        String feedDesc = null;



        holder.cartname.setText(feederInfo.getCartname());
        holder.cartprodetails.setText(feederInfo.getCartprodetails());
       // holder.cartamount.setText(feederInfo.getCartamount());
        holder.cartquantity.setText(feederInfo.getCartquantity());


                final String strrs = mContext.getResources().getString(R.string.Rs);
        holder.pdsubprice.setText(strrs+" "+feederInfo.getCartamount());
        holder.carttotalamount.setText(strrs+" "+feederInfo.getAfterdiscount());



        if (feederInfo.getCartimage() == null || feederInfo.getCartimage().equals("0")||feederInfo.getCartimage().equals("")||feederInfo.getCartimage().equals("null")){
            holder.cartimage.setImageResource(R.drawable.car);
        }else {
            Glide.with(mContext)
                    .load(Uri.parse(feederInfo.getCartimage()))
                    // .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .error(R.drawable.car)
                    .into(holder.cartimage);

        }



holder.butincrement.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        stockquantity=feederInfo.getStockquntity();

        int stockquantyi=Integer.parseInt(stockquantity);



        int currentNos = Integer.parseInt(holder.cartquantity.getText().toString());

        Log.e("testing","stockquantyistockquantyistockquantyi===="+stockquantyi);

        Log.e("testing","stockquantity===="+stockquantity);
        Log.e("testing","currentNoscurrentNoscurrentNoscurrentNos===="+currentNos);

        if (currentNos >= stockquantyi){

            Log.e("testing","succesddd");

            Toast.makeText(mContext, "Product quantity is not Available", Toast.LENGTH_SHORT).show();



        }else {

            holder.cartquantity.setText(String.valueOf(++currentNos));

            feederInfo.setCartquantity(""+currentNos);

            qty = holder.cartquantity.getText().toString();

            rowid = feederInfo.getCartname();

            //mSelectedItem = getAdapterPosition();

            //OnItemClick mCallback = new  OnItemClick();
            //mCallback = new OnItemClick();

            Log.e("qty", "qty ===== " + qty);
            //mCallback.onClick(qty);


            if (mCallback!=null){

                mCallback.onClickedItem(position,Integer.parseInt(qty), 1);

            }

            Log.e("testing","quantyitiy npt");
        }

    }
});

        holder.butdecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentNos = Integer.parseInt(holder.cartquantity.getText().toString());


                if (currentNos > 1){
                    holder.cartquantity.setText(String.valueOf(--currentNos));
                    feederInfo.setCartquantity(""+currentNos);

                    qty = holder.cartquantity.getText().toString();

                    rowid = feederInfo.getCartname();

                    if (mCallback != null) {
                        mCallback.onClickedItem(position,Integer.parseInt(qty), 2);
                    }

                 /*   if (currentNos == 0){
                        Log.e("testing","delete position = "+position);
                        Log.e("testing","delete title = "+feederInfo.get_title());
                        mListFeeds.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,mListFeeds.size());
                    }*/

                    Log.e("qty", "qty ===== " + qty);

                }else{

                }



            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionDetector cd = new ConnectionDetector(mContext);
                if (cd.isConnectingToInternet()) {
                    Productid = feederInfo.getId();

                    new Delete().execute();



                } else {


                    Toast.makeText(mContext, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }
            }
        });





        //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));


        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }



    class Delete extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(mContext);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(EndUrl.Delete_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){
                    Toast.makeText(mContext, ""+message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(mContext, Activity_cart.class);
                    mContext.startActivity(intent);
                    ((Activity_cart)mContext).finish();
                    //Decrease notification count
                    MainActivity.notificationCountCart--;


                }else {
                    Toast.makeText(mContext, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.Delete_inside_json,Productid);
            object.put(EndUrl.Delete_json_inside,UserId);


            //if you want to modify some value just do like this.

            details.put(EndUrl.Deletejsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

    }



    public JSONObject postJsonObject(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));
            Log.e("testing","result in post message========="+json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                // empId = post.getString("empId");
              //  userid  = post.getString("userid ");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}

