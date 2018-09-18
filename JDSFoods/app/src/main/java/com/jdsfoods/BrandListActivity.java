package com.jdsfoods;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Adapter.ProductAdapter;
import com.jdsfoods.Response.AllQuoteResponse;
import com.jdsfoods.Response.ProductResponse;
import com.jdsfoods.Response.ShopResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandListActivity extends BaseActivity {
    ProductAdapter productAdapter;
    String items;
    public static final String BRAND = "brand";
    ShopResponse.ShopData shopData;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setTextSize(18f);
        shopData = (ShopResponse.ShopData) getIntent().getParcelableExtra(BRAND);
        if (shopData!=null)
        {
            title.setText(shopData.getName());
            getBrandItems(shopData.getId());
            getAllQuote(Preferences.getInstance().getQuoteId());
        }
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

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getAllQuote(String quoteId) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvBrand.setLayoutManager(mLayoutManager);
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        jdsFoodService.getAllQuote(quoteId,Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag()==1){
                            items= String.valueOf(response.body().getQuoteItemList().size());
                            if (items.equalsIgnoreCase("0")){
                                tvCart.setText("ITEMS (" + "0" + ")");
                            }
                            else {
                                tvCart.setText("ITEMS (" + items + ")");
                            }
                        }
                        else
                        {

                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getBrandItems(String brandId) {
            JDSFoodService jdsFoodService = RestClient.getInstance().getClient2().create(JDSFoodService.class);
            showProgressbar("Loading", "Please wait...");
            jdsFoodService.getProducts("", "", brandId, Preferences.getInstance().getAuthKey()).
                    enqueue(new Callback<ProductResponse>() {
                        @Override
                        public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                            if (response.body().getFlag() == 1) {
                                productAdapter=new ProductAdapter(BrandListActivity.this,response.body().getProductsList(),response.body().getImagePath(),"brand");
                                rvBrand.setAdapter(productAdapter);
                            } else {

                            }
                            hideProgressBar();
                        }

                        @Override
                        public void onFailure(Call<ProductResponse> call, Throwable t) {
                            hideProgressBar();
                            Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        }
                    });

    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_brand)
    RecyclerView rvBrand;

    @BindView(R.id.tv_cart)
    TextView tvCart;

    @OnClick(R.id.rl_cart)
    void rlCart(){
        Intent intent = new Intent(BrandListActivity.this, AllQuoteActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}
