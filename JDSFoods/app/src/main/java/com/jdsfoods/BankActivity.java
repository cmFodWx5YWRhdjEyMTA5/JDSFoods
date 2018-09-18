package com.jdsfoods;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Response.BankResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setTextSize(18f);
        title.setText("Account Info");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("payment", "bank");
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        if (getIntent().getStringExtra("price")!=null){
            tvPice.setText(getIntent().getStringExtra("price"));
        }
        bankDetail();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void bankDetail() {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait...");
        jdsFoodService.bankDetail(Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<BankResponse>() {
                    @Override
                    public void onResponse(Call<BankResponse> call, Response<BankResponse> response) {
                        if (response.body().getFlag() == 1) {
                            tvName.setText(response.body().getBankData().getNameAccount());
                            tvNo.setText(response.body().getBankData().getAccountNo());
                            tvSwiss.setText(response.body().getBankData().getIfscCode());
                        } else {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<BankResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_no)
    TextView tvNo;

    @BindView(R.id.tv_swiss)
    TextView tvSwiss;

    @BindView(R.id.tv_head2)
    TextView tvPice;





}
