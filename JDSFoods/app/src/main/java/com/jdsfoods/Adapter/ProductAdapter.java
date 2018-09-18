package com.jdsfoods.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.BrandListActivity;
import com.jdsfoods.ProductDetailActivity;
import com.jdsfoods.R;
import com.jdsfoods.Response.AllQuoteResponse;
import com.jdsfoods.Response.ForgetResponse;
import com.jdsfoods.Response.ProductResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.SubCategoryActivity;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dikhong on 01-08-2018.
 */

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductResponse.Product> productsModels;
    private ArrayList<ProductResponse.Product> arraylist;
    List<String> productIds=new ArrayList<>();
    private Context context;
    String imagePath,tag;
    String finalPrice, calPrice, finalPrice1;
    String quoteId,userConfirm;

    public ProductAdapter(Context context, List<ProductResponse.Product> productsModels, String imagePath,String tag) {
        this.productsModels = productsModels;
        this.context = context;
        this.imagePath = imagePath;
        this.tag=tag;
        this.arraylist = new ArrayList<ProductResponse.Product>();
        this.arraylist.addAll(productsModels);
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list, viewGroup, false);
        return new ProductAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ProductAdapter.ViewHolder viewHolder, final int i) {
        final ProductResponse.Product product = productsModels.get(i);
        getAllQuote(product.getProductId(), viewHolder);
        viewHolder.tvName.setText(product.getProductName());
        viewHolder.tvStock.setText(product.getNoOfItemsInPacking()+" items in each carton");
        final double price = Double.parseDouble(product.getPrice());
        finalPrice = String.format("%.2f", price);
        if (Preferences.getInstance().getPriceVisible()==null|| Preferences.getInstance().getPriceVisible().equalsIgnoreCase("0")) {
            viewHolder.tvPriceDown.setVisibility(View.GONE);
        }
        else {
            viewHolder.tvPriceDown.setVisibility(View.VISIBLE);
            if (product.getPromoPrice() == null || product.getPromoPrice().equalsIgnoreCase("")) {
                viewHolder.tvCut.setVisibility(View.GONE);
                viewHolder.tvPriceDown.setText("€ " + finalPrice);
            } else {
                double discount = Double.parseDouble(product.getPrice()) - Double.parseDouble(product.getPromoPrice());
                finalPrice1 = String.format("%.2f", discount);
                viewHolder.tvCut.setVisibility(View.VISIBLE);
                viewHolder.tvPriceDown.setText("€ " + finalPrice1);
                viewHolder.tvCut.setText("€ " + finalPrice);
                viewHolder.tvCut.setPaintFlags(viewHolder.tvCut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

        }

        if (product.getStockLeft().equalsIgnoreCase("0.0000")||product.getStockLeft()==null)
        {
            viewHolder.tvItems.setVisibility(View.GONE);
            viewHolder.rlQuantity.setVisibility(View.GONE);
            viewHolder.tvOut.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.tvOut.setVisibility(View.GONE);
            viewHolder.rlQuantity.setVisibility(View.VISIBLE);
            viewHolder.tvItems.setVisibility(View.VISIBLE);

        }

        Picasso.with(context).load(imagePath + product.getImage()).into(viewHolder.ivImage);
        viewHolder.qvCarton.setMinQuantity(0);
        viewHolder.qvItem.setMinQuantity(0);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag.equalsIgnoreCase("brand")) {
                    Activity activity = (Activity) context;
                    ProductResponse.Product product1 = (ProductResponse.Product) productsModels.get(i);
                    Intent intent = new Intent(activity, ProductDetailActivity.class);
                    intent.putExtra(ProductDetailActivity.PRODUCT, product1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                else {
                    Activity activity = (Activity) context;
                    ProductResponse.Product product1 = (ProductResponse.Product) productsModels.get(i);
                    Intent intent = new Intent(activity, ProductDetailActivity.class);
                    intent.putExtra(ProductDetailActivity.PRODUCT, product1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            }
        });
//        viewHolder.qvCarton.setQuantity(Integer.parseInt(product.getAlertQuantity()));
//        viewHolder.qvItem.setQuantity(Integer.parseInt(product.getAlertQuantity()));
        viewHolder.qvCarton.setOnQuantityChangeListener(new com.jdsfoods.Utilities.QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String TAG) {
                calPrice = "";
                viewHolder.qvCarton.setMinQuantity(0);
                if (TAG.equalsIgnoreCase("add")) {
                    double total=Double.parseDouble(String.valueOf(viewHolder.qvItem.getQuantity()))+Double.parseDouble(String.valueOf(newQuantity))*Double.parseDouble(product.getNoOfItemsInPacking());
                    if (product.getBlockQuantity()==null|| product.getBlockQuantity().equalsIgnoreCase("0")) {
                        if (total < Double.parseDouble(product.getStockLeft())) {
                            matchProduct(product, viewHolder, product.getProductId(), total);
                        } else {
                            viewHolder.qvCarton.setQuantity(oldQuantity);
                            openDialog(context);
                            matchProduct(product, viewHolder, product.getProductId(), total);
                        }
                    }
                    else {
                        if (total < Double.parseDouble(product.getStockLeft()) && total <= Double.parseDouble(product.getBlockQuantity())) {
                            matchProduct(product, viewHolder, product.getProductId(), total);
                        } else {
                            viewHolder.qvCarton.setQuantity(oldQuantity);
                            openDialog(context);
                            matchProduct(product, viewHolder, product.getProductId(), total);
                        }
                    }
                }
                if (TAG.equalsIgnoreCase("sub")) {
                    if (viewHolder.qvCarton.getQuantity()==0 && viewHolder.qvItem.getQuantity()==0)
                    {
                        removeQuote(viewHolder.tvQuoteId.getText().toString(), viewHolder);
                    }
                    else {
                            editQuote(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                    }
                }

            }

            @Override
            public void onLimitReached() {

            }
        });

        viewHolder.qvItem.setOnQuantityChangeListener(new com.jdsfoods.Utilities.QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String tag) {
                calPrice = "";
                viewHolder.qvItem.setMinQuantity(0);
                if (tag.equalsIgnoreCase("add")) {
                    double total=Double.parseDouble(String.valueOf(newQuantity))+Double.parseDouble(String.valueOf(viewHolder.qvCarton.getQuantity()))*Double.parseDouble(product.getNoOfItemsInPacking());
                    if (product.getBlockQuantity()==null|| product.getBlockQuantity().equalsIgnoreCase("0")) {
                        if (total < Double.parseDouble(product.getStockLeft())) {
                            matchProduct(product, viewHolder, product.getProductId(), total);
                        } else {
                            viewHolder.qvItem.setQuantity(oldQuantity);
                            openDialog(context);
                            matchProduct(product, viewHolder, product.getProductId(), total);
                        }
                    }
                    else {
                        if (total < Double.parseDouble(product.getStockLeft()) && total <= Double.parseDouble(product.getBlockQuantity())) {
                            matchProduct(product, viewHolder, product.getProductId(), total);
                        } else {
                            viewHolder.qvItem.setQuantity(oldQuantity);
                            openDialog(context);
                            matchProduct(product, viewHolder, product.getProductId(), total);
                        }
                    }
                }
                if (tag.equalsIgnoreCase("sub")) {
                    if (viewHolder.qvItem.getQuantity()==0 && viewHolder.qvCarton.getQuantity()==0) {
                        removeQuote(viewHolder.tvQuoteId.getText().toString(), viewHolder);

                    }
                    else {

                        editQuote(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                    }
                }
            }

            @Override
            public void onLimitReached() {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllQuote(final String productId, final ViewHolder viewHolder) {
        JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        jdsSaleService.getAllQuote(Preferences.getInstance().getQuoteId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            for (int i=0;i<response.body().getQuoteItemList().size();i++)
                                if (response.body().getQuoteItemList().get(i).getProductId().matches(productId)){
                                    int total=Integer.parseInt(response.body().getQuoteItemList().get(i).getSingleProductQuantity())+Integer.parseInt(response.body().getQuoteItemList().get(i).getPackingQuantity())*Integer.parseInt(response.body().getQuoteItemList().get(i).getNoOfItemsInPacking());
                                    viewHolder.tvItems.setText("Total Items: "+String.valueOf(total));
                                    viewHolder.tvQuoteId.setText(response.body().getQuoteItemList().get(i).getId());
                                    viewHolder.qvCarton.setQuantity(Integer.parseInt(response.body().getQuoteItemList().get(i).getPackingQuantity()));
                                    viewHolder.qvItem.setQuantity(Integer.parseInt(response.body().getQuoteItemList().get(i).getSingleProductQuantity()));
                                }

                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(context, JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void matchProduct(final ProductResponse.Product product, final ViewHolder viewHolder, final String productId, final double total) {
        JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        jdsSaleService.getAllQuote(Preferences.getInstance().getQuoteId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            for (int i = 0; i < response.body().getQuoteItemList().size(); i++) {
                                productIds.add(response.body().getQuoteItemList().get(i).getProductId());
                            }

                            if (productIds.contains(productId)) {
                                if (product.getBlockQuantity()==null|| product.getBlockQuantity().equalsIgnoreCase("0")) {
                                    if (total < Double.parseDouble(product.getStockLeft())) {
                                        editQuote(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                                    }
                                }
                                else {
                                    if (total < Double.parseDouble(product.getStockLeft()) && total <= Double.parseDouble(product.getBlockQuantity())) {
                                        editQuote(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                                    }
                                }
                            } else {
                                if (product.getBlockQuantity()==null|| product.getBlockQuantity().equalsIgnoreCase("0")) {
                                    if (total < Double.parseDouble(product.getStockLeft())) {
                                        addQuote(product, viewHolder,total);
                                    }
                                }
                                else {
                                    if (total < Double.parseDouble(product.getStockLeft()) && total <= Double.parseDouble(product.getBlockQuantity())) {
                                        addQuote(product, viewHolder,total);
                                    }
                                }
                                }
                        }else {
                            productIds.add("00");
                            if (productIds.contains(productId)) {
                                editQuote(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                            } else {
                                addQuote(product, viewHolder,total);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(context, JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
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
    private void editQuote(String id, String unitPrice, String single, String carton, String quantity, String subTotal) {
        final JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.editQuote(id, unitPrice, single, carton, quantity, subTotal, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<ForgetResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void removeQuote(String id, final ViewHolder viewHolder) {
        final JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.deleteQuote(id, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    quoteId = response.body().getQuoteId();
                    ((SubCategoryActivity)context).getAllQuote(quoteId);
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<ForgetResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addQuote(final ProductResponse.Product product, final ViewHolder viewHolder,final double total) {
        final JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        if (Preferences.getInstance().getPriceVisible().equalsIgnoreCase("0"))
        {
            userConfirm="0";
        }
        else {
            userConfirm="1";
        }
        if (Preferences.getInstance().getQuoteId().equalsIgnoreCase("0"))
        {
            quoteId="";
        }
        else {
            quoteId=Preferences.getInstance().getQuoteId();
        }
        jdsSaleService.addQuote(Preferences.getInstance().getUserId(),Preferences.getInstance().getUserName(),product.getProductId(), product.getProductCode(), product.getProductName(), product.getPrice(), "0", "1", "", String.valueOf(total*Double.parseDouble(product.getPrice())),String.valueOf(total*Double.parseDouble(product.getPrice())),String.valueOf(total*product.getSingleProductTax()),product.getProductId(), String.valueOf(product.getSingleProductTax()),product.getTaxRate(),product.getProductCode(),quoteId,userConfirm, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body() != null) {
                    if (response.body().getFlag() == 1) {
                        quoteId = response.body().getQuoteId();
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        getAllQuote(product.getProductId(), viewHolder);
                        if (tag.equalsIgnoreCase("category")) {
                            ((SubCategoryActivity) context).getAllQuote(quoteId);
                        }
                        else {
                            ((BrandListActivity) context).getAllQuote(quoteId);
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<ForgetResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }





    @Override
    public int getItemCount() {
        return productsModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvStock, tvPriceDown,tvOut, tvCut,tvQuoteId,tvItems;
        ImageView ivImage;
        RelativeLayout rlQuantity;
        com.jdsfoods.Utilities.QuantityView qvCarton, qvItem;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvStock = (TextView) view.findViewById(R.id.tv_stock);
            tvPriceDown = (TextView) view.findViewById(R.id.tv_price_down);
            rlQuantity = (RelativeLayout) view.findViewById(R.id.rl_quantity);
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            qvItem = (com.jdsfoods.Utilities.QuantityView) view.findViewById(R.id.qv_item);
            qvCarton = (com.jdsfoods.Utilities.QuantityView) view.findViewById(R.id.qv_carton);
            tvCut = (TextView) view.findViewById(R.id.tv_price_cut);
            tvQuoteId=(TextView) view.findViewById(R.id.tv_quote_id);
            tvItems=(TextView) view.findViewById(R.id.tv_items);
            tvOut=(TextView) view.findViewById(R.id.tv_out);


        }
    }

}


