package g2evolution.Boutique.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import g2evolution.Boutique.Activity.Gallery_Images;
import g2evolution.Boutique.R;


/**
 * Created by Jana on 10/29/2018.
 */

public class Image_Slider_Adapter extends PagerAdapter {

    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;

  //  private static final Integer[] XMEN= {R.drawable.jeans,R.drawable.ic_product2,R.drawable.jeans,R.drawable.ic_product2,R.drawable.jeans};

    ArrayList<String> images1 = new ArrayList<String>();

    public Image_Slider_Adapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide_image_layout, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
      //  myImage.setImageResource(images.get(position));


        images1.add("http://g2evolution.in/pranitha/productImages/f9c8558950071cb8187b34a7fbdd94b2.jpg");
        images1.add("http://g2evolution.in/pranitha/productImages/f9c8558950071cb8187b34a7fbdd94b2.jpg");


        if (images.get(position) == null || images.get(position).trim().equals("0")||images.get(position).trim().equals("")||images.get(position).trim().equals("null")){
            myImage.setImageResource(R.drawable.car);
        }else {
            Glide.with(context)
                    .load(Uri.parse(images.get(position)))
                    // .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .error(R.drawable.car)
                    .into(myImage);

        }


        myImageLayout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if (images == null ||images.size() == 0){

                }else{

                    Intent intent = new Intent(context, Gallery_Images.class);
                    intent.putStringArrayListExtra(Gallery_Images.EXTRA_NAME,images);
                    Log.e("testing12345","images=====multiple image"+images);
                    context.startActivity(intent);
                    //this will log the page number that was click
                    Log.e("testing12345", "This page was clicked: " + images);
                }



            }
        });


        view.addView(myImageLayout, 0);
        return myImageLayout;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
