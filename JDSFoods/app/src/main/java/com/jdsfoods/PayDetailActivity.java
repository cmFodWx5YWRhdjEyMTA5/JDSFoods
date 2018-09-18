package com.jdsfoods;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Response.PayResponse;
import com.jdsfoods.Response.PendingModel;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayDetailActivity extends BaseActivity {

    public static final String PAY = "pay";
    PendingModel pendingModel;
    Button btOnline;
    TextView tvPaying;
    String payMethod;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setTextSize(18f);
        title.setText("Payment Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

        pendingModel = (PendingModel) getIntent().getParcelableExtra(PAY);
        if (pendingModel != null) {
            setFields(pendingModel);
        }

    }

    private void setFields(PendingModel pendingModel) {
        tvDate.setText("Order Placed on: " + convertDate(pendingModel.getDate()));
        tvSale.setText("Sale ID: " + pendingModel.getId());
        tvDeliver.setText("Delivered On: Need to implement");
        if (pendingModel.getPaymentStatus().equalsIgnoreCase("pending")) {
            tvStatus.setText("Pending");
            ivStatus.setImageResource(R.drawable.ic_close_black_24dp);
            ivStatus.setBackgroundColor(getResources().getColor(R.color.red));
        }
        tvItem.setText(pendingModel.getTotalItems());
        tvPrice.setText("€ " + String.format("%.2f", Double.parseDouble(pendingModel.getPendingAmount())));
        tvPending.setText("€ " + String.format("%.2f", Double.parseDouble(pendingModel.getPendingAmount())));
        etPay.setText(String.format("%.2f", Double.parseDouble(pendingModel.getPendingAmount())));
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void payNow(String amount, String payMethod) {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Sending request...");
        jdsFoodService.payNow(pendingModel.getId(),payMethod,amount,Preferences.getInstance().getUserId() ,Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<PayResponse>() {
                    @Override
                    public void onResponse(Call<PayResponse> call, Response<PayResponse> response) {
                        if (response.body().getFlag() == 1) {
                            Intent intent = new Intent(PayDetailActivity.this, WebviewActivity.class);
                            intent.putExtra("link",response.body().getPaymentUrl().getPaymentUrl());
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        } else {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<PayResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
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

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_sale)
    TextView tvSale;

    @BindView(R.id.tv_deliver)
    TextView tvDeliver;

    @BindView(R.id.iv_status)
    ImageView ivStatus;

    @BindView(R.id.rl_status)
    RelativeLayout rlStatus;

    @BindView(R.id.tv_status)
    TextView tvStatus;

    @BindView(R.id.tv_item)
    TextView tvItem;

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_pending)
    TextView tvPending;

    @BindView(R.id.et_pay)
    EditText etPay;

    public void payDialog(Activity activity, final String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pay);
        RelativeLayout rlBal = (RelativeLayout) dialog.findViewById(R.id.rl_bal);
        RelativeLayout rlTotal = (RelativeLayout) dialog.findViewById(R.id.rl_total);
        rlBal.setVisibility(View.GONE);
        rlTotal.setVisibility(View.GONE);
        Button btPay = (Button) dialog.findViewById(R.id.bt_pay);
        btOnline = (Button) dialog.findViewById(R.id.bt_online);
        tvPaying = (TextView) dialog.findViewById(R.id.tv_paying);
        tvPaying.setText("€ " + String.format("%.2f", Double.parseDouble(msg)));

        btPay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (btOnline.getText().toString().equalsIgnoreCase("online payment"))
                {
                    Toast.makeText(getApplicationContext(),"Please select the payment method", Toast.LENGTH_SHORT).show();
                }
                else {
                    payNow(msg, payMethod);
                    dialog.dismiss();
                }
            }
        });

        btOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PayDetailActivity.this,PaymentMethodActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        dialog.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_pay)
    void btPay() {
        if (etPay.getText().toString().length()==0 || etPay.getText().toString().equalsIgnoreCase("0.00")|| etPay.getText().toString().equalsIgnoreCase("0") ) {
            Toast.makeText(getApplicationContext(), "Please enter amount", Toast.LENGTH_SHORT).show();
        }
        else {
            payDialog(PayDetailActivity.this,etPay.getText().toString());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                payMethod = data.getStringExtra("payment");
                btOnline.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                btOnline.setText(payMethod);
            }
        }
    }


}
