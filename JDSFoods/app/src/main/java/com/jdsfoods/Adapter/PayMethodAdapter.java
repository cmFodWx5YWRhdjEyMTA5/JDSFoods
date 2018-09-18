package com.jdsfoods.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jdsfoods.R;
import com.jdsfoods.Response.PaymentResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PayMethodAdapter  extends RecyclerView.Adapter<PayMethodAdapter.ViewHolder> {
    private List<PaymentResponse.MethodData.KeyList> methodData;
    private int lastSelectedPosition = -1;
    private Context context;
    OnItemClick mCallback;

    public PayMethodAdapter(Context context, List<PaymentResponse.MethodData.KeyList> methodData,OnItemClick listener) {
        this.methodData = methodData;
        this.context = context;
        this.mCallback = listener;
    }

    @Override
    public PayMethodAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.method_list, viewGroup, false);
        return new PayMethodAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final PayMethodAdapter.ViewHolder viewHolder, final int position) {
        final PaymentResponse.MethodData.KeyList keyList =methodData.get(position);
        viewHolder.tvName.setText(keyList.getDescription());
        Picasso.with(context).load(keyList.getImage().getBigger()).into(viewHolder.ivImage);
        viewHolder.rlOption.setChecked(lastSelectedPosition == position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = viewHolder.getAdapterPosition();
                notifyDataSetChanged();
                mCallback.onClick(keyList.getId());

            }
        });
        viewHolder.rlOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = viewHolder.getAdapterPosition();
                notifyDataSetChanged();
                mCallback.onClick(keyList.getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return methodData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        RadioButton rlOption;
        ImageView ivImage;
        public ViewHolder(View view) {
            super(view);
            rlOption= (RadioButton)view.findViewById(R.id.rl_option);
            tvName= (TextView)view.findViewById(R.id.tv_name);
            ivImage= (ImageView)view.findViewById(R.id.iv_image);
        }
    }


    public interface OnItemClick {
        void onClick(String value);
    }

}
