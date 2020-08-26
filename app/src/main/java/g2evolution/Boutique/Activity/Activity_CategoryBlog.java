package g2evolution.Boutique.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;

public class Activity_CategoryBlog extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{


    private SliderLayout mDemoSlider;
    HashMap<String,String> url_maps = new HashMap<String, String>();
    JSONParser jsonParser = new JSONParser();
    SliderLayout sliderLayout;

    String categoryblogid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_blog);

        SharedPreferences prefuserdata2 = getSharedPreferences("categoryblogid", 0);
        categoryblogid = prefuserdata2.getString("categoryblogid", "");

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        sliderLayout = (SliderLayout) findViewById(R.id.slider);

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(2000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.stopAutoCycle();

        new SliderImage().execute();

    }

    //for brand logos
    private class SliderImage extends AsyncTask<String, String, String> implements BaseSliderView.OnSliderClickListener

    {
        String responce;
        String status;
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_CategoryBlog.this);
            pDialog.setMessage("Loading Details");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.hide();
        }

        public String doInBackground(String... args) {
            // Create an array

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            url_maps = new HashMap<String, String>();

            userpramas.add(new BasicNameValuePair(EndUrl.SliderImgBlog_category_id, categoryblogid));

            JSONObject json = jsonParser.makeHttpRequest(EndUrl.SliderImgBlog_URL, "GET", userpramas);

            if(json==null){

                return responce;
            }else {

                Log.e("testing", "json result =mDemoSlider " + json);

                try {
                    status = json.getString("status");

                    String arrayresponce = json.getString("data");
                    Log.e("testing", "adapter value=" + arrayresponce);

                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray" + "" + " value=" + responcearray);

                    for (int i = 0; i < responcearray.length(); i++) {

                        JSONObject post = responcearray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();

                        url_maps.put(post.getString("name"), post.getString("image"));

                        Log.e("testing", "Eventdetailsimage" + post.getString("image"));

                    }

                } catch (JSONException e)

                {
                    e.printStackTrace();
                }
            }


            return responce;
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            pDialog.dismiss();


            if (status==null){


            }else if (status.equals("success")){

                Log.e("testing12312", "SliderViewresult is 12= " + responce);

                for (String name : url_maps.keySet()) {

                    TextSliderView textSliderView = new TextSliderView(Activity_CategoryBlog.this);
                    // initialize a SliderLayout
                    textSliderView
                            .description("")
                            .image(url_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(this);
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);

                    textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override public void onSliderClick(BaseSliderView slider) {

                        }
                    });
                    //  banner_slider.addSlider(textSliderView);
                    mDemoSlider.addSlider(textSliderView);

                }


            }else if (status.equals("fail")){


            }else{

            }

        }

        @Override
        public void onSliderClick(BaseSliderView slider) {

        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        // Toast.makeText(getActivity(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

}
