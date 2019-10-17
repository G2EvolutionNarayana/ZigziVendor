package g2evolution.Boutique.Adapter;

/**
 * Created by G2Evolution on 5/26/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import g2evolution.Boutique.Activity.Product_List;
import g2evolution.Boutique.FeederInfo.DetailInfo;
import g2evolution.Boutique.FeederInfo.HeaderInfo;
import g2evolution.Boutique.R;

public class MyListAdapter_MainActivity extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<HeaderInfo> deptList;

    String categoryname,subcategoryname,catid,subcatid;

    public MyListAdapter_MainActivity(Context context, ArrayList<HeaderInfo> deptList) {
        this.context = context;
        this.deptList = deptList;

        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<DetailInfo> productList = deptList.get(groupPosition).getProductList();
        return productList.get(childPosition);

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        final DetailInfo detailInfo = (DetailInfo) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.child_row_userid_main_activity, null);
        }

       // TextView sequence = (TextView) view.findViewById(R.id.sequence);
      //  sequence.setText(detailInfo.getSequence().trim() + ") ");
        TextView childItem = (TextView) view.findViewById(R.id.childItem);
        childItem.setText(detailInfo.getName().trim());
        categoryname =detailInfo.getCategoryname().trim();
        subcategoryname =detailInfo.getName().trim();
        catid =detailInfo.getCategoryid().trim();
        subcatid =detailInfo.getSubcatid().trim();

        Log.e("testing","categoryname===chaild"+catid);
        Log.e("testing","subcategoryname===chaild"+subcatid);

        childItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                categoryname =detailInfo.getCategoryname().trim();
                subcategoryname =detailInfo.getName().trim();

                catid =detailInfo.getCategoryid().trim();
                subcatid =detailInfo.getSubcatid().trim();

                SharedPreferences prefuserdata = context.getSharedPreferences("ProDetails", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();

                prefeditor.putString("category", "" + catid);
                prefeditor.putString("subcategoryname", "" + subcatid);

                prefeditor.putString("categoryname", "" + categoryname);
                prefeditor.commit();

                Log.e("testing","catid===chaild"+catid);
                Log.e("testing","subcatid===chaild"+subcatid);

                Intent intent =new Intent(context, Product_List.class);
                context.startActivity(intent);


            }
        });

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<DetailInfo> productList = deptList.get(groupPosition).getProductList();
        return productList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        HeaderInfo headerInfo = (HeaderInfo) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.group_heading_userid_main_activity, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);
        TextView imgexpand = (TextView) view.findViewById(R.id.imgexpand);
        TextView textchild = (TextView) view.findViewById(R.id.textchild);
        heading.setText(headerInfo.getName().trim());
        textchild.setText(headerInfo.getTextchild().trim());
/*

        ImageView iamge = (ImageView) view.findViewById(R.id.catimage);

        Glide.with(context)
                .load(Uri.parse(headerInfo.getImage()))
                // .diskCacheStrategy(DiskC
                // acheStrategy.NONE)
                //.skipMemoryCache(true)
                .error(R.drawable.car)
                .into(iamge);
*/

        //  iamge.set(headerInfo.getName().trim());


        if (isLastChild) {
            heading.setTypeface(null, Typeface.BOLD);

           // heading.setCompoundDrawablesWithIntrinsicBounds(R.drawable.subexpand, 0, 0, 0);
           // heading.setPadding(30,0,0,0);
            imgexpand.setCompoundDrawablesWithIntrinsicBounds(R.drawable.subexpand, 0,0, 0);
         //   iamge.setVisibility(View.GONE);
            textchild.setVisibility(View.INVISIBLE);
        } else {

            // If group is not expanded then change the text back into normal
            // and change the icon

            heading.setTypeface(null, Typeface.NORMAL);
           // heading.setCompoundDrawablesWithIntrinsicBounds(R.drawable.addexpnd, 0,0, 0);
            imgexpand.setCompoundDrawablesWithIntrinsicBounds(R.drawable.addexpnd, 0,0, 0);
           // iamge.setVisibility(View.VISIBLE);
            textchild.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}