package com.jdsfoods;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Adapter.SliderPagerAdapter;
import com.jdsfoods.Response.AllQuoteResponse;
import com.jdsfoods.Response.ForgetResponse;
import com.jdsfoods.Response.ProDetailResponse;
import com.jdsfoods.Response.ProductResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;
import com.jdsfoods.Utilities.QuantityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private int dotsCount = 0;
    private ImageView[] dots;
    String finalPrice1,userConfirm;
    String quoteItemId, quoteId,productId,productCode,productName,productPrice,proTaxRate;
    double noPacking=0;
    double productLimit=0;
    double stockLeft=0;
    double proSingleTax;
    private SliderPagerAdapter sliderPagerAdapter;
    public static final String PRODUCT = "product";
    List<String> productIds = new ArrayList<>();
    ProDetailResponse.ProDetail proDetail;

    ProductResponse.Product product;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setTextSize(18f);
        title.setText("Product Detail");
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

        product = (ProductResponse.Product) getIntent().getParcelableExtra(PRODUCT);
        viewPager.setOnPageChangeListener(this);
        if (product != null) {
            getAllQuote(product.getProductId());
            getProduct(product.getProductId());
        }
        else {
            getAllQuote(getIntent().getStringExtra("productId"));
            getProduct(getIntent().getStringExtra("productId"));
        }

        qvItem.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String tag1) {
                if (tag1.equalsIgnoreCase("add")) {
                    double total = Double.parseDouble(String.valueOf(newQuantity)) + Double.parseDouble(String.valueOf(qvCarton.getQuantity())) * noPacking;
                    double totalPrice = total * Double.parseDouble(finalPrice1);
                    tvTprice.setText("€ " + String.format("%.2f", totalPrice));
                    tvTotal.setText("Total Items: " + String.valueOf(total).substring(0, String.valueOf(total).lastIndexOf(".")));
                    if (productLimit==0)
                    {
                        if (total<stockLeft) {
                        }
                        else {
                            qvItem.setQuantity(oldQuantity);
                            openDialog(ProductDetailActivity.this);
                        }
                        }
                        else {
                        if (total<stockLeft && total<=productLimit) {

                        }
                        else {
                            qvItem.setQuantity(oldQuantity);
                            openDialog(ProductDetailActivity.this);
                        }
                    }

                }
                if (tag1.equalsIgnoreCase("sub")) {
                    double total = Double.parseDouble(String.valueOf(newQuantity)) + Double.parseDouble(String.valueOf(qvCarton.getQuantity())) * noPacking;
                    double totalPrice = total * Double.parseDouble(finalPrice1);
                    tvTprice.setText("€ " + String.format("%.2f", totalPrice));
                    tvTotal.setText("Total Items: " + String.valueOf(total).substring(0, String.valueOf(total).lastIndexOf(".")));
                }
            }

            @Override
            public void onLimitReached() {

            }
        });

        qvCarton.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String tag1) {
                if (tag1.equalsIgnoreCase("add")) {
                    double total = Double.parseDouble(String.valueOf(qvItem.getQuantity())) + Double.parseDouble(String.valueOf(newQuantity)) * noPacking;
                    double totalPrice = total * Double.parseDouble(finalPrice1);
                    tvTprice.setText("€ " + String.format("%.2f", totalPrice));
                    tvTotal.setText("Total Items: " + String.valueOf(total).substring(0, String.valueOf(total).lastIndexOf(".")));
                    if (productLimit==0)
                    {
                        if (total<stockLeft) {
                        }
                        else {
                            qvCarton.setQuantity(oldQuantity);
                            openDialog(ProductDetailActivity.this);
                        }
                    }
                    else {
                        if (total<stockLeft && total<=productLimit) {

                        }
                        else {
                            qvCarton.setQuantity(oldQuantity);
                            openDialog(ProductDetailActivity.this);
                        }
                    }
                }
                if (tag1.equalsIgnoreCase("sub")) {
                    double total = qvItem.getQuantity() + newQuantity * noPacking;
                    double totalPrice = total * Double.parseDouble(finalPrice1);
                    tvTprice.setText("€ " + String.format("%.2f", totalPrice));
                    tvTotal.setText("Total Items: " + String.valueOf(total).substring(0, String.valueOf(total).lastIndexOf(".")));
                }
            }

            @Override
            public void onLimitReached() {

            }
        });
    }

    private void openDialog(Context context) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Sorry, You can't add more items.");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        Button pbutton = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.parseColor("#00A284"));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getProduct(String productId) {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait...");
        jdsFoodService.productDetail(productId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ProDetailResponse>() {
                    @Override
                    public void onResponse(Call<ProDetailResponse> call, Response<ProDetailResponse> response) {
                        if (response.body().getFlag() == 1) {
                            dotsCount = response.body().getProDetail().getProductPhotos().size();
                            sliderPagerAdapter = new SliderPagerAdapter(ProductDetailActivity.this, response.body().getProDetail().getProductPhotos(), response.body().getImagePath());
                            viewPager.setAdapter(sliderPagerAdapter);
                            viewPager.setCurrentItem(0);
                            setPageViewIndicator(dotsCount);
                            setFields(response.body().getProDetail());
                        }
                        hideProgressBar();
                    }


                    @Override
                    public void onFailure(Call<ProDetailResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setFields(ProDetailResponse.ProDetail proDetail) {
        productId=proDetail.getId();
        productCode=proDetail.getCode();
        productName=proDetail.getName();
        productPrice=proDetail.getPrice();
        proSingleTax=proDetail.getSingleProductTax();
        proTaxRate=proDetail.getTaxRate();
        finalPrice1 = String.format("%.2f", Double.parseDouble(proDetail.getPrice()));
        noPacking = Double.parseDouble(proDetail.getNoOfItemsInPacking());
        if (proDetail.getBlockQuantity()==null){

        }
        else {
            productLimit = Double.parseDouble(proDetail.getBlockQuantity());
        }
        if (proDetail.getStockLeft()==null) {

        }
        else {
            stockLeft = Double.parseDouble(proDetail.getStockLeft());
        }
        if (Preferences.getInstance().getPriceVisible()==null || Preferences.getInstance().getPriceVisible().equalsIgnoreCase("0"))
        {
            tvPrice.setVisibility(View.INVISIBLE);
            tvTprice.setVisibility(View.INVISIBLE);
        }
        else {
            tvPrice.setVisibility(View.VISIBLE);
            tvTprice.setVisibility(View.VISIBLE);
        }
        if (proDetail.getPromoPrice() == null || proDetail.getPromoPrice().equals("")) {
            tvPrice.setText("€ " + finalPrice1);
        } else {
            double discount = Double.parseDouble(proDetail.getPrice()) - Double.parseDouble(proDetail.getPromoPrice());
            tvPrice.setText(String.valueOf(discount));
            tvPrice.setVisibility(View.GONE);
        }

        if (proDetail.getStockLeft().equalsIgnoreCase("0.0000")||proDetail.getStockLeft()==null)
        {
            tvTprice.setVisibility(View.VISIBLE);
            tvTprice.setTextSize(11f);
            rlQuantity.setVisibility(View.GONE);
            tvTprice.setText("Out of Stock");
            tvTprice.setTextColor(getResources().getColor(R.color.red));
            tvTotal.setVisibility(View.INVISIBLE);
        }
        else {
            tvTprice.setTextSize(16f);
            tvTprice.setTextColor(getResources().getColor(R.color.colorBlack));
            rlQuantity.setVisibility(View.VISIBLE);
            tvTotal.setVisibility(View.VISIBLE);
            double total = Double.parseDouble(String.valueOf(qvItem.getQuantity())) + Double.parseDouble(String.valueOf(qvCarton.getQuantity())) * Double.parseDouble(proDetail.getNoOfItemsInPacking());
            tvTotal.setText("Total Items: " + String.valueOf(total).substring(0, String.valueOf(total).lastIndexOf(".")));
            double totalPrice = total * Double.parseDouble(proDetail.getPrice());
            tvTprice.setText("€ " + String.format("%.2f", totalPrice));
        }

        tvItems.setText(proDetail.getNoOfItemsInPacking() + " items in each carton.");
        tvName.setText(proDetail.getName());

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void editQuote(String id, String unitPrice,String single,String carton, String quantity, String subTotal) {
        final JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait....");
        jdsFoodService.editQuote(id, unitPrice,single,carton, quantity, subTotal, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), AllQuoteActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
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
    private void addQuote() {
        final JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait....");
        if (Preferences.getInstance().getPriceVisible().equalsIgnoreCase("0"))
        {
            userConfirm="0";
        }
        else {
            userConfirm="1";
        }
        if (Preferences.getInstance().getQuoteId().equalsIgnoreCase("0")) {
            quoteId = "";
        } else {
            quoteId = Preferences.getInstance().getQuoteId();
        }
//        String.valueOf(qvItem.getQuantity() + qvCarton.getQuantity() * noPacking)
        jdsSaleService.addQuote(Preferences.getInstance().getUserId(), Preferences.getInstance().getUserName(), productId, productCode, productName, productPrice,"0" , String.valueOf(qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity()), tvTprice.getText().toString(), tvTprice.getText().toString(), String.valueOf(proSingleTax * qvItem.getQuantity() + qvCarton.getQuantity() * noPacking), productId, String.valueOf(proSingleTax), proTaxRate, productCode, quoteId, userConfirm, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body() != null) {
                    if (response.body().getFlag() == 1) {
                        quoteId = response.body().getQuoteId();
                        Toast.makeText(ProductDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AllQuoteActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(ProductDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<ForgetResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(ProductDetailActivity.this, JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllQuote(final String productId) {
        JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.getAllQuote(Preferences.getInstance().getQuoteId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            String items = String.valueOf(response.body().getQuoteItemList().size());
                            if (items.equalsIgnoreCase("0"))
                            {
                                rlCart.setVisibility(View.GONE);
                            }
                            else {
                                rlCart.setVisibility(View.VISIBLE);
                                tvCart.setText("ITEMS (" + items + ")");
                            }
                            for (int i = 0; i < response.body().getQuoteItemList().size(); i++) {
                                productIds.add(response.body().getQuoteItemList().get(i).getProductId());
                            }
                            matchItem(productIds, response.body().getQuoteItemList(), productId);
                        } else {
                            rlCart.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void matchItem(List<String> productIds, List<AllQuoteResponse.QuoteItem> quoteItemList, String productId) {
        if (productIds.contains(productId)) {
            tvAdd.setText(" EDIT ");
            for (int i = 0; i < quoteItemList.size(); i++) {
                if (quoteItemList.get(i).getProductId().equalsIgnoreCase(productId)) {
                    quoteItemId = quoteItemList.get(i).getId();
                    Log.d("quoteItemId", "quoteItemId" + quoteItemId);
                    qvCarton.setQuantity(Integer.parseInt(quoteItemList.get(i).getPackingQuantity()));
                    qvItem.setQuantity(Integer.parseInt(quoteItemList.get(i).getSingleProductQuantity()));
                }
            }

        } else {
            tvAdd.setText("  ADD  ");
        }
    }


    private void setPageViewIndicator(int dotsCount) {
        if (dotsCount == 1) {
            viewPagerCountDots.setVisibility(View.INVISIBLE);
        } else {
            dots = new ImageView[dotsCount];
            for (int i = 0; i < dotsCount; i++) {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(4, 0, 4, 0);
                final int presentPosition = i;
                dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        viewPager.setCurrentItem(presentPosition);
                        return true;
                    }

                });
                viewPagerCountDots.addView(dots[i], params);
            }
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if (position + 1 == dotsCount) {

        } else {

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_items)
    TextView tvItems;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.tv_tprice)
    TextView tvTprice;

    @BindView(R.id.tv_add)
    TextView tvAdd;

    @BindView(R.id.tv_cart)
    TextView tvCart;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rl_quantity)
    RelativeLayout rlQuantity;

    @BindView(R.id.qv_item)
    QuantityView qvItem;

    @BindView(R.id.qv_carton)
    QuantityView qvCarton;

    @BindView(R.id.rl_cart)
    RelativeLayout rlCart;

    @OnClick(R.id.rl_cart)
    void rlCart() {
        Intent intent = new Intent(getApplicationContext(), AllQuoteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.tv_add)
    void add() {
        if (qvItem.getQuantity()==0 && qvCarton.getQuantity()==0) {
            Toast.makeText(getApplicationContext(), "Please add the quantity", Toast.LENGTH_SHORT).show();
        }
        else {
            if (tvAdd.getText().toString().equalsIgnoreCase(" Edit ")) {
                editQuote(quoteItemId, finalPrice1, String.valueOf(qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity()), String.valueOf(qvItem.getQuantity() + qvCarton.getQuantity() * noPacking), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity() * noPacking * Double.parseDouble(finalPrice1)));
            } else {
                addQuote();
            }
        }
    }

}