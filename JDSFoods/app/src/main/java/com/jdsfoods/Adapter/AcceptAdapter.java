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
import com.jdsfoods.Response.AcceptQutResponse;
import com.jdsfoods.Utilities.QuantityView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AcceptAdapter extends RecyclerView.Adapter<AcceptAdapter.ViewHolder> {
    private List<AcceptQutResponse.QuoteItem> quoteItems;
    private Context context;
    String imagePath;

    public AcceptAdapter(Context context, List<AcceptQutResponse.QuoteItem> quoteItems, String imagePath) {
        this.quoteItems = quoteItems;
        this.context = context;
        this.imagePath=imagePath;
    }

    @Override
    public AcceptAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.accept_list, viewGroup, false);
        return new AcceptAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final AcceptAdapter.ViewHolder viewHolder, final int i) {
        final AcceptQutResponse.QuoteItem quoteItem =quoteItems.get(i);
        Picasso.with(context).load(imagePath+quoteItem.getImage()).into(viewHolder.ivImage);
        viewHolder.tvName.setText(quoteItem.getProductName());
        viewHolder.tvNo.setText(quoteItem.getNoOfItemsInPacking()+" items in each carton.");
//        viewHolder.tvItem.setText(quoteItem.getQuantity());
        viewHolder.tvPrice.setText(quoteItem.getNetUnitPrice());
        viewHolder.qvCarton.setQuantity(Integer.parseInt(quoteItem.getPackingQuantity()));
        viewHolder.qvItem.setQuantity(Integer.parseInt(quoteItem.getSingleProductQuantity()));
    }

    @Override
    public int getItemCount() {
        return quoteItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivImage;
        TextView tvName,tvNo,tvItem,tvPrice,tvTotal;
        QuantityView qvCarton,qvItem;

        public ViewHolder(View view) {
            super(view);
            ivImage= (ImageView)view.findViewById(R.id.iv_image);
            tvName= (TextView)view.findViewById(R.id.tv_name);
            tvNo= (TextView)view.findViewById(R.id.tv_no);
            tvItem= (TextView)view.findViewById(R.id.tv_item);
            tvPrice= (TextView)view.findViewById(R.id.tv_price);
            tvTotal= (TextView)view.findViewById(R.id.tv_total);
            qvCarton= (QuantityView)view.findViewById(R.id.qv_carton);
            qvItem= (QuantityView)view.findViewById(R.id.qv_item);
        }
    }

}

