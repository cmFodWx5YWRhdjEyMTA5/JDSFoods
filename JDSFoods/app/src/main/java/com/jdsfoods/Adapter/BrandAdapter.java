package com.jdsfoods.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdsfoods.BrandListActivity;
import com.jdsfoods.R;
import com.jdsfoods.Response.ShopResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dikhong on 30-07-2018.
 */

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {
    private List<ShopResponse.ShopData> shopData;
    private Context context;
    String imagePath;

    public BrandAdapter(Context context, List<ShopResponse.ShopData> shopData,String imagePath) {
        this.shopData = shopData;
        this.context = context;
        this.imagePath=imagePath;
    }

    @Override
    public BrandAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand_list, viewGroup, false);
        return new BrandAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final BrandAdapter.ViewHolder viewHolder, final int i) {
        final ShopResponse.ShopData shopData1 =shopData.get(i);
        Picasso.with(context).load(imagePath+shopData1.getImage()).into(viewHolder.ivImages);
        viewHolder.tvTitle.setText(shopData1.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                ShopResponse.ShopData shopData2 = (ShopResponse.ShopData) shopData.get(i);
                Intent intent = new Intent(activity,BrandListActivity.class);
                intent.putExtra(BrandListActivity.BRAND, shopData2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView ivImages;
        AppCompatTextView tvTitle;
        public ViewHolder(View view) {
            super(view);
            ivImages= (AppCompatImageView)view.findViewById(R.id.ivImages);
            tvTitle= (AppCompatTextView)view.findViewById(R.id.tvTitle);
        }
    }

}
