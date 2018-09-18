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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Adapter.ProductAdapter;
import com.jdsfoods.Adapter.SubCateAdapter;
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

public class SubCategoryActivity extends BaseActivity implements SubCateAdapter.OnItemClick {

    public static final String SHOP = "shop";
    ShopResponse.ShopData shopData;
    String parentId;
    ProductAdapter productAdapter;
    String items;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        ButterKnife.bind(this);
        getAllQuote(Preferences.getInstance().getQuoteId());
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setTextSize(18f);
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
        shopData = (ShopResponse.ShopData) getIntent().getParcelableExtra(SHOP);
        setRecycle();
        if (shopData != null) {
            parentId = shopData.getId();
            if (shopData.getChildCategoryCount().equalsIgnoreCase("0")) {
                rvSub.setVisibility(View.GONE);
                getOnlyProduct(shopData.getId());
                title.setText(shopData.getName());
            } else {
                rvSub.setVisibility(View.VISIBLE);
                getSubCategory(shopData.getId());
                title.setText(shopData.getName());
            }
        }
        else {
           parentId= getIntent().getStringExtra("categoryId");
            if (getIntent().getStringExtra("childCount").equalsIgnoreCase("0")) {
                rvSub.setVisibility(View.GONE);
                getOnlyProduct(parentId);
                title.setText(getIntent().getStringExtra("categoryName"));
            } else {
                rvSub.setVisibility(View.VISIBLE);
                getSubCategory(parentId);
                title.setText(getIntent().getStringExtra("categoryName"));
            }

        }

    }

    private void setRecycle() {
        rvSub.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getSubCategory(String parentId) {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient2().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait...");
        jdsFoodService.getSubCat(parentId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ShopResponse>() {
                    @Override
                    public void onResponse(Call<ShopResponse> call, Response<ShopResponse> response) {
                        if (response.body().getFlag() == 1) {
                            getProucts(response.body().getShopDataList().get(0).getId());
                            SubCateAdapter subCateAdapter = new SubCateAdapter(SubCategoryActivity.this, response.body().getShopDataList(), SubCategoryActivity.this);
                            rvSub.setAdapter(subCateAdapter);
                        } else {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ShopResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getProucts(String subCatId) {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient2().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait...");
        jdsFoodService.getProducts("", subCatId, "", Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                        if (response.body().getFlag() == 1) {
                            productAdapter=new ProductAdapter(SubCategoryActivity.this,response.body().getProductsList(),response.body().getImagePath(),"category");
                            rvItems.setAdapter(productAdapter);
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getOnlyProduct(String catId) {
        final JDSFoodService jdsFoodService = RestClient.getInstance().getClient2().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait...");
        jdsFoodService.getProducts(catId, "", "", Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                        if (response.body().getFlag() == 1) {
                            productAdapter = new ProductAdapter(SubCategoryActivity.this, response.body().getProductsList(), response.body().getImagePath(),"category");
                            rvItems.setAdapter(productAdapter);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getAllQuote(final String quoteId) {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        jdsFoodService.getAllQuote(quoteId,Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag()==1){
                            if (!quoteId.equalsIgnoreCase("0"))
                            {
                                Preferences.getInstance().clearQuote();
                                Preferences.getInstance().setQuoteId(quoteId);
                            }
                            else {
                                Preferences.getInstance().setQuoteId("0");
                            }
                            items= String.valueOf(response.body().getQuoteItemList().size());
                            Log.d("hhenaaaaa","items"+items);
                            if (items.equalsIgnoreCase("0")){
                                rlCart.setVisibility(View.GONE);
                                tvCart.setText("ITEMS (" + "0" + ")");

                            }
                            else {
                                rlCart.setVisibility(View.VISIBLE);
                                tvCart.setText("ITEMS (" + items + ")");

                            }
                        }
                        else
                        {
                            rlCart.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }




    @BindView(R.id.rv_sub)
    RecyclerView rvSub;

    @BindView(R.id.rv_items)
    RecyclerView rvItems;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_cart)
    TextView tvCart;

    @BindView(R.id.rl_cart)
    RelativeLayout rlCart;

    @OnClick(R.id.rl_cart)
    void rlCart(){
        Intent intent = new Intent(SubCategoryActivity.this, AllQuoteActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(String value) {
        getProucts(value);
    }
}
