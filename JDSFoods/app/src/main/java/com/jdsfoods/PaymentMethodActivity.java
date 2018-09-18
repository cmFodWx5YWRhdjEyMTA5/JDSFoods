package com.jdsfoods;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Adapter.PayMethodAdapter;
import com.jdsfoods.Response.PaymentResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodActivity extends BaseActivity implements PayMethodAdapter.OnItemClick {
    PaymentResponse.MethodData.KeyList keyList;
    String payMent="none";
    List<PaymentResponse.MethodData.KeyList> keyLists=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setTextSize(18f);
        title.setText("Payment Method");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("payment", payMent);
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        getPayMethod();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getPayMethod() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PaymentMethodActivity.this, LinearLayout.VERTICAL, false);
        rvPay.setLayoutManager(layoutManager);
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient3().create(JDSFoodService.class);
        showProgressbar("Loading", "Sending request...");
        jdsFoodService.payMethod(Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<PaymentResponse>() {
                    @Override
                    public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                        if (response.body().getFlag() == 1) {
                            LinkedHashMap<String, PaymentResponse.MethodData.KeyList> entityList = response.body().getMethodData().getData();
                            for (String mapKey :  entityList.keySet()) {
                                Log.d("Map","mapKey : "+mapKey+" , mapValue : "+entityList.get(mapKey));
                                keyList=entityList.get(mapKey);
                                keyLists.add(keyList);
                            }
                            PayMethodAdapter payMethodAdapter=new PayMethodAdapter(PaymentMethodActivity.this,keyLists,PaymentMethodActivity.this);
                            rvPay.setAdapter(payMethodAdapter);
                        } else {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<PaymentResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @BindView(R.id.rv_pay)
    RecyclerView rvPay;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.bt_done)
    void btDone()
    {
        Intent intent = new Intent();
        intent.putExtra("payment", payMent);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    public void onClick(String value) {
        payMent=value;
    }
}
