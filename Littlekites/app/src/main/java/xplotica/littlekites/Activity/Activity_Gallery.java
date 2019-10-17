package xplotica.littlekites.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.Palette;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;


public class Activity_Gallery extends AppCompatActivity implements ImageGalleryAdapter.ImageThumbnailLoader, FullScreenImageGalleryAdapter.FullScreenImageLoader{


    // region Member Variables
    private PaletteColorType paletteColorType;

    JSONParser jsonParser = new JSONParser();
    JSONArray posts2;
    ArrayList<String> listdata = new ArrayList<String>();

    String[] steimages = {"http://androidappfirst.com/school/uploads/gallery/a4fd29005696d92e0fc4cd3931454609.jpg","http://androidappfirst.com/school/uploads/gallery/8eaaecdac1ff219192806acba7978a1b","http://androidappfirst.com/school/uploads/gallery/f356ceafa408e61cd1c62cfc39752b32.jpeg","http://androidappfirst.com/school/uploads/gallery/f356ceafa408e61cd1c62cfc39752b32.jpeg"};
    // endregion

    /*  // region Listeners
      @OnClick(R.id.image_gallery_activity_btn)
      public void onImageGalleryActivityButtonClicked() {
          Intent intent = new Intent(MainActivity.this, HostImageGalleryActivity.class);

          String[] images = getResources().getStringArray(R.array.unsplash_images);
          Bundle bundle = new Bundle();
          bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, new ArrayList<>(Arrays.asList(images)));
          Log.e("testing", "array========");
          bundle.putString(ImageGalleryActivity.KEY_TITLE, "Unsplash Images");
          Log.e("testing", "array");
          intent.putExtras(bundle);

          startActivity(intent);
      }

      @OnClick(R.id.image_gallery_fragment_btn)
      public void onImageGalleryFragmentButtonClicked() {
          Intent intent = new Intent(MainActivity.this, HostImageGalleryActivity.class);

          String[] images = getResources().getStringArray(R.array.unsplash_images);
          Bundle bundle = new Bundle();
          bundle.putStringArrayList(ImageGalleryFragment.KEY_IMAGES, new ArrayList<>(Arrays.asList(images)));
          bundle.putString(ImageGalleryActivity.KEY_TITLE, "Unsplash Images");
          intent.putExtras(bundle);

          startActivity(intent);
      }*/
    // endregion
    Button gallery;


    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        /*back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Activity_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/


        gallery=(Button)findViewById(R.id.image_gallery_activity_btn);
       // button1=(Button)findViewById(R.id.image_gallery_fragment_btn);
        ImageGalleryActivity.setImageThumbnailLoader(this);
        ImageGalleryFragment.setImageThumbnailLoader(this);
        FullScreenImageGalleryActivity.setFullScreenImageLoader(this);

        // optionally set background color using Palette for full screen images
        paletteColorType = PaletteColorType.VIBRANT;


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                if (cd.isConnectingToInternet()) {

                    new userdata().execute();


                } else {


                    Toast.makeText(Activity_Gallery.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }


            }
        });

      /*  button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Gallery.this, HostImageGalleryActivity.class);

                String[] images = getResources().getStringArray(R.array.unsplash_images);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(ImageGalleryFragment.KEY_IMAGES, new ArrayList<>(Arrays.asList(images)));
                bundle.putString(ImageGalleryActivity.KEY_TITLE, "Unsplash Images");
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });*/
    }


    public class userdata extends AsyncTask<String, String, String> {


        String responce;
        String filepath;
        String headers;
        String childs;


        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_Gallery.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String doInBackground(String... args) {
            // Create an array
            // arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();




            Log.e("testing", "productid loginType=" + 140);
            userpramas.add(new BasicNameValuePair("loginType", "1"));
            userpramas.add(new BasicNameValuePair("schoolId", "2"));
            JSONObject json = jsonParser.makeHttpRequest("http://androidappfirst.com/school/School_android/getGalley", "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");

            Log.e("testing", "jon2222222222222");
            // DateFormat dateFormat = new Si   mpleDateFormat("yyyy-MM-dd");
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {  try {
                JSONObject response = new JSONObject(json.toString());

                Log.e("testing", "jsonParser2222" + json);

                //JSONObject jsonArray1 = new JSONObject(json.toString());
                // Result = response.getString("status");
                JSONArray posts = response.optJSONArray("data");
                Log.e("testing", "jsonParser3333" + posts);

                filepath = response.getString("filepath");


              /* if (posts.equals(null)){
                   listDataHeader = new ArrayList<FeedHeader>();
                   listDataChild= new HashMap<String, FeedDetail>();
               }else{
                   listDataHeader.clear();
                   listDataChild.clear();
               }*/
            /*Initialize array if null*/
                  /*  if (null == listDataHeader || null == listDataChild) {
                        listDataHeader = new ArrayList<FeedHeader>();
                        // listDataChild = new ArrayList<FeedDetail>();
                        listDataChild = new HashMap<String, List<FeedDetail>>();
                    } else {
                        listDataHeader.clear();
                        listDataChild.clear();
                    }*/

                if (posts == null) {
                    Log.e("testing", "jon11111111111111111");

                    //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
                } else {

                    Log.e("testing", "jon122222211");
                    Log.e("testing", "jsonParser4444" + posts);

                    for (int i = 0; i < posts.length(); i++) {
                        Log.e("testing", "" + posts);

                        Log.e("testing", "" + i);
                        JSONObject post = posts.optJSONObject(i);



                        posts2 = post.optJSONArray("galleryImages");
                        for (int i1 = 0; i1 < posts2.length(); i1++) {
                            //JSONObject post2 = posts2.optJSONObject(i1);
                            listdata.add(filepath+posts2.getString(i1));


                            //    images.add(post2.getString("slider_image"));
                            // Log.e("testing","image"+post2.getString("slider_image"));

                            //find the group position inside the list
                            //groupPosition = deptList.indexOf(headerInfo);
                        }

                    }



                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


                return responce;
            }

        }


        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
               pDialog.dismiss();
            Log.e("testing", "result is = " + responce);

           /* Intent intent = new Intent(mContext, GalleryActivity.class);
            intent.putStringArrayListExtra(GalleryActivity.EXTRA_NAME, images);
            mContext.startActivity(intent);
*/
            Log.e("testing", "list array============ is = " + listdata);
            Log.e("testing", "list filepath============ is = " + filepath);


            String[] mStringArray = new String[listdata.size()];
            mStringArray = listdata.toArray(mStringArray);

            for(int i = 0; i < mStringArray.length ; i++){
                Log.d("string is",(String)mStringArray[i]);

            }



            Intent intent = new Intent(Activity_Gallery.this, HostImageGalleryActivity.class);

          //  String[] images = getResources().getStringArray(R.array.unsplash_images);
           // String[] images = listdata;


            //Log.e("testing", "steimages===============" + steimages);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, new ArrayList<>(Arrays.asList(mStringArray)));
          //  Log.e("testing", "array========");
            bundle.putString(ImageGalleryActivity.KEY_TITLE, "Gallery Images");
           // Log.e("testing", "array");
            intent.putExtras(bundle);


            startActivity(intent);

        }

    }

    // endregion

    // region ImageGalleryAdapter.ImageThumbnailLoader Methods
    @Override
    public void loadImageThumbnail(final ImageView iv, String imageUrl, int dimension) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    .resize(dimension, dimension)
                    .centerCrop()
                    .into(iv);
        } else {
            iv.setImageDrawable(null);
        }
    }
    // endregion

    // region FullScreenImageGalleryAdapter.FullScreenImageLoader
    @Override
    public void loadFullScreenImage(final ImageView iv, String imageUrl, int width, final LinearLayout bgLinearLayout) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    .resize(width, 0)
                    .into(iv, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    applyPalette(palette, bgLinearLayout);
                                }
                            });
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else {
            iv.setImageDrawable(null);
        }
    }
    // endregion

    // region Helper Methods
    private void applyPalette(Palette palette, ViewGroup viewGroup){
        int bgColor = getBackgroundColor(palette);
        if (bgColor != -1)
            viewGroup.setBackgroundColor(bgColor);
    }

    private void applyPalette(Palette palette, View view){
        int bgColor = getBackgroundColor(palette);
        if (bgColor != -1)
            view.setBackgroundColor(bgColor);
    }

    private int getBackgroundColor(Palette palette) {
        int bgColor = -1;

        int vibrantColor = palette.getVibrantColor(0x000000);
        int lightVibrantColor = palette.getLightVibrantColor(0x000000);
        int darkVibrantColor = palette.getDarkVibrantColor(0x000000);

        int mutedColor = palette.getMutedColor(0x000000);
        int lightMutedColor = palette.getLightMutedColor(0x000000);
        int darkMutedColor = palette.getDarkMutedColor(0x000000);

        if (paletteColorType != null) {
            switch (paletteColorType) {
                case VIBRANT:
                    if (vibrantColor != 0) { // primary option
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) { // fallback options
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case LIGHT_VIBRANT:
                    if (lightVibrantColor != 0) { // primary option
                        bgColor = lightVibrantColor;
                    } else if (vibrantColor != 0) { // fallback options
                        bgColor = vibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case DARK_VIBRANT:
                    if (darkVibrantColor != 0) { // primary option
                        bgColor = darkVibrantColor;
                    } else if (vibrantColor != 0) { // fallback options
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case MUTED:
                    if (mutedColor != 0) { // primary option
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) { // fallback options
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                case LIGHT_MUTED:
                    if (lightMutedColor != 0) { // primary option
                        bgColor = lightMutedColor;
                    } else if (mutedColor != 0) { // fallback options
                        bgColor = mutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                case DARK_MUTED:
                    if (darkMutedColor != 0) { // primary option
                        bgColor = darkMutedColor;
                    } else if (mutedColor != 0) { // fallback options
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                default:
                    break;
            }
        }

        return bgColor;
    }
    // endregion
}