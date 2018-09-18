package com.jdsfoods.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jdsfoods.ProductDetailActivity;
import com.jdsfoods.R;
import com.jdsfoods.Response.SearchData;
import com.jdsfoods.SubCategoryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikhong on 31-07-2018.
 */

public class SeachAdapter extends RecyclerView.Adapter<SeachAdapter.ViewHolder> {
    private List<SearchData> productLists;
    private List<SearchData> searchDataList=new ArrayList<>();
    private Context context;
    private static final String PREFS_TAG = "SharedPrefs";
    private static final String PRODUCT_TAG = "MyProduct";

    public SeachAdapter(Context context, List<SearchData> productLists) {
        this.productLists = productLists;
        this.context = context;
    }

    @Override
    public SeachAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list, viewGroup, false);
        return new SeachAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final SeachAdapter.ViewHolder viewHolder, final int i) {
        final SearchData product =productLists.get(i);
            if (product.getSearchName().contains(" in ")) {
                String lastWord = product.getSearchName().substring(product.getSearchName().lastIndexOf(" ") + 1);
                String inputString = product.getSearchName().substring(0, product.getSearchName().lastIndexOf(" ")) + "";
                //String replaced=product.getSearchName().replace(lastWord,"");
                viewHolder.tvItem.setText(inputString);
                viewHolder.tvCat.setText(lastWord);
                viewHolder.tvCat.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvItem.setText(product.getSearchName());
                viewHolder.tvCat.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getCategoryId().equalsIgnoreCase("0")){
                    setDataFromSharedPreferences(product);
                    Activity activity = (Activity) context;
                    Intent intent = new Intent(activity,ProductDetailActivity.class);
                    intent.putExtra("productId", product.getProductId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                else {
                    setDataFromSharedPreferences(product);
                    Activity activity = (Activity) context;
                    Intent intent = new Intent(activity,SubCategoryActivity.class);
                    intent.putExtra("categoryId", product.getCategoryId());
                    intent.putExtra("categoryName", product.getSearchName());
                    intent.putExtra("childCount", product.getChildCategoryCounter());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem,tvCat;
        public ViewHolder(View view) {
            super(view);
            tvItem= (TextView)view.findViewById(R.id.tv_item);
            tvCat= (TextView)view.findViewById(R.id.tv_cat);
        }
    }


    private void setDataFromSharedPreferences(SearchData searchData){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(searchData);

        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(PRODUCT_TAG, jsonCurProduct);
        editor.commit();
    }

}
