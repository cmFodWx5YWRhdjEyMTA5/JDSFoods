package com.jdsfoods.Adapter;

import android.app.Activity;
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

import com.jdsfoods.PayDetailActivity;
import com.jdsfoods.R;
import com.jdsfoods.Response.PendingModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dikhong on 30-07-2018.
 */

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {
    private List<PendingModel> pendingModels;
    private Context context;
    String imagePath;

    public PendingAdapter(Context context, List<PendingModel> pendingModels, String imagePath) {
        this.pendingModels = pendingModels;
        this.context = context;
        this.imagePath=imagePath;
    }

    @Override
    public PendingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pending_list, viewGroup, false);
        return new PendingAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final PendingAdapter.ViewHolder viewHolder, final int i) {
        final PendingModel pendingModel =pendingModels.get(i);
        viewHolder.tvDate.setText("Due on: "+convertDate(pendingModel.getDate()));
        viewHolder.tvOrder.setText(pendingModel.getId());
        viewHolder.tvAmount.setText("€ "+String.format("%.2f", Double.parseDouble(pendingModel.getPendingAmount())));
        if (pendingModel.getPaymentStatus().equalsIgnoreCase("pending"))
        {
            viewHolder.tvStatus.setText("Pending");
            viewHolder.ivStatus.setImageResource(R.drawable.ic_close_black_24dp);
            viewHolder.ivStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            viewHolder.tvStatus.setText("Partial");
            viewHolder.ivStatus.setImageResource(R.drawable.partial_icon);
            viewHolder.ivStatus.setBackgroundColor(context.getResources().getColor(R.color.colorBlack));
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                PendingModel pendingModel1 = (PendingModel) pendingModels.get(i);
                Intent intent = new Intent(activity,PayDetailActivity.class);
                intent.putExtra(PayDetailActivity.PAY, pendingModel1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
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

    @Override
    public int getItemCount() {
        return pendingModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDate,tvOrder,tvAmount,tvStatus;
        RelativeLayout rlStatus;
        ImageView ivStatus;
        public ViewHolder(View view) {
            super(view);
            tvDate= (TextView)view.findViewById(R.id.tv_date);
            tvOrder= (TextView)view.findViewById(R.id.tv_order);
            tvAmount= (TextView)view.findViewById(R.id.tv_amount);
            tvStatus= (TextView)view.findViewById(R.id.tv_status);
            rlStatus= (RelativeLayout)view.findViewById(R.id.rl_status);
            ivStatus= (ImageView)view.findViewById(R.id.iv_status);
        }
    }

}
