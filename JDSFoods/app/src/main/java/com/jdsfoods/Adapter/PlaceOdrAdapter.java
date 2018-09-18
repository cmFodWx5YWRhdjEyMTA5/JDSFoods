package com.jdsfoods.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdsfoods.R;
import com.jdsfoods.Response.OrderResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaceOdrAdapter extends RecyclerView.Adapter<PlaceOdrAdapter.ViewHolder> {
    private List<OrderResponse.QuoteList> quoteLists;
    private Context context;
    String imagePath;

    public PlaceOdrAdapter(Context context, List<OrderResponse.QuoteList> quoteLists,String imagePath) {
        this.quoteLists = quoteLists;
        this.context = context;
        this.imagePath=imagePath;
    }

    @Override
    public PlaceOdrAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_list, viewGroup, false);
        return new PlaceOdrAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final PlaceOdrAdapter.ViewHolder viewHolder, final int i) {
        final OrderResponse.QuoteList quoteList =quoteLists.get(i);
        viewHolder.tvPrice.setText("Single Item: € "+String.format("%.2f", Double.parseDouble(quoteList.getRealUnitPrice())));
        viewHolder.tvItem.setText("Items: "+quoteList.getQuantity().substring(0, quoteList.getQuantity().lastIndexOf(".")));
        viewHolder.tvAmt.setText("€ "+String.format("%.2f", Double.parseDouble(quoteList.getSubtotal())));
        viewHolder.tvName.setText(quoteList.getProductName());
        Picasso.with(context).load(imagePath+quoteList.getImage()).into(viewHolder.ivImage);
    }

    @Override
    public int getItemCount() {
        return quoteLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvAmt,tvItem,tvPrice,tvName;
        ImageView ivImage;
        public ViewHolder(View view) {
            super(view);
            tvAmt= (TextView)view.findViewById(R.id.tv_amt);
            tvItem= (TextView)view.findViewById(R.id.tv_item);
            tvPrice= (TextView)view.findViewById(R.id.tv_price);
            tvName= (TextView)view.findViewById(R.id.tv_name);
            ivImage= (ImageView)view.findViewById(R.id.iv_image);
        }
    }

}


