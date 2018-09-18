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
import com.jdsfoods.Response.SalePayment;
import com.jdsfoods.Utilities.Preferences;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SaleItemAdapter extends RecyclerView.Adapter<SaleItemAdapter.ViewHolder> {
    private List<SalePayment> saleDetails;
    private Context context;

    public SaleItemAdapter(Context context, List<SalePayment> saleDetails) {
        this.saleDetails = saleDetails;
        this.context = context;
    }

    @Override
    public SaleItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sale_item, viewGroup, false);
        return new SaleItemAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final SaleItemAdapter.ViewHolder viewHolder, final int i) {
        final SalePayment salePayment =saleDetails.get(i);
        Picasso.with(context).load( Preferences.getInstance().getUserPhotoPath()+salePayment.getImage()).into(viewHolder.ivImage);
        viewHolder.tvName.setText(salePayment.getProductName());
        viewHolder.tvItem.setText(salePayment.getNoOfItemsInPacking()+" items in each carton");
        String quantity=salePayment.getQuantity().substring(0, salePayment.getQuantity().lastIndexOf("."));
        double amount=Double.parseDouble(salePayment.getUnitPrice())*Double.parseDouble(quantity);
        viewHolder.tvAmount.setText("€ " + String.format("%.2f", amount));
        viewHolder.tvPrice.setText("€ " +String.format("%.2f", Double.parseDouble(salePayment.getUnitPrice()))+" X "+quantity);
        viewHolder.tvItems.setText("Items: "+quantity);
        viewHolder.tvName.setText(salePayment.getProductName());

    }

    @Override
    public int getItemCount() {
        return saleDetails.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivImage;
        TextView tvName,tvItem,tvAmount,tvPrice,tvItems;
        public ViewHolder(View view) {
            super(view);
            tvName= (TextView)view.findViewById(R.id.tv_name);
            tvItem= (TextView)view.findViewById(R.id.tv_item);
            tvAmount= (TextView)view.findViewById(R.id.tv_amount);
            tvPrice= (TextView)view.findViewById(R.id.tv_price);
            tvItems= (TextView)view.findViewById(R.id.tv_items);
            ivImage= (ImageView)view.findViewById(R.id.iv_image);
        }
    }

}

