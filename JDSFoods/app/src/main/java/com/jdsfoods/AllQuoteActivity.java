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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Adapter.AllQuoteAdapter;
import com.jdsfoods.Response.AllQuoteResponse;
import com.jdsfoods.Response.ForgetResponse;
import com.jdsfoods.Response.PayResponse;
import com.jdsfoods.Response.QuoteModel;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllQuoteActivity extends BaseActivity implements AllQuoteAdapter.OnItemClick {
    String listSize = "0";
    List<QuoteModel> quoteModels;
    AllQuoteAdapter allQuoteAdapter;
    Button btOnline,btBank;
    double totalPrice = 0;
    double totalQuantity = 0;
    double totalTax = 0;
    double grandTotal= 0;
    String payMethod="";
    RelativeLayout rlAmt;
    TextView tvAmt;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_quote);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setTextSize(18f);
        title.setText("Quotation");
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
        setRecycle();
        getQuoteItems();
        if (Preferences.getInstance().getPriceVisible()==null||Preferences.getInstance().getPriceVisible().equalsIgnoreCase("0"))
        {
            tvSubmit.setText("SUBMIT QUOTATION");
            r2.setVisibility(View.GONE);
            r3.setVisibility(View.GONE);
            r4.setVisibility(View.GONE);
        }
        else {
            tvSubmit.setText("PLACE ORDER");
            r2.setVisibility(View.VISIBLE);
            r3.setVisibility(View.VISIBLE);
            r4.setVisibility(View.VISIBLE);
        }
    }

    private void setRecycle() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvItems.setLayoutManager(mLayoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getQuoteItems() {
        JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.getAllQuote(Preferences.getInstance().getQuoteId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            allQuoteAdapter = new AllQuoteAdapter(AllQuoteActivity.this, response.body().getQuoteItemList(), response.body().getImagePath(), AllQuoteActivity.this);
                            listSize = String.valueOf(response.body().getQuoteItemList().size());
                            for (int i = 0; i < response.body().getQuoteItemList().size(); i++) {
                                totalPrice += Double.parseDouble(response.body().getQuoteItemList().get(i).getSubtotal());
                                totalQuantity += Double.parseDouble(response.body().getQuoteItemList().get(i).getQuantity());
                                totalTax += Double.parseDouble(response.body().getQuoteItemList().get(i).getItemTax())*Double.parseDouble(response.body().getQuoteItemList().get(i).getQuantity());
                            }
                            setItems(totalPrice,totalQuantity,totalTax);
                            rvItems.setAdapter(allQuoteAdapter);
                            tvCart.setText("ITEMS(" + listSize + ")");
                        } else {
                            listSize = "0";
                            tvCart.setText("ITEMS(" + listSize + ")");
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void submitQuote() {
        JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.submitQuote(Preferences.getInstance().getQuoteId(), Preferences.getInstance().getUserId(),"","","","" ,"",Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ForgetResponse>() {
                    @Override
                    public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                        if (response.body().getFlag() == 1) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ForgetResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void placeOrder(String payNow) {
        if (payNow.equalsIgnoreCase("0"))
        {
            payMethod="";
        }
        JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.placeOrder(Preferences.getInstance().getQuoteId(), Preferences.getInstance().getIsCredit(),payNow,payMethod,Preferences.getInstance().getPaymentTerm(), String.valueOf(grandTotal),Preferences.getInstance().getUserId(),String.valueOf(totalTax),String.valueOf(totalPrice),String.valueOf(grandTotal) ,"1",Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<PayResponse>() {
                    @Override
                    public void onResponse(Call<PayResponse> call, Response<PayResponse> response) {
                        if (response.body().getFlag() == 1) {
                            Intent intent = new Intent(AllQuoteActivity.this, WebviewActivity.class);
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


    private void setItems(double totalPrice, double totalQuantity,double totalTax) {
        tvCart.setText("ITEMS(" + listSize + ")");
        tvSub.setText("€ " +String.valueOf(totalPrice));
        int tQuantity = (int) totalQuantity;
        tvItems.setText(String.valueOf(tQuantity));
        tvTax.setText("€ " +String.format("%.2f", totalTax));
        tvTotal.setText("€ " +String.valueOf(totalPrice+totalTax));
        grandTotal=totalPrice+totalTax;
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_items)
    RecyclerView rvItems;

    @BindView(R.id.tv_cart)
    TextView tvCart;

    @BindView(R.id.tv_sub)
    TextView tvSub;

    @BindView(R.id.tv_items)
    TextView tvItems;

    @BindView(R.id.tv_tax)
    TextView tvTax;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @BindView(R.id.r2)
    RelativeLayout r2;

    @BindView(R.id.r3)
    RelativeLayout r3;

    @BindView(R.id.r4)
    RelativeLayout r4;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.rl_submit)
    void rlSubmit(){
        if (Preferences.getInstance().getPriceVisible()==null || Preferences.getInstance().getPriceVisible().equalsIgnoreCase("0") ) {
            submitQuote();
        }
        else {
            showDialog1(AllQuoteActivity.this, tvTotal.getText().toString());
        }
    }

    @Override
    public void onClick(String value) {

    }

    @Override
    public void sendList(ArrayList<QuoteModel> quoteModels) {
    }


    public void showDialog1(final Activity activity, final String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);

        TextView tvPrice = (TextView) dialog.findViewById(R.id.tv_price);
        LinearLayout llPayLater = (LinearLayout) dialog.findViewById(R.id.ll_pay_later);
        TextView tvDay = (TextView) dialog.findViewById(R.id.tv_day);
        tvPrice.setText("TOTAL: "+msg);
        if (Preferences.getInstance().getPaymentTerm()==null|| Preferences.getInstance().getPaymentTerm().equalsIgnoreCase("0"))
        {
            llPayLater.setVisibility(View.GONE);
        }
        else {
            llPayLater.setVisibility(View.VISIBLE);
            tvDay.setText("You have " + Preferences.getInstance().getPaymentTerm()+" days to pay this amount.");
        }

        Button payLater = (Button) dialog.findViewById(R.id.bt_pay_later);
        Button payNow = (Button) dialog.findViewById(R.id.bt_pay_now);
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showDialog2(activity,msg);
            }
        });

        payLater.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                placeOrder("0");
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    public void showDialog2(Activity activity, final String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_two);
        final EditText etAmount = (EditText) dialog.findViewById(R.id.et_amount);
        TextView tvTotal = (TextView) dialog.findViewById(R.id.tv_total);
        tvTotal.setText(msg);
        Button btPay = (Button) dialog.findViewById(R.id.bt_pay);
         rlAmt = (RelativeLayout) dialog.findViewById(R.id.rl_amt);
         tvAmt = (TextView) dialog.findViewById(R.id.tv_amt);
        btOnline = (Button) dialog.findViewById(R.id.bt_online);
        btBank = (Button) dialog.findViewById(R.id.bt_bank);
        btPay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (etAmount.getText().toString().length()!=0){
                    placeOrder("1");
                    dialog.dismiss();
                }
                else {
                    if (payMethod.equalsIgnoreCase("bank"))
                    {
                        placeOrder("0");
                        dialog.dismiss();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Please add amount to pay", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AllQuoteActivity.this,PaymentMethodActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        btBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AllQuoteActivity.this,BankActivity.class);
                intent.putExtra("price",msg);
                startActivityForResult(intent, 2);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        dialog.show();

    }

    public void showDialog3(final Activity activity, final String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog3);

        TextView tvPrice = (TextView) dialog.findViewById(R.id.tv_price);
        LinearLayout llPayLater = (LinearLayout) dialog.findViewById(R.id.ll_pay_later);
        TextView tvDay = (TextView) dialog.findViewById(R.id.tv_day);
        tvPrice.setText("TOTAL: "+msg);
        if (Preferences.getInstance().getPaymentTerm()==null|| Preferences.getInstance().getPaymentTerm().equalsIgnoreCase("0"))
        {
            llPayLater.setVisibility(View.GONE);
        }
        else {
            llPayLater.setVisibility(View.VISIBLE);
            tvDay.setText("You have " + Preferences.getInstance().getPaymentTerm()+" days to pay this amount.");
        }

        Button payLater = (Button) dialog.findViewById(R.id.bt_pay_later);
        Button payNow = (Button) dialog.findViewById(R.id.bt_pay_now);
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showDialog2(activity,msg);
            }
        });

        payLater.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                placeOrder("0");
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                payMethod = data.getStringExtra("payment");
                tvAmt.setVisibility(View.VISIBLE);
                rlAmt.setVisibility(View.VISIBLE);
                btBank.setBackgroundColor(getResources().getColor(R.color.dgray));
                btBank.setText("BANK");
                btOnline.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                btOnline.setText(payMethod);
               // Toast.makeText(getApplicationContext(), payMethod, Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == 2){
            if(resultCode == RESULT_OK) {
                payMethod = data.getStringExtra("payment");
                tvAmt.setVisibility(View.GONE);
                rlAmt.setVisibility(View.GONE);
                btOnline.setBackgroundColor(getResources().getColor(R.color.dgray));
                btOnline.setText("ONLINE PAYMENT");
                btBank.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                btBank.setText(payMethod);
                //Toast.makeText(getApplicationContext(), payMethod, Toast.LENGTH_SHORT).show();
            }
        }
    }
}