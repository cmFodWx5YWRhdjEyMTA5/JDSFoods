package com.jdsfoods;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Adapter.SaleItemAdapter;
import com.jdsfoods.Response.HistoryResponse;
import com.jdsfoods.Response.PayResponse;
import com.jdsfoods.Response.SaleDetailResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleInfoActivity extends BaseActivity {
    public static final String SALE = "sale";
    HistoryResponse.SaleData saleData;
    String saleId;
    Button btOnline;
    String payMethod,total;
    TextView tvPaying,tvTotalA,tvBalance;
    private static final String TAG = "SaleInfoActivity";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_info);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setTextSize(18f);
        title.setText("Sale Order Detail");
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
        saleData = (HistoryResponse.SaleData) getIntent().getParcelableExtra(SALE);
        if (saleData != null) {
            setField(saleData);
            saleDetails(saleData.getId());
        }



        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
                if (newState.toString().equalsIgnoreCase("EXPANDED"))
                {
                    ivArrow.setImageResource(R.drawable.down_arrow);
                }
                else {
                    ivArrow.setImageResource(R.drawable.up_arrow);
                }
            }

        });
        slidingUpPanelLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });




    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void saleDetails(String id) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        rvSaleItems.setLayoutManager(layoutManager);
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait...");
        jdsFoodService.saleDetail(id, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<SaleDetailResponse>() {
                    @Override
                    public void onResponse(Call<SaleDetailResponse> call, Response<SaleDetailResponse> response) {
                        if (response.body().getFlag() == 1) {
                            saleId=response.body().getSaleDetail().getId();
                            setSlide(response.body().getSaleDetail());
                            SaleItemAdapter saleItemAdapter = new SaleItemAdapter(SaleInfoActivity.this, response.body().getSaleDetail().getSaleItems());
                            rvSaleItems.setAdapter(saleItemAdapter);
                        } else {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<SaleDetailResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setSlide(SaleDetailResponse.SaleDetail saleDetail) {
        tvShip.setText("€ "+String.format("%.2f", Double.parseDouble(saleDetail.getShipping())));
        tvDis.setText("€ "+String.format("%.2f", Double.parseDouble(saleDetail.getTotalDiscount())));
        tvItem.setText(saleDetail.getTotalItems());
        tvSub.setText("€ "+String.format("%.2f", Double.parseDouble(saleDetail.getTotal())));
        tvTax.setText("€ "+String.format("%.2f", Double.parseDouble(saleDetail.getTotalTax())));
        tvTotal1.setText("€ "+String.format("%.2f",Double.parseDouble(saleDetail.getGrandTotal())));
        etPay.setText(String.format("%.2f",Double.parseDouble(saleDetail.getPendingAmount())));
    }

    private void setField(HistoryResponse.SaleData saleData) {
        tvDate.setText(convertDate(saleData.getDate()));
        tvSale.setText(saleData.getId());
        tvTotal.setText("€ " + String.format("%.2f", Double.parseDouble(saleData.getGrandTotal())));
        total=String.format("%.2f", Double.parseDouble(saleData.getGrandTotal()));
        tvBal.setText("€ " + String.format("%.2f", Double.parseDouble(saleData.getBalance())));
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void payNow(String amount, String payMethod) {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Sending request...");
        jdsFoodService.payNow(saleId,payMethod,amount,Preferences.getInstance().getUserId() ,Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<PayResponse>() {
                    @Override
                    public void onResponse(Call<PayResponse> call, Response<PayResponse> response) {
                        if (response.body().getFlag() == 1) {
                            Intent intent = new Intent(SaleInfoActivity.this, WebviewActivity.class);
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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_sale)
    TextView tvSale;

    @BindView(R.id.tv_bal)
    TextView tvBal;

    @BindView(R.id.tv_total1)
    TextView tvTotal1;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.rv_sale_items)
    RecyclerView rvSaleItems;

    @BindView(R.id.tv_item)
    TextView tvItem;

    @BindView(R.id.tv_tax)
    TextView tvTax;

    @BindView(R.id.tv_ship)
    TextView tvShip;

    @BindView(R.id.tv_sub)
    TextView tvSub;

    @BindView(R.id.tv_dis)
    TextView tvDis;

    @BindView(R.id.et_pay)
    EditText etPay;

    @BindView(R.id.iv_arrow)
    ImageView ivArrow;

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_pay)
    void btPay() {
        payDialog(SaleInfoActivity.this,etPay.getText().toString(),total);
    }


    public void payDialog(Activity activity, final String paying,final String total){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pay);
        Button btPay = (Button) dialog.findViewById(R.id.bt_pay);
        btOnline = (Button) dialog.findViewById(R.id.bt_online);
        tvTotalA = (TextView) dialog.findViewById(R.id.tv_total);
        tvPaying = (TextView) dialog.findViewById(R.id.tv_paying);
        tvBalance= (TextView) dialog.findViewById(R.id.tv_balance);
        tvTotalA.setText("€ " + String.format("%.2f", Double.parseDouble(total)));
        tvPaying.setText("€ " + String.format("%.2f", Double.parseDouble(paying)));
        double balance=Double.parseDouble(total)-Double.parseDouble(paying);
        tvBalance.setText("€ " + String.format("%.2f", balance));
        btPay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (btOnline.getText().toString().equalsIgnoreCase("online payment"))
                {
                    Toast.makeText(getApplicationContext(),"Please select the payment method", Toast.LENGTH_SHORT).show();
                }
                else {
                payNow(paying,payMethod);
                    dialog.dismiss();
            }
            }
        });

        btOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SaleInfoActivity.this,PaymentMethodActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        dialog.show();

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
