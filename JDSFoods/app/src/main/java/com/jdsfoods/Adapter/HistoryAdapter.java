package com.jdsfoods.Adapter;

import
        android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdsfoods.R;
import com.jdsfoods.Response.HistoryResponse;
import com.jdsfoods.SaleInfoActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dikhong on 30-07-2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryResponse.SaleData> saleData;
    private Context context;


    public HistoryAdapter(Context context, List<HistoryResponse.SaleData> saleData) {
        this.saleData = saleData;
        this.context = context;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_list, viewGroup, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final HistoryAdapter.ViewHolder viewHolder, final int i) {
        final HistoryResponse.SaleData saleData1 =saleData.get(i);
        viewHolder.tvPrice.setText("€ "+String.format("%.2f", Double.parseDouble(saleData1.getGrandTotal())));
        if (saleData1.getPaymentStatus().equalsIgnoreCase("pending")|| saleData1.getPaymentStatus().equalsIgnoreCase("due"))
        {
            viewHolder.tvStatus.setText("Pending");
            viewHolder.ivStatus.setImageResource(R.drawable.ic_close_black_24dp);
            viewHolder.ivStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            viewHolder.tvStatus.setText("Done");
            viewHolder.ivStatus.setImageResource(R.drawable.ic_check_black_24dp);
            viewHolder.ivStatus.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
        viewHolder.tvSale.setText(saleData1.getId());
        viewHolder.tvDate.setText(convertDate(saleData1.getDate()));
        viewHolder.tvItems.setText(saleData1.getTotalItems());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                HistoryResponse.SaleData saleData2 = (HistoryResponse.SaleData) saleData.get(i);
                Intent intent = new Intent(activity,SaleInfoActivity.class);
                intent.putExtra(SaleInfoActivity.SALE, saleData2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return saleData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPrice,tvStatus,tvSale,tvDate,tvItems;
        ImageView ivStatus;
        RelativeLayout rlStatus;
        public ViewHolder(View view) {
            super(view);
            tvPrice= (TextView)view.findViewById(R.id.tv_price);
            tvStatus= (TextView)view.findViewById(R.id.tv_status);
            tvSale= (TextView)view.findViewById(R.id.tv_sale);
            tvDate= (TextView)view.findViewById(R.id.tv_date);
            tvItems= (TextView)view.findViewById(R.id.tv_items);
            rlStatus= (RelativeLayout)view.findViewById(R.id.rl_status);
            ivStatus= (ImageView)view.findViewById(R.id.iv_status);
        }
    }


    public String convertDate(String date) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        Date d = null;
        try {
            d = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }


}

