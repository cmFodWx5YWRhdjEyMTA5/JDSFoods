package com.jdsfoods.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdsfoods.R;
import com.jdsfoods.Response.SubQutResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dikhong on 30-07-2018.
 */

public class SubQutAdapter extends RecyclerView.Adapter<SubQutAdapter.ViewHolder> {
    private List<SubQutResponse.SubQuote> subQuotes;
    private Context context;
    String imagePath;

    public SubQutAdapter(Context context, List<SubQutResponse.SubQuote> subQuotes, String imagePath) {
        this.subQuotes = subQuotes;
        this.context = context;
        this.imagePath=imagePath;
    }

    @Override
    public SubQutAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sub_qut_list, viewGroup, false);
        return new SubQutAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final SubQutAdapter.ViewHolder viewHolder, final int i) {
        final SubQutResponse.SubQuote subQuote =subQuotes.get(i);
        Picasso.with(context).load(imagePath+subQuote.getImage()).into(viewHolder.ivImage);
        viewHolder.tvName.setText(subQuote.getProductName());
        if (Integer.parseInt(subQuote.getNoOfItemsInPacking())<=1) {
            viewHolder.tvNo.setText(subQuote.getNoOfItemsInPacking() + " item in each carton");
        }
        else {
            viewHolder.tvNo.setText(subQuote.getNoOfItemsInPacking() + " items in each carton");
        }
        viewHolder.tvSingle.setText("Single Qty: "+subQuote.getSingleProductQuantity());
        double subDiscount = Double.parseDouble(subQuote.getSubtotal());
        String subDis = String.format("%.2f", subDiscount);
        viewHolder.tvSub.setText("€ " + subDis);
        viewHolder.tvCarton.setText("Carton Qty: "+subQuote.getPackingQuantity());
        String item = subQuote.getQuantity().substring(0, subQuote.getQuantity().indexOf("."));
        viewHolder.tvItem.setText("Items: "+item);
        if (subQuote.getItemDiscount() == null ||subQuote.getItemDiscount().equals("")) {
            double price = Double.parseDouble(subQuote.getRealUnitPrice());
            String finalPrice = String.format("%.2f", price);
            viewHolder.tvPromo.setVisibility(View.GONE);
            viewHolder.tvPrice.setText("€ " + finalPrice);
        } else {
            double price = Double.parseDouble(subQuote.getRealUnitPrice());
            double discount = Double.parseDouble(subQuote.getRealUnitPrice()) - Double.parseDouble(subQuote.getItemDiscount());
            String noDiscount = String.format("%.2f", price);
            String withDiscount = String.format("%.2f", discount);
            viewHolder.tvPromo.setVisibility(View.VISIBLE);
            viewHolder.tvPromo.setText("€ " + noDiscount);
            viewHolder.tvPrice.setText("€ " + withDiscount);
            viewHolder.tvPromo.setPaintFlags(viewHolder.tvPromo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return subQuotes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivImage;
        TextView tvName,tvNo,tvSingle,tvCarton,tvItem,tvSub,tvPrice,tvPromo;
        public ViewHolder(View view) {
            super(view);
            ivImage= (ImageView)view.findViewById(R.id.iv_image);
            tvName= (TextView)view.findViewById(R.id.tv_name);
            tvNo= (TextView)view.findViewById(R.id.tv_no);
            tvSingle= (TextView)view.findViewById(R.id.tv_single);
            tvCarton= (TextView)view.findViewById(R.id.tv_carton);
            tvItem= (TextView)view.findViewById(R.id.tv_item);
            tvSub= (TextView)view.findViewById(R.id.tv_sub);
            tvPrice= (TextView)view.findViewById(R.id.tv_price);
            tvPromo= (TextView)view.findViewById(R.id.tv_promo);

        }
    }

}

