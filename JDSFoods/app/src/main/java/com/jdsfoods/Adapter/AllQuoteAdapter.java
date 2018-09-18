package com.jdsfoods.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.jdsfoods.AllQuoteActivity;
import com.jdsfoods.R;
import com.jdsfoods.Response.AllQuoteResponse;
import com.jdsfoods.Response.ForgetResponse;
import com.jdsfoods.Response.QuoteModel;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;
import com.jdsfoods.Utilities.QuantityView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dikhong on 01-08-2018.
 */

public class AllQuoteAdapter extends RecyclerSwipeAdapter<AllQuoteAdapter.ViewHolder> {
    private List<AllQuoteResponse.QuoteItem> quoteResponses;
    private Context context;
    double unitPrice;
    String imagePath;
    int countItem = 0, countCarton = 0;
    ArrayList<QuoteModel> quoteModels = new ArrayList<>();
    OnItemClick mCallback;
    String finalPrice, calPrice, noDiscount, withDiscount, subDis;

    public AllQuoteAdapter(Context context, List<AllQuoteResponse.QuoteItem> quoteResponses, String imagePath, OnItemClick listener) {
        this.quoteResponses = quoteResponses;
        this.context = context;
        this.imagePath = imagePath;
        this.mCallback = listener;
    }

    @Override
    public AllQuoteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quote_items, viewGroup, false);
        return new AllQuoteAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final AllQuoteAdapter.ViewHolder viewHolder, final int i) {
        final AllQuoteResponse.QuoteItem quoteResponse = quoteResponses.get(i);
        final QuoteModel quoteModel = new QuoteModel();
        viewHolder.tvName.setText(quoteResponse.getProductName());
        viewHolder.tvQuat.setText(quoteResponse.getNoOfItemsInPacking()+" items in each Carton" );
        Double calculatedValue = Double.parseDouble(quoteResponse.getSubtotal());
        int total=Integer.parseInt(quoteResponse.getSingleProductQuantity())+Integer.parseInt(quoteResponse.getPackingQuantity())*Integer.parseInt(quoteResponse.getNoOfItemsInPacking());
        viewHolder.tvItems.setText("Total Items: "+String.valueOf(total));
        calPrice = String.format("%.2f", calculatedValue);
        if (quoteResponse.getApprovedForApp()==null){
            viewHolder.tvPriceDown.setVisibility(View.GONE);
        }
        else if (quoteResponse.getApprovedForApp().equalsIgnoreCase("1")){
            viewHolder.tvPriceDown.setVisibility(View.VISIBLE);
            viewHolder.tvPriceDown.setText("REVIEWED");
            viewHolder.tvPriceDown.setTextColor(Color.parseColor("#00A284"));
        }
        if (Preferences.getInstance().getPriceVisible()==null || Preferences.getInstance().getPriceVisible().equalsIgnoreCase("0") ) {
            viewHolder.tvPriceDown.setVisibility(View.GONE);
            viewHolder.tvPrice.setVisibility(View.GONE);
        }
        else {
            viewHolder.tvPriceDown.setTextColor(Color.parseColor("#000000"));
            viewHolder.tvPrice.setVisibility(View.VISIBLE);
            if (quoteResponse.getPromoPrice() == null || quoteResponse.getPromoPrice().equals("")) {
                double price = Double.parseDouble(quoteResponse.getNetUnitPrice());
                finalPrice = String.format("%.2f", price);
                viewHolder.tvCut.setVisibility(View.GONE);
                viewHolder.tvPriceDown.setText("€ " + finalPrice);
                viewHolder.tvPrice.setText("€ " + calPrice);
            } else {
                double price = Double.parseDouble(quoteResponse.getNetUnitPrice());
                double discount = Double.parseDouble(quoteResponse.getNetUnitPrice()) - Double.parseDouble(quoteResponse.getPromoPrice());
                noDiscount = String.format("%.2f", price);
                withDiscount = String.format("%.2f", discount);
                viewHolder.tvCut.setVisibility(View.VISIBLE);
                viewHolder.tvCut.setText("€ " + noDiscount);
                viewHolder.tvPriceDown.setText("€ " + withDiscount);
                viewHolder.tvCut.setPaintFlags(viewHolder.tvCut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                Double subDiscount = Double.parseDouble(quoteResponse.getSubtotal());
                subDis = String.format("%.2f", subDiscount);
                viewHolder.tvPrice.setText("€ " + subDis);

            }
        }


        Picasso.with(context).load(imagePath + quoteResponse.getImage()).into(viewHolder.ivImage);
        viewHolder.qvCarton.setMinQuantity(0);
        viewHolder.qvItem.setMinQuantity(0);
        viewHolder.qvCarton.setQuantity(Integer.parseInt(quoteResponse.getPackingQuantity()));
        viewHolder.qvItem.setQuantity(Integer.parseInt(quoteResponse.getSingleProductQuantity()));
        quoteModel.setSingleProductQuantity(quoteResponse.getSingleProductQuantity());
        quoteModel.setPackingQuantity(quoteResponse.getPackingQuantity());
        quoteModel.setItemTax(quoteResponse.getItemTax());
        quoteModel.setNetUnitPrice(quoteResponse.getNetUnitPrice());
        quoteModel.setUnitPrice(quoteResponse.getUnitPrice());
        quoteModel.setItemTax(quoteResponse.getItemTax());
        quoteModel.setQuantity(String.valueOf(viewHolder.qvItem.getQuantity() + viewHolder.qvCarton.getQuantity() * Integer.parseInt(quoteResponse.getNoOfItemsInPacking())));
        quoteModel.setQuoteItemId(quoteResponse.getId());
        quoteModel.setSubtotal(quoteResponse.getSubtotal());
        viewHolder.qvCarton.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String TAG) {
                calPrice = "";
                viewHolder.qvCarton.setMinQuantity(0);
                if (TAG.equalsIgnoreCase("add")) {
                    if (Integer.parseInt(String.valueOf(viewHolder.qvItem.getQuantity())) == 0) {
                        viewHolder.qvCarton.setMinQuantity(0);
                    } else {
                        viewHolder.qvCarton.setMinQuantity(0);
                    }
                    double totalItems = Double.parseDouble(quoteResponse.getNoOfItemsInPacking());
                    double noOfItems = totalItems * Double.parseDouble(String.valueOf(newQuantity));
                    double singles = viewHolder.qvItem.getQuantity();
                    double allitems = singles + noOfItems;
                    Log.d("noOfItems", "noOfItems" + noOfItems);
                    if (quoteResponse.getPromoPrice() == null || quoteResponse.getPromoPrice().equals("")) {
                        unitPrice = Double.parseDouble(quoteResponse.getNetUnitPrice());
                    }
                    else {
                        unitPrice = Double.parseDouble(quoteResponse.getNetUnitPrice())-Double.parseDouble(quoteResponse.getPromoPrice());
                    }
                    double calculatedValue = unitPrice * allitems;
                    calPrice = String.format("%.2f", calculatedValue);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(calPrice);
                    if (quoteResponse.getBlockQuantity()==null || quoteResponse.getBlockQuantity().equalsIgnoreCase("0")){
                        if (allitems < Double.parseDouble(quoteResponse.getStockLeft())) {
                            editQuote(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                        } else {
                            viewHolder.qvCarton.setQuantity(oldQuantity);
                            openDialog(context);
                        }
                    }
                    else {
                        if (allitems < Double.parseDouble(quoteResponse.getStockLeft()) && allitems <= Double.parseDouble(quoteResponse.getBlockQuantity())) {
                            editQuote(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                        } else {
                            viewHolder.qvCarton.setQuantity(oldQuantity);
                            openDialog(context);
                        }
                    }
                }
                if (TAG.equalsIgnoreCase("sub")) {
                    if (Integer.parseInt(String.valueOf(viewHolder.qvItem.getQuantity())) == 0) {
                        viewHolder.qvCarton.setMinQuantity(1);
                    } else {
                        viewHolder.qvCarton.setMinQuantity(0);
                    }
                    double totalItems = Double.parseDouble(quoteResponse.getNoOfItemsInPacking());
                    double noOfItems = totalItems * Double.parseDouble(String.valueOf(newQuantity));
                    double singles = viewHolder.qvItem.getQuantity();
                    double allitems = singles + noOfItems;
                    Log.d("noOfItems", "noOfItems" + allitems);
                    if (quoteResponse.getPromoPrice() == null || quoteResponse.getPromoPrice().equals("")) {
                        unitPrice = Double.parseDouble(quoteResponse.getNetUnitPrice());
                    }
                    else {
                        unitPrice = Double.parseDouble(quoteResponse.getNetUnitPrice())-Double.parseDouble(quoteResponse.getPromoPrice());
                    }
                    double calculatedValue = unitPrice * allitems;
                    calPrice = String.format("%.2f", calculatedValue);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(calPrice);
                        editQuote(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                }

                quoteModel.setSubtotal(calPrice);
            }

            @Override
            public void onLimitReached() {

            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                removeQuote(quoteResponse.getId(), i);
                mItemManger.closeAllItems();

            }
        });
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));


        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });





        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, i);

        viewHolder.qvItem.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String tag) {
                calPrice = "";
                viewHolder.qvItem.setMinQuantity(0);
                if (tag.equalsIgnoreCase("add")) {
                    if (Integer.parseInt(String.valueOf(viewHolder.qvCarton.getQuantity())) == 0) {
                        viewHolder.qvItem.setMinQuantity(0);
                    } else {
                        viewHolder.qvItem.setMinQuantity(0);
                    }
                    if (quoteResponse.getPromoPrice() == null || quoteResponse.getPromoPrice().equals("")) {
                        unitPrice = Double.parseDouble(quoteResponse.getNetUnitPrice());
                    }
                    else {
                        unitPrice = Double.parseDouble(quoteResponse.getNetUnitPrice())-Double.parseDouble(quoteResponse.getPromoPrice());
                    }
                    double cartonQuant = Double.parseDouble(String.valueOf(viewHolder.qvCarton.getQuantity()));
                    double noInCarton = Double.parseDouble(quoteResponse.getNoOfItemsInPacking());
                    double totalCartonItems = cartonQuant * noInCarton;
                    double overAll = totalCartonItems + Double.parseDouble(String.valueOf(newQuantity));
                    if (quoteResponse.getBlockQuantity()==null || quoteResponse.getBlockQuantity().equalsIgnoreCase("0")) {
                        if (overAll < Double.parseDouble(quoteResponse.getStockLeft())) {
                            double totalPrice = unitPrice * overAll;
                            calPrice = String.format("%.2f", totalPrice);
                            viewHolder.tvPrice.setText("€ " + calPrice);
                            mCallback.onClick(calPrice);
                            editQuote(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                        } else {
                            double totalPrice = unitPrice * overAll;
                            calPrice = String.format("%.2f", totalPrice);
                            viewHolder.tvPrice.setText("€ " + calPrice);
                            mCallback.onClick(calPrice);
                            viewHolder.qvItem.setQuantity(oldQuantity);
                            openDialog(context);
                        }
                    }
                    else {
                        if (overAll < Double.parseDouble(quoteResponse.getStockLeft()) && overAll <= Double.parseDouble(quoteResponse.getBlockQuantity())) {
                            double totalPrice = unitPrice * overAll;
                            calPrice = String.format("%.2f", totalPrice);
                            viewHolder.tvPrice.setText("€ " + calPrice);
                            mCallback.onClick(calPrice);
                            editQuote(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                        } else {
                            double totalPrice = unitPrice * overAll;
                            calPrice = String.format("%.2f", totalPrice);
                            viewHolder.tvPrice.setText("€ " + calPrice);
                            mCallback.onClick(calPrice);
                            viewHolder.qvItem.setQuantity(oldQuantity);
                            openDialog(context);
                        }
                    }
                }
                if (tag.equalsIgnoreCase("sub")) {
                    if (Integer.parseInt(String.valueOf(viewHolder.qvCarton.getQuantity())) == 0) {
                        viewHolder.qvItem.setMinQuantity(0);
                    } else {
                        viewHolder.qvItem.setMinQuantity(0);
                    }
                    if (quoteResponse.getPromoPrice() == null || quoteResponse.getPromoPrice().equals("")) {
                        unitPrice = Double.parseDouble(quoteResponse.getNetUnitPrice());
                    }
                    else {
                        unitPrice = Double.parseDouble(quoteResponse.getNetUnitPrice())-Double.parseDouble(quoteResponse.getPromoPrice());
                    }
                    double cartonQuant = Double.parseDouble(String.valueOf(viewHolder.qvCarton.getQuantity()));
                    double noInCarton = Double.parseDouble(quoteResponse.getNoOfItemsInPacking());
                    double totalCartonItems = cartonQuant * noInCarton;
                    double overAll = totalCartonItems + Double.parseDouble(String.valueOf(newQuantity));
                    double totalPrice = unitPrice * overAll;
                    calPrice = String.format("%.2f", totalPrice);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(calPrice);
                    editQuote(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                }
            }

            @Override
            public void onLimitReached() {

            }
        });
        quoteModels.add(quoteModel);
        mCallback.sendList(quoteModels);
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
    private void removeQuote(String id, final int i) {
        final JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.deleteQuote(id, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    removeAt(i);
                    ((AllQuoteActivity)context).getQuoteItems();
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
    private void editQuote(String id,String unitPrice,String single,String carton,String quantity,String subTotal) {
        final JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.editQuote(id,unitPrice,single,carton,quantity,subTotal, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    ((AllQuoteActivity)context).getQuoteItems();
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
        return quoteResponses.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvQuat, tvPrice, tvPriceDown, tvCut, tvItems;
        ImageView ivImage;
        SwipeLayout swipeLayout;
        RelativeLayout bottomwrapper;
        LinearLayout delete;
        QuantityView qvCarton, qvItem;

        public ViewHolder(View view) {
            super(view);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            bottomwrapper=(RelativeLayout)itemView.findViewById(R.id.bottom_wrapper);
            delete=(LinearLayout)bottomwrapper.findViewById(R.id.ll_delete);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvQuat = (TextView) view.findViewById(R.id.tv_quat);
            tvPriceDown = (TextView) view.findViewById(R.id.tv_price_down);
            tvCut = (TextView) view.findViewById(R.id.tv_promo);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            qvItem = (QuantityView) view.findViewById(R.id.qv_item);
            qvCarton = (QuantityView) view.findViewById(R.id.qv_carton);
            tvItems = (TextView) view.findViewById(R.id.tv_items);


        }
    }

    public interface OnItemClick {
        void onClick(String value);
        void sendList(ArrayList<QuoteModel> quoteModels);
    }

    public void removeAt(int position) {
        quoteResponses.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, quoteResponses.size());
    }


}


