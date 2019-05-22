package xplotica.littlekites.Adapter_parent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import xplotica.littlekites.Activity_parent.HostImageGalleryActivity_Parent;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.ParentGallery_List;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;
import xplotica.littlekites.ViewHolder_parent.ParentGallery_ViewHolderFeed;


/**
 * Created by santa on 3/21/2017.
 */
public class ParentGallery_Adapter extends RecyclerView.Adapter<ParentGallery_ViewHolderFeed>  implements ImageGalleryAdapter.ImageThumbnailLoader, FullScreenImageGalleryAdapter.FullScreenImageLoader{



    private ArrayList<ParentGallery_List> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;

    String Pid,name,company,price,photo;
    String rating;

    private PaletteColorType paletteColorType;

    JSONParser jsonParser = new JSONParser();
    JSONArray posts2;
    ArrayList<String> listdata = new ArrayList<String>();
    String School_id;
    String strgalleryid;


    public ParentGallery_Adapter(Context context, ArrayList<ParentGallery_List> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;

        SharedPreferences prefuserdata= mContext.getSharedPreferences("registerUser",0);
        School_id=prefuserdata.getString("schoolid","");


        ImageGalleryActivity.setImageThumbnailLoader(this);
        ImageGalleryFragment.setImageThumbnailLoader(this);
        FullScreenImageGalleryActivity.setFullScreenImageLoader(this);

        // optionally set background color using Palette for full screen images
        paletteColorType = PaletteColorType.VIBRANT;



    }

    public void setData( ArrayList<ParentGallery_List> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();



    }


    @Override
    public ParentGallery_ViewHolderFeed onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parentgallery_list, null);
        ParentGallery_ViewHolderFeed rcv = new ParentGallery_ViewHolderFeed(layoutView);
        return rcv;
    }




    @Override
    public void onBindViewHolder(final ParentGallery_ViewHolderFeed holder, int position) {
        final ParentGallery_List feederInfo = mListFeeds.get(position);


        String feedDesc = null;

        holder.textname.setText(feederInfo.getTextname());
        holder.textcount.setText(feederInfo.getTextcount());
        holder.thumnail.setImageResource(feederInfo.getThumbnail());

        String strfinalphoto = feederInfo.getStrthumbnailurl()+""+feederInfo.getStrthumbnail();
        Log.e("testing","image in adapter"+feederInfo.getStrthumbnail());
        Log.e("testing","strfinalphoto in adapter"+strfinalphoto);

        if (feederInfo.getStrthumbnail() == null || feederInfo.getStrthumbnail().equals("")||feederInfo.getStrthumbnail().equals(null)||feederInfo.getStrthumbnail().equals("null")){

            holder.thumnail.setImageResource(R.drawable.defaultgallery);


        }else{

            Log.e("testing","Image in Dynamic="+strfinalphoto);
            Glide.with(mContext)
                    .load(Uri.parse(strfinalphoto))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.logokitel2)
                    .into(holder.thumnail);

        }


        holder.thumnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strgalleryid = feederInfo.getGalleryid();
                ConnectionDetector cd = new ConnectionDetector(mContext);
                if (cd.isConnectingToInternet()) {

                    new userdata().execute();


                } else {


                    Toast.makeText(mContext, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }

            }
        });


        //  holder.rate.setRating(feederInfo.get_rating());



        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }



    public class userdata extends AsyncTask<String, String, String> {


        String responce;
        String filepath;


        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
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




            Log.e("testing", "School_id = " +School_id);
            Log.e("testing", "strgalleryid = " +strgalleryid);

            userpramas.add(new BasicNameValuePair(End_Urls.GALLLERYVIEW_Schoolid, School_id));
            userpramas.add(new BasicNameValuePair(End_Urls.GALLLERYVIEW_Galleryid, strgalleryid));
            JSONObject json = jsonParser.makeHttpRequest(End_Urls.GALLLERYVIEW_URL, "POST", userpramas);


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

                JSONArray posts = response.optJSONArray("Images");
                Log.e("testing", "jsonParser3333" + posts);




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
                        filepath = post.getString("galleryURL");
                        posts2 = post.optJSONArray("gallery");
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

            Intent intent = new Intent(mContext, HostImageGalleryActivity_Parent.class);

            //  String[] images = getResources().getStringArray(R.array.unsplash_images);
            // String[] images = listdata;

            //Log.e("testing", "steimages===============" + steimages);

            Bundle bundle = new Bundle();
            bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, new ArrayList<>(Arrays.asList(mStringArray)));
            bundle.putString(ImageGalleryActivity.KEY_TITLE, "Gallery Images");
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

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